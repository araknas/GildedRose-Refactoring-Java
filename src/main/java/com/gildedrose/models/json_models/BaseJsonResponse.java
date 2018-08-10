package com.gildedrose.models.json_models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by Giedrius on 2018-08-09.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total",
        "data",
        "status:",
        "message"
})
public class BaseJsonResponse {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("data")
    private List<JsonDataItem> data = null;
    @JsonProperty("status")
    private Integer status = HttpStatus.OK.value();
    @JsonProperty("message")
    private String message = "";

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
