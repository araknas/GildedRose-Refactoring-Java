package com.gildedrose;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import com.gildedrose.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Giedrius on 2018-08-10.
 */
@Service
public class GildedRoseUpdateService {
    public static final int ITEMS_UPDATE_INTERVAL_IN_MILLIS = 24 * 60 * 60 * 1000; // One day
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final ExecutorService pool = Executors.newFixedThreadPool(1);
    ExecutorService serviceRunner = Executors.newSingleThreadExecutor();
    Object lock = new Object();
    private boolean run = true;

    @Autowired
    ItemService itemService;

    public void initiateGildedRoseUpdateService() {
        try{
            logger.info("Initiating GildedRoseUpdateService.");
            startServiceThread();
        }
        catch (Exception e){
            logger.error("Exception while initiating GildedRoseUpdateService, e = " + e.getMessage(), e);
        }
    }

    private void startServiceThread() {

        logger.info("Starting  GildedRoseUpdateService update task thread.");
        serviceRunner.execute(() -> {
            while (this.run && !Thread.currentThread().isInterrupted()) {
                try {
                    pool.execute(new TaskHandler());
                    Thread.sleep(ITEMS_UPDATE_INTERVAL_IN_MILLIS);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Exception while running GildedRoseUpdateService thread.");
                }
            }
        });
    }

    public void stopServiceThread()
    {
        try{
            logger.info("Shutting down GildedRoseUpdateService");
            pool.shutdownNow();
            pool.shutdown();
            serviceRunner.shutdownNow();
            serviceRunner.shutdown();
        }
        catch (Exception e){
            logger.error("Exception while shutting down GildedRoseUpdateService, e = " + e.getMessage());
        }
        this.run = false;
    }

    class TaskHandler implements Runnable {

        TaskHandler() {
        }

        public void run() {
            synchronized (lock) {
                manageTask();
            }
        }
    }

    private void manageTask() {
        try{
            List<ItemEntity> items = itemService.findAll();
            GildedRose gildedRose = new GildedRose();
            List<ItemEntity> updatedItems = gildedRose.updateQuality(items);
            itemService.save(updatedItems);
        }
        catch (Exception e){
            logger.error("Exception updating GildedRose items, e = " + e.getMessage(), e);
        }
    }


}
