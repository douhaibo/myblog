package cn.ljtnono.myblog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  导航类的实体
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
*/
public class BKCategory implements Serializable {

    /**
     * 网站默认导航的id 为1
     */
    public static final Integer BLOG_CATEGORY_ID = 1;

    private static final long serialVersionUID = 7381181086230146901L;

    /**
     * 导航的id
     */
    private Integer id;
    /**
     * 对此导航的描述
     */
    private String desc;

    /**
     * 包含的链接数
     */
    private int itemCount;
    /**
     * 包含所有的链接
     */
    private List<BKLink> items;

    /**
     * 更新的时间
     */
    private Date updateDateTime;

    /**
     * 创建的时间
     */
    private Date createDateTime;

    private BKCategory(Builder builder) {
        setId(builder.id);
        setDesc(builder.desc);
        setItemCount(builder.itemCount);
        setItems(builder.items);
        setUpdateDateTime(builder.updateDateTime);
        setCreateDateTime(builder.createDateTime);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BKCategory copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.desc = copy.getDesc();
        builder.itemCount = copy.getItemCount();
        builder.items = copy.getItems();
        builder.updateDateTime = copy.getUpdateDateTime();
        builder.createDateTime = copy.getCreateDateTime();
        return builder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<BKLink> getItems() {
        return items;
    }

    public void setItems(List<BKLink> items) {
        this.items = items;
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

    public BKCategory() {
    }

    public static final class Builder {
        private Integer id;
        private String desc;
        private int itemCount;
        private List<BKLink> items;
        private Date updateDateTime;
        private Date createDateTime;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder desc(String val) {
            desc = val;
            return this;
        }

        public Builder itemCount(int val) {
            itemCount = val;
            return this;
        }

        public Builder items(List<BKLink> val) {
            items = val;
            return this;
        }

        public Builder updateDateTime(Date val) {
            updateDateTime = val;
            return this;
        }

        public Builder createDateTime(Date val) {
            createDateTime = val;
            return this;
        }

        public BKCategory build() {
            return new BKCategory(this);
        }
    }
}
