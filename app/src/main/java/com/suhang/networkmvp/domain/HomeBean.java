package com.suhang.networkmvp.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏杭 on 2016/11/14 11:52.
 */

public class HomeBean extends ErrorBean {
    public static final String URL = "api/room/historyList.php";
    public static final String METHOD = "getHistoryInfo";

    private String total = "0";

    private List<ListEntity> list = new ArrayList<>();

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "total='" + total + '\'' +
                ", list=" + list +
                '}';
    }

    public static class ListEntity {
        private String uid="";
        private String title="";
        private String orientation="";
        private String gameName="";
        private String ctime="0";
        private String isLiving="";
        private String poster="";
        private String head="";
        private String nick="";
        private String stime="";
        private String viewCount="0";

        private boolean isSwipeMenuOpen;

        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isSwipeMenuOpen() {
            return isSwipeMenuOpen;
        }

        public void setSwipeMenuOpen(boolean swipeMenuOpen) {
            isSwipeMenuOpen = swipeMenuOpen;
        }

        @Override
        public String toString() {
            return "ListEntity{" +
                    "uid='" + uid + '\'' +
                    ", title='" + title + '\'' +
                    ", orientation='" + orientation + '\'' +
                    ", gameName='" + gameName + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", isLiving='" + isLiving + '\'' +
                    ", poster='" + poster + '\'' +
                    ", head='" + head + '\'' +
                    ", nick='" + nick + '\'' +
                    ", stime='" + stime + '\'' +
                    ", viewCount='" + viewCount + '\'' +
                    '}';
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getIsLiving() {
            return isLiving;
        }

        public void setIsLiving(String isLiving) {
            this.isLiving = isLiving;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }
    }
}
