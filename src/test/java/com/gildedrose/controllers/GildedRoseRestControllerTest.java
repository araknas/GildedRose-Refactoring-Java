package com.gildedrose.controllers;

import com.gildedrose.GildedRoseMain;
import com.gildedrose.models.elasticsearch_models.ItemEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Giedrius on 2018-08-09.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GildedRoseMain.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GildedRoseRestControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    GildedRoseRestController gildedRoseRestController;

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
    public void testGildedRoseRestControllerInit(){
        Assert.assertNotNull(gildedRoseRestController);
    }
    @Test
    public void testGildedRoseRestControllerOkResponse() throws Exception{

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/items"),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testListAllItemsJsonResponse() throws Exception{

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/items"),
                HttpMethod.GET, entity, String.class);
        String expectedOutput = "{\"total\":0,\"data\":[],\"message\":\"OK\",\"status\":200}";
        Assert.assertEquals("Json response for all items is not acceptable.", expectedOutput, response.getBody());
    }

    @Test
    public void testGetItemByIdJsonResponse() throws Exception{

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/items/1"),
                HttpMethod.GET, entity, String.class);
        String expectedOutput = "{\"total\":0,\"data\":[],\"message\":\"Not Found\",\"status\":404}";
        Assert.assertEquals("Json response for item by id is not acceptable.", expectedOutput, response.getBody());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
