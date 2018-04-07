package com.odbpo.fenggou.rxjavaretrofit.bean;

/**
 * @author: zc
 * @Time: 2017/11/24 9:08
 * @Desc:
 */
public class MemberLevelBean {

    /**
     * id : 92
     * name : 普通会员
     * pointNeed : 0~9999999
     * pointDiscount : 1
     * deleteFlag : 0
     */

    private int id;
    private String name;
    private String pointNeed;
    private int pointDiscount;
    private String deleteFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPointNeed() {
        return pointNeed;
    }

    public void setPointNeed(String pointNeed) {
        this.pointNeed = pointNeed;
    }

    public int getPointDiscount() {
        return pointDiscount;
    }

    public void setPointDiscount(int pointDiscount) {
        this.pointDiscount = pointDiscount;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
