package com.example.jewel.clothingrec;

/**
 * Created by jewel on 16/9/30.
 */
public class SuitCase {
    private String detail;
    private int percent;
    private int pictureId;
    SuitCase(String d,int p,int pId) {
        this.detail = d;
        this.percent = p;
        this.pictureId = pId;
    }
    public String getDetail(){
        return detail;
    }
    public int getPer(){
        return percent;
    }
    public int getPic(){
        return pictureId;
    }


}
