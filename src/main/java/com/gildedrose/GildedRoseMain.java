package com.gildedrose;

import com.gildedrose.services.GildedRoseUpdateSchedulerService;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;

/**
 * Created by Giedrius on 2018-08-09.
 */
@SpringBootApplication
public class GildedRoseMain implements CommandLineRunner {
    private ConfigurableApplicationContext springContext;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchOperations es;
    @Autowired
    private GildedRoseUpdateSchedulerService gildedRoseUpdateSchedulerService;

    public static void main(String args[]) {
        SpringApplication.run(GildedRoseMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Started GildedRose Spring Boot Application.");
        gildedRoseUpdateSchedulerService.initiateGildedRoseUpdateService();
        printElasticSearchInfo();
    }

    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }
}
