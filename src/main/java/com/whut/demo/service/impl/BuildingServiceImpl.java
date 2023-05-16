package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.entity.Building;
import com.whut.demo.entity.Site;
import com.whut.demo.entity.Parcel;
import com.whut.demo.form.SearchForm;
import com.whut.demo.mapper.BuildingMapper;
//import com.whut.demo.mapper.DormitoryAdminMapper;
import com.whut.demo.mapper.SiteMapper;
import com.whut.demo.mapper.ParcelMapper;
import com.whut.demo.service.BuildingService;
import com.whut.demo.vo.BuildingVO;
import com.whut.demo.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-06-01
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;
//    @Autowired
//    private DormitoryAdminMapper dormitoryAdminMapper;
    @Autowired
    private SiteMapper siteMapper;
    @Autowired
    private ParcelMapper parcelMapper;

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Building> buildingPage = new Page<>(page, size);
        Page<Building> resultPage = this.buildingMapper.selectPage(buildingPage, null);
        //building转为buildingVO
        List<BuildingVO> buildingVOList = new ArrayList<>();
        for (Building building : resultPage.getRecords()) {
            BuildingVO buildingVO = new BuildingVO();
            BeanUtils.copyProperties(building, buildingVO);
//            buildingVO.setAdminName(this.dormitoryAdminMapper.selectById(building.getAdminId()).getName());
            buildingVOList.add(buildingVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(buildingVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Building> buildingPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Building> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.buildingMapper.selectPage(buildingPage, null);
        } else {
            QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.buildingMapper.selectPage(buildingPage, queryWrapper);
        }
        //building转为buildingVO
        List<BuildingVO> buildingVOList = new ArrayList<>();
        for (Building building : resultPage.getRecords()) {
            BuildingVO buildingVO = new BuildingVO();
            BeanUtils.copyProperties(building, buildingVO);
//            buildingVO.setAdminName(this.dormitoryAdminMapper.selectById(building.getAdminId()).getName());
            buildingVOList.add(buildingVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(buildingVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean deleteById(Integer id) {
        //找到楼宇中的所有宿舍
        //找到宿舍中的所有学生
        //给学生换宿舍
        //删除宿舍
        //删除楼宇
        QueryWrapper<Site> siteQueryWrapper = new QueryWrapper<>();
        siteQueryWrapper.eq("building_id", id);
        List<Site> siteList = this.siteMapper.selectList(siteQueryWrapper);
        for (Site site : siteList) {
            QueryWrapper<Parcel> parcelQueryWrapper = new QueryWrapper<>();
            //
             parcelQueryWrapper.eq("site_id", site.getId());
            List<Parcel> parcelList = this.parcelMapper.selectList(parcelQueryWrapper);
            //
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
            int delete = this.siteMapper.deleteById(site.getId());
            if (delete != 1) return false;
        }
        int delete = this.buildingMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }
}
