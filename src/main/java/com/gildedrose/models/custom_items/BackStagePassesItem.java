package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class BackStagePassesItem extends Item implements CustomItem {

    private static final int MAX_QUALITY_VALUE = 50;
    private static final int QUALITY_VALUE_AFTER_THE_CONCERT = 0;
    private static final int FIRST_SELL_INN_THRESHOLD_FOR_QUALITY_INCREASE = 11;
    private static final int SECOND_SELL_INN_THRESHOLD_FOR_QUALITY_INCREASE = 6;

    public BackStagePassesItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {

        int quality = super.quality;
        int sellIn = super.sellIn;

        if (quality < MAX_QUALITY_VALUE) {
            quality++;

            if (sellIn < FIRST_SELL_INN_THRESHOLD_FOR_QUALITY_INCREASE && quality < MAX_QUALITY_VALUE) {
                quality++;
            }
            if (sellIn < SECOND_SELL_INN_THRESHOLD_FOR_QUALITY_INCREASE && quality < MAX_QUALITY_VALUE) {
                quality++;
            }
        }

        sellIn--;

        if (sellIn < 0) {
            quality = QUALITY_VALUE_AFTER_THE_CONCERT;
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
