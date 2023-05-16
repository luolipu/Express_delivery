package com.whut.demo.controller;

import com.whut.demo.entity.Pstudent;
import com.whut.demo.entity.Student;
import com.whut.demo.form.RuleForm;
import com.whut.demo.service.PstudentService;
import com.whut.demo.service.StudentService;
import com.whut.demo.util.ResultVOUtil;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pstudent")
public class PstudentController {
    @Autowired
    private PstudentService pstudentService;

    @GetMapping("/login")
    public ResultVO login(RuleForm ruleForm) {
        ResultVO resultVO = this.pstudentService.login(ruleForm);
        return resultVO;
    }
    @PostMapping("/save")
    public ResultVO save(@RequestBody Pstudent pstudent) {
        boolean save = this.pstudentService.save(pstudent);
        if (!save) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        return ResultVOUtil.success(this.pstudentService.getById(id));
    }
    @PutMapping("/update")
    public ResultVO update(@RequestBody Pstudent pstudent) {
        boolean update = this.pstudentService.updateById(pstudent);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
}
