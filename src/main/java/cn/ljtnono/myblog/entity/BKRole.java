package cn.ljtnono.myblog.entity;

import java.io.Serializable;

/**
 *  角色对应的表
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
*/
public class BKRole implements Serializable {


    private static final long serialVersionUID = -7562503535532812092L;
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BKRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
