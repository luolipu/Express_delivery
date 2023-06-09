package com.whut.demo.controller;

import com.whut.demo.form.SearchForm;
import com.whut.demo.service.MoveoutService;
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
@RequestMapping("/moveout")
public class MoveoutController {

    @Autowired
    private MoveoutService moveoutService;

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.moveoutService.list(page, size));
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        return ResultVOUtil.success(this.moveoutService.search(searchForm));
    }

    @PutMapping("/moveout/{id}/{reason}")
    public ResultVO moveout(@PathVariable("id") Integer id, @PathVariable("reason") String reason) {
        Boolean moveout = this.moveoutService.moveout(id, reason);
        if (!moveout) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    @PutMapping("/send/{id}/{phone}/{pcode}")
    public ResultVO sendMessage(@PathVariable("phone") String phone ) throws Exception {
        Boolean moveout = this.moveoutService.sendMessage(phone);
        if (!moveout) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }
    //@PathVariable("id") Integer id,@PathVariable("phone") String phone,@PathVariable("pcode") String pcode
    @GetMapping("/moveoutList/{page}/{size}")
    public ResultVO moveoutList(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResultVOUtil.success(this.moveoutService.moveoutList(page, size));
    }

    @GetMapping("/moveoutSearch")
    public ResultVO moveoutSearch(SearchForm searchForm) {
        return ResultVOUtil.success(this.moveoutService.moveoutSearch(searchForm));
    }
}

