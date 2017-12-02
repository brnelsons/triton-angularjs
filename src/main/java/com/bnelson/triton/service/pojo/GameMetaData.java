package com.bnelson.triton.service.pojo;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
public class GameMetaData implements Serializable{

    private String name;
    private String url;
    private String imageUrl;
    private String status;
    private List<String> commands;

    public GameMetaData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
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
