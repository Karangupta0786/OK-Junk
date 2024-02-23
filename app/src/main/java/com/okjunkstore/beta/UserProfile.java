package com.okjunkstore.beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.okjunkstore.beta.Helperclass.HomeAdapter.NoticeData;
import com.okjunkstore.beta.Helperclass.HomeAdapter.TeacherAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {

//    EditText yourname, youremail, yourphoneNo, yourpassword;
//    TextView fullnameLabel, usernameLabel;

    private RecyclerView csDepartment,meachanicalDepartment,physicsDepartment,chemistryDepartment;
    private LinearLayout csNoData,mechNoData,physicsNoData,chemistryNoData;
    private List<NoticeData> list1,list2,list3,list4;
    private DatabaseReference reference,dbRef;

    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_user_profile);

        csDepartment = findViewById(R.id.csDepartment);
        meachanicalDepartment = findViewById(R.id.meachanicalDepartment);
        physicsDepartment = findViewById(R.id.physicsDepartment);
        chemistryDepartment = findViewById(R.id.chemistryDepartment);

        csNoData = findViewById(R.id.csNoData);
        mechNoData = findViewById(R.id.mechNoData);
        physicsNoData = findViewById(R.id.physicsNoData);
        chemistryNoData = findViewById(R.id.chemistryNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");
        
        csDepartment();
        meachanicalDepartment();
        physicsDepartment();
        chemistryDepartment();

       /* //Hooks
        yourname = findViewById(R.id.full_name_profile);
        youremail = findViewById(R.id.email_profile);
        yourphoneNo = findViewById(R.id.phone_no_profile);
        yourpassword = findViewById(R.id.password_profile);
        fullnameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        //ShowAllData
        showAllUserData();
    }
    private void showAllUserData() {
        Intent intent = getIntent();

        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phoneNo");
        String user_password = intent.getStringExtra("password");

        fullnameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        yourname.setText(user_name);
        youremail.setText(user_email);
        yourphoneNo.setText(user_phoneNo);
        yourpassword.setText(user_password);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),RetailerDashboard.class));
        finish();
    }
*/
    }

    private void csDepartment() {
        dbRef = reference.child("House");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else{
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        NoticeData data = snapshot1.getValue(NoticeData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new TeacherAdapter(list1,UserProfile.this);
                    csDepartment.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void meachanicalDepartment() {
        dbRef = reference.child("Apartment");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    mechNoData.setVisibility(View.VISIBLE);
                    meachanicalDepartment.setVisibility(View.GONE);
                }else{

                    mechNoData.setVisibility(View.GONE);
                    meachanicalDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        NoticeData data = snapshot1.getValue(NoticeData.class);
                        list2.add(data);
                    }
                    meachanicalDepartment.setHasFixedSize(true);
                    meachanicalDepartment.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new TeacherAdapter(list2,UserProfile.this);
                    meachanicalDepartment.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void physicsDepartment() {
        dbRef = reference.child("Shop");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){
                    physicsNoData.setVisibility(View.VISIBLE);
                    physicsDepartment.setVisibility(View.GONE);
                }else{
                    physicsNoData.setVisibility(View.GONE);
                    physicsDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        NoticeData data = snapshot1.getValue(NoticeData.class);
                        list3.add(data);
                    }
                    physicsDepartment.setHasFixedSize(true);
                    physicsDepartment.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new TeacherAdapter(list3,UserProfile.this);
                    physicsDepartment.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void chemistryDepartment() {
        dbRef = reference.child("School");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()){
                    chemistryNoData.setVisibility(View.VISIBLE);
                    chemistryDepartment.setVisibility(View.GONE);
                }else{
                    chemistryNoData.setVisibility(View.GONE);
                    chemistryDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        NoticeData data = snapshot1.getValue(NoticeData.class);
                        list4.add(data);
                    }
                    chemistryDepartment.setHasFixedSize(true);
                    chemistryDepartment.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new TeacherAdapter(list4,UserProfile.this);
                    chemistryDepartment.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}