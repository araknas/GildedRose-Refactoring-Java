package com.gildedrose;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import com.gildedrose.services.GildedRoseUpdateSchedulerService;
import com.gildedrose.services.GildedRoseUpdateService;
import com.gildedrose.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */
@SpringBootApplication
public class GildedRoseMain implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GildedRoseUpdateSchedulerService gildedRoseUpdateSchedulerService;
    @Autowired
    private ItemService itemService;

    public static void main(String args[]) {
        SpringApplication.run(GildedRoseMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Started GildedRose Spring Boot Application.");
        insertExampleData();
        gildedRoseUpdateSchedulerService.initiateGildedRoseUpdateService();
    }

    @PreDestroy
    public void onExit() {
        logger.info("Stopping GildedRose Spring Boot Application.");
        gildedRoseUpdateSchedulerService.stopServiceThread();
    }

    private void insertExampleData() {
        try{
            long existingItemsCount = itemService.findAll().size();
            if(existingItemsCount == 0){
                logger.info("Inserting some initial items data...");
                List<ItemEntity> list = new ArrayList<>();
                list.add(new ItemEntity(GildedRoseUpdateService.SULFURAS_ITEM, 0, 80));
                list.add(new ItemEntity(GildedRoseUpdateService.SULFURAS_ITEM, -1, 80));
                list.add(new ItemEntity(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 15, 20));
                list.add(new ItemEntity(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 10, 49));
                list.add(new ItemEntity(GildedRoseUpdateService.BACKSTAGE_PASSES_ITEM, 5, 49));
                itemService.save(list);
            }
        }
        catch (Exception e){
            logger.error("Exception while inserting initial data, e = " + e.getMessage(), e);
        }
    }
}
