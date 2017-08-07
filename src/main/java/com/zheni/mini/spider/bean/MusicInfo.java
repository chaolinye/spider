package com.zheni.mini.spider.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chaolinye
 * @since 2017/8/4
 */
public class MusicInfo {
    private long id;
    private String title;
    private String albumTitle;
    private String cover;
    private String url;
    private String creatTime;
    private long playCount;
    private String introduction;
    private int duration;


    private static final String TITLE_KEY="title";
    private static final String ALBUM_TITLE_KEY="album_title";
    private static final String COVER_KEY="cover_url_142";
    private static final String URL_KEY="play_path";
    private static final String CREAT_TIME_KEY="formatted_created_at";
    private static final String ID_KEY="id";
    private static final String PLAY_COUNT_KEY="play_count";
    private static final String INTRODUCTION_KEY="intro";
    private static final String DURATION_KEY="duration";
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static MusicInfo parseFromJSON(String json){
        MusicInfo musicInfo=new MusicInfo();
        JSONObject jsonObject =JSONObject.parseObject(json);
        musicInfo.setId(jsonObject.getIntValue(ID_KEY));
        musicInfo.setTitle(jsonObject.getString(TITLE_KEY));
        musicInfo.setAlbumTitle(jsonObject.getString(ALBUM_TITLE_KEY));
        musicInfo.setCover(jsonObject.getString(COVER_KEY));
        musicInfo.setUrl(jsonObject.getString(URL_KEY));
        musicInfo.setCreatTime(jsonObject.getString(CREAT_TIME_KEY));
        musicInfo.setPlayCount(jsonObject.getLongValue(PLAY_COUNT_KEY));
        musicInfo.setIntroduction(jsonObject.getString(INTRODUCTION_KEY));
        musicInfo.setDuration(jsonObject.getIntValue(DURATION_KEY));
        return  musicInfo;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder("{");
        sb.append(ID_KEY+":"+id+",");
        sb.append(TITLE_KEY+":"+title+",");
        sb.append(COVER_KEY+":"+cover+",");
        sb.append(URL_KEY+":"+url+",");
        sb.append(CREAT_TIME_KEY+":"+creatTime+",");
        sb.append(PLAY_COUNT_KEY+":"+playCount+",");
        sb.append(INTRODUCTION_KEY+":"+introduction+",");
        sb.append(DURATION_KEY+":"+duration+"}");
        return sb.toString();
    }

    public String toExcelString(){
        StringBuilder sb=new StringBuilder();
        sb.append(+id+"#");
        sb.append(title+"#");
        sb.append(cover+"#");
        sb.append(url+"#");
        sb.append(creatTime+"#");
        sb.append(playCount+"#");
        sb.append(introduction+"#");
        sb.append(duration);
        return sb.toString();
    }
}
