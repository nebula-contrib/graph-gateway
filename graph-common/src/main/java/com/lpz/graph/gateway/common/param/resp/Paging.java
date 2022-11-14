package com.lpz.graph.gateway.common.param.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 */
@ApiModel("分页")
public class Paging<T> implements Serializable {
    private static final long serialVersionUID = -1683800405530086022L;

    @ApiModelProperty("总行数")
    @JSONField(name = "total")
    @JsonProperty("total")
    private long total = 0;

    @ApiModelProperty("数据列表")
    @JSONField(name = "records")
    @JsonProperty("records")
    private List<T> records = Collections.emptyList();

    public Paging() {
    }

    public Paging(IPage page) {
        this.total = page.getTotal();
        this.records = page.getRecords();
    }

    public Paging(long total, List<T> records) {
        this.total = total;
        this.records = records;
    }

    /**
     *
     * @param page
     */
    public void addPaging(IPage page) {
        this.total += page.getTotal();
        records.addAll(page.getRecords());
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Paging)) {
            return  false;
        }
        Paging paging = (Paging) obj;
        if (this.total != paging.total) {
            return false;
        }
        if (records == null && paging.records == null) {
            return true;
        }
        if ((records == null && paging.records != null) || (records != null && paging.records == null)
                || (records.size() != paging.records.size())) {
            return false;
        }
        for (int i = 0; i < records.size(); i++) {
            if (!records.get(i).equals(paging.records.get(i))) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Paging{" +
                "total=" + total +
                ", records=" + records +
                '}';
    }
}
