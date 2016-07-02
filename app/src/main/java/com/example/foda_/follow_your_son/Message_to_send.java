package com.example.foda_.follow_your_son;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Message_to_send extends android.support.v4.app.Fragment {
EditText editText;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragment_message_to_send, container, false);



        editText=(EditText)root.findViewById(R.id.message);
        button=(Button)root.findViewById(R.id.Submit_message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Note=editText.getText().toString();
                if (!Note.isEmpty()) {
                    final ProgressDialog dialog=new ProgressDialog(getContext());
                    dialog.setMessage("Sending");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    String Name=getArguments().getString("Name2");
                    String parent_Email=getArguments().getString("Parent_Email");
                    String Teacher_Email=getArguments().getString("Teacher_Email");
                    Saved_Message Object_message = new Saved_Message(Name, parent_Email, Teacher_Email, Note);
                    Backendless.Persistence.save(Object_message, new AsyncCallback<Saved_Message>() {
                        @Override
                        public void handleResponse(Saved_Message response) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Your Note About  Send Successfully ", Toast.LENGTH_SHORT).show();
                            getFragmentManager().beginTransaction().replace(R.id.frame, new teacher_successfully_logedin()).commit();

                        }
                        @Override
                        public void handleFault(BackendlessFault fault) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Your Note About  Sorry Your Note Not Send \t try Again Later" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "please Enter your Note First ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}
