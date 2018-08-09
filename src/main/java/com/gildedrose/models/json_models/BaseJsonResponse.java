package com.gildedrose.models.json_models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total",
        "data",
        "success"
})
public class BaseJsonResponse {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("data")
    private List<JsonDataItem> data = null;
    @JsonProperty("success")
    private Boolean success;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<JsonDataItem> getData() {
        return data;
    }

    public void setData(List<JsonDataItem> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
