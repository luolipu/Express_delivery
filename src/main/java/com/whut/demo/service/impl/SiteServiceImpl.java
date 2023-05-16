package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.entity.Building;
import com.whut.demo.entity.Parcel;
import com.whut.demo.entity.Site;
import com.whut.demo.form.SearchForm;
import com.whut.demo.mapper.BuildingMapper;
import com.whut.demo.mapper.ParcelMapper;
import com.whut.demo.mapper.SiteMapper;
import com.whut.demo.service.SiteService;
import com.whut.demo.vo.PageVO;
import com.whut.demo.vo.SiteVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {

    @Autowired
    private SiteMapper siteMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private ParcelMapper parcelMapper;
    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Site> sitePage = new Page<>(page, size);
        Page<Site> resultPage = this.siteMapper.selectPage(sitePage, null);
        List<SiteVO> siteVOList = new ArrayList<>();
        for (Site site : resultPage.getRecords()) {
            SiteVO siteVo = new SiteVO();
            BeanUtils.copyProperties(site, siteVo);
            Building building = this.buildingMapper.selectById(site.getBuildingId());
            siteVo.setBuildingName(building.getName());
            siteVOList.add(siteVo);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(siteVOList);
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Site> sitePage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Site> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.siteMapper.selectPage(sitePage, null);
        } else {
            QueryWrapper<Site> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.siteMapper.selectPage(sitePage, queryWrapper);
        }
        List<SiteVO> siteVOList = new ArrayList<>();
        for (Site site : resultPage.getRecords()) {
            SiteVO siteVO = new SiteVO();
            BeanUtils.copyProperties(site, siteVO);
            Building building = this.buildingMapper.selectById(site.getBuildingId());
            siteVO.setBuildingName(building.getName());
            siteVOList.add(siteVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(siteVOList);
        return pageVO;
    }

    @Override
    public Boolean deleteById(Integer id) {
        QueryWrapper<Parcel> parcelQueryWrapper = new QueryWrapper<>();
        parcelQueryWrapper.eq("site_id", id);
        List<Parcel> parcelList = this.parcelMapper.selectList(parcelQueryWrapper);
        for (Parcel parcel : parcelList) {
            Integer availableSiteId = this.siteMapper.findAvailableSiteId();
            parcel.setSiteId(availableSiteId);
            try {
                this.parcelMapper.updateById(parcel);
                this.siteMapper.subAvailable(availableSiteId);
            } catch (Exception e) {
                return false;
            }
        }
        int delete = this.siteMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }
}

