package com.example.foda_.follow_your_son;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class group_details extends android.support.v4.app.Fragment {
    EditText Student_name,Parent_id,Parent_Email,Parent_Mobile;
    Button Save;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_add_student_details, container, false);

        Student_name=(EditText)root.findViewById(R.id.Student_name);
        Parent_id=(EditText)root.findViewById(R.id.parent_id);
        Parent_Email=(EditText)root.findViewById(R.id.Parent_Email);
        Parent_Mobile=(EditText)root.findViewById(R.id.Parent_Moile);
        Save=(Button)root.findViewById(R.id.Save);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=Student_name.getText().toString();
                String parentId=Parent_id.getText().toString();
                String parentEmail=Parent_Email.getText().toString();
                String parentMobile=Parent_Mobile.getText().toString();
             //   Student_data student_data=new Student_data(Name,parentId,parentEmail,parentMobile);
                if (Name.isEmpty()||parentId.isEmpty()||parentEmail.isEmpty()||parentMobile.isEmpty())
                {
                    Snackbar.make(container, "All field Are Required", Snackbar.LENGTH_LONG).show();
                }
                else {
                    String Name1=Student_name.getText().toString();
                    String parentId1=Parent_id.getText().toString();
                    String parentEmail1=Parent_Email.getText().toString();
                    String parentMobile1=Parent_Mobile.getText().toString();

                    ProgressDialog progressDialog=new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading ...");
                    progressDialog.show();
                    BackendlessUser user = Backendless.UserService.CurrentUser();
                    String email = (String) user.getProperty("email");
                       progressDialog.dismiss();

                    Student_data student_data=new Student_data(Name1,parentId1,parentEmail1,parentMobile1,email);
                    Toast.makeText(getContext(), "Saving Student ....  ", Toast.LENGTH_LONG).show();
                    Backendless.Persistence.save(student_data, new AsyncCallback<Student_data>() {
                        @Override
                        public void handleResponse(Student_data response) {
                            Toast.makeText(getContext(), "Your data Successfully saved on the server ", Toast.LENGTH_SHORT).show();
                            getFragmentManager().beginTransaction().replace(R.id.frame, new teacher_successfully_logedin()).commit();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getContext(), "Failed to save Try Again ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return root;
    }


}

    // TODO: Rename method, update argument and hook method into UI event

