package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.entity.Pstudent;
import com.whut.demo.entity.Student;
import com.whut.demo.form.RuleForm;
import com.whut.demo.mapper.PstudentMapper;
import com.whut.demo.mapper.StudentMapper;
import com.whut.demo.service.PstudentService;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PstudentServiceImpl  extends ServiceImpl<PstudentMapper, Pstudent> implements PstudentService {
    @Autowired
    private PstudentMapper pstudentMapper;


    @Override
    public ResultVO login(RuleForm ruleForm) {
        //1、判断用户名是否存在
        QueryWrapper<Pstudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", ruleForm.getUsername());
        Pstudent pstudent = this.pstudentMapper.selectOne(queryWrapper);
        ResultVO resultVO = new ResultVO();
        if (pstudent == null) {
            resultVO.setCode(-1);
        } else {
            //2、判断密码是否正确
            if (!pstudent.getPassword().equals(ruleForm.getPassword())) {
                resultVO.setCode(-2);
            } else {
                resultVO.setCode(0);
                resultVO.setData(pstudent);
            }
        }
        return resultVO;
    }
}
