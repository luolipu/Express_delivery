package com.whut.demo.controller;


import com.whut.demo.entity.Building;
import com.whut.demo.entity.SystemAdmin;
import com.whut.demo.form.RuleForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.service.SystemAdminService;
import com.whut.demo.util.ResultVOUtil;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-06-01
 */
@RestController
@RequestMapping("/systemAdmin")
public class SystemAdminController {

    @Autowired
    private SystemAdminService systemAdminService;

    @GetMapping("/login")
    public ResultVO login(RuleForm ruleForm) {
        ResultVO resultVO = this.systemAdminService.login(ruleForm);
        return resultVO;
    }
    @PostMapping("/save")
    public ResultVO save(@RequestBody SystemAdmin systemAdmin) {
        boolean save = this.systemAdminService.save(systemAdmin);
        if (!save) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    @PutMapping("/update")
    public ResultVO update(@RequestBody SystemAdmin systemAdmin) {
        boolean update = this.systemAdminService.updateById(systemAdmin);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        Boolean delete = this.systemAdminService.deleteById(id);

        if (!delete) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        return ResultVOUtil.success(this.systemAdminService.getById(id));
    }
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.systemAdminService.list(page, size));
    }

    @GetMapping("/list")
    public ResultVO list() {
        return ResultVOUtil.success(this.systemAdminService.list());
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.systemAdminService.search(searchForm));
    }
}

