package com.gildedrose;

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
                if (quality > 0) {
                    if (!name.equals(SULFURAS_ITEM)) {
                        quality--;
                    }
                }
            } else {
                if (quality < 50) {
                    quality++;

                    if (name.equals(BACKSTAGE_PASSES_ITEM)) {
                        if (sellIn < 11) {
                            if (quality < 50) {
                                quality++;
                            }
                        }

                        if (sellIn < 6) {
                            if (quality < 50) {
                                quality++;
                            }
                        }
                    }
                }
            }

            if (!name.equals(SULFURAS_ITEM)) {
                sellIn--;
            }

            if (sellIn < 0) {
                if (!name.equals(AGED_BRIE_ITEM)) {
                    if (!name.equals(BACKSTAGE_PASSES_ITEM)) {
                        if (quality > 0) {
                            if (!name.equals(SULFURAS_ITEM)) {
                                quality--;
                            }
                        }
                    } else {
                        quality = 0;
                    }
                } else {
                    if (quality < 50) {
                        quality++;
                    }
                }
            }
            currentItem.quality = quality;
            currentItem.sellIn = sellIn;
        }

    }
}