package com.gildedrose.services;

import com.gildedrose.GildedRoseMain;
import com.gildedrose.models.Item;
import com.gildedrose.models.custom_items.*;
import com.gildedrose.models.elasticsearch_models.ItemEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GildedRoseMain.class)
public class GildedRoseUpdateServiceTest {

    AgedBrieItem agedBrieItem = null;
    BackStagePassesItem backStagePassesItem = null;
    SulfurasItem sulfurasItem = null;
    ConjuredItem conjuredItem = null;
    OtherItem otherItem = null;
    Item parentItem = null;

    @Autowired
    GildedRoseUpdateService gildedRoseUpdateService;

    @Before
    public void setUp(){
    }

    @Test
    public void testGildedRoseServiceInit(){
        Assert.assertNotNull("failed initiating GildedRose (as service) bean", gildedRoseUpdateService);
    }

    @Test
    public void testCustomItemsInitiation() throws Exception{
        agedBrieItem = new AgedBrieItem(GildedRoseUpdateService.AGED_BRIE_ITEM, 0 ,0);
        backStagePassesItem = new BackStagePassesItem(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 0 ,0);
        sulfurasItem = new SulfurasItem(GildedRoseUpdateService.SULFURAS_ITEM, 0 ,0);
        otherItem = new OtherItem("Elixir of the Mongoose", 0 ,0);
        conjuredItem = new ConjuredItem(GildedRoseUpdateService.CONJURED_ITEM, 0 ,0);

        assertEquals(GildedRoseUpdateService.AGED_BRIE_ITEM, agedBrieItem.name);
        assertEquals(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, backStagePassesItem.name);
        assertEquals(GildedRoseUpdateService.SULFURAS_ITEM, sulfurasItem.name);
        assertEquals("Elixir of the Mongoose", otherItem.name);
        assertEquals(GildedRoseUpdateService.CONJURED_ITEM, conjuredItem.name);
    }
    @Test
     public void testAgedBrieItemValuesRecalculation() throws Exception{

        agedBrieItem = new AgedBrieItem(GildedRoseUpdateService.AGED_BRIE_ITEM, 2, 0);
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

        backStagePassesItem = new BackStagePassesItem(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 15, 20);
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

        sulfurasItem = new SulfurasItem(GildedRoseUpdateService.SULFURAS_ITEM, 0, 80);
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

    @Test
    public void testConjuredItemValuesRecalculation() throws Exception{

        conjuredItem = new ConjuredItem(GildedRoseUpdateService.CONJURED_ITEM, 0, 4);
        conjuredItem.recalculateItemValuesAfterOneDay();

        // Values are taken form golden_output.txt
        int expectedSellIn = -1;
        int expectedQuality = 0;

        int currentQuality = conjuredItem.quality;
        int currentSellIn = conjuredItem.sellIn;

        assertEquals("After one day Conjured item sell inn value is incorrect.", expectedSellIn, currentSellIn);
        assertEquals("After one day Conjured item quality value is incorrect.", expectedQuality, currentQuality);
    }

    @Test
    public void testCustomItemInitiationFromParentItem() throws Exception{

        parentItem = new Item(GildedRoseUpdateService.AGED_BRIE_ITEM, 0 ,0);
        CustomItem customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertNotNull("Custom item cannot be null.", customItem);
        Assert.assertTrue("Custom item expected to be instance of AgedBrieItem.",
                customItem instanceof AgedBrieItem);

        parentItem = new Item(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 0 ,0);
        customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertTrue("Custom item expected to be instance of BackStagePassesItem.",
                customItem instanceof BackStagePassesItem);

        parentItem = new Item(GildedRoseUpdateService.SULFURAS_ITEM, 0 ,0);
        customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertTrue("Custom item expected to be instance of SulfurasItem.",
                customItem instanceof SulfurasItem);

        parentItem = new Item("Elixir of the Mongoose", 0 ,0);
        customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertTrue("Custom item expected to be instance of OtherItem.",
                customItem instanceof OtherItem);

        parentItem = new Item("Conjured", 0 ,0);
        customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertTrue("Custom item expected to be instance of ConjuredItem.",
                customItem instanceof ConjuredItem);
    }

    @Test
    public void testCustomItemInitWhenNameMatchesOnlyPartially() throws Exception{
        parentItem = new Item("super duper Conjured item", 0 ,0);
        CustomItem customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertNotNull("Custom item cannot be null.", customItem);
        Assert.assertTrue("Custom item expected to be instance of ConjuredItem.",
                customItem instanceof ConjuredItem);
    }

    @Test
    public void testCustomItemGetter() throws Exception{

        parentItem = new Item(GildedRoseUpdateService.AGED_BRIE_ITEM, 5 ,3);
        CustomItem customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        Assert.assertEquals("Custom item's getter return wrong sell inn value.", customItem.getSellInn(), 5);
        Assert.assertEquals("Custom item's getter return wrong quality value.", customItem.getQuality(), 3);

    }
    @Test
    public void testCustomItemSetters() throws Exception{

        parentItem = new Item(GildedRoseUpdateService.AGED_BRIE_ITEM, 5 ,3);
        CustomItem customItem = GildedRoseUpdateService.identifyCustomItem(parentItem);
        customItem.setSellInn(10);
        customItem.setQuality(20);
        Assert.assertEquals("Custom item's setter sets wrong sell inn value.", customItem.getSellInn(), 10);
        Assert.assertEquals("Custom item's setter sets wrong quality value.", customItem.getQuality(), 20);
    }

    @Test
    public void testGildedRoseAsyncUpdate() throws Exception{

        List<ItemEntity> list = new ArrayList<>();
        list.add(new ItemEntity(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 15, 20));
        list.add(new ItemEntity(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 10, 49));
        list.add(new ItemEntity(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 5, 49));

        List<ItemEntity> updatedList = gildedRoseUpdateService.updateQuality(list);
        Assert.assertNotNull(updatedList);
        Assert.assertEquals("Updated list size is incorrect", 3, updatedList.size());

        // NOTE: correct values are taken from the golden_output.txt
        Assert.assertEquals("Updated item sell in value is incorrect", 14, updatedList.get(0).getSellIn());
        Assert.assertEquals("Updated item quality value is incorrect", 21, updatedList.get(0).getQuality());

        Assert.assertEquals("Updated item sell in value is incorrect", 9, updatedList.get(1).getSellIn());
        Assert.assertEquals("Updated item quality value is incorrect", 50, updatedList.get(1).getQuality());

        Assert.assertEquals("Updated item sell in value is incorrect", 4, updatedList.get(2).getSellIn());
        Assert.assertEquals("Updated item quality value is incorrect", 50, updatedList.get(2).getQuality());
    }

}
