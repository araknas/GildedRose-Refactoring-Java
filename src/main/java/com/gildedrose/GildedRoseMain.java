package com.gildedrose;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Giedrius on 2018-08-09.
 */
@SpringBootApplication
public class GildedRoseMain extends Application{
    private ConfigurableApplicationContext springContext;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        launch(GildedRoseMain.class, args);
    }

    @Override
    public void init() throws Exception {
        logger.info("Initiating GildedRose Spring Boot Application context.");
        springContext = SpringApplication.run(GildedRoseMain.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting GildedRose Spring Boot application.");

    }

    @Override
    public void stop() throws Exception {
        logger.info("Stopping GildedRose Spring Boot application.");
    }
}
