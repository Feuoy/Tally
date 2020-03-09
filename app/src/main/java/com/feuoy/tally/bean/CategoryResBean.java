package com.feuoy.tally.bean;


public class CategoryResBean {

    public String title;    // 分类名称
    public int resBlack;    //  黑色资源
    public int resWhite;    //  白色资源


    public CategoryResBean(String title, int resBlack, int resWhite) {
        this.title = title;
        this.resBlack = resBlack;
        this.resWhite = resWhite;
    }

}
