package com.ruoyi.common.core.domain.model;

/**
 * @author chenyue7@foxmail.com
 * @date 2022/7/29
 * @description:
 */
public class EmailLoginVo {
    private String email;
    private String uuid;

    public EmailLoginVo() {
    }

    public EmailLoginVo(String email, String uuid) {
        this.email = email;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
