package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static cn.ljtnono.myblog.common.Tables.*;

/**
 *  处理书籍的数据接口
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
public interface BKBookMapper {

    /**
     *  获取我的书单列表
     * @return 书单的前5
     */
    @Select("select " + BOOKTABLE + ".id," + BOOKTABLE + ".name," + BOOKTABLE + ".price," + BOOKTABLE + ".desc," +
            BOOKTABLE + ".imgId " +
            "from " + BOOKTABLE +
            " limit 5"
    )
    @ResultMap(BOOKTABLE)
    List<BKBook> getMyBookList();

    /**
     * 根据id获取book实例
     * @param bookId book的id
     * @return Book实例
     */
    @Select("select * from " + BOOKTABLE + " where id = #{v}")
    @Results(id = BOOKTABLE,value = {
            @Result(property = "img",column = "imgId",one = @One(select = "cn.ljtnono.myblog.mapper.BKImgMapper.getImgById"))
    })
    BKBook getBookById(Integer bookId);

    /**
     * 获取全部书单
     * @return 全部书单
     */
    @Select("select * from " + BOOKTABLE + " where mode = 1")
    @ResultMap(BOOKTABLE)
    List<BKBook> getBookListAll();



}
