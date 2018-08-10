package com.gildedrose.services;

import com.gildedrose.models.elasticsearch_models.ItemEntity;

import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */
public interface ItemService {

    ItemEntity save(ItemEntity itemEntity);

    void delete(ItemEntity itemEntity);

    ItemEntity findOne(String id);

    List<ItemEntity> findAll();

    List<ItemEntity> findByName(String name);
}
