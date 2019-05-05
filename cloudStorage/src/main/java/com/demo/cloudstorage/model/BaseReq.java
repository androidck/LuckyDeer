package com.demo.cloudstorage.model;

import com.google.gson.annotations.SerializedName;

public class BaseReq {

    /**
     * hash : FncHiyyWnEzrFIS2OwXeHjeXdyHN
     * key : FncHiyyWnEzrFIS2OwXeHjeXdyHN
     * x:address : 山东济南
     * x:phone : 12345678
     */

    private String hash;
    private String key;
    /**
     * 返回信息
     */
    private String message;
    @SerializedName("x:address")
    private String _$XAddress246;
    @SerializedName("x:phone")
    private String _$XPhone25;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String get_$XAddress246() {
        return _$XAddress246;
    }

    public void set_$XAddress246(String _$XAddress246) {
        this._$XAddress246 = _$XAddress246;
    }

    public String get_$XPhone25() {
        return _$XPhone25;
    }

    public void set_$XPhone25(String _$XPhone25) {
        this._$XPhone25 = _$XPhone25;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
