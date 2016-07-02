package com.example.foda_.follow_your_son;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;


public class My_Sons extends android.support.v4.app.Fragment {
    ProgressDialog progressDialog;

    ArrayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my__sons, container, false);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);


        get_my_Sons getMySons=new get_my_Sons();
        getMySons.execute();
    }
    public class get_my_Sons extends AsyncTask<Void, Void, Void>
    {    ListView list;
        ArrayList<Saved_Message> sons=new ArrayList<Saved_Message>();
        @Override
        protected Void doInBackground(Void... params) {
            BackendlessUser user=Backendless.UserService.CurrentUser();
            String Parent=(String )user.getProperty("email");
            String whereClause = "Parent_Email = '" + Parent + "'";
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);
            BackendlessCollection<Saved_Message> myresult = Backendless.Persistence.of(Saved_Message.class ).find(dataQuery);
            for (int i=0;i<myresult.getCurrentPage().size();i++)
            {
                sons.add(myresult.getCurrentPage().get(i));
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
            String []array=new String[sons.size()];
            for (int i=0;i<array.length;i++) {
                array[i] = sons.get(i).Student_Name;
            }
            progressDialog.dismiss();
            adapter=new ArrayAdapter(getContext(), R.layout.textview,R.id.mytextview,array);
            list=(ListView)getActivity().findViewById(R.id.List_Sons);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String note=sons.get(position).Notes;
                    String Student=sons.get(position).Student_Name;
                    new AlertDialog.Builder(getContext()).setTitle(Student+" Note").setMessage(note).show();
                }
            });

        }
    }
}
