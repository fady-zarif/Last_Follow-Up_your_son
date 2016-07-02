package com.example.foda_.follow_your_son;

import java.util.Date;

/**
 * Created by foda_ on 2016-05-17.
 */
public class Saved_Message  {
    public  String  Student_Name;
    public  String Parent_Email;
    public  String Teacher_Email;
    public String Notes;
    public  String objectId;
    private Date created;
    private Date updated;
    public Saved_Message()
    {

    }
    public  Saved_Message(String Student_Name,String Parent_Email,String Teacher_Email,String Notes)
    {
        this.Student_Name=Student_Name;
        this.Parent_Email=Parent_Email;
        this.Teacher_Email=Teacher_Email;
        this.Notes=Notes;
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
