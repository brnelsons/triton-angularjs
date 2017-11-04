package com.bnelson.triton.pojo;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
public class GameMetaData implements Serializable{

    private String name;
    private String url;
    private String status;

    public GameMetaData() {
    }

    public GameMetaData(String name, String url, String status) {
        this.name = name;
        this.url = url;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMetaData that = (GameMetaData) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(url, that.url) &&
                Objects.equal(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, url, status);
    }
}
