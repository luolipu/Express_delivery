package com.whut.demo.controller;

import com.whut.demo.entity.Parcel;
import com.whut.demo.entity.Pparcel;
import com.whut.demo.form.ParcelForm;
import com.whut.demo.form.PparcelForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.service.ParcelService;
import com.whut.demo.service.PparcelService;
import com.whut.demo.util.ResultVOUtil;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pparcel")
public class PparcelController {
    @Autowired
    private PparcelService pparcelService;

    @PostMapping("/save")
    public ResultVO save(@RequestBody Pparcel pparcel) {
        Boolean saveParcel = this.pparcelService.saveParcel(pparcel);
        if (!saveParcel) return ResultVOUtil.fail();

        return ResultVOUtil.success(null);
    }
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.pparcelService.list(page, size));
    }
    //给收件人下单后查看下单列表
    @GetMapping("/list/{telephone}/{page}/{size}")
    public ResultVO list(@PathVariable("telephone") String telephone,@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.pparcelService.list(telephone,page, size));
    }
    //给代取人接取订单后查看待取货的订单
    @GetMapping("/list1/{id}/{page}/{size}")
    public ResultVO picklist(@PathVariable("id") Integer id,@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.pparcelService.list1(id,page, size));
    }

    @GetMapping("/list2/{id}/{page}/{size}")
    public ResultVO finishlist(@PathVariable("id") Integer id,@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.pparcelService.list2(id,page, size));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.pparcelService.search(searchForm));
    }
    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        Pparcel pparcel = this.pparcelService.getById(id);
        PparcelForm pparcelForm = new PparcelForm();
        BeanUtils.copyProperties(pparcel, pparcelForm);
//        pparcelForm.setOldsiteId(pparcel.getSiteId());
        return ResultVOUtil.success(pparcelForm);
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody PparcelForm pparcelForm) {
        Boolean update = this.pparcelService.update(pparcelForm);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        Boolean delete = this.pparcelService.deleteById(id);
        if (!delete) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }


    @PostMapping("/add/{id}/{pid}/{username}")
    public ResultVO update1(@PathVariable("id") Integer id,@PathVariable("pid") Integer pstudentid,@PathVariable("username") String  username) {
        Boolean update = this.pparcelService.update1(id,pstudentid,username);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @PostMapping("/pick/{id}")
    public ResultVO update2(@PathVariable("id") Integer id) {
        Boolean update = this.pparcelService.update2(id);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @PostMapping("/finish/{id}")
    public ResultVO update3(@PathVariable("id") Integer id) {
        Boolean update = this.pparcelService.update3(id);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }




}
