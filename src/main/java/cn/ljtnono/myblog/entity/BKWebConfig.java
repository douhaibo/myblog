package cn.ljtnono.myblog.entity;

import java.io.Serializable;

/**
 *  整个网站的信息
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
*/
public class BKWebConfig implements Serializable {


    /**
     * 默认的webConfigId 是 1
     */
    public static final Integer BLOG_WEBCONFIG_DEFAULT = 1;

    private static final long serialVersionUID = -3093355875661251954L;
    /**
     * 配置的id
     */
    private Integer id;

    /**
     * blog的名字
     */
    private String blogName;

    /**
     * blog主
     */
    private String blogAuthor;
    /**
     * 网站备案
     */
    private String record;

    /**
     * 改自思欲主题
     */
    private String from;

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogAuthor() {
        return blogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        this.blogAuthor = blogAuthor;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
