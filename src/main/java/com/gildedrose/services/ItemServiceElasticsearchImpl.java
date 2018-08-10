package com.gildedrose.services;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import com.gildedrose.repositories.ItemElasticsearchRepository;
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
    public void delete(ItemEntity itemEntity) {
        itemElasticsearchRepository.delete(itemEntity);
    }

    @Override
    public ItemEntity findOne(String id) {
        return itemElasticsearchRepository.findOne(id);
    }

    @Override
    public List<ItemEntity> findAll() {
        return itemElasticsearchRepository.findAll();
    }

    @Override
    public List<ItemEntity> findByName(String name) {
        return itemElasticsearchRepository.findByName(name);
    }
}
