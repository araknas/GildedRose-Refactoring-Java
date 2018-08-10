package com.gildedrose.repositories;

import com.gildedrose.models.elasticsearch_models.ItemEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */
public interface ItemElasticsearchRepository extends ElasticsearchRepository<ItemEntity, String> {

    List<ItemEntity> findAll();
    ItemEntity findOne(String id);
    List<ItemEntity> findByName(String name);
}
