package com.example.administrator.news.entity;

import java.io.Serializable;
import java.util.List;

/**
 *新闻列表实体类
 *Gson解析规则，遇到大括号写类，遇到中括号写集合
 */

public class NewsListInfo implements Serializable{

    private int status;
    private String message;
    private List<News> data;


    public static class News implements Serializable{
        private int nid;
        private String stamp;
        private String icon;
        private String title;
        private String summary;
        private String link;


        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "News{" +
                    "nid=" + nid +
                    ", stamp='" + stamp + '\'' +
                    ", icon='" + icon + '\'' +
                    ", title='" + title + '\'' +
                    ", summary='" + summary + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NewsListInfo{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
