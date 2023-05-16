package com.whut.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Moveout implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String number;
    private  String pcode;
    private String phone;

    private Integer parcelId;

    private Integer siteId;

    private String reason;

    private String createDate;



}
