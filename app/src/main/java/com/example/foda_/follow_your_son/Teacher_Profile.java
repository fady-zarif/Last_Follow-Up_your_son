package com.example.foda_.follow_your_son;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.persistence.BackendlessDataQuery;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Teacher_Profile extends android.support.v4.app.Fragment {
TextView Email;

EditText Name,Phone,Area,Subject,Years;
    Button share_Profile;
    ImageView user_pic;
    ProgressDialog progress;
    String object="";
    boolean image=false;
    boolean onActivityResult=false;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_teacher__profile, container, false);

        BackendlessUser user = Backendless.UserService.CurrentUser();
        Name=(EditText)root.findViewById(R.id.naMe);
        Email=(TextView)root.findViewById(R.id.emAil);
        Phone=(EditText)root.findViewById(R.id.phONE);
        Area=(EditText)root.findViewById(R.id.arEA);
        Subject=(EditText)root.findViewById(R.id.suBject);
        Years=(EditText)root.findViewById(R.id.yeARS);
        share_Profile=(Button)root.findViewById(R.id.Share_profile);
        Name.setText(user.getProperty("name").toString());
        Email.setText(user.getProperty("email").toString());
        user_pic=(ImageView)root.findViewById(R.id.User_image);
        user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
            }
        });
        share_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TName=Name.getText().toString();
                String TEmail=Email.getText().toString();
                String phone=Phone.getText().toString();
                String area=Area.getText().toString();
                String years=Years.getText().toString();
                String subject=Subject.getText().toString();

                if (TName.isEmpty()||TEmail.isEmpty()||phone.isEmpty()||area.isEmpty()||years.isEmpty()||subject.isEmpty())
                {
                    Snackbar.make(container, "All field Are Required", Snackbar.LENGTH_LONG).show();
                }
                else if(image==false)
                {
                    Snackbar.make(container, "Choose your picture ", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Teachers_profiles teachers_profiles=new Teachers_profiles(TName,TEmail,subject,phone,area,years);
                    if (!object.isEmpty()||!object.equals(""))
                    {
                        teachers_profiles.objectId=object;
                    }
                    Toast.makeText(getContext(), "Sharing profile ....  ", Toast.LENGTH_LONG).show();
                    Backendless.Persistence.save(teachers_profiles, new AsyncCallback<Teachers_profiles>() {
                        @Override
                        public void handleResponse(Teachers_profiles response) {
                            Toast.makeText(getContext(), "Your profile Successfully saved ", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStart() {
        super.onStart();
        if (onActivityResult==false)
        {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Loading ... ");
        progress.setCanceledOnTouchOutside(false);
        Get_Profile get_profile=new Get_Profile();
        get_profile.execute();
    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResult=true;
        if(requestCode==1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                final ProgressDialog progress=new ProgressDialog(getContext());
                progress.setMessage("Uploading your picture");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                boolean overwrite=true;
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                BackendlessUser user=Backendless.UserService.CurrentUser();
                String Email_text=(String )user.getProperty("email");
                Backendless.Files.Android.upload(bitmap,
                        Bitmap.CompressFormat.PNG,
                        100,
                        Email_text,
                        "pictures", overwrite , new AsyncCallback<BackendlessFile>() {
                            @Override
                            public void handleResponse(BackendlessFile response) {
                            progress.dismiss();
                                Toast.makeText( getContext(),"Successfully", Toast.LENGTH_SHORT ).show();
                                image=true;
                            }
                            @Override
                            public void handleFault(BackendlessFault fault) {
                               progress.dismiss();
                                Toast.makeText( getContext(), fault.toString(), Toast.LENGTH_SHORT ).show();
                            }
                        });

                    user_pic.setImageResource(0);
                    user_pic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public class Get_Profile extends AsyncTask<Void,Void,Void>
    {
        ArrayList<Teachers_profiles> profilesArrayList=new ArrayList<Teachers_profiles>();
        String URL="";
        @Override
        protected Void doInBackground(Void... params) {

            BackendlessUser USer=Backendless.UserService.CurrentUser();
            String email = (String) USer.getProperty("email");
            String WhereClause = "Email = '" + email + "'";
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(WhereClause);
            BackendlessCollection<Teachers_profiles> result=Backendless.Persistence.of(Teachers_profiles.class).find(dataQuery);
            int count = result.getCurrentPage().size();
            if (count !=0) {
                profilesArrayList.add(result.getCurrentPage().get(0));
                URL="https://api.backendless.com/6EDD4F2D-E780-9B9C-FF5D-21F589050F00/v1/files/pictures/"+email;
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
            Name=(EditText)getActivity().findViewById(R.id.naMe);
            Email=(TextView)getActivity().findViewById(R.id.emAil);
            Phone=(EditText)getActivity().findViewById(R.id.phONE);
            Area=(EditText) getActivity().findViewById(R.id.arEA);
            Subject=(EditText)getActivity().findViewById(R.id.suBject);
            Years=(EditText)getActivity().findViewById(R.id.yeARS);

            if (profilesArrayList.size()!=0)
            {
                String Email_=profilesArrayList.get(0).Email;
                String Name_=profilesArrayList.get(0).Tname;
                String Subject_=profilesArrayList.get(0).Subject;
                String Years_=profilesArrayList.get(0).Years;
                String Area_=profilesArrayList.get(0).Area;
                String Phone_=profilesArrayList.get(0).Phone;
                object=profilesArrayList.get(0).objectId;
                user_pic=(ImageView)getActivity().findViewById(R.id.User_image);
                Name.setText(Name_);
                Email.setText(Email_);
                Phone.setText(Phone_);
                Subject.setText(Subject_);
                Years.setText(Years_);
                Area.setText(Area_);
                user_pic.setImageDrawable(null);

                Picasso.with(getContext()).load(URL).resize(100 ,100).placeholder(R.drawable.spinner).into(user_pic);
                image=true;

            }
            progress.dismiss();


        }
    }

}
