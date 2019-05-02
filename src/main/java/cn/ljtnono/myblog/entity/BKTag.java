package cn.ljtnono.myblog.entity;

import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.validate.tag.DeleteTagByIdGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *  页面上标签的抽象
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
*/
public class BKTag implements Serializable, CheckAble{


    private static final long serialVersionUID = -4215085816155859168L;
    /**
     * 标签的id
     */
    @NotNull(message = "id不能为空！", groups = {DeleteTagByIdGroup.class})
    @Range(min = 1, message = "id要不能小于1", groups = {DeleteTagByIdGroup.class})
    private Integer id;
    /**
     * 标签的链接
     */
    private BKLink link;

    /**
     * 标签的标题
     */
    @NotBlank(message = "标签不能为空！")
    @Length(min = 1, max = 10, message = "标签必须在1-10个字符！")
    private String title;

    /**
     * 创建的时间
     */
    private Date createDateTime;
    /**
     * 最后更新的时间
     */
    private Date updateDateTime;

    /**
     * 0 删除 1 正常
     */
    @NotNull(message = "模式只能为0(已删除)1(正常)")
    @Range(min = 0,max = 1,message = "模式只能为0(已删除)1(正常)")
    private Integer mode;

    public BKTag() {
    }

    private BKTag(Builder builder) {
        setId(builder.id);
        setCreateDateTime(builder.createDateTime);
        setLink(builder.link);
        setMode(builder.mode);
        setUpdateDateTime(builder.updateDateTime);
        setTitle(builder.title);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BKTag copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.title = copy.getTitle();
        builder.createDateTime = copy.getCreateDateTime();
        builder.link = copy.getLink();
        builder.mode = copy.getMode();
        builder.updateDateTime = copy.getUpdateDateTime();
        return builder;
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

    public BKLink getLink() {
        return link;
    }

    public void setLink(BKLink link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "BKTag{" +
                "id=" + id +
                ", link=" + link +
                ", title='" + title + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BKTag tag = (BKTag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(link, tag.link) &&
                Objects.equals(title, tag.title) &&
                Objects.equals(createDateTime, tag.createDateTime) &&
                Objects.equals(updateDateTime, tag.updateDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, title, createDateTime, updateDateTime);
    }

    /**
     * 检查整个实体的每个字段是否合法
     *
     * @return true 没有问题
     * @throws FieldIllegalException 记录哪个字段出现问题
     */
    @Override
    public boolean check() throws FieldIllegalException {
        return false;
    }

    /**
     *  建造者模式
     *  @author ljt
     *  @date 2019/1/17
     *  @version 1.0
    */
    public static final class Builder {
        private Integer id;
        private String title;
        private BKLink link;
        private Date createDateTime;
        private Date updateDateTime;
        private Integer mode;

        public Builder id(Integer val) {
            id = val;
            // id 在设置的时候都是自己自增的
            return this;
        }

        public Builder title(String val) {
            // 标题不能超过4个字符
            if (StringUtils.isEmpty(val)) {
                throw new IllegalArgumentException("标题不能为空！");
            }
            if (val.length() < 1 || val.length() > 10) {
                throw new IllegalArgumentException("标题的长度不能超过10个字符！");
            }
            title = val;
            return this;
        }
        public Builder link(BKLink val) {
            link = val;
            return this;
        }
        public Builder createDateTime(Date val) {
            createDateTime = val;
            if (createDateTime == null) {
                createDateTime = new Date();
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
        public Builder mode(Integer val) {
            mode = val;
            if (mode != 0 && mode != 1) {
                throw new IllegalStateException("mode 只能为0(已经删除)或者1(正常)");
            }
            return this;
        }

        public BKTag build() {
            return new BKTag(this);
        }
    }


}
