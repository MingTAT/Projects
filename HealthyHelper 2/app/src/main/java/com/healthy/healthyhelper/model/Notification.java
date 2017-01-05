package com.healthy.healthyhelper.model;


/**
 * 消息通知模型
 * Created by Ming on 2016/4/3.
 */
public class Notification {

    /**
     * id
     */
    private String id;
    /**
     * 内容
     */
    private String content;
    /**
     * 标题
     */
    private String title;

    public Notification(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
