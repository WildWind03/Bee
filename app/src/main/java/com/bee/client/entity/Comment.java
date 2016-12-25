package com.bee.client.entity;

import android.os.Parcel;
import android.os.Parcelable;
import org.apache.log4j.Logger;

public class Comment implements Parcelable {
    private static final Logger logger = Logger.getLogger(Comment.class.getName());
    private final String text;
    private final String username;
    private final int countOfLikes;

    public Comment(String text, String username, int countOfLikes) {
        this.text = text;
        this.username = username;
        this.countOfLikes = countOfLikes;
    }

    protected Comment(Parcel in) {
        text = in.readString();
        username = in.readString();
        countOfLikes = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public int getCountOfLikes() {
        return countOfLikes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(username);
        parcel.writeInt(countOfLikes);
    }
}
