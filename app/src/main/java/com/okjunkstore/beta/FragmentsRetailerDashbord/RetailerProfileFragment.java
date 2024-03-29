package com.okjunkstore.beta.FragmentsRetailerDashbord;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.okjunkstore.beta.HowWeWork;
import com.okjunkstore.beta.R;
import com.okjunkstore.beta.UserDashboard;
import com.okjunkstore.beta.UserProfile;
import com.google.android.material.button.MaterialButton;

public class RetailerProfileFragment extends Fragment {

    public RetailerProfileFragment(){
    }

    MaterialButton profile, signup;
    Button how;

    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_profile, container, false);

        profile = view.findViewById(R.id.login_btn);
        signup = view.findViewById(R.id.signup_btn);
        how = view.findViewById(R.id.how);
        dialog = new Dialog(getContext());

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent =new Intent(getActivity() , UserProfile.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(profile, "transition_login");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
                startActivity(intent, options.toBundle());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpDialoge();
            }
        });

        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity() , HowWeWork.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(how, "transition_signup");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
                startActivity(intent, options.toBundle());            }
        });

        return view;

    }

    private void openSignUpDialoge() {
        dialog.setContentView(R.layout.signup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        TextView btn = dialog.findViewById(R.id.just);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getContext(), UserDashboard.class));
            }
        });
        dialog.show();
    }
//    public void callLoginScreen(View view){
//        Intent intent = new Intent(getContext() , Login.class);
//        startActivity(intent);
//    }
//
//    public void callSignUpScreen(View view){
//        Intent intent = new Intent(getContext() , SingUp.class);
//        startActivity(intent);
//    }
}