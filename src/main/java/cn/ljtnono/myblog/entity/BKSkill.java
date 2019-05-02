package cn.ljtnono.myblog.entity;

import java.io.Serializable;

/**
 *  个人技能
 *  @author ljt
 *  @date 2019/1/17
 *  @version 1.0
*/
public class BKSkill implements Serializable {


    private static final long serialVersionUID = -5842365675618464406L;

    public static final String BKSKILL_REDIS_HASH_KEY = "BKSkill";
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 分数
     */
    private Integer grade;
    /**
     * 0 删除 1 正常
     */
    private Integer mode;

    public BKSkill() {
    }

    private BKSkill(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setGrade(builder.grade);
        setMode(builder.mode);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BKSkill copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.grade = copy.getGrade();
        builder.mode = copy.getMode();
        return builder;
    }

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Integer grade;
        private Integer mode = 1;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder grade(Integer val) {
            grade = val;
            return this;
        }

        public Builder mode(Integer val) {
            mode = val;
            return this;
        }

        public BKSkill build() {
            return new BKSkill(this);
        }
    }
}
