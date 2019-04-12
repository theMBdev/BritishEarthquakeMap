//
// Name                 Martin Brown
// Student ID           S1718539
// Programme of Study   Computing
//

package com.example.britishearthquakemap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class IndividualActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "IndividualActivity";
    public GoogleMap mMap;
    String lati;
    String longi;
    String titlei;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    // get the intent values passed over
    private void getIncomingIntent(){

            titlei = getIntent().getStringExtra("title");
            longi = getIntent().getStringExtra("geoLong");
            lati = getIntent().getStringExtra("geoLat");
            String location = getIntent().getStringExtra("location");
            String magnitude = getIntent().getStringExtra("magnitude");
            String depth = getIntent().getStringExtra("depth");
            String dateTime = getIntent().getStringExtra("dateTime");
            String link = getIntent().getStringExtra("link");


        setValues(titlei, lati, longi, depth, location, magnitude, dateTime, link);

    }

    private void setValues(String inpTitle, String inpLong, String inpLat, String inpDepth, String inpMagnitude, String inpLocation, String inpDateTime, String inpLink){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView depth = findViewById(R.id.depth);
        depth.setText(inpDepth + " deep");

        TextView geoLong = findViewById(R.id.geoLong);
        geoLong.setText("Long " + inpLong);

        TextView geoLat = findViewById(R.id.geoLat);
        geoLat.setText("Lat " + inpLat);

        //TextView pubDate = findViewById(R.id.pubDate);
        //pubDate.setText(inpPubDate);

        TextView location = findViewById(R.id.location);
        location.setText(inpLocation);

        TextView magnitude = findViewById(R.id.magnitude);
        magnitude.setText(inpMagnitude );

        TextView dateTime = findViewById(R.id.dateTime);
        dateTime.setText(inpDateTime);

        TextView link = findViewById(R.id.link);
        link.setText(inpLink);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        double lat;
        double lng;

        lat = Double.parseDouble(lati);
        lng = Double.parseDouble(longi);

        LatLng latLngInput = new LatLng(lat, lng);

        mMap.addMarker(new MarkerOptions().position(latLngInput).title(titlei));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngInput, 6));

    }
}


















