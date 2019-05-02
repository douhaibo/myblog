package cn.ljtnono.myblog.entity;

import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.utils.HTMLUtil;
import cn.ljtnono.myblog.validate.blog.AddBlogGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Date;


/**
 *  博客实体类
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
 */
public class BKBlog implements Serializable, CheckAble {


    private static final long serialVersionUID = -1593858170919978328L;

    public static final String REDIS_PREFIX = BKBlog.class.getSimpleName();

    public static final int SUMMARY_LENGTH_MAX = 150;

    public static final int TITLE_LENGTH_MAX = 30;

    public static final int TITLE_LENGTH_MIN = 4;

    /**
     * 博文的id
     */
    private Integer id;
    /**
     * 博文的标题
     */
    @NotEmpty(message = "博客的标题不能为空！", groups = {AddBlogGroup.class})
    @Length(min = TITLE_LENGTH_MIN, max = TITLE_LENGTH_MAX, message = "博客的标题4-30个字符", groups = {AddBlogGroup.class})
    private String title;
    /**
     * 博文的HTML文本
     */
    @NotEmpty(message = "博客的内容不能为空！", groups = {AddBlogGroup.class})
    private String contentHtml;
    /**
     * 存储了博文的markDown文本
     */
    @NotEmpty(message = "博客的内容不能为空！", groups = {AddBlogGroup.class})
    private String contentMarkDown;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 标签
     */
    private BKTag tag;
    /**
     * 创建的时间
     */
    private Date createDateTime;
    /**
     * 最后更新的时间
     */
    private Date updateDateTime;

    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     *  博客项的封面的图片
     */
    private BKImg coverImg;

    /**
     * 是否删除
     * 0 删除  1 正常
     */
    @Range(min = 0, max = 1, message = "模式只能为已删除(0)正常(1)", groups = {AddBlogGroup.class})
    private Integer mode;

    private BKBlog(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setContentHtml(builder.contentHtml);
        setContentMarkDown(builder.contentMarkDown);
        setSummary(builder.summary);
        setTag(builder.tag);
        setCreateDateTime(builder.createDateTime);
        setUpdateDateTime(builder.updateDateTime);
        setCommentCount(builder.commentCount);
        setViewCount(builder.viewCount);
        setCoverImg(builder.coverImg);
        setMode(builder.mode);
    }

    public BKBlog(){}

    public static Builder newBuilder() {
        return new Builder();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getContentMarkDown() {
        return contentMarkDown;
    }

    public void setContentMarkDown(String contentMarkDown) {
        this.contentMarkDown = contentMarkDown;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BKTag getTag() {
        return tag;
    }

    public void setTag(BKTag tag) {
        this.tag = tag;
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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public BKImg getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(BKImg coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    /**
     * 检查整个实体的每个字段是否合法
     * @return true 没有问题
     * @throws FieldIllegalException 记录哪个字段出现问题
     */
    @Override
    public boolean check() throws FieldIllegalException {
        boolean title = StringUtils.isEmpty(this.getTitle()) || StringUtils.length(this.getTitle()) > 30 || StringUtils.length(this.getTitle()) < 4;
        boolean contentHtml = StringUtils.isEmpty(this.getContentHtml());
        boolean contentMarkDown = StringUtils.isEmpty(this.getContentMarkDown());
        boolean mode = this.getMode() == null || (this.getMode() != 0 && this.getMode() != 1);
        boolean[] is = {title,contentHtml,contentMarkDown,mode};
        String[] messages = {
                "博客的标题必须在4-30个字符",
                "博客内容不能为空",
                "博客内容不能为空",
                "链接的模式只能为0(已删除)1(正常)"
        };
        for (int i = 0; i < is.length; i++) {
            if (is[i]) {
                throw new FieldIllegalException(messages[i]);
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "BKBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                ", contentMarkDown='" + contentMarkDown + '\'' +
                ", summary='" + summary + '\'' +
                ", tag=" + tag +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", coverImg=" + coverImg +
                ", mode=" + mode +
                '}';
    }

    public static final class Builder {
        private Integer id;
        private String title;
        private String contentHtml;
        private String contentMarkDown;
        private String summary;
        private BKTag tag;
        private Date createDateTime = new Date();
        private Date updateDateTime = new Date();
        private Integer commentCount = 0;
        private Integer viewCount = 0;
        private BKImg coverImg;
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
            if (StringUtils.isEmpty(val) || val.length() < 4 || val.length() > 30) {
                throw new IllegalArgumentException("博客的标题长度为4-30");
            }
            title = val;
            return this;
        }

        public Builder contentHtml(String val) {
            if (StringUtils.isEmpty(val)) {
                throw new IllegalArgumentException("博客内容不能为空！");
            }
            contentHtml = val;
            return this;
        }

        public Builder contentMarkDown(String val) {
            if (StringUtils.isEmpty(val)) {
                throw new IllegalArgumentException("博客内容不能为空！");
            }
            contentMarkDown = val;
            return this;
        }

        public Builder summary(String val) {
            summary = val;
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
            if (val != 0 && val != 1) {
                throw new IllegalArgumentException(" mode cannot be " + val + " 0(删除) 1(正常)");
            }
            mode = val;
            return this;
        }

        public BKBlog build() {
            return new BKBlog(this);
        }
    }

    /**
     * 自动生成博客的摘要信息
     */
    public void generateSummary() {
        // summary最多150个字符
        String html = getContentHtml();
        if (StringUtils.isEmpty(html)) {
            throw new IllegalArgumentException("博客内容不能为空！");
        }
        String content = HTMLUtil.delHTMLTag(html);
        if (content.length() > SUMMARY_LENGTH_MAX) {
            content = content.substring(0, SUMMARY_LENGTH_MAX);
        }
        setSummary(content);
    }
}
