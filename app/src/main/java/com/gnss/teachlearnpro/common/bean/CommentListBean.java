package com.gnss.teachlearnpro.common.bean;

public class CommentListBean {
    public String avatarLogo;
    public String nick;
    public String content;
    public String commentDate;

    public CommentListBean(String avatarLogo, String nick, String content, String commentDate) {
        this.avatarLogo = avatarLogo;
        this.nick = nick;
        this.content = content;
        this.commentDate = commentDate;
    }
}
