package com.gildedrose;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import com.gildedrose.services.ItemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GildedRoseMain.class)

public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void before() {
        esTemplate.deleteIndex(ItemEntity.class);
        esTemplate.createIndex(ItemEntity.class);
        esTemplate.putMapping(ItemEntity.class);
        esTemplate.refresh(ItemEntity.class);
    }

    @Test
    public void testItemServiceInit(){
        Assert.assertNotNull("failed initiating ItemService bean", itemService);
    }

    @Test
    public void testItemSave() {

        ItemEntity itemEntity = new ItemEntity("1", GildedRose.AGED_BRIE_ITEM, 1, 2);
        ItemEntity itemEntityTest = itemService.save(itemEntity);

        Assert.assertNotNull(itemEntityTest.getId());
        Assert.assertEquals(itemEntity.getName(), itemEntityTest.getName());
        Assert.assertEquals(itemEntity.getSellIn(), itemEntityTest.getSellIn());
        Assert.assertEquals(itemEntity.getQuality(), itemEntityTest.getQuality());
    }

    @Test
    public void testItemSaveSeveral() {

        List<ItemEntity> list = new ArrayList<>();
        list.add(new ItemEntity("1", GildedRose.AGED_BRIE_ITEM, 1, 2));
        list.add(new ItemEntity("2", GildedRose.AGED_BRIE_ITEM, 1, 2));
        list.add(new ItemEntity("3", GildedRose.AGED_BRIE_ITEM, 1, 2));
        list.add(new ItemEntity("4", GildedRose.AGED_BRIE_ITEM, 1, 2));

        itemService.save(list);

        List<ItemEntity> newItems = itemService.findAll();

        Assert.assertNotNull(newItems);
        Assert.assertTrue("Expected items list size is equal to 4.", newItems.size() == 4);
    }

    @Test
    public void testItemFindOne() {

        ItemEntity itemEntity = new ItemEntity("1", GildedRose.AGED_BRIE_ITEM, 1, 2);
        itemService.save(itemEntity);

        ItemEntity itemEntityTest = itemService.findOne(itemEntity.getId());
        Assert.assertNotNull(itemEntityTest.getId());
        Assert.assertEquals(itemEntity.getName(), itemEntityTest.getName());
        Assert.assertEquals(itemEntity.getSellIn(), itemEntityTest.getSellIn());
        Assert.assertEquals(itemEntity.getQuality(), itemEntityTest.getQuality());
    }

    @Test
    public void testItemFinByName() {

        ItemEntity itemEntity = new ItemEntity("1", GildedRose.AGED_BRIE_ITEM, 1, 2);
        itemService.save(itemEntity);

        List<ItemEntity> foundItems = itemService.findByName(itemEntity.getName());
        Assert.assertNotNull(foundItems);
        Assert.assertTrue("Expected items list size is equal to 1.", foundItems.size() == 1);
    }

    @Test
    public void testItemDelete() {

        ItemEntity itemEntity = new ItemEntity("1", GildedRose.AGED_BRIE_ITEM, 1, 2);
        itemService.save(itemEntity);
        ItemEntity itemEntityTest = itemService.findOne(itemEntity.getId());
        Assert.assertNotNull("Failed on inserting an item for deletion.", itemEntityTest.getId());

        itemService.delete(itemEntityTest);
        itemEntityTest = itemService.findOne(itemEntity.getId());
        Assert.assertNull("Failed on deleting an item.", itemEntityTest);
    }

    @Test
    public void testItemUpdate() {

        ItemEntity itemEntity = new ItemEntity("1", GildedRose.AGED_BRIE_ITEM, 1, 2);
        itemService.save(itemEntity);

        ItemEntity itemEntityTest = itemService.findOne(itemEntity.getId());
        itemEntityTest.setName("Updated name");
        itemEntityTest.setQuality(0);
        itemEntityTest.setSellIn(0);
        itemService.save(itemEntityTest);

        itemEntityTest = itemService.findOne(itemEntity.getId());

        Assert.assertNotNull(itemEntityTest);
        Assert.assertEquals("Updated name", itemEntityTest.getName());
        Assert.assertEquals(0, itemEntityTest.getSellIn());
        Assert.assertEquals(0, itemEntityTest.getQuality());
    }
}
