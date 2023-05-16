package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Site;
import com.whut.demo.form.SearchForm;
import com.whut.demo.vo.PageVO;

public interface SiteService extends IService<Site> {
    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);

    public Boolean deleteById(Integer id);
}
