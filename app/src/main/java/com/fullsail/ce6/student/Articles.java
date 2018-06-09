package com.fullsail.ce6.student;

import android.net.Uri;

import java.io.Serializable;

public class Articles implements Serializable {
    String _id;
    String title;
    String thumbnailURL;
    String body;

    public static final String URI_AUTHORITY = "com.fullsail.ce6.student.provider";
    public static final String CONTENT_URI_STRING =
            "content://" + URI_AUTHORITY + "/";

    public static final String DATA_TABLE = "articles";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING + DATA_TABLE);
    public static final String ID = "_id";
    public static final String Title = "title";
    public static final String URI_THUMBNAIL = "thumbnail";
    public static final String BODY = "body";

    Articles(){
        super();
    }

    String get_id() {
        return _id;
    }

    void set_id(String id){
        this._id = id;
    }

    String getTitle(){
        return title;
    }

    void setTitle(String title){
        this.title = title;
    }

    String getThumbnailURL(){
        return thumbnailURL;
    }

    void setThumbnailURL(String image){
        this.thumbnailURL = image;
    }

    String getBody(){
        return body;
    }

    void setBody(String desc){
        this.body = desc;
    }
}
