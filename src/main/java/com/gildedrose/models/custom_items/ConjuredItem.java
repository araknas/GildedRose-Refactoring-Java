package com.gildedrose.models.custom_items;

import com.gildedrose.models.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class ConjuredItem extends Item implements CustomItem {

    private static final int MIN_QUALITY_VALUE = 0;
    private static final int QUALITY_DEGRADE_QUANTITY = 2;

    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {

        int quality = super.quality;
        int sellIn = super.sellIn;

        if (quality > MIN_QUALITY_VALUE) {
            quality = degradeQuality(quality);
        }

        sellIn--;

        if (sellIn < 0 && quality > MIN_QUALITY_VALUE) {
            quality = degradeQuality(quality);
        }

        super.sellIn = sellIn;
        super.quality = quality;
    }

    private int degradeQuality(int quality) {
        int degradedQuality = quality;

        if(degradedQuality < QUALITY_DEGRADE_QUANTITY){
            degradedQuality = MIN_QUALITY_VALUE;
        }
        else{
            degradedQuality -= QUALITY_DEGRADE_QUANTITY;
        }
        return degradedQuality;
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
