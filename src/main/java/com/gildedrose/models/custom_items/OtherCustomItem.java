package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class OtherCustomItem extends Item implements CustomItem {

    public OtherCustomItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {
        int quality = super.quality;
        int sellIn = super.sellIn;

        //- The Quality of an item is never negative
        if (quality > 0) {
            //- Regular quality decrease
            quality--;
        }

        // - Regular sellIn decrease
        sellIn--;

        // - Once the sell by date has passed, Quality degrades twice as fast
        if (sellIn < 0) {
            //- The Quality of an item is never negative
            if (quality > 0) {
                //- Once the sell by date has passed, Quality degrades twice as fast (second decrease)
                quality--;
            }
        }

        super.sellIn = sellIn;
        super.quality = quality;
    }
}
