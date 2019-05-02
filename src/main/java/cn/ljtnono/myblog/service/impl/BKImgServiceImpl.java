package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.exception.DataBaseOperationException;
import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.fastdfs.FastDFSClient;
import cn.ljtnono.myblog.fastdfs.FastDFSException;
import cn.ljtnono.myblog.mapper.BKImgMapper;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKImgService;
import cn.ljtnono.myblog.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 处理图像业务的实现类
 *
 * @author ljt
 * @version 1.0
 * @date 2018/12/10
 */
@Service
public class BKImgServiceImpl extends EssentialServiceImpl implements BKImgService {

    @Autowired
    private BKImgMapper imgMapper;

    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(BKImgServiceImpl.class);

    /**
     * 获取首页的相册的内容
     *
     * @return Set<BKImg>
     */
    @Override
    public Set<BKImg> getBlogAlbum() {
        Set<BKImg> blogAlbum = imgMapper.getBlogAlbum(BKImg.BKIMG_BLOGALBUM_COUNT);
        if (blogAlbum.size() == 0) {
            logger.info("数据库中没有数据！");
        }
        //对Set进行按照修改时间进行排序,这里创建了一个TreeSet 按照修改时间降序进行排列
        TreeSet<BKImg> imgList = new TreeSet<>((o1, o2) -> (int) (o2.getUpdateDateTime().getTime() - o1.getUpdateDateTime().getTime()));
        imgList.addAll(blogAlbum);
        return imgList;
    }

    /**
     * 根据Id数组获取图片列表
     *
     * @param imgArray 图像数组
     * @return 该数组对应的img实体
     */
    @Override
    public Set<BKImg> getImgSetByIdArray(Integer[] imgArray) {
        if (imgArray == null || imgArray.length == 0) {
            logger.info("参数错误！");
            return new HashSet<>();
        }
        return imgMapper.getImgSetByIdArray(imgArray);
    }


    /**
     * 处理图片上传的请求
     *
     * @param file 需要上传的文件
     * @return 成功或者失败的信息
     */
    @Override
    public JSONObject uploadImg(MultipartFile file) {
        JSONObject res = new JSONObject();
        //保存
        try {
            // TODO 待写blog相关的类的时候进行重构
            BKImg img = BKImg.newBuilder()
                    .src(file.getName())
                    .id(null)
                    .title(file.getOriginalFilename())
                    .mode(1)
                    .build();
            boolean addResult = addOne(img, true, file);
            if (addResult) {
                String src = getOne(img).getSrc();
                res.accumulate("url", src);
                res.accumulate("success", 1);
                res.accumulate("message", "上传成功!");
            }
        } catch (Exception e) {
            logger.error("文件上传出现错误！原因：" + e.getMessage());
            res.accumulate("success", 0);
            res.accumulate("message", "上传失败！");
        }
        return res;
    }

    /**
     * 通过图片的Url数组，来获取图片的IdList
     *
     * @param imageSrcArray 图片的Url数组
     * @return 图片的IdList
     */
    @Override
    public List<Integer> getImageIdListBySrcArray(String[] imageSrcArray) {
        if (imageSrcArray == null || imageSrcArray.length == 0) {
            logger.info("url数组为空！");
            return new ArrayList<>();
        }
        final List<Integer> imageIdList = imgMapper.getImageIdListBySrcArray(imageSrcArray);
        if (imageIdList.size() == 0) {
            logger.info("没有数据");
        }
        return imageIdList;
    }

    /**
     * 根据图片的scr 获取图片的id
     *
     * @param src 图片的src
     * @return 图片的id
     */
    @Override
    public String getImgIdBySrc(String src) {
        return imgMapper.getImgIdBySrc(src);
    }


    /**
     * 根据图片的id 获取图片的fastdfsid
     *
     * @param id 图片的id
     * @return 如果是存储在fastdfs的图片那么返回其src（即fastdfsid） ，如果不是 返回 null
     * @throws IllegalArgumentException id参数为null或者空字符串时抛出
     */
    private String getImgFastDFSIdById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("图片id不能为null或者空字符串！");
        } else {
            // 根据id获取图片的src
            final String src = imgMapper.getImgSrcById(id);
            // 识别src 是否是fastdfs类型的
            // 如果是带有 http 和 group 字样的表示是存储在fastdfs中的图片类型
            if (src != null) {
                boolean isFastdfs = StringUtils.containsIgnoreCase(src, "group") && StringUtils.containsIgnoreCase(src, "http");
                if (isFastdfs) {
                    // 返回截取出来的fastdfsid
                    return src.substring(StringUtils.indexOf(src, "/group"));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * @param ids 要删获取的图片的id数组
     * @return 图片的src数组
     */
    private String[] getImgFastDFSIdArrayByIdArray(String[] ids) {
        String[] fastDFSIds = {};
        for (int i = 0; i < ids.length; i++) {
            String imgFastDFSIdById = getImgFastDFSIdById(ids[i]);
            fastDFSIds[i] = imgFastDFSIdById;
        }
        return fastDFSIds;
    }


    /**
     * 获取一个img对象，参数t是一个BKImg对象实体，必须包括有一个字段合法
     *
     * @param t 获取的凭证，比如实体的某个字段  id 、 title之类
     * @return 返回一个BKImg实体对象
     * @throws DataBaseOperationException 当参数t的字段都不合法时抛出此异常
     * @throws NullPointerException       检查参数是否为null {@link EssentialServiceImpl[checkParam]}
     */
    @Override
    public <T> T getOne(T t) {
        super.getOne(t);
        BKImg result;
        try {
            // TODO 缓存以及其他优化操作
            result = imgMapper.getOne((BKImg) t);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("获取图片信息错误！原因：" + e.getMessage());
            }
            // 这里由于img的一些属性不具有唯一性，导致可能有多个结果 或者参数为空参数导致多个结果
            throw new DataBaseOperationException("请输入正确的参数信息！");
        }
        return (T) result;
    }

    /**
     * 更新一条BKImg数据
     *
     * @param t 要更新的数据  以id为依据
     * @return 满足条件的实体对象
     * @throws DataBaseOperationException 当参数t的字段都不合法时抛出此异常
     * @throws NullPointerException       检查参数是否为null {@link EssentialServiceImpl[checkParam]}
     */
    @Override
    public <T> boolean updateOne(T t) {
        super.updateOne(t);
        Integer result;
        try {
            result = imgMapper.updateOne((BKImg) t);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("更新图片信息错误！原因：" + e.getMessage());
            }
            throw new DataBaseOperationException("请输入正确的参数信息！");
        }
        return result > 0;
    }

    /**
     * 新增一条BKImg数据
     *
     * @param t             参数t必须合法
     * @param saveToFastDFS 是否存储在fastdfs中去
     * @param file          spring封装的文件实体
     * @return true 成功
     * @throws FieldIllegalException      检查字段合法性{@link BKImg[check]}
     * @throws DataBaseOperationException 执行sql出错
     * @throws FastDFSException           fastdfs上传失败
     */
    @Override
    public <T> boolean addOne(T t, boolean saveToFastDFS, MultipartFile file) {
        super.addOne(t, saveToFastDFS, file);
        String src;
        try {
            final BKImg bkImg = (BKImg) t;
            bkImg.check();
            if (saveToFastDFS) {
                src = FastDFSClient.uploadFile(file);
                if (StringUtils.isEmpty(src)) {
                    throw new FastDFSException("图片上传系统错误！");
                }
                bkImg.setSrc(src);
            }
            imgMapper.addOne(bkImg);
        }catch (FieldIllegalException | FastDFSException e) {
            if (logger.isErrorEnabled()) {
                logger.error("添加图片失败！原因：" + e.getMessage());
            }
            throw e;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("添加图片失败！原因：" + e.getMessage());
            }
            throw new DataBaseOperationException("图片上传失败！");
        }
        return true;
    }

    /**
     * 删除一条图片数据
     *
     * @param t      要删除的图片数据
     * @param remove 是否从数据库中删除记录
     * @return true 删除成功
     * @throws DataBaseOperationException 数据库操作错误
     * @throws FastDFSException           操作fastdfs失败
     */
    @Override
    public <T> boolean deleteOne(T t, boolean remove) {
        super.deleteOne(t, remove);
        final BKImg bkImg = (BKImg) t;
        try {
            if (remove) {
                Integer removeOneResult = imgMapper.removeOne(bkImg);
                String imgFastDFSIdById = getImgFastDFSIdById(bkImg.getId());
                boolean deleteFileResult = FastDFSClient.deleteFile(imgFastDFSIdById);
                if (removeOneResult > 0) {
                    if (deleteFileResult) {
                        return true;
                    } else {
                        throw new FastDFSException("从fastdfs中移除失败！");
                    }
                } else {
                    throw new DataBaseOperationException("数据库操作失败！");
                }
            } else {
                Integer deleteOneResult = imgMapper.deleteOne(bkImg);
                if (deleteOneResult > 0) {
                    return true;
                } else {
                    throw new DataBaseOperationException("删除错误！");
                }
            }
        } catch (FastDFSException e) {
            if (logger.isErrorEnabled()) {
                logger.error("删除失败！原因：" + e.getMessage());
            }
            throw e;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("删除失败！原因：" + e.getMessage());
            }
            throw new DataBaseOperationException("删除失败！请重试");
        }
    }

    /**
     * 获取多条BKImg数据
     *
     * @param pageParam 分页参数
     * @return 如果分页参数小于1 默认返回前10条数据，如果分页参数正常，则正常分页查询，如果分页参数为null则查询所有
     */
    @Override
    public List<?> getMultiple(PageParam pageParam) {
        super.getMultiple(pageParam);
        if (pageParam != null) {
            pageParam.check();
            PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
            return imgMapper.getMultiple();
        }
        return imgMapper.getMultiple();
    }


    /**
     * 批量删除一个实体
     *
     * @param primaryKey 主键数组
     * @param remove     是否移除
     * @return true 删除成功
     */
    @Override
    public boolean deleteMultiple(Object[] primaryKey, boolean remove) {
        super.deleteMultiple(primaryKey, remove);
        boolean result;
        try {
            String[] params = (String[]) primaryKey;
            if (remove) {
                // TODO 有时间再进行重构
                result = imgMapper.removeMultiple(params) > 0;
                String[] imgFastDFSIdArrayByIdArray = getImgFastDFSIdArrayByIdArray(params);
                FastDFSClient.deleteMultiple(imgFastDFSIdArrayByIdArray);
            } else {
                result = imgMapper.deleteMultiple(params) > 0;
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("删除失败！原因：" + e.getMessage());
            }
            throw new DataBaseOperationException("删除失败！请重试");
        }
        return result;
    }
}
