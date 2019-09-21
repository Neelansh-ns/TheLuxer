package com.e.theluxur;

public class menuone {
    private  String title;
    private String images;

    public menuone(String title,String images) {
        this.title=title;

        this.images=images;
    }


    public String getTitle(){
        return title;
    }


    public String getImages() {
        return images;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setImages(String images) {
        this.images = images;
    }


}

