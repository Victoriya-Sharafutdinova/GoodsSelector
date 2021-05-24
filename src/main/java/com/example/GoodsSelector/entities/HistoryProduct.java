package com.example.GoodsSelector.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historiesProducts")
public class HistoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "historyProductId")
    private List<CharacteristicHistoryProduct> characteristicHistoryProducts;

    @Column(nullable = false)
    private Integer orderIndex;

    public HistoryProduct() {}

    public HistoryProduct(Long historyId, Product product, Integer orderIndex) {
        this.historyId = historyId;
        this.product = product;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<CharacteristicHistoryProduct> getCharacteristicHistoryProducts() {
        return characteristicHistoryProducts;
    }

    public void setCharacteristicHistoryProducts(List<CharacteristicHistoryProduct> characteristicHistoryProducts) {
        this.characteristicHistoryProducts = characteristicHistoryProducts;
    }
}
