package com.example.foda_.follow_your_son;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.persistence.BackendlessDataQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Info_Teacher extends android.support.v4.app.Fragment {
TextView Name_,Subject_,Area_,Years_;
    ImageView Picture;
    RatingBar ratingBar;
    Button button1,button2;
    String object_id="";

    @Override
    public void onStart() {
        super.onStart();
        String x="0";
        getRating rating =new getRating();
        rating.execute(x);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_info__teacher, container, false);
        final Teachers_profiles Teachers=getArguments().getParcelable("Teacher_Object");
        String Name=Teachers.Tname;
        String Subject=Teachers.Subject;
        final String Area=Teachers.Area;
        final String Email=Teachers.Email;
        String Years=Teachers.Years;
        String Phone=Teachers.Phone;

        String Url=getArguments().getString("Teacher_picture");
        Name_=(TextView)root.findViewById(R.id.info_name);
        Area_=(TextView)root.findViewById(R.id.info_Area);
        Subject_=(TextView)root.findViewById(R.id.info_Subject);
        Years_=(TextView)root.findViewById(R.id.info_years);
        ratingBar=(RatingBar)root.findViewById(R.id.ratingBar);
        Picture=(ImageView)root.findViewById(R.id.info_image);
        button1=(Button)root.findViewById(R.id.button_phone);
        button2=(Button)root.findViewById(R.id.button_email);
        Picasso.with(getContext()).load(Url).placeholder(R.drawable.loading_teacher2).into(Picture);
        Name_.setText("Name : " + Name);
        Area_.setText("Area : "+Area);
        Years_.setText("Years : "+Years);
        Subject_.setText("Subject : " + Subject);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + Teachers.Phone));
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{Email});
                intent.setType("text/plain");
                startActivity(intent);

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                getRating rating1 =new getRating();
                rating1.execute(String.valueOf(rating));

            }
        });
        return root;
    }
        public class  getRating extends AsyncTask<String, Void, Float>
         {Teachers_profiles Teachers=getArguments().getParcelable("Teacher_Object");


             String Parent_Email;
             ProgressDialog dialog;
       /*  BackendlessUser user=Backendless.UserService.CurrentUser();
            String email=(String)user.getProperty("email");*/
             Rating_Details rating_details;
             float Total=0f;
             float Average=0f;
             String Email=Teachers.Email;

             @Override
             protected Float doInBackground(String... params) {
                 if (params[0]=="0")
                 {
                     String WhereClause="teacher_email = '"+Email+"'";
                     BackendlessDataQuery dataQuery=new BackendlessDataQuery(WhereClause);
                     BackendlessCollection<Rating_Details> User=Backendless.Persistence.of(Rating_Details.class).find(dataQuery);
                     for (int i=0;i<User.getCurrentPage().size();i++)
                     {
                         Total+=User.getCurrentPage().get(i).rating_value;
                     }
                     Average=Total/User.getCurrentPage().size();
                     return Average;

                 }
                 else{
                    BackendlessUser user = Backendless.UserService.CurrentUser();
                     Parent_Email = (String) user.getProperty("email");
                     String WhereClause="parent_email = '"+Parent_Email+"'";
                     ArrayList<Rating_Details> rating_arraylist=new ArrayList<Rating_Details>();
                     BackendlessDataQuery dataQuery=new BackendlessDataQuery(WhereClause);
                     BackendlessCollection<Rating_Details> User=Backendless.Persistence.of(Rating_Details.class).find(dataQuery);
                     for (int i=0;i<User.getCurrentPage().size();i++)
                     {
                         rating_arraylist.add(User.getCurrentPage().get(i));
                         if (Email.equals(rating_arraylist.get(i).teacher_email.toString()))
                         {
                             object_id=  rating_arraylist.get(i).objectId;
                         }
                     }
                     float x=Float.parseFloat(params[0]);
                     rating_details = new Rating_Details(Parent_Email, Email,Float.parseFloat(params[0]) );
                     if (!object_id.isEmpty() )
                     {
                         rating_details.objectId = object_id;
                     }
                     Backendless.Persistence.save( rating_details);
                 }
                 return null;
             }
             @Override
             protected void onPreExecute() {
                 super.onPreExecute();
                 dialog=new ProgressDialog(getContext());
                 dialog.setMessage("Loading ....");
                 dialog.show();
             }
             @Override
             protected void onPostExecute(Float aVoid) {
                 super.onPostExecute(aVoid);
                 if (Total!=0) {
                     ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
                     ratingBar.setRating(Average);
                     dialog.dismiss();
                     return;
                 }
                 dialog.dismiss();
             }
         }
}
