package com.gildedrose.controllers;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import com.gildedrose.models.json_models.BaseJsonResponse;
import com.gildedrose.models.json_models.GildedRoseJsonDataItem;
import com.gildedrose.models.json_models.JsonDataItem;
import com.gildedrose.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */
@RestController
public class GildedRoseRestController {
    @Autowired
    ItemService itemService;

    @RequestMapping("/items")
    public ResponseEntity<?> getAllItems() throws Exception{
        try{
            List<ItemEntity> allItems = itemService.findAll();
            List<JsonDataItem> jsonDataItemList = new ArrayList<>();

            for(ItemEntity item : allItems){
                JsonDataItem jsonDataItem =
                        new GildedRoseJsonDataItem(item.getId(), item.getName(), item.getSellIn(), item.getQuality());
                jsonDataItemList.add(jsonDataItem);
            }
            return new ResponseEntity<>(createResponseBody(jsonDataItemList, HttpStatus.OK, null), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(createResponseBody(null, HttpStatus.INTERNAL_SERVER_ERROR, e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/items/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") String id) throws Exception{
        try{
            HttpStatus status = HttpStatus.OK;
            ItemEntity item = itemService.findOne(id);
            List<JsonDataItem> jsonDataItemList = new ArrayList<>();

            if(item != null){
                JsonDataItem jsonDataItem =
                        new GildedRoseJsonDataItem(item.getId(), item.getName(), item.getSellIn(), item.getQuality());
                jsonDataItemList.add(jsonDataItem);
            }
            else{
                status =  HttpStatus.NOT_FOUND;
            }
            return new ResponseEntity<>(createResponseBody(jsonDataItemList, status, null), status);
        }
        catch (Exception e){
            return new ResponseEntity<>(createResponseBody(null, HttpStatus.INTERNAL_SERVER_ERROR, e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private BaseJsonResponse createResponseBody(
            List<JsonDataItem> jsonDataItemList,
            HttpStatus status,
            Exception e) throws Exception{

        BaseJsonResponse response = new BaseJsonResponse();

        String message = "";
        if(status != null){
            message = status.getReasonPhrase();
            response.setStatus(status.value());
        }
        if(e != null){
            message += "; e = " + e.getMessage();
        }

        if(jsonDataItemList != null){
            response.setData(jsonDataItemList);
            response.setTotal(jsonDataItemList.size());
        }
        response.setMessage(message);

        return response;
    }
}
