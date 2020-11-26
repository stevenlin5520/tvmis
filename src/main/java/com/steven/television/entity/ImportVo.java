package com.steven.television.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author steven
 * @desc
 * @date 2020/11/15 19:26
 */
@Data
public class ImportVo {
    private String importId;
    private String tvId;
    private String orgId;
    private Date startTime;
    private Date endTime;
    private long importLength;
    private int auditState;
    private String tvName;
    private String tvScreen;
    private String tvLocation;
    private int type;
}
