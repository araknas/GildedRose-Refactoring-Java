package com.gildedrose;

import com.gildedrose.models.custom_items.AgedBrieItem;
import com.gildedrose.models.custom_items.BackStagePassesItem;
import com.gildedrose.models.custom_items.OtherItem;
import com.gildedrose.models.custom_items.SulfurasItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    AgedBrieItem agedBrieItem = null;
    BackStagePassesItem backStagePassesItem = null;
    SulfurasItem sulfurasItem = null;
    OtherItem otherItem = null;

    @Before
    public void setUp(){
    }

    @Test
    public void testCustomItemsInitiation() throws Exception{
        agedBrieItem = new AgedBrieItem(GildedRose.AGED_BRIE_ITEM, 0 ,0);
        backStagePassesItem = new BackStagePassesItem(GildedRose.BACKSTAGE_PASSES_ITEM, 0 ,0);
        sulfurasItem = new SulfurasItem(GildedRose.SULFURAS_ITEM, 0 ,0);
        otherItem = new OtherItem("Elixir of the Mongoose", 0 ,0);

        assertEquals("Aged Brie", agedBrieItem.name);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", backStagePassesItem.name);
        assertEquals("Sulfuras, Hand of Ragnaros", sulfurasItem.name);
        assertEquals("Elixir of the Mongoose", otherItem.name);
    }
    @Test
     public void testAgedBrieItemValuesRecalculation() throws Exception{

        agedBrieItem = new AgedBrieItem(GildedRose.AGED_BRIE_ITEM, 2, 0);
        agedBrieItem.recalculateItemValuesAfterOneDay();

        // Values are taken form golden_output.txt
        int expectedSellIn = 1;
        int expectedQuality = 1;

        int currentQuality = agedBrieItem.quality;
        int currentSellIn = agedBrieItem.sellIn;

        assertEquals("After one day Aged Brie item sell inn value is incorrect.", expectedSellIn, currentSellIn);
        assertEquals("After one day Aged Brie item quality value is incorrect.", expectedQuality, currentQuality);
    }

    @Test
    public void testBackStagePassesItemValuesRecalculation() throws Exception{

        backStagePassesItem = new BackStagePassesItem(GildedRose.BACKSTAGE_PASSES_ITEM, 15, 20);
        backStagePassesItem.recalculateItemValuesAfterOneDay();

        // Values are taken form golden_output.txt
        int expectedSellIn = 14;
        int expectedQuality = 21;

        int currentQuality = backStagePassesItem.quality;
        int currentSellIn = backStagePassesItem.sellIn;

        assertEquals("After one day Backstage passes item sell inn value is incorrect.", expectedSellIn, currentSellIn);
        assertEquals("After one day Backstage passes item quality value is incorrect.", expectedQuality, currentQuality);
    }

    @Test
    public void testSulfurasItemValuesRecalculation() throws Exception{

        sulfurasItem = new SulfurasItem(GildedRose.SULFURAS_ITEM, 0, 80);
        sulfurasItem.recalculateItemValuesAfterOneDay();

        // Values are taken form golden_output.txt
        int expectedSellIn = 0;
        int expectedQuality = 80;

        int currentQuality = sulfurasItem.quality;
        int currentSellIn = sulfurasItem.sellIn;

        assertEquals("After one day Sulfuras item sell inn value is incorrect.", expectedSellIn, currentSellIn);
        assertEquals("After one day Sulfuras item quality value is incorrect.", expectedQuality, currentQuality);
    }

    @Test
    public void testOtherItemValuesRecalculation() throws Exception{

        otherItem = new OtherItem("Elixir of the Mongoose", 5, 7);
        otherItem.recalculateItemValuesAfterOneDay();

        // Values are taken form golden_output.txt
        int expectedSellIn = 4;
        int expectedQuality = 6;

        int currentQuality = otherItem.quality;
        int currentSellIn = otherItem.sellIn;

        assertEquals("After one day Sulfuras item sell inn value is incorrect.", expectedSellIn, currentSellIn);
        assertEquals("After one day Sulfuras item quality value is incorrect.", expectedQuality, currentQuality);
    }

}
