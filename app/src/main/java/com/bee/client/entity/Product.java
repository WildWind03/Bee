package com.bee.client.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.logging.Logger;

public class Product implements Parcelable{
    private static final Logger logger = Logger.getLogger(Product.class.getName());
    private final String name ;
    private final String description;
    private final int averageRate;
    private final String organisation;

    public Product(String name, String description, String organisation, int averageRate) {
        this.name = name;
        this.description = description;
        this.organisation = organisation;
        this.averageRate = averageRate;
    }

    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        averageRate = in.readInt();
        organisation = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAverageRate() {
        return averageRate;
    }

    public String getOrganisation() {
        return organisation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(averageRate);
        parcel.writeString(organisation);
    }
}
