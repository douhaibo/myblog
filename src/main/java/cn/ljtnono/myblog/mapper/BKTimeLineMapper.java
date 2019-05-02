package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKTimeLine;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import static cn.ljtnono.myblog.common.Tables.BK_TIME_LINE_TABLE;

/**
 *  时间轴的数据接口
 *  @author ljt
 *  @date 2019/1/19
 *  @version 1.0
*/
public interface BKTimeLineMapper {


    @Select("select * from " + BK_TIME_LINE_TABLE + " where mode = 1")
    List<BKTimeLine> getBKTimeLineAll();

}
