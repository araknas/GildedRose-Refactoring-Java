package com.gildedrose.models.custom_items;

import com.gildedrose.Item;

/**
 * Created by Giedrius on 2018-08-08.
 */
public class SulfurasItem extends Item implements ItemBehaviour {

    public SulfurasItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void recalculateItemValuesAfterOneDay() {
        // Note: Sulfuras item currently won't be effected in any way.
    }
}
