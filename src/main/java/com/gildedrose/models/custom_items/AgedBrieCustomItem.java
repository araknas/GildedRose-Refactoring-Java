package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class AgedBrieCustomItem extends Item implements CustomItem {

    public AgedBrieCustomItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {
        int quality = super.quality;
        int sellIn = super.sellIn;

        if (quality < 50) {
            // - "Aged Brie" actually increases in Quality the older it gets
            // - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
            quality++;
        }

        // - Regular sellIn decrease
        sellIn--;

        if (sellIn < 0) {

            //- The Quality of an item is never more than 50
            if (quality < 50) {
                // - Once the sell by date has passed, Quality increase twice as fast (only for Aged Brie)
                quality++;
            }
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
