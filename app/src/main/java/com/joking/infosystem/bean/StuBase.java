package com.joking.infosystem.bean;

import org.litepal.crud.DataSupport;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public abstract class StuBase extends DataSupport {
    public abstract int getId();

    public abstract String getContent();

    public abstract void setNO_id(int NO_id);

    public abstract void setContent(String content);
}
