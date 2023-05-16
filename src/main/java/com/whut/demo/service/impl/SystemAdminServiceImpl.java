package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.entity.Parcel;
import com.whut.demo.entity.Site;
import com.whut.demo.entity.SystemAdmin;
import com.whut.demo.form.RuleForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.mapper.SystemAdminMapper;
import com.whut.demo.service.SystemAdminService;
import com.whut.demo.vo.PageVO;
import com.whut.demo.vo.ResultVO;
import com.whut.demo.vo.SystemAdminVO;
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
public class SystemAdminServiceImpl extends ServiceImpl<SystemAdminMapper, SystemAdmin> implements SystemAdminService {

    @Autowired
    private SystemAdminMapper systemAdminMapper;

    @Override
    public ResultVO login(RuleForm ruleForm) {
        //1、判断用户名是否存在
        QueryWrapper<SystemAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", ruleForm.getUsername());
        SystemAdmin systemAdmin = this.systemAdminMapper.selectOne(queryWrapper);
        ResultVO resultVO = new ResultVO();
        if (systemAdmin == null) {
            resultVO.setCode(-1);
        } else {
            //2、判断密码是否正确
            if (!systemAdmin.getPassword().equals(ruleForm.getPassword())) {
                resultVO.setCode(-2);
            } else {
                resultVO.setCode(0);
                resultVO.setData(systemAdmin);
            }
        }
        return resultVO;
    }

//    @Override
//    public Boolean deleteById(Integer id) {
//        return null;
//    }

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<SystemAdmin> systemAdminPage = new Page<>(page, size);
        Page<SystemAdmin> resultPage = this.systemAdminMapper.selectPage(systemAdminPage, null);
        //building转为buildingVO
        List<SystemAdminVO> systemAdminVOList = new ArrayList<>();
        for (SystemAdmin systemAdmin : resultPage.getRecords()) {
            SystemAdminVO systemAdminVO = new SystemAdminVO();
            BeanUtils.copyProperties(systemAdmin, systemAdminVO);
//            systemAdminVO.setAdminName(this.dormitoryAdminMapper.selectById(building.getAdminId()).getName());
            systemAdminVOList.add(systemAdminVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(systemAdminVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<SystemAdmin> systemAdminPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<SystemAdmin> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.systemAdminMapper.selectPage(systemAdminPage, null);
        } else {
            QueryWrapper<SystemAdmin> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.systemAdminMapper.selectPage(systemAdminPage, queryWrapper);
        }
        //building转为buildingVO
        List<SystemAdminVO> systemAdminVOList = new ArrayList<>();
        for (SystemAdmin systemAdmin : resultPage.getRecords()) {
            SystemAdminVO systemAdminVO = new SystemAdminVO();
            BeanUtils.copyProperties(systemAdmin, systemAdminVO);
//            systemAdminVO.setAdminName(this.dormitoryAdminMapper.selectById(building.getAdminId()).getName());
            systemAdminVOList.add(systemAdminVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(systemAdminVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean deleteById(Integer id) {
        QueryWrapper<SystemAdmin> systemAdminQueryWrapper = new QueryWrapper<>();
        systemAdminQueryWrapper.eq("id", id);
        List<SystemAdmin> systemAdminList = this.systemAdminMapper.selectList(systemAdminQueryWrapper);
//        for (SystemAdmin systemAdmin : systemAdminList) {
//            Integer availableSiteId = this.siteMapper.findAvailableSiteId();
//            parcel.setSiteId(availableSiteId);
//            try {
//                this.parcelMapper.updateById(parcel);
//                this.siteMapper.subAvailable(availableSiteId);
//            } catch (Exception e) {
//                return false;
//            }
//        }
        int delete = this.systemAdminMapper.deleteById(id);
        if (delete != 1) return false;
        return true;
    }
}
