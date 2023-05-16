package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Building;
import com.whut.demo.form.SearchForm;
import com.whut.demo.vo.PageVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2022-06-01
 */
public interface BuildingService extends IService<Building> {
    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);

    public Boolean deleteById(Integer id);
}
