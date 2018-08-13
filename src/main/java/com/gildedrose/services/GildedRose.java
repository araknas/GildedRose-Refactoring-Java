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
                    Item currentItem = items[i];
                    serviceRunner.execute(new UpdateTaskHandler(currentItem, latch));
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
                for (ItemEntity currentItem : items) {
                    serviceRunner.execute(new UpdateTaskHandler(currentItem, latch));
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

    public static CustomItem initiateCustomItemFromParent(Item parentItem) {

        CustomItem customItem = null;
        try{
            String itemName = parentItem.name;
            switch (itemName){
                case AGED_BRIE_ITEM:
                    customItem = new AgedBrieItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                case BACKSTAGE_PASSES_ITEM:
                    customItem = new BackStagePassesItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                case SULFURAS_ITEM:
                    customItem = new SulfurasItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                case CONJURED_ITEM:
                    customItem = new ConjuredItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
                default:
                    customItem = new OtherItem(parentItem.name, parentItem.sellIn, parentItem.quality);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return customItem;
    }

    private CustomItem initiateCustomItemFromParent(ItemEntity parentItem) {
        CustomItem customItem = null;
        try{
            String itemName = parentItem.getName();
            switch (itemName){
                case AGED_BRIE_ITEM:
                    customItem = new AgedBrieItem(parentItem.getName(), parentItem.getSellIn(), parentItem.getQuality());
                    break;
                case BACKSTAGE_PASSES_ITEM:
                    customItem = new BackStagePassesItem(parentItem.getName(), parentItem.getSellIn(), parentItem.getQuality());
                    break;
                case SULFURAS_ITEM:
                    customItem = new SulfurasItem(parentItem.getName(), parentItem.getSellIn(), parentItem.getQuality());
                    break;
                case CONJURED_ITEM:
                    customItem = new ConjuredItem(parentItem.getName(), parentItem.getSellIn(), parentItem.getQuality());
                    break;
                default:
                    customItem = new OtherItem(parentItem.getName(), parentItem.getSellIn(), parentItem.getQuality());
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return customItem;
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
            CustomItem customItem = initiateCustomItemFromParent(item);
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
            CustomItem customItem = initiateCustomItemFromParent(item);
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
}