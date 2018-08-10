package com.gildedrose.models.elasticsearch_models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by Giedrius on 2018-08-09.
 */

@Document(indexName = "gildedrose", type = "GildedRoseItems")
public class ItemEntity {

    @Id
    private String id;
    private String name;
    private int sellIn;
    private int quality;

    public ItemEntity(String id, String name, int sellIn, int quality) {
        this.id = id;
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public ItemEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sellIn=" + sellIn +
                ", quality=" + quality +
                '}';
    }
}
