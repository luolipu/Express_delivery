package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.entity.Parcel;
import com.whut.demo.entity.Pparcel;
import com.whut.demo.entity.Site;
import com.whut.demo.form.ParcelForm;
import com.whut.demo.form.PparcelForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.mapper.ParcelMapper;
import com.whut.demo.mapper.PparcelMapper;
import com.whut.demo.service.ParcelService;
import com.whut.demo.service.PparcelService;
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
public class PparcelServiceImpl extends ServiceImpl<PparcelMapper, Pparcel> implements PparcelService {

    @Autowired
    private PparcelMapper pparcelMapper;

    @Override
    public Boolean saveParcel(Pparcel pparcel) {
        pparcel.setCreateDate(CommonUtil.createDate());
        int insert = this.pparcelMapper.insert(pparcel);
        if (insert != 1) return false;
//        修改宿舍数据

        return true;
    }
//给代取人订单list
    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Pparcel> pparcelPage = new Page<>(page, size);
        Page<Pparcel> resultPage = null;

        QueryWrapper<Pparcel> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("pstate","未接单");

        resultPage = this.pparcelMapper.selectPage(pparcelPage, queryWrapper);

        List<Pparcel> pparcelList = resultPage.getRecords();
        //VO转换
        List<PparcelVO> pparcelVOList = new ArrayList<>();
        for (Pparcel pparcel : pparcelList) {
            PparcelVO pparcelVO = new PparcelVO();
            BeanUtils.copyProperties(pparcel, pparcelVO);

            pparcelVOList.add(pparcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(pparcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }
//给用户代取订单list
    @Override
    public PageVO list(String telephone, Integer page, Integer size) {
        Page<Pparcel> pparcelPage = new Page<>(page, size);
        Page<Pparcel> resultPage = null;


         //根据电话查询
            QueryWrapper<Pparcel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",telephone);

            resultPage = this.pparcelMapper.selectPage(pparcelPage, queryWrapper);


        List<Pparcel> pparcelList = resultPage.getRecords();
        //VO转换
        List<PparcelVO> pparcelVOList = new ArrayList<>();
        for (Pparcel pparcel : pparcelList) {
            PparcelVO pparcelVO = new PparcelVO();
            BeanUtils.copyProperties(pparcel, pparcelVO);

            pparcelVOList.add(pparcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(pparcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO list1(Integer id, Integer page, Integer size) {
        Page<Pparcel> pparcelPage = new Page<>(page, size);
        Page<Pparcel> resultPage = null;


        //根据电话查询
        QueryWrapper<Pparcel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pstudentid",id);
        queryWrapper.eq("pstate","待取货");
        resultPage = this.pparcelMapper.selectPage(pparcelPage, queryWrapper);


        List<Pparcel> pparcelList = resultPage.getRecords();
        //VO转换
        List<PparcelVO> pparcelVOList = new ArrayList<>();
        for (Pparcel pparcel : pparcelList) {
            PparcelVO pparcelVO = new PparcelVO();
            BeanUtils.copyProperties(pparcel, pparcelVO);

            pparcelVOList.add(pparcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(pparcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO list2(Integer id, Integer page, Integer size) {
        Page<Pparcel> pparcelPage = new Page<>(page, size);
        Page<Pparcel> resultPage = null;


        //根据电话查询
        QueryWrapper<Pparcel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pstudentid",id);
        queryWrapper.eq("pstate","已取货");
        resultPage = this.pparcelMapper.selectPage(pparcelPage, queryWrapper);


        List<Pparcel> pparcelList = resultPage.getRecords();
        //VO转换
        List<PparcelVO> pparcelVOList = new ArrayList<>();
        for (Pparcel pparcel : pparcelList) {
            PparcelVO pparcelVO = new PparcelVO();
            BeanUtils.copyProperties(pparcel, pparcelVO);

            pparcelVOList.add(pparcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(pparcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Pparcel> pparcelPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Pparcel> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.pparcelMapper.selectPage(pparcelPage, null);
        } else {
            QueryWrapper<Pparcel> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.pparcelMapper.selectPage(pparcelPage, queryWrapper);
        }
        List<Pparcel> pparcelList = resultPage.getRecords();
        //VO转换
        List<PparcelVO> pparcelVOList = new ArrayList<>();
        for (Pparcel pparcel : pparcelList) {
            PparcelVO pparcelVO = new PparcelVO();
            BeanUtils.copyProperties(pparcel, pparcelVO);
//            Site site = this.siteMapper.selectById(parcel.getSiteId());
//            parcelVO.setSiteName(site.getName());
            pparcelVOList.add(pparcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(pparcelVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean update(PparcelForm pparcelForm) {
        //更新快递信息
        Pparcel pparcel = new Pparcel();
        BeanUtils.copyProperties(pparcelForm, pparcel);

        int update = this.pparcelMapper.updateById(pparcel);
        if (update != 1) return false;
        //更新宿舍数据
//        if (!parcelForm.getSiteId().equals(parcelForm.getOldsiteId())) {
//            //old+1，new-1
//            try {
//                this.siteMapper.addAvailable(parcelForm.getOldsiteId());
//                this.siteMapper.subAvailable(parcelForm.getSiteId());
//            } catch (Exception e) {
//                return false;
//            }
//        }
        return true;
    }

    @Override
    public Boolean update1(Integer id, Integer pstudentid,String username) {
        Pparcel pparcel = this.pparcelMapper.selectById(id);
        pparcel.setPstate("待取货");
        pparcel.setPname(username);
        pparcel.setPstudentid(pstudentid);
        int update=this.pparcelMapper.updateById(pparcel);
//        this.pparcelMapper.add(id,pstudentid,username);
        return true;
    }

    @Override
    public Boolean update2(Integer id) {
        Pparcel pparcel = this.pparcelMapper.selectById(id);
        pparcel.setPstate("已取货");
        int update=this.pparcelMapper.updateById(pparcel);
        return true;
    }

    @Override
    public Boolean update3(Integer id) {
        Pparcel pparcel = this.pparcelMapper.selectById(id);
        pparcel.setPstate("确认送达");
        int update=this.pparcelMapper.updateById(pparcel);
        return true;
    }


    @Override
    public Boolean deleteById(Integer id) {
        //修改宿舍数据
        Pparcel pparcel = this.pparcelMapper.selectById(id);
//        try {
//            Site site = this.siteMapper.selectById(parcel.getSiteId());
//            if (site.getType() > site.getAvailable()) {
//                this.siteMapper.addAvailable(parcel.getSiteId());
//            }
//        } catch (Exception e) {
//            return false;
//        }
        //删除代取快递数据
//        if(pparcel.getPstate()!="未接单"){
//            return false;
//        }
        int delete = this.pparcelMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }
}
