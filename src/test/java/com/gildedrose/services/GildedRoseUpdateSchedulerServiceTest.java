package com.gildedrose.services;

import com.gildedrose.GildedRoseMain;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Giedrius on 2018-08-10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GildedRoseMain.class)
public class GildedRoseUpdateSchedulerServiceTest {
    @Autowired
    GildedRoseUpdateSchedulerService gildedRoseUpdateSchedulerService;

    @Test
    public void testGildedRoseUpdateServiceInit(){
        Assert.assertNotNull("failed initiating GildedRoseUpdateService bean", gildedRoseUpdateSchedulerService);
    }
}
