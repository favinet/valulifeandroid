package org.tensorflow.demo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QueriesVO implements Serializable {

    @SerializedName("request")
    @Expose
    private List<RequestVO> requestvo = null;
    @SerializedName("nextPage")
    @Expose
    private List<NextPageVO> nextPagevo = null;

    public List<RequestVO> getRequestVo() {
        return requestvo;
    }

    public void setRequestVo(List<RequestVO> request) {
        this.requestvo = requestvo;
    }

    public List<NextPageVO> getNextPageVo() {
        return nextPagevo;
    }

    public void setNextPageVo(List<NextPageVO> nextPagevo) {
        this.nextPagevo = nextPagevo;
    }

}
