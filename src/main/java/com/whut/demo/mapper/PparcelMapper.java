package com.whut.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.demo.entity.Pparcel;
import com.whut.demo.entity.Site;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PparcelMapper extends BaseMapper<Pparcel> {
    public void add(Integer id,Integer pstudentid,String username);
    public void pick(Integer id);
    public void finish(Integer id);


}
