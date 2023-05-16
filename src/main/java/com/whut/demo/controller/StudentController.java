package com.whut.demo.controller;

import com.whut.demo.entity.Building;
import com.whut.demo.entity.Student;
import com.whut.demo.entity.SystemAdmin;
import com.whut.demo.form.RuleForm;
import com.whut.demo.service.StudentService;
import com.whut.demo.service.SystemAdminService;
import com.whut.demo.util.ResultVOUtil;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public ResultVO login(RuleForm ruleForm) {
        ResultVO resultVO = this.studentService.login(ruleForm);
        return resultVO;
    }
    @PostMapping("/save")
    public ResultVO save(@RequestBody Student student) {
        boolean save = this.studentService.save(student);
        if (!save) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        return ResultVOUtil.success(this.studentService.getById(id));
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody Student student) {
        boolean update = this.studentService.updateById(student);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
}
