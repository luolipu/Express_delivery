package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Parcel;
import com.whut.demo.entity.Pparcel;
import com.whut.demo.form.ParcelForm;
import com.whut.demo.form.PparcelForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.vo.PageVO;

public interface PparcelService extends IService<Pparcel> {
    public Boolean saveParcel(Pparcel pparcel);
    public PageVO list(Integer page, Integer size);
    //给收件人下单后查看下单列表
    public PageVO list(String telephone,Integer page, Integer size);
    //给代取人接取订单后查看待取货的订单
    public PageVO list1(Integer id,Integer page, Integer size);

    public PageVO list2(Integer id,Integer page, Integer size);


    public PageVO search(SearchForm searchForm);

    public Boolean update(PparcelForm pparcelForm);

    public Boolean update1(Integer id,Integer pstudentid,String username);

    public Boolean update2(Integer id);

    public Boolean update3(Integer id);

    public Boolean deleteById(Integer id);
}
