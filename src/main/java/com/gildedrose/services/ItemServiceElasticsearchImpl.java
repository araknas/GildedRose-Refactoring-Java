package com.gildedrose.services;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import com.gildedrose.repositories.ItemElasticsearchRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */
@Service
public class ItemServiceElasticsearchImpl implements ItemService {

    @Autowired
    private ItemElasticsearchRepository itemElasticsearchRepository;

    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        return itemElasticsearchRepository.save(itemEntity);
    }

    @Override
    public void save(List<ItemEntity> itemEntities) {
        if(itemEntities != null && itemEntities.size() > 0){
            Iterable<ItemEntity> iterableList = itemEntities;
            itemElasticsearchRepository.save(iterableList);
        }
    }

    @Override
    public void delete(ItemEntity itemEntity) {
        itemElasticsearchRepository.delete(itemEntity);
    }

    @Override
    public ItemEntity findOne(String id) {
        return itemElasticsearchRepository.findOne(id);
    }

    @Override
    public List<ItemEntity> findAll()
    {
        return Lists.newArrayList(itemElasticsearchRepository.findAll());
    }

    @Override
    public List<ItemEntity> findByName(String name) {
        return itemElasticsearchRepository.findByName(name);
    }
}
