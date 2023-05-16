package com.whut.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.demo.entity.Building;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-06-01
 */
@Repository
@Mapper
public interface BuildingMapper extends BaseMapper<Building> {

}
