package com.whut.demo.controller;

import com.whut.demo.common.SMSUtils;
import com.whut.demo.entity.Parcel;
import com.whut.demo.form.ParcelForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.service.ParcelService;
import com.whut.demo.util.ResultVOUtil;
import com.whut.demo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcel")
public class ParcelController {
    @Autowired
    private ParcelService parcelService;

    private SMSUtils smsUtils;

    @PostMapping("/save")
    public ResultVO save(@RequestBody Parcel parcel) {
        Boolean saveParcel = this.parcelService.saveParcel(parcel);
        if (!saveParcel) return ResultVOUtil.fail();

        return ResultVOUtil.success(null);
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.parcelService.list(page, size));
    }
    @GetMapping("/list/{telephone}/{page}/{size}")
    public ResultVO list(@PathVariable("telephone") String telephone,@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.parcelService.list(telephone,page, size));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.parcelService.search(searchForm));
    }

    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        Parcel parcel = this.parcelService.getById(id);
        ParcelForm parcelForm = new ParcelForm();
        BeanUtils.copyProperties(parcel, parcelForm);
        parcelForm.setOldsiteId(parcel.getSiteId());
        return ResultVOUtil.success(parcelForm);
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody ParcelForm parcelForm) {
        Boolean update = this.parcelService.update(parcelForm);
        if (!update) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        Boolean delete = this.parcelService.deleteById(id);
        if (!delete) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
}
