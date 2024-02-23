package com.okjunkstore.beta.FragmentsRetailerDashbord;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.okjunkstore.beta.R;
import com.okjunkstore.beta.UserDashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RetailerSellFragment extends Fragment {

    static final int REQUEST_CALL = 1;
    FloatingActionButton call;
    FloatingActionButton sellActivity;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
        case REQUEST_CALL:{
            //if request is cancelled, th array is empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Permission is Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Permission not Granted, We need to disable the Functionality", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_sell, container, false);

        call = view.findViewById(R.id.callUs);
        sellActivity = view.findViewById(R.id.post);

        sellActivity.setOnClickListener(view1 -> {
//            Toast.makeText(getContext(), "Please Do not apply Dark Theme", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(getActivity() , UserDashboard.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(sellActivity, "transition_sell");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
            startActivity(intent, options.toBundle());
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),Manifest.permission.CALL_PHONE)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Request Permission");
                    builder.setMessage("You should enable this permission to CALL_PHONE so we can to that and that...");
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    });
                    builder.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    });
                    builder.show();

                    }else{
                        ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    }
                }else {
                    //Permission has already been granted

                }

               Intent intent = new Intent(Intent.ACTION_CALL);
               intent.setData(Uri.parse("tel:7905845935"));
               if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                   return;
               }
               startActivity(intent);
            }

        });


        return view;
       }
}