package com.whut.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whut.demo.entity.Site;
import com.whut.demo.form.SearchForm;
import com.whut.demo.service.SiteService;
import com.whut.demo.util.ResultVOUtil;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @GetMapping("/availableList")
    public ResultVO availableList() {
        QueryWrapper<Site> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("available", 0);
        List<Site> dormitoryList = this.siteService.list(queryWrapper);
        return ResultVOUtil.success(dormitoryList);
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Site site) {
        site.setAvailable(site.getType());
        boolean save = this.siteService.save(site);
        if (!save) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.siteService.list(page, size));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.siteService.search(searchForm));
    }

    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        return ResultVOUtil.success(this.siteService.getById(id));
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody Site site) {
        boolean update = this.siteService.updateById(site);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        Boolean delete = this.siteService.deleteById(id);
        if (!delete) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
}
