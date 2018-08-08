package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class AgedBrieItem extends Item implements CustomItem {

    private static final int MAX_QUALITY_VALUE = 50;

    public AgedBrieItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {
        int quality = super.quality;
        int sellIn = super.sellIn;

        if (quality < MAX_QUALITY_VALUE) {
            quality++;
        }

        sellIn--;

        if (sellIn < 0 && quality < MAX_QUALITY_VALUE) {
            quality++;
        }

        super.sellIn = sellIn;
        super.quality = quality;
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
