package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Student;
import com.whut.demo.form.RuleForm;
import com.whut.demo.vo.ResultVO;


public interface StudentService extends IService<Student> {
    public ResultVO login(RuleForm ruleForm);
}
