package com.zxcl.webapp.dto;

/**
 * 
 * @ClassName: BankDTO
 * @Description: 银行
 * @author 赵晋
 * @date 2016年2月24日 上午11:12:16
 *
 */
public class BankDTO {
    private String id;

    private String code;

    private String icon;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
