package com.mitchelldevries.mywishlist;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.Editable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Mitchell de Vries.
 */
public class Wish extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private double target;
    private double current;
    @DrawableRes
    private int image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
