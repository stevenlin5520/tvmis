package com.steven.television.dao;

import com.steven.television.entity.ImportVo;
import com.steven.television.entity.TImport;
import com.steven.television.entity.TImportExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TImportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    long countByExample(TImportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int deleteByExample(TImportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int deleteByPrimaryKey(String importId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int insert(TImport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int insertSelective(TImport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    List<TImport> selectByExample(TImportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    TImport selectByPrimaryKey(String importId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int updateByExampleSelective(@Param("record") TImport record, @Param("example") TImportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int updateByExample(@Param("record") TImport record, @Param("example") TImportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int updateByPrimaryKeySelective(TImport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_import
     *
     * @mbg.generated Thu Nov 19 00:16:45 CST 2020
     */
    int updateByPrimaryKey(TImport record);

    List<ImportVo> selectByPage(Map map);

    int selectByPageCount(Map map);

    List<TImport> selectImportForm(Map map);
}