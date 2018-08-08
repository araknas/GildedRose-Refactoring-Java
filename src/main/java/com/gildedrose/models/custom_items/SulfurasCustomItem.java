package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class SulfurasCustomItem extends Item implements CustomItem {

    public SulfurasCustomItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {
        // Note: Sulfuras item currently won't be effected in any way.
    }

    @Override
    public void setSellInn(int sellInn) {
        super.sellIn = sellInn;
    }

    @Override
    public void setQuality(int quality) {
        super.quality = quality;
    }

    @Override
    public int getSellInn() {
        return super.sellIn;
    }

    @Override
    public int getQuality() {
        return super.quality;
    }
}
