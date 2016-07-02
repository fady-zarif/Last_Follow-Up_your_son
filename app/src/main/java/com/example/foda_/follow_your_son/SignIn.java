package com.example.foda_.follow_your_son;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


public class SignIn extends android.support.v4.app.Fragment {
    EditText editText1,editText2;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View root= inflater.inflate(R.layout.fragment_sign_in, container, false);
     //   String Parent_id=getArguments().getString("Parent_id");
        editText1=(EditText)root.findViewById(R.id.Sign_in_UserName);
        editText2=(EditText)root.findViewById(R.id.Sign_in_pass);

        button=(Button)root.findViewById(R.id.Log_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog =new ProgressDialog(getContext());
                if (haveNetworkConnection())
                {
                progressDialog.setMessage("Loading .. ");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
                Backendless.UserService.login(editText1.getText().toString(), editText2.getText().toString(), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        BackendlessUser user = Backendless.UserService.CurrentUser();
                        if (user != null) {
                            // get user's phone number (i.e. custom property)
                            String register = (String) user.getProperty("register");
                            if (register.equals("Teacher")) {
                                progressDialog.dismiss();
                                getFragmentManager().beginTransaction().replace(R.id.frame, new teacher_successfully_logedin()).commit();
                                Snackbar.make(container, "Successfully logIn", Snackbar.LENGTH_LONG).show();


                            } else {
                                progressDialog.dismiss();
                                getFragmentManager().beginTransaction().replace(R.id.frame, new parent_successfully_logedin()).commit();
                                Snackbar.make(container, "Successfully logIn", Snackbar.LENGTH_LONG).show();

                            }
                        }
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                         progressDialog.dismiss();
                        Snackbar.make(container, "Your username or password wrong \n Try Again", Snackbar.LENGTH_LONG).show();
                    }
                });
            }else{
                    Toast.makeText(getContext(), "NO Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  root;
    }
    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
