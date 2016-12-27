package com.bee.client.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.logging.Logger;

public class Category implements Parcelable {
    private static final Logger logger = Logger.getLogger(Category.class.getName());

    private final String name;
    private final String id;

    protected Category(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
    }
}
