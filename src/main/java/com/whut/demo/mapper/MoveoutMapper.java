package com.whut.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.demo.entity.Moveout;
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
@Mapper
@Repository
public interface MoveoutMapper extends BaseMapper<Moveout> {

}
