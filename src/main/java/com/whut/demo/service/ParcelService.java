package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Parcel;
import com.whut.demo.form.ParcelForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.vo.PageVO;

public interface ParcelService extends IService<Parcel> {
    public Boolean saveParcel(Parcel parcel);

    public PageVO list(Integer page, Integer size);
    public PageVO list(String telephone,Integer page, Integer size);
    public PageVO search(SearchForm searchForm);

    public Boolean update(ParcelForm parcelForm);

    public Boolean deleteById(Integer id);
}
