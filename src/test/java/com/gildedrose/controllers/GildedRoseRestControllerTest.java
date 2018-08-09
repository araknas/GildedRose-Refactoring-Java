package com.gildedrose.controllers;

import com.gildedrose.GildedRoseMain;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
