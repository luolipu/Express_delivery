package com.whut.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whut.demo.entity.Student;
import com.whut.demo.entity.SystemAdmin;
import com.whut.demo.form.RuleForm;
import com.whut.demo.mapper.StudentMapper;
import com.whut.demo.mapper.SystemAdminMapper;
import com.whut.demo.service.StudentService;
import com.whut.demo.service.SystemAdminService;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public ResultVO login(RuleForm ruleForm) {
        //1、判断用户名是否存在
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", ruleForm.getUsername());
        Student student = this.studentMapper.selectOne(queryWrapper);
        ResultVO resultVO = new ResultVO();
        if (student == null) {
            resultVO.setCode(-1);
        } else {
            //2、判断密码是否正确
            if (!student.getPassword().equals(ruleForm.getPassword())) {
                resultVO.setCode(-2);
            } else {
                resultVO.setCode(0);
                resultVO.setData(student);
            }
        }
        return resultVO;
    }
}
