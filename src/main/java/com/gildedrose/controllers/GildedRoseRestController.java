package com.gildedrose.controllers;

import com.gildedrose.models.json_models.BaseJsonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Giedrius on 2018-08-09.
 */
@RestController
public class GildedRoseRestController {
    @RequestMapping("/items")
    public @ResponseBody BaseJsonResponse getAllItems() throws Exception{
        BaseJsonResponse response = new BaseJsonResponse();
        // TODO: implement items retrieve
        return response;
    }
}
