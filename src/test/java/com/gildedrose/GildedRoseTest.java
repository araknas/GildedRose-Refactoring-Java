package com.gildedrose;

import com.gildedrose.models.custom_items.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    AgedBrieCustomItem agedBrieItem = null;
    BackStagePassesCustomItem backStagePassesItem = null;
    SulfurasCustomItem sulfurasItem = null;
    OtherCustomItem otherItem = null;
    Item parentItem = null;

    @Before
    public void setUp(){
    }

    @Test
    public void testCustomItemsInitiation() throws Exception{
        agedBrieItem = new AgedBrieCustomItem(GildedRose.AGED_BRIE_ITEM, 0 ,0);
        backStagePassesItem = new BackStagePassesCustomItem(GildedRose.BACKSTAGE_PASSES_ITEM, 0 ,0);
        sulfurasItem = new SulfurasCustomItem(GildedRose.SULFURAS_ITEM, 0 ,0);
        otherItem = new OtherCustomItem("Elixir of the Mongoose", 0 ,0);

        assertEquals("Aged Brie", agedBrieItem.name);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", backStagePassesItem.name);
        assertEquals("Sulfuras, Hand of Ragnaros", sulfurasItem.name);
        assertEquals("Elixir of the Mongoose", otherItem.name);
    }
    @Test
     public void testAgedBrieItemValuesRecalculation() throws Exception{

        agedBrieItem = new AgedBrieCustomItem(GildedRose.AGED_BRIE_ITEM, 2, 0);
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

        backStagePassesItem = new BackStagePassesCustomItem(GildedRose.BACKSTAGE_PASSES_ITEM, 15, 20);
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

        sulfurasItem = new SulfurasCustomItem(GildedRose.SULFURAS_ITEM, 0, 80);
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

        otherItem = new OtherCustomItem("Elixir of the Mongoose", 5, 7);
        otherItem.recalculateItemValuesAfterOneDay();

        // Values are taken form golden_output.txt
        int expectedSellIn = 4;
        int expectedQuality = 6;

        int currentQuality = otherItem.quality;
        int currentSellIn = otherItem.sellIn;

        assertEquals("After one day Sulfuras item sell inn value is incorrect.", expectedSellIn, currentSellIn);
        assertEquals("After one day Sulfuras item quality value is incorrect.", expectedQuality, currentQuality);
    }

    @Test
    public void testCustomItemInitiationFromParentItem() throws Exception{

        parentItem = new Item(GildedRose.AGED_BRIE_ITEM, 0 ,0);
        CustomItem customItem = GildedRose.initiateCustomItemFromParent(parentItem);
        Assert.assertNotNull("Custom item cannot be null.", customItem);
        Assert.assertTrue("Custom item expected to be instance of AgedBrieCustomItem.",
                customItem instanceof AgedBrieCustomItem);

        parentItem = new Item(GildedRose.BACKSTAGE_PASSES_ITEM, 0 ,0);
        customItem = GildedRose.initiateCustomItemFromParent(parentItem);
        Assert.assertTrue("Custom item expected to be instance of BackStagePassesCustomItem.",
                customItem instanceof BackStagePassesCustomItem);

        parentItem = new Item(GildedRose.SULFURAS_ITEM, 0 ,0);
        customItem = GildedRose.initiateCustomItemFromParent(parentItem);
        Assert.assertTrue("Custom item expected to be instance of SulfurasCustomItem.",
                customItem instanceof SulfurasCustomItem);

        parentItem = new Item("Elixir of the Mongoose", 0 ,0);
        customItem = GildedRose.initiateCustomItemFromParent(parentItem);
        Assert.assertTrue("Custom item expected to be instance of OtherCustomItem.",
                customItem instanceof OtherCustomItem);
    }

    @Test
    public void testCustomItemGetter() throws Exception{

        parentItem = new Item(GildedRose.AGED_BRIE_ITEM, 5 ,3);
        CustomItem customItem = GildedRose.initiateCustomItemFromParent(parentItem);
        Assert.assertEquals("Custom item's getter return wrong sell inn value.", customItem.getSellInn(), 5);
        Assert.assertEquals("Custom item's getter return wrong quality value.", customItem.getQuality(), 3);

    }
    @Test
    public void testCustomItemSetters() throws Exception{

        parentItem = new Item(GildedRose.AGED_BRIE_ITEM, 5 ,3);
        CustomItem customItem = GildedRose.initiateCustomItemFromParent(parentItem);
        customItem.setSellInn(10);
        customItem.setQuality(20);
        Assert.assertEquals("Custom item's setter sets wrong sell inn value.", customItem.getSellInn(), 10);
        Assert.assertEquals("Custom item's setter sets wrong quality value.", customItem.getQuality(), 20);
    }

}
