package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.Moveout;
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
public interface MoveoutService extends IService<Moveout> {
    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);

    public Boolean moveout(Integer id, String reason);

    public Boolean sendMessage(String phone) throws Exception;
    //Integer id,String phone ,String pcode
    public PageVO moveoutList(Integer page, Integer size);

    public PageVO moveoutSearch(SearchForm searchForm);
}
