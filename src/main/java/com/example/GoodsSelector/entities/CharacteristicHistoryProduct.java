package com.example.GoodsSelector.entities;


import javax.persistence.*;

@Entity
@Table(name = "characteristicsHistoriesProducts")
public class CharacteristicHistoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long historyProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characteristicId", nullable = false)
    private Characteristic characteristic;

    @Column(nullable = false)
    private Integer orderIndex;


    public CharacteristicHistoryProduct() {}

    public CharacteristicHistoryProduct(Long historyProductId, Characteristic characteristic, Integer orderIndex) {
        this.historyProductId = historyProductId;
        this.characteristic = characteristic;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHistoryProductId() {
        return historyProductId;
    }

    public void setHistoryProductId(Long historyProductId) {
        this.historyProductId = historyProductId;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
