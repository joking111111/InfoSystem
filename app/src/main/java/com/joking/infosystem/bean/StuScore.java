package com.joking.infosystem.bean;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class StuScore extends StuBase {
    private int id;
    private int NO_id;
    private String score;

    @Override
    public String getContent() {
        return score;
    }

    public StuScore() {

    }

    public StuScore(int NO_id, String score) {

        this.NO_id = NO_id;
        this.score = score;
    }

    @Override
    public String toString() {
        return "StuScore[" +
                " NO_id=" + NO_id +
                " score=" + score + "]";
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
        this.score = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
