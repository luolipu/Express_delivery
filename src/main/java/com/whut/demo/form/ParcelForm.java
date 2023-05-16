package com.whut.demo.form;

import lombok.Data;

@Data
public class ParcelForm {
    private Integer id;
    private String uid;
    private String address;
    private String phone;
    private String pcode;
//    private String gender;
    private Integer siteId;
    private Integer oldsiteId;
    private String state;
    private String createDate;
}
