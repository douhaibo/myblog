package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.provider.BKImgProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

import static cn.ljtnono.myblog.common.Tables.IMGTABLE;

/**
 *  处理img业务的mapper接口
 *  @author ljt
 *  @date 2018/12/10
 *  @version 1.0
*/
public interface BKImgMapper {


    /**
     * 查询首页显示的图片
     * @param count 获取的条数
     * @return List<BKImg>
     */
    @Select("select * from " + IMGTABLE + " where title like '%index_album%' limit #{v}")
    Set<BKImg> getBlogAlbum(Integer count);

    /**
     * 根据id获取Img实体
     * @param imgId imgId
     * @return img实体
     */
    @Select("select * from " + IMGTABLE + " where id = #{v}")
    @Results(id=IMGTABLE)
    BKImg getImgById(String imgId);

    /**
     * 根据id数组获取相应的图片列表
     * @param imgIds id数组
     * @return 图片列表
     */
    @SelectProvider(type = BKImgProvider.class,method = "getImgSetByIdArray")
    Set<BKImg> getImgSetByIdArray(@Param("imgIds") Integer[] imgIds);


    /**
     * 根据imageSrc数组获取imageIdList
     * @param imageSrcArray imageSrcArray
     * @return List<Integer>
     */
    @SelectProvider(type = BKImgProvider.class,method = "getImageIdListBySrcArray")
    List<Integer> getImageIdListBySrcArray(@Param("imageSrcArray") String[] imageSrcArray);


    /**
     * 根据id 获取其src
     * @param id 图片的id
     * @return 图片的src
     */
    @Select("select src from " + IMGTABLE + " where id = #{id}")
    String getImgSrcById(String id);

    /**
     * 根据src获取图片的id
     * @param src 图片的src属性
     * @return 图片的id属性
     */
    @Select("select id from " + IMGTABLE + " where src = #{src}" )
    String getImgIdBySrc(String src);

    /**
     * 获取单个img对象
     * @param img 获取的Field
     * @return DO对象
     */
    @SelectProvider(type = BKImgProvider.class,method = "getOne")
    BKImg getOne(@Param("img") BKImg img);
    /**
     * 更新单个img对象
     * @param img 获取的Field
     * @return  影响的行数
     */
    @UpdateProvider(type = BKImgProvider.class,method = "updateOne")
    Integer updateOne(@Param("img") BKImg img);

    /**
     *  插入一个img对象
     * @param img 新增的img对象
     * @return 影响的行数
     */
    @Insert("insert into " + IMGTABLE + " (id,title,src,createDateTime,updateDateTime,mode) values(#{id},#{title},#{src},#{createDateTime},#{updateDateTime},#{mode})")
    Integer addOne(BKImg img);

    /**
     * 删除一个img对象
     * @param img 删除一个img 只是将mode改为0
     * @return 影响的行数
     */
    @Update("update " + IMGTABLE + " set mode = 0 where id = #{id}")
    Integer deleteOne(BKImg img);

    /**
     * 从数据库中彻底删除该条信息
     * @param img 要删除的img实体
     * @return 影响的行数
     */
    @Delete("delete from " + IMGTABLE + " where id = #{id}")
    Integer removeOne(BKImg img);

    /**
     * 无条件获取数据库中的所有记录
     * @return  所有记录
     */
    @Select("select * from " + IMGTABLE )
    List<BKImg> getMultiple();

    /**
     * 一次删除多个
     * @param params 主键数组
     * @return 影响的行数
     */
    @UpdateProvider(type = BKImgProvider.class,method = "deleteMultiple")
    Integer deleteMultiple(@Param("params") String[] params);

    /**
     * 一次移除多个
     * @param params 主键数组
     * @return 影响的行数
     */
    @DeleteProvider(type = BKImgProvider.class,method = "removeMultiple")
    Integer removeMultiple(@Param("params") String[] params);
}
