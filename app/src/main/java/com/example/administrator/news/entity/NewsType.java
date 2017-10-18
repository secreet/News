package com.example.administrator.news.entity;

/**
 * Created by Administrator on 2017/10/10.
 */

public  class NewsType {
    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

   String subgroup;
   int subid;

    public NewsType(String subgroup, int subid) {
        this.subgroup = subgroup;
        this.subid = subid;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "subgroup='" + subgroup + '\'' +
                ", subid=" + subid +
                '}';
    }
}
