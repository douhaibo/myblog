package cn.ljtnono.myblog.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 *  书的实体
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
*/
public class BKBook implements Serializable {


    private static final long serialVersionUID = 578000953199480981L;

    /**
     * 描述显示的最长字数
     */
    public static final Integer MAX_DESC_LENGTH = 60;

    /**
     * aboutme中书单的最大条目数
     */
    public static final Integer MAX_SHOW = 10;

    /**
     * 在redis中的缓存前缀
     */
    public static final String REDIS_PREFIX = BKBook.class.getSimpleName();

    /**
     * id
     */
    private Integer id;
    /**
     * name
     */
    private String name;
    /**
     * desc
     */
    private String desc;
    /**
     * 书的图片
     */
    private BKImg img;

    /**
     * 书的价格
     */
    private Double price;

    /**
     * 书的作者
     */
    private String author;
    /**
     * 书的出版时间
     */
    private Date publishDate;

    /**
     * 0 删除 1 正常
     */
    private Integer mode;

    private BKBook(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setDesc(builder.desc);
        setImg(builder.img);
        setPrice(builder.price);
        setAuthor(builder.author);
        setPublishDate(builder.publishDate);
        setMode(builder.mode);
    }



    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BKBook copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.desc = copy.getDesc();
        builder.img = copy.getImg();
        builder.price = copy.getPrice();
        builder.author = copy.getAuthor();
        builder.publishDate = copy.getPublishDate();
        builder.mode = copy.getMode();
        return builder;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BKImg getImg() {
        return img;
    }

    public void setImg(BKImg img) {
        this.img = img;
    }

    public BKBook() {
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private String desc;
        private BKImg img = BKImg.BKIMG_DEFAULT;
        private Double price = 0.00;
        private String author;
        private Date publishDate;
        private Integer mode = 1;

        private Builder() {
        }

        public Builder name(String val) {
            if (StringUtils.isEmpty(val)) {
                throw new IllegalArgumentException("参数错误！ name is " + val);
            }
            name = val;
            return this;
        }

        public Builder desc(String val) {
            if (StringUtils.isEmpty(val)) {
                throw new IllegalArgumentException("参数错误！ desc is " + val);
            }
            if (val.length() > MAX_DESC_LENGTH) {
                desc = val.substring(0,MAX_DESC_LENGTH);
            } else {
                desc = val;
            }
            return this;
        }

        public Builder img(BKImg val) {
            if (val == null) {
                img = BKImg.BKIMG_DEFAULT;
                return this;
            }
            //这里检查img的完整性
            img = val;
            return this;
        }

        public Builder price(Double val) {
            if (val <= 0) {
                throw new IllegalArgumentException(" price is " + val);
            }
            price = val;
            return this;
        }

        public Builder author(String val) {
            if (StringUtils.isEmpty(val)) {
                throw new IllegalArgumentException(" author is " + val);
            }
            author = val;
            return this;
        }

        public Builder publishDate(Date val) {
            publishDate = val;
            return this;
        }

        public Builder mode(Integer val) {
            if (val != 0 && val != 1) {
                throw new IllegalArgumentException(" mode cannot be " + val + " 0(删除) 1(正常)");
            }
            mode = val;
            return this;
        }

        public BKBook build() {
            return new BKBook(this);
        }
    }
}
