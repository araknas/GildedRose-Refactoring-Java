package com.gildedrose;

import com.gildedrose.models.custom_items.*;

class GildedRose {

    public static final String AGED_BRIE_ITEM = "Aged Brie";
    public static final String BACKSTAGE_PASSES_ITEM = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_ITEM = "Sulfuras, Hand of Ragnaros";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item currentItem = items[i];
            String name = currentItem.name;
            int sellIn = currentItem.sellIn;
            int quality = currentItem.quality;

            if (!name.equals(AGED_BRIE_ITEM) && !name.equals(BACKSTAGE_PASSES_ITEM)) {
                //- The Quality of an item is never negative
                if (quality > 0) {
                    //- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
                    if (!name.equals(SULFURAS_ITEM)) {
                        //- Regular quality decrease
                        quality--;
                    }
                }
            } else {
                //- The Quality of an item is never more than 50
                if (quality < 50) {
                    // - "Aged Brie" actually increases in Quality the older it gets
                    // - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
                    quality++;

                    if (name.equals(BACKSTAGE_PASSES_ITEM)) {
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
                }
            }
            //- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
            if (!name.equals(SULFURAS_ITEM)) {
                // - Regular sellIn decrease
                sellIn--;
            }

            // - Once the sell by date has passed, Quality degrades twice as fast
            if (sellIn < 0) {
                if (!name.equals(AGED_BRIE_ITEM)) {
                    if (!name.equals(BACKSTAGE_PASSES_ITEM)) {
                        //- The Quality of an item is never negative
                        if (quality > 0) {
                            //- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
                            if (!name.equals(SULFURAS_ITEM)) {
                                //- Once the sell by date has passed, Quality degrades twice as fast (second decrease)
                                quality--;
                            }
                        }
                    } else {
                        // - For Backstage passes Quality drops to 0 after the concert
                        quality = 0;
                    }
                } else {
                    //- The Quality of an item is never more than 50
                    if (quality < 50) {
                        // - Once the sell by date has passed, Quality increase twice as fast (only for Aged Brie)
                        quality++;
                    }
                }
            }
            currentItem.quality = quality;
            currentItem.sellIn = sellIn;
        }

    }

    public static CustomItem initiateCustomItemFromParent(Item parentItem) {

        CustomItem customItem = null;
        try{
            String itemName = parentItem.name;
            switch (itemName){
                case AGED_BRIE_ITEM:
                    customItem = new AgedBrieCustomItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                case BACKSTAGE_PASSES_ITEM:
                    customItem = new BackStagePassesCustomItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                case SULFURAS_ITEM:
                    customItem = new SulfurasCustomItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                default:
                    customItem = new OtherCustomItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return customItem;
    }
}