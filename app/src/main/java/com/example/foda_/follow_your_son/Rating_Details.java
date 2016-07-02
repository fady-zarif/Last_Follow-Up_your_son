package com.example.foda_.follow_your_son;

import java.util.Date;

/**
 * Created by foda_ on 2016-05-11.
 */
public class Rating_Details {
    public  String parent_email;
    public String teacher_email;
    public  String objectId;
    private Date created;
    private Date updated;

    public float rating_value;
    public Rating_Details()
    {

    }

    public Rating_Details(String parent_email, String teacher_email, float rating_value)
    {
        this.parent_email=parent_email;
        this.teacher_email=teacher_email;
        this.rating_value=rating_value;
    }

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

}
