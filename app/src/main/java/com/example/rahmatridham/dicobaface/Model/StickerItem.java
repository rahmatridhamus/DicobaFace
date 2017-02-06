package com.example.rahmatridham.dicobaface.Model;

/**
 * Created by rahmatridham on 1/9/2017.
 */

public class StickerItem {
    String title;
    int image;

    public StickerItem(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
