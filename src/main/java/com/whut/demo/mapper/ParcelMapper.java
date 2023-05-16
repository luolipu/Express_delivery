package com.whut.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.demo.entity.Parcel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ParcelMapper extends BaseMapper<Parcel> {
}
