package org.tensorflow.demo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ImageSearchListVO implements Serializable {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("url")
    @Expose
    private UrlVO urlvo;
    @SerializedName("queries")
    @Expose
    private QueriesVO queriesvo;
    @SerializedName("context")
    @Expose
    private ContextVO contextvo;
    @SerializedName("searchInformation")
    @Expose
    private SearchInformationVO searchInformationvo;
    @SerializedName("items")
    @Expose
    private List<ItemVO> itemsvo = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public UrlVO getUrlVo() {
        return urlvo;
    }

    public void setUrlVo(UrlVO urlvo) {
        this.urlvo = urlvo;
    }

    public QueriesVO getQueriesVo() {
        return queriesvo;
    }

    public void setQueriesVo(QueriesVO queriesvo) {
        this.queriesvo = queriesvo;
    }

    public ContextVO getContextVo() {
        return contextvo;
    }

    public void setContext(ContextVO contextvo) {
        this.contextvo = contextvo;
    }

    public SearchInformationVO getSearchInformationVo() {
        return searchInformationvo;
    }

    public void setSearchInformationVo(SearchInformationVO searchInformationvo) {
        this.searchInformationvo = searchInformationvo;
    }

    public List<ItemVO> getItemsVo() {
        return itemsvo;
    }

    public void setItems(List<ItemVO> itemsvo) {
        this.itemsvo = itemsvo;
    }

}
