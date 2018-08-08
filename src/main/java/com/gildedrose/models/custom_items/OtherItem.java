package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class OtherItem extends Item implements CustomItem {

    private static final int MIN_QUALITY_VALUE = 0;

    public OtherItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {
        int quality = super.quality;
        int sellIn = super.sellIn;

        if (quality > MIN_QUALITY_VALUE) {
            quality--;
        }

        sellIn--;

        if (sellIn < 0 && quality > MIN_QUALITY_VALUE) {
            quality--;
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
