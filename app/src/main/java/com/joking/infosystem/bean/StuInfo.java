package com.joking.infosystem.bean;

import org.litepal.crud.DataSupport;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class StuInfo extends DataSupport {
    public static final String ID = "id";

    private int id;
    private String name;
    private int class_id;
    private int NO_id;
    private int pic_id;

    public StuInfo() {

    }

    public StuInfo(String name, int class_id, int NO_id, int pic_id) {
        this.name = name;
        this.class_id = class_id;
        this.NO_id = NO_id;
        this.pic_id = pic_id;
    }

    @Override
    public String toString() {
        return "StuInfo[" +
                " id=" + id +
                " name=" + name +
                " class_id=" + class_id +
                " NO_id=" + NO_id +
                " pic_id=" + pic_id + "]";
    }

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

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getNO_id() {
        return NO_id;
    }

    public void setNO_id(int NO_id) {
        this.NO_id = NO_id;
    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }
}
