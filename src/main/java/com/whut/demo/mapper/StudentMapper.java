package com.whut.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.demo.entity.Site;
import com.whut.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
