package com.example.foda_.follow_your_son;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
Button parent_for_sign_up,teacher_for_sign_up,sign_in,Local;
  //  EditText Sign_in_Email,Sign_in_password;
    Button button;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View root= inflater.inflate(R.layout.fragment_main, container, false);
        parent_for_sign_up=(Button)root.findViewById(R.id.parent_signup );
        parent_for_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(" ").replace(R.id.frame, new Parent()).commit();
            }
        });
        teacher_for_sign_up=(Button)root.findViewById(R.id.teacher_sign_up );
        teacher_for_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//new Teacher()
                getFragmentManager().beginTransaction().addToBackStack("ab").replace(R.id.frame,new Teacher()).commit();
            }
        });
      /*  Local=(Button)root.findViewById(R.id.local_contact);
        Local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("ab").replace(R.id.frame,new teacher_Account()).commit();
            }
        });*/
        sign_in=(Button)root.findViewById(R.id.Sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("a").replace(R.id.frame , new SignIn()).commit();
            }
        });
        return root;
    }


}
