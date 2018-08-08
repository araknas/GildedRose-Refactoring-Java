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

            CustomItem customItem = initiateCustomItemFromParent(currentItem);
            customItem.recalculateItemValuesAfterOneDay();

            currentItem.sellIn = customItem.getSellInn();
            currentItem.quality = customItem.getQuality();
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