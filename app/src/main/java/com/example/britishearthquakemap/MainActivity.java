//
// Name                 Martin Brown
// Student ID           S1718539
// Programme of Study   Computing
//

package com.example.britishearthquakemap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnMapReadyCallback {
    private String result;
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    public String resultFromNet = null;

    public GoogleMap mMap;

    public ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start getting the data from xml url
        startProgress();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            InputStream is = new ByteArrayInputStream(resultFromNet.getBytes(StandardCharsets.UTF_8));

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsingItem(parser);

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
    }

    private void processParsingItem(XmlPullParser parser) throws IOException, XmlPullParserException {


        int eventType = parser.getEventType();

        Item currentItem = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();


                    if ("item".equals(eltName)) {
                        currentItem = new Item();
                        items.add(currentItem);
                    } else if (currentItem != null) {
                        if ("title".equals(eltName)) {
                            currentItem.title = parser.nextText();
                        } else if ("description".equals(eltName)) {
                            currentItem.description = parser.nextText();

                            String[] splitDescription = currentItem.description.split(";");
                            String locationWithTag = splitDescription[1];
                            String depthWithTag = splitDescription[3];
                            String magnitudeWithTag = splitDescription[4];

                            String[] splitLocation = locationWithTag.split(":");
                            String location = splitLocation[1];
                            // remove space from start of text
                            location = location.substring(1);
                            currentItem.location = location;

                            String[] splitDepth = depthWithTag.split(":");
                            String depth = splitDepth[1];
                            // remove space from start of text
                            depth = depth.substring(1);
                            currentItem.depth = depth;

                            String[] splitMagnitude = magnitudeWithTag.split(":");
                            String magnitude = splitMagnitude[1];
                            // remove 2 spaces from start of text
                            magnitude = magnitude.substring(2);
                            currentItem.magnitude = magnitude;


                        } else if ("link".equals(eltName)) {
                            currentItem.link = parser.nextText();
                        } else if ("pubDate".equals(eltName)) {
                            currentItem.pubDate = parser.nextText();

                            String[] splitPubDate = currentItem.pubDate.split(",");

                            String day = splitPubDate[0];
                            String dateTime = splitPubDate[1];

                            currentItem.day = day;

                            // remove space from start of text
                            dateTime = dateTime.substring(1);
                            currentItem.dateTime = dateTime;



                        } else if ("category".equals(eltName)) {
                            currentItem.category = parser.nextText();
                        } else if ("geo:lat".equals(eltName)) {
                            currentItem.geoLat = parser.nextText();
                        } else if ("geo:long".equals(eltName)) {
                            currentItem.geoLong = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }

        initRecyclerView();
        highestMagnitude(items);
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void onClick(View aview)
    {
        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng camera = new LatLng(55.3781, -3.4360);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 5));


        double lat = 0;
        double lng = 0;

        LatLng latLngInput1 = new LatLng(lat, lng);

        //Add all earthquake markers
        for (Item item : items) {
            lat = Double.parseDouble(item.geoLat);
            lng = Double.parseDouble(item.geoLong);

            LatLng latLngInput = new LatLng(lat, lng);

            mMap.addMarker(new MarkerOptions().position(latLngInput).title(item.title));
        }



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                double lat = 0;
                double lng = 0;

                LatLng latLngInput1 = new LatLng(lat, lng);

                //Add all earthquake markers
                for (Item item : items) {
                    lat = Double.parseDouble(item.geoLat);
                    lng = Double.parseDouble(item.geoLong);

                    LatLng latLngInput = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(latLngInput).title(item.title));
                }
            }
        });



    }

    // get highest magnitude
    public void highestMagnitude(ArrayList<Item> items){

        TextView magnitudeBig = findViewById(R.id.magnitudeBig);
        Double highestNum = 0.0;

        for (Item item : items) {

            double input = Double.parseDouble(item.magnitude);

            if (input > highestNum) {
                highestNum = input;
            }
        }

        magnitudeBig.setText("Highest Magnitude " + highestNum.toString());
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";



            Log.e("MyTag1","in run");

            try
            {
                Log.e("MyTag1","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    resultFromNet = result;
                    // remove the null from the returned xml
                    resultFromNet = resultFromNet.substring(4);

                    parseXML();
                    //Log.d("Result Output", resultFromNet.substring(4));

                    Log.d("Result Output", resultFromNet);


                }
            });
        }
    }
}