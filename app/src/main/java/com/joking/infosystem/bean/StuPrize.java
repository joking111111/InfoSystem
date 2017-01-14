package com.joking.infosystem.bean;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class StuPrize extends StuBase {
    private int id;
    private int NO_id;
    private String prize;

    @Override
    public String getContent() {
        return prize;
    }

    public StuPrize() {

    }

    public StuPrize(int NO_id, String prize) {

        this.NO_id = NO_id;
        this.prize = prize;
    }

    @Override
    public String toString() {
        return "StuPrize[" +
                " NO_id=" + NO_id +
                " prize=" + prize + "]";
    }

    public int getNO_id() {
        return NO_id;
    }

    @Override
    public void setNO_id(int NO_id) {
        this.NO_id = NO_id;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setContent(String content) {
        this.prize = content;
    }
}
