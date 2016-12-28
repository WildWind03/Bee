package com.bee.client.entity;

import android.os.Parcel;
import android.os.Parcelable;
import org.apache.log4j.Logger;

public class Comment implements Parcelable {
    private static final Logger logger = Logger.getLogger(Comment.class.getName());

    private final String id;
    private final String text;
    private final String userName;
    private final String productId;

    protected Comment(Parcel in) {
        text = in.readString();
        userName = in.readString();
        id = in.readString();
        productId = in.readString();
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

    public String getUserName() {
        return userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(userName);
        parcel.writeString(id);
        parcel.writeString(productId);
    }
}
