package com.whut.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whut.demo.entity.SystemAdmin;
import com.whut.demo.form.RuleForm;
import com.whut.demo.form.SearchForm;
import com.whut.demo.vo.PageVO;
import com.whut.demo.vo.ResultVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2022-06-01
 */
public interface SystemAdminService extends IService<SystemAdmin> {
    public ResultVO login(RuleForm ruleForm);

    public Boolean deleteById(Integer id);
    public PageVO list(Integer page, Integer size);

    public PageVO search(SearchForm searchForm);


}
