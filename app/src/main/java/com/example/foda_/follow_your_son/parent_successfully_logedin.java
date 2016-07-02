package com.example.foda_.follow_your_son;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class parent_successfully_logedin extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button MySons,Teachers_profiles;
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_parent_successfully_logedin, container, false);
        MySons=(Button)root.findViewById(R.id.My_Sons);
        Teachers_profiles=(Button)root.findViewById(R.id.Teachers_profiles);
        getFragmentManager().beginTransaction().replace(R.id.parent_frames,new My_Sons()).commit();
        Teachers_profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.parent_frames, new Teachers_Grid_View()).commit();

            }
        });
        MySons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.parent_frames,new My_Sons()).commit();

            }
        });


        return  root;
    }

}
