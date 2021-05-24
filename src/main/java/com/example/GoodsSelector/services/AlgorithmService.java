package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.*;
import com.example.GoodsSelector.models.*;
import com.example.GoodsSelector.repositories.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlgorithmService implements IAlgorithmService {
    private KeyManager keyManager;

    @Autowired
    private final IProductTypeRepository productTypeRepository;

    @Autowired
    private final IUserRepository userRepository;

    @Autowired
    private final IHistoryRepository historyRepository;

    @Autowired
    private final IHistoryProductRepository historyProductRepository;

    @Autowired
    private final ICharacteristicHistoryProductRepository characteristicHistoryProductRepository;

    public AlgorithmService(IProductTypeRepository productTypeRepository, IUserRepository userRepository, IHistoryRepository historyRepository, IHistoryProductRepository historyProductRepository, ICharacteristicHistoryProductRepository characteristicHistoryProductRepository){
        this.productTypeRepository = productTypeRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.historyProductRepository = historyProductRepository;
        this.characteristicHistoryProductRepository = characteristicHistoryProductRepository;

        keyManager = KeyManager.getInstance();
    }

    @Override
    public List<CharacteristicsRangeModel> getCharacteristicsRange(ProductTypeModel productTypeModel) {
        var characteristicsRangeModels = new ArrayList<CharacteristicsRangeModel>();
        var products = productTypeRepository.getOne(productTypeModel.getId()).getProducts();

        for (var product : products) {
            for (var characteristic : product.getCharacteristics()) {
                var characteristicModel = new CharacteristicModel(characteristic);
                boolean isFound = false;
                for (var characteristicRangeModel : characteristicsRangeModels) {
                    if (characteristicRangeModel.getName().toLowerCase().equals(characteristicModel.getName().toLowerCase())
                            && characteristicRangeModel.getType() == characteristicModel.getType()) {
                        isFound = true;
                        if (characteristicRangeModel.getType() == 0) {
                            if (!characteristicRangeModel.getValues().contains(characteristicModel.getValue())) {
                                characteristicRangeModel.getValues().add(characteristicModel.getValue());
                            }
                            continue;
                        }
                        var min = characteristicRangeModel.getValues().get(0);
                        var max = characteristicRangeModel.getValues().get(1);
                        if (characteristicModel.compare(characteristicRangeModel.getType(), min) == -1) {
                            characteristicRangeModel.getValues().set(0, characteristicModel.getValue());
                        }
                        if (characteristicModel.compare(characteristicRangeModel.getType(), max) == 1) {
                            characteristicRangeModel.getValues().set(1, characteristicModel.getValue());
                        }
                    }
                }

                if (!isFound) {
                    characteristicsRangeModels.add(new CharacteristicsRangeModel(characteristic));
                }
            }
        }

        return characteristicsRangeModels;
    }

    @Override
    public List<ProductModel> getGoodsTop(List<CharacteristicsRangeModel> characteristicsRangeModels, ProductTypeModel productTypeModel, UserTokenModel userTokenModel) {
        var products = productTypeRepository.getOne(productTypeModel.getId()).getProducts();

        // Фильтрация товаров по заданным диапазонам характеристик
        for (var i = 0; i < products.size(); i++) {
            var characteristics = products.get(i).getCharacteristics();
            boolean isPassed = true;
            for (var characteristic : characteristics) {
                var type = characteristic.getType();
                for (var characteristicsRangeModel : characteristicsRangeModels) {
                    if (characteristic.getName().toLowerCase().equals(characteristicsRangeModel.getName().toLowerCase())
                            && type == characteristicsRangeModel.getType()) {

                        if (type % 3 == 0) {
                            isPassed = false;
                            for (var value : characteristicsRangeModel.getValues()) {
                                if (value.equals(characteristic.getValue())) {
                                    isPassed = true;
                                    break;
                                }
                            }
                        }
                        else if (type % 3  == 1) {
                            int minValue = Integer.parseInt(characteristicsRangeModel.getValues().get(0));
                            int maxValue = Integer.parseInt(characteristicsRangeModel.getValues().get(1));
                            int value = Integer.parseInt(characteristic.getValue());
                            if (value < minValue || value > maxValue) {
                                isPassed = false;
                                break;
                            }
                        }
                        else {
                            float minValue = Float.parseFloat(characteristicsRangeModel.getValues().get(0));
                            float maxValue = Float.parseFloat(characteristicsRangeModel.getValues().get(1));
                            float value = Float.parseFloat(characteristic.getValue());
                            if (value < minValue || value > maxValue) {
                                isPassed = false;
                                break;
                            }
                        }
                    }
                }
                if (!isPassed) {
                    products.remove(i);
                    i--;
                    break;
                }
            }
        }

        // Возврат результата, если после фильтрации ни один элемент не удовлетворил условиям поиска
        if (products.size() == 0) {
            var productModels = new ArrayList<ProductModel>();
            for (var product : products) {
                productModels.add(new ProductModel(product));
            }
            return productModels;
        }


       var allCharacteristicsRangeModels = new ArrayList<>(characteristicsRangeModels);
        // Удаление текстовых характеристик из листа диапазона характеристик
        for (int i = 0; i < characteristicsRangeModels.size(); i++) {
            if (characteristicsRangeModels.get(i).getType() % 3 == 0) {
                characteristicsRangeModels.remove(i);
                i--;
            }
        }

        // Создание исходной матрицы
        double[][] matrix = new double[products.size()][characteristicsRangeModels.size()];
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < characteristicsRangeModels.size(); j++) {
                for (int k = 0; k < products.get(i).getCharacteristics().size(); k++) {
                    if (products.get(i).getCharacteristics().get(k).getName().toLowerCase().equals(characteristicsRangeModels.get(j).getName().toLowerCase())
                            && products.get(i).getCharacteristics().get(k).getType() == characteristicsRangeModels.get(j).getType()) {
                        matrix[i][j] = Double.parseDouble(products.get(i).getCharacteristics().get(k).getValue());
                    }
                }
            }
        }

        var s = "";
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                s += matrix[i][j] + " ";
            }
            s += "\n";
        }
        System.out.println(s);

        // Преобразование матрицы в матрицу решений
        for(int j = 0; j < matrix[0].length; j++) {
            if (characteristicsRangeModels.get(j).getType() >= 3) {
                double max = matrix[0][j] ;
                for(int k = 1; k < matrix.length; k++) {
                    if (matrix[k][j] > max) {
                        max = matrix[k][j];
                    }
                }
                for(int k = 0; k < matrix.length; k++) {
                    matrix[k][j] = max / matrix[k][j];
                }
            }
        }

        s = "";
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                s += matrix[i][j] + " ";
            }
            s += "\n";
        }
        System.out.println(s);

        // Нормализация матрицы решений
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double sum = 0;
                for (int k = 0; k < matrix.length; k++) {
                    sum += matrix[k][j];
                }
                for (int k = 0; k < matrix.length; k++) {
                    matrix[k][j] /= sum;
                }
            }
        }

        s = "";
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                s += matrix[i][j] + " ";
            }
            s += "\n";
        }
        System.out.println(s);

        // Создание массива весов характеристик
        double[] weight = new double[characteristicsRangeModels.size()];
        double weightSum = 0;
        for (int i = 0; i < weight.length; i++) {
            weight[i] = weight.length - i;
            weightSum += weight[i];
        }
        for (int i = 0; i < weight.length; i++) {
            weight[i] /= weightSum;
        }

        s = "";
        for (int i = 0; i < weight.length; i++) {
            s += weight[i] + " ";
        }
        System.out.println(s);

        // Рассчет по критерию Байеса-Лапласа
        Map<Integer, Double> result = new TreeMap<>();
        for (int i = 0; i < matrix.length; i++) {
            double productWeight = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                productWeight += matrix[i][j] * weight[j];
            }
            result.put(i, productWeight);
        }
        SortedSet<Map.Entry<Integer, Double>> sortedResult = new TreeSet<>((e1, e2) -> -e1.getValue().compareTo(e2.getValue()));
        sortedResult.addAll(result.entrySet());
        System.out.println(sortedResult.toString());

        // Сборка результата
        var productModels = new ArrayList<ProductModel>();
        for (var element : sortedResult) {
            productModels.add(new ProductModel(products.get(element.getKey())));
        }


        // Сохраняем результаты поиска в историю, если пользователь авторизован
        if (userTokenModel.getToken() != null && !userTokenModel.getToken().equals("")) {
            long id = 0;
            String subject = "";
            try {
                subject = Jwts.parserBuilder().setSigningKey(keyManager.getKey()).build().parseClaimsJws(userTokenModel.getToken()).getBody().getSubject();
            }
            catch (SignatureException e) {
                return productModels;
            }
            try {
                id = Long.parseLong(subject);
            }
            catch (NumberFormatException e) {
                return productModels;
            }
            var user = userRepository.getOne(id);
            if (user != null) {
                var history = new History(id, new Date(System.currentTimeMillis()));
                history = historyRepository.save(history);
                for (var i = 0; i < productModels.size(); i++) {
                    var productModel = productModels.get(i);
                    var historyProduct = historyProductRepository.save(new HistoryProduct(history.getId(), new Product(productModel.getId()), i));
                    for (var j = 0; j < allCharacteristicsRangeModels.size(); j++) {
                        for (var characteristic : productModel.getCharacteristics()) {
                            if (allCharacteristicsRangeModels.get(j).getName().equals(characteristic.getName())
                                    && allCharacteristicsRangeModels.get(j).getType() == characteristic.getType()) {
                                characteristicHistoryProductRepository.save(new CharacteristicHistoryProduct(historyProduct.getId(), new Characteristic(characteristic.getId()), j));
                            }
                        }
                    }
                }
            }
        }

        return productModels;
    }
}
