package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Pstudent;
import com.whut.demo.form.RuleForm;
import com.whut.demo.vo.ResultVO;

public interface PstudentService extends IService<Pstudent> {
    public ResultVO login(RuleForm ruleForm);
}
