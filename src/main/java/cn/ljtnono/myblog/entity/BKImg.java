package cn.ljtnono.myblog.entity;

import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.exception.ParamNotPresentException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 *  封装了图片的信息
 *  @author ljt
 *  @date 2018/11/18
 *  @version 1.0
*/
public class BKImg implements Serializable,CheckAble{

    /**
     * 博客的默认图片的ID
     */
    public static final String BKIMG_DEFAULT_ID = "d39b45c076b14cf0a97cf1d9f6fa6363";
    /**
     * blog页面中我的相册显示的图片的个数
     */
    public static final int BKIMG_BLOGALBUM_COUNT = 6;
    /**
     * 默认的图片封装
     */
    public static final BKImg BKIMG_DEFAULT = new BKImg(BKIMG_DEFAULT_ID,"BKIMG_DEFAULT","https://www.ljtnono.cn/group1/M00/00/00/rBKlY1x-YGWAH3TFAAAWufqxBS8650.jpg",new Date(),new Date(),1);
    /**
     * img 在redis中hset的键值
     */
    public static final String BKIMG_REDIS_PREFIX_HASH = BKImg.class.getSimpleName();

    private static final long serialVersionUID = 2691027752219527925L;

    public BKImg() {
        super();
    }

    public BKImg(String id, String title,String src, Date updateDateTime, Date createDateTime,Integer mode) {
        this.id = id;
        this.title = title;
        this.src = src;
        this.updateDateTime = updateDateTime;
        this.createDateTime = createDateTime;
        this.mode = mode;
    }

    /**
     * 图片的id
     */
    @NotBlank(message = "图片的id不能为空")
    private String id;
    /**
     * 图片的标题
     */
    @NotBlank(message = "图片的title不能为空")
    private String title;

    /**
     * 图片的链接地址
     */
    @NotBlank(message = "图片的src不能为空")
    private String src;

    /**
     * 最后修改的时间
     */
    private Date updateDateTime;
    /**
     * 创建的时间
     */
    private Date createDateTime;
    /**
     * 0 删除 1 正常
     */
    @NotNull(message = "链接的模式只能为0(已删除)1(正常)")
    @Range(min = 0,max = 1,message = "链接的模式只能为0(已删除)1(正常)")
    private Integer mode;

    private BKImg(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setSrc(builder.src);
        setUpdateDateTime(builder.updateDateTime);
        setCreateDateTime(builder.createDateTime);
        setMode(builder.mode);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BKImg copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.title = copy.getTitle();
        builder.src = copy.getSrc();
        builder.updateDateTime = copy.getUpdateDateTime();
        builder.createDateTime = copy.getCreateDateTime();
        builder.mode = copy.getMode();
        return builder;
    }


    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BKImg{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", src='" + src + '\'' +
                ", updateDateTime=" + updateDateTime +
                ", createDateTime=" + createDateTime +
                ", mode=" + mode +
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
        BKImg bkImg = (BKImg) o;
        return Objects.equals(id, bkImg.id) &&
                Objects.equals(title, bkImg.title) &&
                Objects.equals(src, bkImg.src) &&
                Objects.equals(updateDateTime, bkImg.updateDateTime) &&
                Objects.equals(createDateTime, bkImg.createDateTime) &&
                Objects.equals(mode, bkImg.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, src, updateDateTime, createDateTime, mode);
    }

    /**
     * @return true 没有问题
     * @throws FieldIllegalException 记录哪个字段出现问题
     */
    @Override
    public boolean check() throws FieldIllegalException{
        if (StringUtils.isEmpty(this.getId())) {
            throw new FieldIllegalException("图片的id" + "不能为" + this.getId());
        }
        if (StringUtils.isEmpty(this.getTitle())) {
            throw new FieldIllegalException("图片的title" + "不能为" + this.getTitle());
        }
        if (StringUtils.isEmpty(this.getSrc())) {
            throw new FieldIllegalException("图片的src" + "不能为" + this.getSrc());
        }
        boolean mode = this.getMode() == null || (this.getMode() != 0 && this.getMode() != 1);
        if (mode) {
            throw new FieldIllegalException("图片的mode" + "只能为 0(已经删除)1(正常)");
        }
        return true;
    }


    public static final class Builder {
        private String id;
        private String title;
        private String src;
        private Date updateDateTime = new Date();
        private Date createDateTime = new Date();
        private Integer mode = 1;

        private Builder() {
        }

        public Builder id(String val) {
            id = val;
            // 默认的id 是直接生成的UUID
            if (StringUtils.isEmpty(id)) {
                id = UUID.randomUUID().toString().replace("-","");
            }
            return this;
        }

        public Builder title(String val) {
            title = val;
            if (StringUtils.isEmpty(title)) {
                title = src.substring(src.lastIndexOf("/") + 1);
            }
            return this;
        }

        public Builder src(String val) {
            src = val;
            if (StringUtils.isEmpty(src)) {
                throw new IllegalArgumentException("路径不能为空！");
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
        public BKImg build() {
            return new BKImg(this);
        }
    }

}
