package com.steven.television.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author steven
 * @desc
 * @date 2020/12/7 10:23
 */
public class FormVo {
    private String channelName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date formDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date playStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date playEnd;
    private int playLength;
    private String playName;
    private String playScreen;
    private String playVideo;
    private int tvType;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
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

    public int getPlayLength() {
        return playLength;
    }

    public void setPlayLength(int playLength) {
        this.playLength = playLength;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPlayScreen() {
        return playScreen;
    }

    public void setPlayScreen(String playScreen) {
        this.playScreen = playScreen;
    }

    public String getPlayVideo() {
        return playVideo;
    }

    public void setPlayVideo(String playVideo) {
        this.playVideo = playVideo;
    }

    public int getTvType() {
        return tvType;
    }

    public void setTvType(int tvType) {
        this.tvType = tvType;
    }
}
