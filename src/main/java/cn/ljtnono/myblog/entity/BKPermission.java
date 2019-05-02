package cn.ljtnono.myblog.entity;

import java.io.Serializable;

/**
 *  权限
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
*/
public class BKPermission implements Serializable {


    private static final long serialVersionUID = 3546190783278085850L;
    /**
     * id
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限对应的资源
     */
    private String res;

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

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "BKPermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", res='" + res + '\'' +
                '}';
    }
}
