package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btn_loc;
    TextView text_view1,text_view2,text_view3,text_view4,text_view5;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_loc = findViewById(R.id.btn_loc);
        text_view1 = findViewById(R.id.text_view1);
        text_view2 = findViewById(R.id.text_view2);
        text_view3 = findViewById(R.id.text_view3);
        text_view4 = findViewById(R.id.text_view4);
        text_view5 = findViewById(R.id.text_view5);


        //initialize fused Location Provider Client

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission

                if(ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    //when permission granted
                    getLocation();

                }else{
                    //when permission denied
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            },44);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //initialize location

                Location location = task.getResult();
                if(location != null){
                    try {

                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        //initialize address list


                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        //set latitudes on TextView
                        text_view1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude : </b><br></font>"
                                + addresses.get(0).getLatitude()));

                        //set Longitude on TextView
                        text_view2.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude : </b><br></font>"
                                + addresses.get(0).getLongitude()));

                        //set country on TextView
                        text_view3.setText(Html.fromHtml("<font color='#6200EE'><b>Country : </b><br></font>"
                                + addresses.get(0).getCountryName()));

                        //set Address on TextView
                        text_view5.setText(Html.fromHtml("<font color='#6200EE'><b>Address Line : </b><br></font>"
                                + addresses.get(0).getAddressLine(0)));

                        //set country on TextView
                        text_view4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality : </b><br></font>"
                                + addresses.get(0).getCountryName()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}