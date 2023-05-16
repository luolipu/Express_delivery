package com.whut.demo.service.impl;

import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.common.SMSUtils;
import com.whut.demo.entity.Site;
import com.whut.demo.entity.Moveout;
import com.whut.demo.entity.Parcel;
import com.whut.demo.form.SearchForm;
import com.whut.demo.mapper.SiteMapper;
import com.whut.demo.mapper.MoveoutMapper;
import com.whut.demo.mapper.ParcelMapper;
import com.whut.demo.service.MoveoutService;
import com.whut.demo.util.CommonUtil;
import com.whut.demo.vo.MoveoutVO;
import com.whut.demo.vo.PageVO;
import com.whut.demo.vo.ParcelVO;
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
public class MoveoutServiceImpl extends ServiceImpl<MoveoutMapper, Moveout> implements MoveoutService {

    @Autowired
    private ParcelMapper parcelMapper;
    @Autowired
    private SiteMapper siteMapper;
    @Autowired
    private MoveoutMapper moveoutMapper;

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Parcel> parcelPage = new Page<>(page, size);
        QueryWrapper<Parcel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", "已入库");
        Page<Parcel> resultPage = this.parcelMapper.selectPage(parcelPage, queryWrapper);
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
        QueryWrapper<Parcel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", "已入库");
        if (searchForm.getValue().equals("")) {
            resultPage = this.parcelMapper.selectPage(parcelPage, queryWrapper);
        } else {
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.parcelMapper.selectPage(parcelPage, queryWrapper);
        }
        List<Parcel> parcelList = resultPage.getRecords();
        //VO转换
        List<ParcelVO> studentVOList = new ArrayList<>();
        for (Parcel parcel : parcelList) {
            ParcelVO parcelVO = new ParcelVO();
            BeanUtils.copyProperties(parcel, parcelVO);
            Site site = this.siteMapper.selectById(parcel.getSiteId());
            parcelVO.setSiteName(site.getName());
            studentVOList.add(parcelVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(studentVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean moveout(Integer id, String reason) {
        Moveout moveout = new Moveout();
        moveout.setParcelId(id);
        Parcel parcel = this.parcelMapper.selectById(id);
        moveout.setSiteId(parcel.getSiteId());
       // moveout.setSiteId(parcel.getSiteId());
        moveout.setReason(reason);
        moveout.setCreateDate(CommonUtil.createDate());
        int insert = this.moveoutMapper.insert(moveout);
        if (insert != 1) return false;
        parcel.setState("已出库");
        int update = this.parcelMapper.updateById(parcel);
        if (update != 1) return false;
        try {
            this.siteMapper.addAvailable(parcel.getSiteId());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean sendMessage(String phone) throws Exception {
//        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
//                .signName("武理快递")
//                .templateCode("SMS_275345458")
//                .phoneNumbers("15346820169")
//                .templateParam("{\"code\":\"1234\"}")
//                // Request-level configuration rewrite, can set Http request parameters, etc.
//                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
//                .build();
            SMSUtils.sendMessage(phone);

        return true;
    }

    @Override
    public PageVO moveoutList(Integer page, Integer size) {
        Page<Moveout> moveoutPage = new Page<>(page, size);
        Page<Moveout> resultPage = this.moveoutMapper.selectPage(moveoutPage, null);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        List<MoveoutVO> moveoutVOList = new ArrayList<>();
        for (Moveout moveout : resultPage.getRecords()) {
            MoveoutVO moveoutVO = new MoveoutVO();
            BeanUtils.copyProperties(moveout, moveoutVO);
            Parcel parcel = this.parcelMapper.selectById(moveout.getParcelId());
            Site site = this.siteMapper.selectById(moveout.getSiteId());
            moveoutVO.setParcelAddress(parcel.getAddress());
            moveoutVO.setSiteName(site.getName());
            moveoutVOList.add(moveoutVO);
        }
        pageVO.setData(moveoutVOList);
        return pageVO;
    }

    @Override
    public PageVO moveoutSearch(SearchForm searchForm) {
        Page<Moveout> moveoutPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Moveout> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.moveoutMapper.selectPage(moveoutPage, null);
        } else {
            QueryWrapper<Moveout> queryWrapper = new QueryWrapper<>();
            if (searchForm.getKey().equals("parcelPcode")) {
                QueryWrapper<Parcel> parcelQueryWrapper = new QueryWrapper<>();
                parcelQueryWrapper.like("pcode", searchForm.getValue());
                List<Parcel> parcelList = this.parcelMapper.selectList(parcelQueryWrapper);
                List<Integer> idList = new ArrayList<>();
                for (Parcel parcel : parcelList) {
                    idList.add(parcel.getId());
                }
                queryWrapper.in("parcel_id", idList);
            }
            if (searchForm.getKey().equals("siteName")) {
                QueryWrapper<Site> siteQueryWrapper = new QueryWrapper<>();
                siteQueryWrapper.like("name", searchForm.getValue());
                List<Site> siteList = this.siteMapper.selectList(siteQueryWrapper);
                List<Integer> idList = new ArrayList<>();
                for (Site site : siteList) {
                    idList.add(site.getId());
                }
                queryWrapper.in("site_id", idList);
            }
            resultPage = this.moveoutMapper.selectPage(moveoutPage, queryWrapper);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        List<MoveoutVO> moveoutVOList = new ArrayList<>();
        for (Moveout moveout : resultPage.getRecords()) {
            MoveoutVO moveoutVO = new MoveoutVO();
            BeanUtils.copyProperties(moveout, moveoutVO);
            Parcel parcel = this.parcelMapper.selectById(moveout.getParcelId());
            Site site = this.siteMapper.selectById(moveout.getSiteId());
            moveoutVO.setParcelAddress(parcel.getAddress());
            moveoutVO.setSiteName(site.getName());
            moveoutVOList.add(moveoutVO);
        }
        pageVO.setData(moveoutVOList);
        return pageVO;
    }
}
