package com.example.foda_.follow_your_son;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;


public class teacher_successfully_logedin extends android.support.v4.app.Fragment {
    ListView listView;
   // Sqlite sqlite;
    ArrayList<Student_data> arrayList= new ArrayList<Student_data>();
    ProgressDialog progress;
    Button Add;

    @Override
    public void onStart() {
        super.onStart();

        progress=new ProgressDialog(getContext());
        progress.setMessage("Loading ... ");
        progress.setCanceledOnTouchOutside(false);
        BackendlessUser user = Backendless.UserService.CurrentUser();
        if (user != null) {
            // get user's phone number (i.e. custom property)
            String  email = (String)user.getProperty("email");
            Search search=new Search();
            search.execute(email);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_teacher_successfully_logedin, container, false);
        // sqlite=new Sqlite(getContext());
        Add=(Button)root.findViewById(R.id.backend);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new group_details()).commit();
            }
        });

        return root;

    }
  /*  public ArrayList<Student_data> select()
    {   ArrayList<Student_data> groups_datas=new ArrayList<Student_data>();

        Cursor cursor=sqlite.select();
        if (cursor.getCount()==0) {
            Log.d("Database", "No data yet");
            return groups_datas;
        }
        else
        {
            while (cursor.moveToNext())
            {
                String Name = cursor.getString(cursor.getColumnIndex("Student_Name"));
                String Parent_id =cursor.getString(cursor.getColumnIndex("parent_id"));
                String Parent_Email =cursor.getString(cursor.getColumnIndex("parent_Email"));
                String Parent_mobile =cursor.getString(cursor.getColumnIndex("parent_mobile"));
                Student_data student_data=new Student_data(Name,Parent_id,Parent_Email,Parent_mobile);
                groups_datas.add(student_data);
            }
        }
        return groups_datas;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.teacher, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.Edit)
        {
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Teacher_Profile()).commit();
        }
       /* else if (id==R.id.Teacher_item)
        {
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Teachers_Grid_View()).commit();
        }*/
        return super.onOptionsItemSelected(item);

    }
    public  class Search extends AsyncTask<String, Void, Void>
    { ArrayList<Student_data> Student_array=new ArrayList<Student_data>();
        @Override
        protected Void doInBackground(String... params) {

                String whereClause = "Teacher_Email = '" + params[0] + "'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                dataQuery.setWhereClause(whereClause);
                BackendlessCollection<Student_data> result = Backendless.Persistence.of(Student_data.class).find(dataQuery);
                for (int i = 0; i < result.getCurrentPage().size(); i++) {
                    Student_array.add(result.getCurrentPage().get(i));
                }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

      listView = (ListView) getActivity().findViewById(R.id.List_of_Student);
      int x = Student_array.size();
      String []array = new String[x];
      for (int i = 0; i < array.length; i++) {
          array[i] = Student_array.get(i).name;
      }
        progress.dismiss();
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.textview, R.id.mytextview, array);
      listView.setAdapter(adapter);

      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String Parent_id = Student_array.get(position).parent_id;
              String Name = Student_array.get(position).name;
              String object_id=Student_array.get(position).objectId;
              String parent_email = Student_array.get(position).parent_email;
              String parent_mobile = Student_array.get(position).parent_mobile;

              Bundle bundle=new Bundle();
              bundle.putString("Parent_id", Parent_id);
              bundle.putString("Name", Name);
              bundle.putString("objectId",object_id);
              bundle.putString("Parent_Email",parent_email);
              bundle.putString("Parent_Mobile",parent_mobile);
              Student_Details_Fragment fragment =new Student_Details_Fragment();
              fragment.setArguments(bundle);
              getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, fragment).commit();


             /* String Parent_id = arrayList.get(position).parent_id;
              Bundle bundle = new Bundle();
              bundle.putString("Parent_id", Parent_id);
              Message_to_send fragment = new Message_to_send();
              fragment.setArguments(bundle);
              getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, fragment).commit();
*/
          }
      });

        }
      }
    }

