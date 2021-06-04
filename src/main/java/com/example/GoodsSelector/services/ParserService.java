package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.Category;
import com.example.GoodsSelector.entities.Characteristic;
import com.example.GoodsSelector.entities.Product;
import com.example.GoodsSelector.entities.ProductType;
import com.example.GoodsSelector.repositories.ICategoryRepository;
import com.example.GoodsSelector.repositories.ICharacteristicRepository;
import com.example.GoodsSelector.repositories.IProductRepository;
import com.example.GoodsSelector.repositories.IProductTypeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService implements IParserService {
    @Autowired
    private final ICategoryRepository categoryRepository;
    @Autowired
    private final IProductTypeRepository productTypeRepository;
    @Autowired
    private final IProductRepository productRepository;
    @Autowired
    private final ICharacteristicRepository characteristicRepository;

    public ParserService(ICategoryRepository categoryRepository, IProductTypeRepository productTypeRepository, IProductRepository productRepository, ICharacteristicRepository characteristicRepository){
        this.categoryRepository = categoryRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.characteristicRepository = characteristicRepository;
    }

    @Override
    public void parse(String fname, int rowsCount, int cIndex, int pTypeIndex, int pTypeResIndex, int pIndex, int priceIndex, int brandIndex, int characteristicsIndex) {
        Workbook workbook = new XSSFWorkbook();
        try {
            FileInputStream file = new FileInputStream(new File("./data/" + fname));
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(0);

        List<Category> categories = categoryRepository.findAll();

        for (int i = 1; i < rowsCount; i++) {
            Row row = sheet.getRow(i);
            if(row == null) continue;
            if(row.getCell(0) == null) continue;

            String newCategory = row.getCell(cIndex).getStringCellValue();
            var category = getCategory(categories, newCategory);
            if (category == null) {
                var c = new Category();
                c.setName(newCategory);
                category = categoryRepository.save(c);
                categories.add(category);
            }

            String newProductType = row.getCell(pTypeIndex).getStringCellValue();
            if (newProductType.equals("")) {
                newProductType = row.getCell(pTypeResIndex).getStringCellValue();
            }
            ProductType productType = null;
            if (category.getProductTypes() != null) {
                productType = getProductType(category.getProductTypes(), newProductType);
            }
            if (productType == null) {
                var pt = new ProductType();
                pt.setName(newProductType);
                pt.setCategoryId(category.getId());
                productType = productTypeRepository.save(pt);
                List<ProductType> ptList = new ArrayList<>();
                if (category.getProductTypes() != null) {
                    ptList = category.getProductTypes();
                }
                ptList.add(productType);
                category.setProductTypes(ptList);
            }

            String newProduct = row.getCell(pIndex).getStringCellValue();
            Product product = null;
            if (productType.getProducts() != null) {
                product = getProduct(productType.getProducts(), newProduct);
            }
            if (product == null) {
                var p = new Product();
                p.setName(newProduct);
                p.setProductTypeId(productType.getId());
                product = productRepository.save(p);
                List<Product> pList = new ArrayList<>();
                if (productType.getProducts() != null) {
                    pList = productType.getProducts();
                }
                pList.add(product);
                productType.setProducts(pList);
            }

            String newBrand = row.getCell(brandIndex).getStringCellValue();
            Characteristic characteristic = null;
            if (product.getCharacteristics() != null) {
                characteristic = getCharacteristic(product.getCharacteristics(), "Производитель");
            }
            if (characteristic == null) {
                var ch = new Characteristic();
                ch.setName("Производитель");
                ch.setType(0);
                ch.setValue(newBrand);
                ch.setProductId(product.getId());
                characteristic = characteristicRepository.save(ch);
                List<Characteristic> chList = new ArrayList<>();
                if (product.getCharacteristics() != null) {
                    chList = product.getCharacteristics();
                }
                chList.add(characteristic);
                product.setCharacteristics(chList);
            }

            int newPrice = (int)Float.parseFloat(removeStringSpaces(row.getCell(priceIndex).getStringCellValue().replace(',', '.')));
            characteristic = null;
            if (product.getCharacteristics() != null) {
                characteristic = getCharacteristic(product.getCharacteristics(), "Цена");
            }
            if (characteristic == null) {
                var ch = new Characteristic();
                ch.setName("Цена");
                ch.setType(4);
                ch.setValue(newPrice + "");
                ch.setProductId(product.getId());
                characteristic = characteristicRepository.save(ch);
                List<Characteristic> chList = new ArrayList<>();
                if (product.getCharacteristics() != null) {
                    chList = product.getCharacteristics();
                }
                chList.add(characteristic);
                product.setCharacteristics(chList);
            }

            String newChListS = row.getCell(characteristicsIndex).getStringCellValue();
            var newChList = deserealizeString(newChListS);
            for (var newCh : newChList) {
                characteristic = null;
                if (product.getCharacteristics() != null) {
                    characteristic = getCharacteristic(product.getCharacteristics(), newCh[0]);
                }
                if (characteristic == null) {
                    String valueS = newCh[1];
                    if (isNumber(valueS.charAt(0))) {
                        for (int j = 1; j < valueS.length(); j++) {
                            if (!isNumber(valueS.charAt(j))) {
                                String numS = valueS.substring(0, j);
                                float num;
                                try {
                                    num = Float.parseFloat(numS);
                                    valueS = num + "";

                                    var ch = new Characteristic();
                                    ch.setName(newCh[0]);
                                    ch.setType(2);
                                    ch.setValue(valueS);
                                    ch.setProductId(product.getId());
                                    characteristic = characteristicRepository.save(ch);
                                    List<Characteristic> chList = new ArrayList<>();
                                    if (product.getCharacteristics() != null) {
                                        chList = product.getCharacteristics();
                                    }
                                    chList.add(characteristic);
                                    product.setCharacteristics(chList);
                                }
                                catch (Exception ignored) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Category getCategory(List<Category> categories, String name) {
        for (var category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
    private ProductType getProductType(List<ProductType> productTypes, String name) {
        for (var productType : productTypes) {
            if (productType.getName().equals(name)) {
                return productType;
            }
        }
        return null;
    }
    private Product getProduct(List<Product> products, String name) {
        for (var product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
    private Characteristic getCharacteristic(List<Characteristic> characteristics, String name) {
        for (var characteristic : characteristics) {
            if (characteristic.getName().equals(name)) {
                return characteristic;
            }
        }
        return null;
    }
    private String removeStringSpaces(String string) {
        char c = ' ';
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != '0' &&
                    string.charAt(i) != '1' &&
                    string.charAt(i) != '2' &&
                    string.charAt(i) != '3' &&
                    string.charAt(i) != '4' &&
                    string.charAt(i) != '5' &&
                    string.charAt(i) != '6' &&
                    string.charAt(i) != '7' &&
                    string.charAt(i) != '8' &&
                    string.charAt(i) != '9' &&
                    string.charAt(i) != '.') {
                int len = string.length();
                string = string.substring(0, i) + string.substring(i + 1, len);
                i--;
            }
        }
        return string;
    }
    private boolean isNumber(char c) {
        if (c == '0' ||
                c == '1' ||
                c == '2' ||
                c == '3' ||
                c == '4' ||
                c == '5' ||
                c == '6' ||
                c == '7' ||
                c == '8' ||
                c == '9' ||
                c == '.' ||
                c == ',') {
            return true;
        }
        return false;
    }
    private List<String[]> deserealizeString(String s) {
        List<String[]> sl = new ArrayList<>();
        int start = 0;
        String name = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '<') {
                if (s.substring(i, i + 4).equals("<td>")) {
                    start = i + 4;
                }
                if (s.substring(i, i + 5).equals("</td>")) {
                    if (name.equals("")) {
                        name = s.substring(start, i);
                    }
                    else {
                        var sa = new String[2];
                        sa[0] = name;
                        sa[1] = s.substring(start, i);
                        sl.add(sa);
                        name = "";
                    }
                }
            }
        }

        return sl;
    }
}
