package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.History;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryModel {
    private Long id;

    private Date date;

    private String categoryName;

    private String productTypeName;

    private List<HistoryProductModel> historyProductModels;

    public HistoryModel() {}

    public HistoryModel(History history, String categoryName, String productTypeName) {
        this.id = history.getId();
        this.date = history.getDate();
        this.categoryName = categoryName;
        this.productTypeName = productTypeName;
        var historyProducts = history.getHistoryProducts();
        if (historyProducts != null) {
            this.historyProductModels = new ArrayList<>();
            for (int i = 0; i < historyProducts.size(); i++) {
                for (var historyProduct : historyProducts) {
                    if (historyProduct.getOrderIndex() == i) {
                        this.historyProductModels.add(new HistoryProductModel(historyProduct));
                        break;
                    }
                }
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<HistoryProductModel> getHistoryProductModels() {
        return historyProductModels;
    }

    public void setHistoryProductModels(List<HistoryProductModel> historyProductModels) {
        this.historyProductModels = historyProductModels;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
}
