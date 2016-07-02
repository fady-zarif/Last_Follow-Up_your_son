package com.example.foda_.follow_your_son;

import java.util.Date;

/**
 * Created by foda_ on 2016-04-28.
 */
public class Student_data {
    int id ;
    public  String objectId;
    private Date created;
    private Date updated;
    public String name;
    public String parent_id;
    public String parent_email;
    public String parent_mobile;
    public String Teacher_Email;
    public Student_data()
    {

    }

    public Student_data (String name,String parent_id,String parent_email,String parent_mobile,String Teacher_Email)
    {
        this.name=name;
        this.parent_id=parent_id;
        this.parent_email=parent_email;
        this.parent_mobile=parent_mobile;
        this.Teacher_Email=Teacher_Email;
    }




    public Student_data (String name,String parent_id,String parent_email,String parent_mobile)
    {
        this.name=name;
        this.parent_id=parent_id;
        this.parent_email=parent_email;
        this.parent_mobile=parent_mobile;
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
