package cn.ljtnono.myblog.entity;

import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.validate.link.AddLinkGroup;
import cn.ljtnono.myblog.validate.link.DeleteLinkByIdGroup;
import cn.ljtnono.myblog.validate.link.UpdateLinkGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *  封装了链接资源的类
 *  @author ljt
 *  @date 2018/11/18
 *  @version 1.0
*/
public class BKLink implements Serializable,CheckAble {

    private static final long serialVersionUID = 8655680497203147600L;
    /**
     * 链接的id
     */
    @NotNull(message = "id不能为null",groups = {DeleteLinkByIdGroup.class, UpdateLinkGroup.class})
    @Range(message = "id不能小于1",min = 1,groups = {DeleteLinkByIdGroup.class,UpdateLinkGroup.class})
    private Integer id;

    /**
     * 链接的标题
     */
    @NotBlank(message = "链接标题不能为空",groups = {AddLinkGroup.class,UpdateLinkGroup.class})
    @Length(min = 4,max = 20,message = "链接的标题必须在4-20个字符",groups = {AddLinkGroup.class,UpdateLinkGroup.class})
    private String title;

    /**
     * 链接的类型
     * 1、本站的链接
     * 2、外部链接
     */
    @NotNull(message = "链接的类型只能为1(本站的链接)2(外链)",groups = {AddLinkGroup.class,UpdateLinkGroup.class})
    @Range(min = 1,max = 2,message = "链接的类型只能为1(本站的链接)2(外链)",groups = {AddLinkGroup.class,UpdateLinkGroup.class})
    private Integer type;
    /**
     * 链接的类名数组
     * 用于使用模糊查询的标志
     */
    private String classNames;
    /**
     * 链接地址
     */
    @NotBlank(message = "链接地址不能为空",groups = {AddLinkGroup.class})
    private String href;
    /**
     * 最后更新的时间
     */
    private Date updateDateTime;
    /**
     * 创建的时间
     */
    private Date createDateTime;

    /**
     * 0 已经删除
     * 1 正常
     */
    @NotNull(message = "链接的模式只能为0(已删除)1(正常)",groups = {AddLinkGroup.class})
    @Range(min = 0,max = 1,message = "链接的模式只能为0(已删除)1(正常)",groups = {AddLinkGroup.class})
    private Integer mode;

    private BKLink(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setType(builder.type);
        setClassNames(builder.classNames);
        setHref(builder.href);
        setUpdateDateTime(builder.updateDateTime);
        setCreateDateTime(builder.createDateTime);
        setMode(builder.mode);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BKLink copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.title = copy.getTitle();
        builder.type = copy.getType();
        builder.classNames = copy.getClassNames();
        builder.href = copy.getHref();
        builder.updateDateTime = copy.getUpdateDateTime();
        builder.createDateTime = copy.getCreateDateTime();
        builder.mode = copy.getMode();
        return builder;
    }

    public String getTitle() {
        return title;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Override
    public String toString() {
        return "BKLink{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", classNames='" + classNames + '\'' +
                ", href='" + href + '\'' +
                ", updateDateTime=" + updateDateTime +
                ", createDateTime=" + createDateTime +
                ", mode=" + mode +
                '}';
    }

    /**
     * 检查Object的合法性
     *
     * @return true 没有问题  false 不是本类型的对象
     * @throws FieldIllegalException 记录哪个字段出现问题
     */
    @Override
    public boolean check() throws FieldIllegalException {
        boolean title = StringUtils.isEmpty(this.getTitle()) || StringUtils.length(this.getTitle()) > 20 || StringUtils.length(this.getTitle()) < 4;
        boolean mode = this.getMode() == null || (this.getMode() != 0 && this.getMode() != 1);
        boolean type = this.getType() == null || (this.getType() != 1 && this.getType() != 2);
        boolean href = StringUtils.isEmpty(this.getHref());
        boolean[] is = {title,mode,type,href};
        String[] messages = {
                "链接的标题必须在4-20个字符",
                "链接的模式只能为0(已删除)1(正常)",
                "链接的类型只能为1(本站的链接)2(外链)",
                "链接地址不能为空"
        };
        for (int i = 0; i < is.length; i++) {
            if (is[i]) {
                throw new FieldIllegalException(messages[i]);
            }
        }
        return true;
    }

    public static final class Builder {
        private Integer id;
        private String title;
        private Integer type = 1; //默认是本站的链接
        private String classNames;
        private String href;
        private Date updateDateTime = new Date();
        private Date createDateTime = new Date();
        private Integer mode = 1;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("id不能为" + id);
            }
            return this;
        }

        public Builder title(String val) {
            title = val;
            if (StringUtils.isEmpty(title)) {
                throw new IllegalArgumentException("title不能为" + title);
            }
            return this;
        }

        public Builder type(Integer val) {
            type = val;
            if (type != 1 && type != 2) {
                throw new IllegalArgumentException("type只能为1(本站的链接)2(外链)");
            }
            return this;
        }

        public Builder classNames(String val) {
            classNames = val;
            if (StringUtils.isEmpty(classNames)) {
                throw new IllegalArgumentException("classNames不能为" + classNames);
            }
            return this;
        }

        public Builder href(String val) {
            href = val;
            if (StringUtils.isEmpty(href)) {
                throw new IllegalArgumentException("href不能为" + href);
            }
            return this;
        }

        public Builder updateDateTime(Date val) {
            updateDateTime = val;
            if (updateDateTime == null) {
                updateDateTime = new Date();
            }
            return this;
        }

        public Builder createDateTime(Date val) {
            createDateTime = val;
            if (createDateTime == null) {
                createDateTime = new Date();
            }
            return this;
        }

        public Builder mode(Integer val) {
            mode = val;
            if (mode != 0 && mode != 1) {
                throw new IllegalStateException("mode 只能为0(已经删除)或者1(正常)");
            }
            return this;
        }
        public BKLink build() {
            return new BKLink(this);
        }
    }

    public BKLink() {
    }
}
