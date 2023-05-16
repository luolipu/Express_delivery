package com.whut.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.demo.entity.Site;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SiteMapper extends BaseMapper<Site> {
    public void subAvailable(Integer id);

    public void addAvailable(Integer id);

    public Integer findAvailableSiteId();
}
