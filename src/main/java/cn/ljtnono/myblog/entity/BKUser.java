package cn.ljtnono.myblog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  封装了User对象的类
 *  @author ljt
 *  @date 2018/11/18
 *@version 1.0
        */
public class BKUser implements Serializable {

    private static final long serialVersionUID = 9210459573209972528L;
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 描述
     */
    private String desc;
    /**
     * 创建的时间
     */
    private Date createDateTime;
    /**
     * 更新的时间
     */
    private Date updateDateTime;
    /**
     * qq
     */
    private String qq;
    /**
     * 电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 所拥有的角色
     */
    private List<BKRole> roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BKRole> getRoles() {
        return roles;
    }

    public void setRoles(List<BKRole> roles) {
        this.roles = roles;
    }

}
