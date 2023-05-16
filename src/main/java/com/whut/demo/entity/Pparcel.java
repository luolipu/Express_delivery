package com.whut.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Pparcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String uid;

    private String address;


    private String phone;
    private String pcode;



    private String pstate = "未接单";

    private String createDate;
    private String money;
    private String pname ="未接单";

    private Integer pstudentid ;

}
