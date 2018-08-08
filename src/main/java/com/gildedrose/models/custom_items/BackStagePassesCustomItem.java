package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class BackStagePassesCustomItem extends Item implements CustomItem {

    public BackStagePassesCustomItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {

        int quality = super.quality;
        int sellIn = super.sellIn;

        //- The Quality of an item is never more than 50
        if (quality < 50) {
            // - "Aged Brie" actually increases in Quality the older it gets
            // - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
            quality++;

            // For Backstage passes Quality increases by 2 when there are 10 days or less
            if (sellIn < 11) {
                //- The Quality of an item is never more than 50
                if (quality < 50) {
                    // - Second quality increase for Backstage passes
                    quality++;
                }
            }
            // - For Backstage passes Quality increases by 3 when there are 5 days or less
            if (sellIn < 6) {
                // - The Quality of an item is never more than 50
                if (quality < 50) {
                    // - Third quality increase for Backstage passes
                    quality++;
                }
            }
        }

        // - Regular sellIn decrease
        sellIn--;

        // - Once the sell by date has passed, Quality degrades twice as fast
        if (sellIn < 0) {
            // - For Backstage passes Quality drops to 0 after the concert
            quality = 0;
        }

        super.sellIn = sellIn;
        super.quality = quality;
    }
}
