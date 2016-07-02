package com.example.foda_.follow_your_son;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Teacher.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Teacher#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Teacher extends android.support.v4.app.Fragment {
    public ProgressDialog progressDialog;
    EditText teacherName,teacherEmail,teacherPassword,teacherConfirmPassword;
    Button Submit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Teacher.
     */
    // TODO: Rename and change types and number of parameters
    public static Teacher newInstance(String param1, String param2) {
        Teacher fragment = new Teacher();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Teacher() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_teacher, container, false);

        teacherName=(EditText)root.findViewById(R.id.TeacherName);
        teacherEmail=(EditText)root.findViewById(R.id.TeacherEmail);
        teacherPassword=(EditText)root.findViewById(R.id.TeacherPassword);
        teacherConfirmPassword=(EditText)root.findViewById(R.id.TeacherConfirmPassword);
        Submit=(Button)root.findViewById(R.id.TeacherSubmit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading");
                progressDialog.show();

                String Password = teacherPassword.getText().toString();
                String Email = teacherEmail.getText().toString();
                String Name = teacherName.getText().toString();
                String ConfirmPassword = teacherConfirmPassword.getText().toString();

                if (!ConfirmPassword.equals(Password)) {
                    progressDialog.dismiss();
                    teacherPassword.setError("Not Matching");
                    teacherConfirmPassword.setError("Not Matching");
                }
                else if (Email.isEmpty()||Name.isEmpty()||Password.isEmpty()||ConfirmPassword.isEmpty()){
                   progressDialog.dismiss();
                    Toast.makeText(getContext(), "All fields are required \n please check it again", Toast.LENGTH_LONG).show();
                }
                else {
                    boolean x= haveNetworkConnection();
                    if (x==true){

                    final BackendlessUser backendlessUser = new BackendlessUser();
                    backendlessUser.setPassword(Password);
                    backendlessUser.setProperty("email", Email);
                    backendlessUser.setProperty("name", Name);
                    backendlessUser.setProperty("register","Teacher");
                    Backendless.UserService.register(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            progressDialog.dismiss();
                            Snackbar.make(container,"Successfully Registered", Snackbar.LENGTH_LONG).show();
                            getFragmentManager().beginTransaction().replace(R.id.frame, new MainActivityFragment()).commit();
                            Toast.makeText(getContext(), "The Confirmation Email has been Sent", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void handleFault(BackendlessFault fault) {
                            progressDialog.dismiss();
                            Snackbar.make(container,fault.getMessage(),Snackbar.LENGTH_LONG).show();
                            Log.e("message", fault.getMessage());

                            Log.e("aaa",fault.getCode());


                        }
                    });
                }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "No internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


         return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public  boolean haveNetworkConnection() {
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
