package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKSkill;
import org.apache.ibatis.annotations.*;
import java.util.List;

import static cn.ljtnono.myblog.common.Tables.BK_SKILL_TABLE;

/**
 *  Skill mapper
 *  delete 代表设置状态为0
 *  remove 代表从数据库中删除
 *  @author ljt
 *  @date 2019/1/17
 *  @version 1.0
*/
public interface BKSkillMapper {
    /**
     * 根据id获取
     * @param bkSkillId id
     * @return BKSkill
     */
    @Select("select * from " + BK_SKILL_TABLE + " where mode = 1 and id = #{value}")
    BKSkill getBKSkillById(Integer bkSkillId);

    @Insert("insert into " + BK_SKILL_TABLE + " (id,name,grade,mode) values (#{id},#{name},#{grade},#{mode})")
    Integer insertBKSkill(BKSkill bkSkill);

    @Update("update " + BK_SKILL_TABLE + " set mode = 0 where id = #{value}")
    Integer deleteBKSkillById(Integer bkSkillId);

    @Delete("delete from " + BK_SKILL_TABLE + " where id = #{value}")
    Integer removeBKSkillById(Integer bkSkillId);

    @Select("select * from " + BK_SKILL_TABLE + " where mode = 1")
    List<BKSkill> getBKSkillAll();
}
