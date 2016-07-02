package com.example.foda_.follow_your_son;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by foda_ on 2016-05-06.
 */
public class Teachers_profiles implements Parcelable {
    int id ;
    public  String objectId;
    private Date created;
    private Date updated;
    public String Tname;
    public String Email;
    public String Subject;
    public String Phone;
    public String Area;
    public String Years;
    public String Image;

    public  Teachers_profiles()
    {

    }


    public  Teachers_profiles(String Tname,String Email,String Subject,String Phone,String Area,String Years)
    {
        this.Tname=Tname;
        this.Email=Email;
        this.Subject=Subject;
        this.Phone=Phone;
        this.Area=Area;
        this.Years=Years;
    }

    protected Teachers_profiles(Parcel in) {
        id = in.readInt();
        objectId = in.readString();
        Tname = in.readString();
        Email = in.readString();
        Subject = in.readString();
        Phone = in.readString();
        Area = in.readString();
        Years = in.readString();
        Image = in.readString();
    }

    public static final Creator<Teachers_profiles> CREATOR = new Creator<Teachers_profiles>() {
        @Override
        public Teachers_profiles createFromParcel(Parcel in) {
            return new Teachers_profiles(in);
        }

        @Override
        public Teachers_profiles[] newArray(int size) {
            return new Teachers_profiles[size];
        }
    };

    public Date getCreated()
    {
        return created;
    }

    public void setCreated( Date created )
    {
        this.created = created;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated( Date updated )
    {
        this.updated = updated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(objectId);
        dest.writeString(Tname);
        dest.writeString(Email);
        dest.writeString(Subject);
        dest.writeString(Phone);
        dest.writeString(Area);
        dest.writeString(Years);
        dest.writeString(Image);
    }
}
