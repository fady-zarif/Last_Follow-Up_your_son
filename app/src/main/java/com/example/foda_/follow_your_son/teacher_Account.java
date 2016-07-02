/*
package com.example.foda_.follow_your_son;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class teacher_Account extends android.support.v4.app.Fragment {
// public Sqlite sqlite;
    Button Add_groups,Create_Alert,Cancel_Alert;
    TextView group__name,appointment;///for custom layout
     public ArrayList<Student_data> arrayList=new ArrayList<Student_data>();

    ListView group_list;
    EditText Name_Alert , Appointment_Alert;
    String [] array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teacher__account, container, false);
   //     sqlite=new Sqlite(getContext());
        group_list = (ListView)root.findViewById(R.id.List_Groups);
        Add_groups = (Button) root.findViewById(R.id.Add_Group);



        arrayList =select();
        array=new String[arrayList.size()];
        for (int i=0;i<array.length;i++)
        {
            array[i]=arrayList.get(i).name;
        }

        ArrayAdapter<String>  adapter=new ArrayAdapter<String>(getContext(), R.layout.textview, R.id.mytextview,array);
        group_list.setAdapter(adapter);
        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Name=arrayList.get(position).name;
                String Parent_id=arrayList.get(position).parent_id;
                String Parent_Email=arrayList.get(position).parent_email;
                String Parent_Mobile=arrayList.get(position).parent_mobile;
                Bundle bundle=new Bundle();
                bundle.putString("Name", Name);
                bundle.putString("Parent_id",Parent_id);
                bundle.putString("Parent_Email",Parent_Email);
                bundle.putString("Parent_Mobile",Parent_Mobile);

                Student_Details_Fragment fragment =new Student_Details_Fragment();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,fragment).commit();

            }
        });
        group_list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        Add_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new group_details()).commit();
            }
        });

        return root;
    }
public ArrayList<Student_data> select()
{   ArrayList<Student_data> groups_datas=new ArrayList<Student_data>();

    Cursor cursor=sqlite.select();
    if (cursor.getCount()==0) {
        Log.d("Database","No data yet");
        return groups_datas;
    }
    else
    {
        while (cursor.moveToNext())
        {
                String Name = cursor.getString(cursor.getColumnIndex("Student_Name"));
                String Parent_id =cursor.getString(cursor.getColumnIndex("parent_id"));
                String Paretnt_Email =cursor.getString(cursor.getColumnIndex("parent_Email"));
                String Parent_mobile =cursor.getString(cursor.getColumnIndex("parent_mobile"));
                Student_data student_data=new Student_data(Name,Parent_id,Paretnt_Email,Parent_mobile);
                groups_datas.add(student_data);
        }
    }
    return groups_datas;
}




    }*/
