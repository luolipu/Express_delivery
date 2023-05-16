package com.whut.demo.vo;

import lombok.Data;

@Data
public class MoveoutVO {
    private Integer id;
    private String parcelAddress;
    private String siteName;
    private String reason;
    private String createDate;
}
