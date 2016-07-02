package com.example.foda_.follow_your_son;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class Student_Details_Fragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose name`s that match

  //  Sqlite sqlite;
    TextView Student_For_Contact;
    Button email,phone,delete,notification;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    //    sqlite=new Sqlite(getContext());
        View root= inflater.inflate(R.layout.fragment_student__details_, container, false);
        BackendlessUser user = Backendless.UserService.CurrentUser();
         String email1=(String)user.getProperty("email");


        Student_For_Contact=(TextView)root.findViewById(R.id.Student_For_Contact);
        String StudentName=getArguments().getString("Name");
        Student_For_Contact.setText(StudentName);
        email=(Button)root.findViewById(R.id.Email);
        phone=(Button)root.findViewById(R.id.Phone);
        delete=(Button)root.findViewById(R.id.delete);
        notification=(Button)root.findViewById(R.id.Notification);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Parent_Email = getArguments().getString("Parent_Email");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{Parent_Email});
                email.setType("text/plain");
                startActivity(email);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Parent_Mobile=getArguments().getString("Parent_Mobile");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + Parent_Mobile));
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackendlessUser user = Backendless.UserService.CurrentUser();
                String email1=(String)user.getProperty("email");
                String Name=getArguments().getString("Name");
                String Parent_Email = getArguments().getString("Parent_Email");
                Bundle bundle = new Bundle();
                bundle.putString("Name2",Name);
                bundle.putString("Parent_Email",Parent_Email);
                bundle.putString("Teacher_Email",email1);
                Message_to_send fragment = new Message_to_send();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,fragment).commit();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackendlessUser user = Backendless.UserService.CurrentUser();
                String email1=(String)user.getProperty("email");
                String Name=getArguments().getString("Name");
                String Parent_id=getArguments().getString("Parent_id");
                String Parent_Email = getArguments().getString("Parent_Email");
                String Parent_Mobile=getArguments().getString("Parent_Mobile");

                String object_id=getArguments().getString("objectId");
                Student_data Student=new Student_data(Name,Parent_id,Parent_Email,Parent_Mobile,email1);
                Student.objectId=object_id;
                Toast.makeText(getContext(), "Deleting Student ..... ", Toast.LENGTH_LONG).show();
                Backendless.Persistence.of(Student_data.class).remove(Student, new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new teacher_successfully_logedin()).commit();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return  root;
    }

}
