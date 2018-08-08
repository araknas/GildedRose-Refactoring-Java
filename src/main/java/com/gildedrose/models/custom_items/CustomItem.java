package com.gildedrose.models.custom_items;

/**
 * Created by Giedrius on 2018-08-08.
 */
public interface CustomItem {
    void recalculateItemValuesAfterOneDay();
    void setSellInn(int sellInn);
    void setQuality(int quality);
    int getSellInn();
    int getQuality();
}
