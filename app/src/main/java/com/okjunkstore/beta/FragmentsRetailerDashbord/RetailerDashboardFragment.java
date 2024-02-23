package com.okjunkstore.beta.FragmentsRetailerDashbord;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.okjunkstore.beta.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.okjunkstore.beta.UserDashboard;

import java.util.ArrayList;
import java.util.List;

public class RetailerDashboardFragment extends Fragment {

//////////internet
    RelativeLayout internetNotLayout;
    TextView retry;
    LinearLayout internet_connected;
/////////internet

    FloatingActionButton fab;
    ScrollView scrollView;
    SwitchMaterial switchMaterial;

    ImageView callme;
    ImageSlider mainSlider;

    TextView books,plastics,steels,irons,bikes,cars,electronics;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    CardView plasticPrice,ironprice;
    RelativeLayout electronicPrice,newsPrice,steelPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_retailer_dashboard, container, false);

        switchMaterial = v.findViewById(R.id.switch_theme);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

/////   //   //internet
        internetNotLayout = v.findViewById(R.id.internet);
        internet_connected = v.findViewById(R.id.internet_connected);
        retry = v.findViewById(R.id.retry);
        if (networkAvailable()){
            internet_connected.setVisibility(View.VISIBLE);
            internetNotLayout.setVisibility(View.GONE);
        }else{
            internet_connected.setVisibility(View.GONE);
            internetNotLayout.setVisibility(View.VISIBLE);
        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkAvailable()){
                    internet_connected.setVisibility(View.VISIBLE);
                    internetNotLayout.setVisibility(View.GONE);
                }else{
                    internet_connected.setVisibility(View.GONE);
                    internetNotLayout.setVisibility(View.VISIBLE);
                }
            }
        });
///  ///////////////internet

///////////Hideable Action Button
        fab = v.findViewById(R.id.fab);
        scrollView = v.findViewById(R.id.scroll);
        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
//                if (i > 0 || i < 0 && i1 > 0 || i1 < 0 && fab.isShown())
//                    fab.hide();
            if (i > 0 || i1 > 0 || i2 > 0 || i3 > 0 && fab.isShown())
                fab.hide();
            else if(i < 0 || i1 < 0 || i2 < 0 || i3 < 0 && !fab.isShown())
                fab.show();
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity() , UserDashboard.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(fab, "transition_sell");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
                startActivity(intent, options.toBundle());
            }
        });
///////////Hideable Action Button

        mainSlider = v.findViewById(R.id.slider);
        final List<SlideModel> remoteImages = new ArrayList<>();

////////////////////PRICE
////////////////////PRICE
        plasticPrice = v.findViewById(R.id.plasticPrice);
        electronicPrice = v.findViewById(R.id.electronicPrice);
        newsPrice = v.findViewById(R.id.newspaperPrice);
        ironprice = v.findViewById(R.id.ironPrice);
        steelPrice = v.findViewById(R.id.steelPrice);
        plasticPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomSheetPriceFragment bottomSheetPriceFragment = new BottomSheetPriceFragment();
//                bottomSheetPriceFragment.show(getActivity().getSupportFragmentManager(), bottomSheetPriceFragment.getTag());

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String plastic = "Plastic";
                        String plasticRate = snapshot.child("plastic").getValue().toString();
                        String plastic5 = "Plastic 50Kg+";
                        String plastic5Rate = snapshot.child("plastic5").getValue().toString();
                        String plastic10 = "Plastic 100Kg+";
                        String plastic10Rate = snapshot.child("plastic10").getValue().toString();
                        String blackPlastic = "Black/Hard Plastic";
                        String blackPlasticRate = "Rs.5/Kg";

                        bundle.putString("plasti", plastic );
                        bundle.putString("plasticR", plasticRate);
                        bundle.putString("i5", plastic5 );
                        bundle.putString("i5R", plastic5Rate);
                        bundle.putString("i10", plastic10);
                        bundle.putString("i10R", plastic10Rate);
                        bundle.putString("bplastic", blackPlastic );
                        bundle.putString("bplasticRate", blackPlasticRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

              }
        });
        electronicPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Bundle bundle = new Bundle();
                        String washing = "Washing Machine";
                        String washingRate = snapshot.child("ElecWashing").getValue().toString();
                        String fridge = "Fridge";
                        String fridgeRate = snapshot.child("ElecFridge").getValue().toString();
                        String tv = "T.V.";
                        String tvRate = snapshot.child("ElecTv").getValue().toString();

                        bundle.putString("plasti", washing );
                        bundle.putString("plasticR", washingRate);
                        bundle.putString("i5", tv );
                        bundle.putString("i5R", tvRate);
                        bundle.putString("i10", fridge );
                        bundle.putString("i10R", fridgeRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        newsPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String book = "Book/Paper/Cartoon";
                        String bookRate = snapshot.child("Book").getValue().toString();
                        String book5 = "Books/Paper/Cartoon 50Kg+";
                        String book5Rate = snapshot.child("Book5").getValue().toString();
                        String book10 = "Books/Paper/Cartoon 100Kg+";
                        String book10Rate = snapshot.child("Book10").getValue().toString();
                        String saariBox = "Saari Boxes/Cards";
                        String saariBoxRate = "Rs.5/Kg";

                        bundle.putString("plasti", book );
                        bundle.putString("plasticR", bookRate);
                        bundle.putString("i5", book5 );
                        bundle.putString("i5R", book5Rate);
                        bundle.putString("i10", book10);
                        bundle.putString("i10R", book10Rate);
                        bundle.putString("bplastic", saariBox );
                        bundle.putString("bplasticRate", saariBoxRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        ironprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String iron = "Iron";
                        String ironRate = snapshot.child("iron").getValue().toString();
                        String iron5 = "Iron 50Kg+";
                        String iron5Rate = snapshot.child("iron5").getValue().toString();
                        String iron10 = "Iron 100Kg+";
                        String iron10Rate = snapshot.child("iron10").getValue().toString();
                        String tin = "Tin Shades/Boxes/Drum";
                        String tinRate = "Rs.15/Kg";

                        bundle.putString("plasti", iron);
                        bundle.putString("plasticR", ironRate);
                        bundle.putString("i5", iron5 );
                        bundle.putString("i5R", iron5Rate);
                        bundle.putString("i10", iron10);
                        bundle.putString("i10R", iron10Rate);
                        bundle.putString("bplastic", tin );
                        bundle.putString("bplasticRate", tinRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        steelPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String steel = "Steel";
                        String steelRate = snapshot.child("steel").getValue().toString();
                        String steel5 = "Steel 50Kg+";
                        String steel5Rate = snapshot.child("steel5").getValue().toString();
                        String steel10 = "Steel 100Kg+";
                        String steel10Rate = snapshot.child("steel10").getValue().toString();

                        bundle.putString("plasti", steel);
                        bundle.putString("plasticR", steelRate);
                        bundle.putString("i5", steel5 );
                        bundle.putString("i5R", steel5Rate);
                        bundle.putString("i10", steel10);
                        bundle.putString("i10R", steel10Rate);
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
////////////////////PRICE
////////////////////PRICE

        books = v.findViewById(R.id.boo);
        plastics = v.findViewById(R.id.plastics);
        steels = v.findViewById(R.id.steels);
        irons = v.findViewById(R.id.irons);
        bikes = v.findViewById(R.id.bikes);
        cars = v.findViewById(R.id.cars);
        electronics = v.findViewById(R.id.electronicsP);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Prices");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String book = snapshot.child("Book").getValue().toString();
                books.setText(book);

                String plastic = snapshot.child("plastic").getValue().toString();
                plastics.setText(plastic);

                String steel = snapshot.child("steel").getValue().toString();
                steels.setText(steel);

                String iron = snapshot.child("iron").getValue().toString();
                irons.setText(iron);

                String bike = snapshot.child("Bike").getValue().toString();
                bikes.setText(bike);

                String car = snapshot.child("Car").getValue().toString();
                cars.setText(car);

                String elect = snapshot.child("Electronics").getValue().toString();
                electronics.setText(elect);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data: snapshot.getChildren())
                            remoteImages.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.FIT));

                        mainSlider.setImageList(remoteImages,ScaleTypes.FIT);

                        /*mainSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Toast.makeText(getContext(),remoteImages.get(i).getTitle().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        callme = v.findViewById(R.id.callme);
        callme.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:7905845935"));
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            startActivity(intent);
        });

        return v;
    }

    private boolean networkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else
                        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }
}