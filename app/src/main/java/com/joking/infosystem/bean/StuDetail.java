package com.joking.infosystem.bean;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class StuDetail extends StuBase {
    private int id;
    private int NO_id;
    private String detail;

    @Override
    public String getContent() {
        return detail;
    }

    public StuDetail() {

    }

    public StuDetail(int NO_id, String detail) {
        this.detail = detail;
        this.NO_id = NO_id;
    }

    @Override
    public String toString() {
        return "StuDetail[" +
                " detail=" + detail +
                " NO_id=" + NO_id + "]";
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNO_id() {
        return NO_id;
    }

    @Override
    public void setNO_id(int NO_id) {
        this.NO_id = NO_id;
    }

    @Override
    public void setContent(String content) {
        this.detail = content;
    }
}
