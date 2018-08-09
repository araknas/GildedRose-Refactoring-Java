package com.gildedrose.models.json_models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Giedrius on 2018-08-09.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "sellIn",
        "quality"
})
public class GildedRoseJsonDataItem implements JsonDataItem{

    @JsonProperty("name")
    private Integer name;
    @JsonProperty("sellIn")
    private Integer sellIn;
    @JsonProperty("quality")
    private String quality;

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getSellIn() {
        return sellIn;
    }

    public void setSellIn(Integer sellIn) {
        this.sellIn = sellIn;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
