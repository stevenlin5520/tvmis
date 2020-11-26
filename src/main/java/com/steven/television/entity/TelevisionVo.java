package com.steven.television.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @desc
 * @author steven
 * @date 2020/11/23 17:36
 */
public class TelevisionVo {
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
    private Date playDate;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "HH:mm:ss")
    private Date playStart;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "HH:mm:ss")
    private Date playEnd;
    private String tvName;
    private String tvDesc;
    private String tvImage;
    private String tvVideo;

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    public Date getPlayStart() {
        return playStart;
    }

    public void setPlayStart(Date playStart) {
        this.playStart = playStart;
    }

    public Date getPlayEnd() {
        return playEnd;
    }

    public void setPlayEnd(Date playEnd) {
        this.playEnd = playEnd;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvDesc() {
        return tvDesc;
    }

    public void setTvDesc(String tvDesc) {
        this.tvDesc = tvDesc;
    }

    public String getTvImage() {
        return tvImage;
    }

    public void setTvImage(String tvImage) {
        this.tvImage = tvImage;
    }

    public String getTvVideo() {
        return tvVideo;
    }

    public void setTvVideo(String tvVideo) {
        this.tvVideo = tvVideo;
    }
}
