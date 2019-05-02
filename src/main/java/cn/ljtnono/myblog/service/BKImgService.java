package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.BKImg;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 *  处理img业务的接口
 *  @author ljt
 *  @date 2018/12/10
 *  @version 1.0
*/
public interface BKImgService extends EssentialService {

    /**
     * 获取首页的相册的内容
     * @return List<BKImg>
     */
    Set<BKImg> getBlogAlbum();

    /**
     * 根据imgIds 获取图片列表
     * @param imgArray 图像数组
     * @return 该数组对应的img实体
     */
    Set<BKImg> getImgSetByIdArray(Integer[] imgArray);

    /**
     * 处理文件上传请求
     * @param file  需要上传的文件
     * @return 成功或者失败的信息
     */
    JSONObject uploadImg(MultipartFile file);


    /**
     * 通过图片的Url数组，来获取图片的IdList
     * @param imageSrcArray 图片的Url数组
     * @return 图片的IdList
     */
    List<Integer> getImageIdListBySrcArray(String[] imageSrcArray);

    /**
     * 根据图片的scr 获取图片的id
     * @param src 图片的src
     * @return 图片的id
     */
    String getImgIdBySrc(String src);
}
