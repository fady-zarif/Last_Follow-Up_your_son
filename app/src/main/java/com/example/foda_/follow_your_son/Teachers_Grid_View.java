package com.example.foda_.follow_your_son;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;

import java.util.ArrayList;


public class Teachers_Grid_View extends android.support.v4.app.Fragment {
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_teachers__grid__view, container, false);


        return  root;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        get_Pics pics=new get_Pics();
        pics.execute();
    }

    public class  get_Pics extends AsyncTask<Void,Void,Void>
    {
        ArrayList<Teachers_profiles> profiles=new ArrayList<Teachers_profiles>();

        @Override
        protected Void doInBackground(Void... params) {


            BackendlessCollection<Teachers_profiles> result = Backendless.Persistence.of( Teachers_profiles.class ).find();
            for (int i=0;i<result.getCurrentPage().size();i++)
            {
                profiles.add(result.getCurrentPage().get(i));
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GridView ListView_teacher=(GridView)getActivity().findViewById(R.id.grid_view);
            ListView_Adapter adapter;
           ArrayList<String>images=new ArrayList<String>();

            for (int i=0;i<profiles.size();i++)
            {
                String mail=profiles.get(i).Email;
                String  URL="https://api.backendless.com/6EDD4F2D-E780-9B9C-FF5D-21F589050F00/v1/files/pictures/"+mail;
               images.add(URL);
            }

            adapter=new ListView_Adapter(getActivity(),images,profiles);
            ListView_teacher.setAdapter( adapter);
            progressDialog.dismiss();

             ListView_teacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Name=profiles.get(position).Tname;
                String Subject=profiles.get(position).Subject;
                String Area=profiles.get(position).Area;
                String Email=profiles.get(position).Email;
                String Years=profiles.get(position).Years;
                String Phone=profiles.get(position).Phone;
                String URL="https://api.backendless.com/6EDD4F2D-E780-9B9C-FF5D-21F589050F00/v1/files/pictures/"+Email;
                Bundle bundle=new Bundle();
                Teachers_profiles teachersProfiles=new Teachers_profiles(Name,Email,Subject,Phone,Area,Years);
                bundle.putParcelable("Teacher_Object", teachersProfiles);
                bundle.putString("Teacher_picture", URL);
                Info_Teacher fragment=new Info_Teacher();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().addToBackStack("text").replace(R.id.frame,fragment).commit();

            }
        });
        }

    }


}
