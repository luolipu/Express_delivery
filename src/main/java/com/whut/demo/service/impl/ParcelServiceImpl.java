package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.common.SMSUtils;
import com.whut.demo.entity.Parcel;
import com.whut.demo.entity.Pparcel;
import com.whut.demo.entity.Site;
import com.whut.demo.form.ParcelForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.mapper.ParcelMapper;
import com.whut.demo.mapper.SiteMapper;
import com.whut.demo.service.ParcelService;
import com.whut.demo.util.CommonUtil;
import com.whut.demo.vo.PageVO;
import com.whut.demo.vo.ParcelVO;
import com.whut.demo.vo.PparcelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelServiceImpl extends ServiceImpl<ParcelMapper,Parcel> implements ParcelService {
    @Autowired
    private ParcelMapper parcelMapper;
    @Autowired
    private SiteMapper siteMapper;


    @Override
    public Boolean saveParcel(Parcel parcel) {
        parcel.setCreateDate(CommonUtil.createDate());
        int insert = this.parcelMapper.insert(parcel);
        if (insert != 1) return false;
//        修改宿舍数据
        Site site = this.siteMapper.selectById(parcel.getSiteId());
        if (site.getAvailable() == 0) {
            return false;
        }
        site.setAvailable(site.getAvailable() - 1);
        int update = this.siteMapper.updateById(site);
        if (update != 1) return false;
        SMSUtils.sendMessage(parcel.getPhone());
        return true;
    }

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Parcel> parcelPage = new Page<>(page, size);
        Page<Parcel> resultPage = this.parcelMapper.selectPage(parcelPage, null);
        List<Parcel> parcelList = resultPage.getRecords();
        //VO转换
        List<ParcelVO> parcelVOList = new ArrayList<>();
        for (Parcel parcel : parcelList) {
            ParcelVO parcelVO = new ParcelVO();
            BeanUtils.copyProperties(parcel, parcelVO);
            Site site = this.siteMapper.selectById(parcel.getSiteId());
            parcelVO.setSiteName(site.getName());
            parcelVOList.add(parcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(parcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO list(String telephone, Integer page, Integer size) {
        Page<Parcel> parcelPage = new Page<>(page, size);
        Page<Parcel> resultPage = null;

        //根据电话查询
        QueryWrapper<Parcel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",telephone);

        resultPage = this.parcelMapper.selectPage(parcelPage, queryWrapper);

        List<Parcel> parcelList = resultPage.getRecords();
        //VO转换
        List<ParcelVO> parcelVOList = new ArrayList<>();
        for (Parcel parcel : parcelList) {
            ParcelVO parcelVO = new ParcelVO();
            BeanUtils.copyProperties(parcel, parcelVO);
            Site site = this.siteMapper.selectById(parcel.getSiteId());
            parcelVO.setSiteName(site.getName());
            parcelVOList.add(parcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(parcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Parcel> parcelPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Parcel> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.parcelMapper.selectPage(parcelPage, null);
        } else {
            QueryWrapper<Parcel> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.parcelMapper.selectPage(parcelPage, queryWrapper);
        }
        List<Parcel> parcelList = resultPage.getRecords();
        //VO转换
        List<ParcelVO> parcelVOList = new ArrayList<>();
        for (Parcel parcel : parcelList) {
            ParcelVO parcelVO = new ParcelVO();
            BeanUtils.copyProperties(parcel, parcelVO);
            Site site = this.siteMapper.selectById(parcel.getSiteId());
            parcelVO.setSiteName(site.getName());
            parcelVOList.add(parcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(parcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean update(ParcelForm parcelForm) {
        //更新快递信息
        Parcel parcel = new Parcel();
        BeanUtils.copyProperties(parcelForm, parcel);
        int update = this.parcelMapper.updateById(parcel);
        if (update != 1) return false;
        //更新宿舍数据
        if (!parcelForm.getSiteId().equals(parcelForm.getOldsiteId())) {
            //old+1，new-1
            try {
                this.siteMapper.addAvailable(parcelForm.getOldsiteId());
                this.siteMapper.subAvailable(parcelForm.getSiteId());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteById(Integer id) {
        //修改宿舍数据
        Parcel parcel = this.parcelMapper.selectById(id);
        try {
            Site site = this.siteMapper.selectById(parcel.getSiteId());
            if (site.getType() > site.getAvailable()) {
                this.siteMapper.addAvailable(parcel.getSiteId());
            }
        } catch (Exception e) {
            return false;
        }
        //删除学生数据
        int delete = this.parcelMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }
}
