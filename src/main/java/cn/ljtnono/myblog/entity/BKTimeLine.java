package cn.ljtnono.myblog.entity;

import java.io.Serializable;
import java.util.Date;

/** 时间轴实体
 *  @author ljt
 *  @date 2019/1/18
 *  @version 1.0
*/
public class BKTimeLine implements Serializable {

    public static final String REDIS_PREFIX = BKTimeLine.class.getSimpleName();

    private static final long serialVersionUID = -4673173248654202627L;

    private Integer id;

    private Date date;

    private Integer mode;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BKTimeLine() {}


}
