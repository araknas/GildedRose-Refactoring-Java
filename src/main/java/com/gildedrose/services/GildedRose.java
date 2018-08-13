package com.gildedrose.services;

import com.gildedrose.models.Item;
import com.gildedrose.models.custom_items.*;
import com.gildedrose.models.elasticsearch_models.ItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* NOTE: This class has two updateQuality methods:
* void updateQuality() is for the Items[] which have no id property and won't be saved into the database;
*
* List<ItemEntity> updateQuality(List<ItemEntity> items) is for the items which are passed with the method.
* The method returns items for the further work associated with the database.
* Ideally I would remake the original Item class and avoid this duplicate code but in the REFACTORING assignment
* it was said that the Item class cannot be changed so I created a new one called it The ItemEntity and used it
* for the additional tasks.
* All the update logic are in the CustomItem interface implementations so it can be easily maintainable from there.*/

@Service
public class GildedRose {

    public static final String AGED_BRIE_ITEM = "Aged Brie";
    public static final String BACKSTAGE_PASSES_ITEM = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_ITEM = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_ITEM = "Conjured";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int THREAD_POOL_SIZE = 10;
    private ExecutorService serviceRunner = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public GildedRose() {
    }

    public void updateQuality() {
        try{
            if(items != null){
                CountDownLatch latch = new CountDownLatch(items.length);

                for (int i = 0; i < items.length; i++) {
                    Item item = items[i];
                    serviceRunner.execute(new UpdateTaskHandler(item, latch));
                }
                logger.info("Waiting for items to be updated (updating " + items.length + " item(s)");
                latch.await();
                logger.info("Finished updating items.");
            }
        }
        catch (Exception e){
            logger.error("Exception while updating items quality, e = " + e.getMessage(), e);
        }
    }

    public List<ItemEntity> updateQuality(List<ItemEntity> items) {
        try{
            if(items != null){
                CountDownLatch latch = new CountDownLatch(items.size());
                for (ItemEntity item : items) {
                    serviceRunner.execute(new UpdateTaskHandler(item, latch));
                }
                logger.info("Waiting for items to be updated (updating " + items.size() + " item(s)");
                latch.await();
                logger.info("Finished updating items.");
            }
        }
        catch (Exception e){
            logger.error("Exception while updating items quality, e = " + e.getMessage(), e);
        }
        return items;
    }

    class UpdateTaskHandler implements Runnable {

        Item item;
        ItemEntity itemEntity;

        CountDownLatch latch;

        UpdateTaskHandler(Item item, CountDownLatch latch) {
            this.item = item;
            this.latch = latch;
        }
        UpdateTaskHandler(ItemEntity item, CountDownLatch latch) {
            this.itemEntity = item;
            this.latch = latch;
        }

        public void run() {
            if(item != null){
                handleUpdateTask(this.item, latch);
            }
            else if(itemEntity != null){
                handleUpdateTask(this.itemEntity, latch);
            }
        }
    }

    private void handleUpdateTask(Item item, CountDownLatch latch) {
        try{
            CustomItem customItem = identifyCustomItem(item);
            logger.info("Update item: " +  (item != null ? item.toString() : ""));
            customItem.recalculateItemValuesAfterOneDay();
            item.sellIn = customItem.getSellInn();
            item.quality = customItem.getQuality();
        }
        catch (Exception e){
            logger.error("Exception while handling item (" + item.toString() + ") update task, e = " + e.getMessage(), e);
        }
        finally {
            logger.info("Finishing update task for item: " +  (item != null ? item.name : ""));
            latch.countDown();
        }
    }

    private void handleUpdateTask(ItemEntity item, CountDownLatch latch) {
        try{
            CustomItem customItem = identifyCustomItem(item);
            logger.info("Update item: " +  (item != null ? item.toString() : ""));
            customItem.recalculateItemValuesAfterOneDay();
            item.setSellIn(customItem.getSellInn());
            item.setQuality(customItem.getQuality());
        }
        catch (Exception e){
            logger.error("Exception while handling item (" + item.toString() + ") update task, e = " + e.getMessage(), e);
        }
        finally {
            logger.info("Finishing update task for item: " + (item != null ? item.getName() : ""));
            latch.countDown();
        }
    }

    public static CustomItem identifyCustomItem(Item parentItem) throws Exception{
        String name = parentItem.name;
        int sellIn = parentItem.sellIn;
        int quality = parentItem.quality;
        return createCustomItem(name, sellIn, quality);
    }

    public static CustomItem identifyCustomItem(ItemEntity item) throws Exception{
        String name = item.getName();
        int sellIn = item.getSellIn();
        int quality = item.getQuality();
        return createCustomItem(name, sellIn, quality);
    }

    private static CustomItem createCustomItem(String name, int sellIn, int quality) throws Exception{

        CustomItem customItem = null;

        switch (name) {
            case AGED_BRIE_ITEM:
                customItem = new AgedBrieItem(name, sellIn,quality);
                break;
            case BACKSTAGE_PASSES_ITEM:
                customItem = new BackStagePassesItem(name, sellIn, quality);
                break;
            case SULFURAS_ITEM:
                customItem = new SulfurasItem(name, sellIn, quality);
                break;
            case CONJURED_ITEM:
                customItem = new ConjuredItem(name, sellIn, quality);
                break;
            default:
                customItem = new OtherItem(name, sellIn, quality);
                break;
        }
        return customItem;
    }
}