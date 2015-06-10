package com.example.danny.android_project;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WeatherFragment extends Fragment {

    HttpURLConnection connection = null;
    private ProgressBar progressLoading;
    WeatherJSONParse weather= new WeatherJSONParse(); //all of the weather data with

    private TextView city;
    private FrameLayout WIcon;
    private TextView Temp;
    private TextView condition;
    private TextView conDescrip;
    private TextView deg,deg1,deg2,maxLab,minLab;
    private ImageButton OWButton;
    URL picURL;
    Drawable d;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_weather, container, false);

        city = (TextView) view.findViewById(R.id.location);
        WIcon = (FrameLayout) view.findViewById(R.id.wIcon);
        Temp = (TextView) view.findViewById(R.id.currentTemp);
        condition = (TextView) view.findViewById(R.id.mainWeather);
        conDescrip = (TextView) view.findViewById(R.id.weatherDes);
        progressLoading = (ProgressBar) view.findViewById(R.id.progressBar);
        deg=(TextView) view.findViewById(R.id.degree);
        deg1=(TextView) view.findViewById(R.id.degree2);
        deg2=(TextView) view.findViewById(R.id.degree3);
        maxLab=(TextView) view.findViewById(R.id.maxLab);
        minLab=(TextView) view.findViewById(R.id.minLab);
        OWButton=(ImageButton) view.findViewById(R.id.weatherFrom);

        //hide all the text
        //city.setVisibility(View.INVISIBLE);
        WIcon.setVisibility(View.INVISIBLE);
        Temp.setVisibility(View.INVISIBLE);
        condition.setVisibility(View.INVISIBLE);
        conDescrip.setVisibility(View.INVISIBLE);

        deg.setVisibility(View.INVISIBLE);
        deg1.setVisibility(View.INVISIBLE);
        deg2.setVisibility(View.INVISIBLE);
        maxLab.setVisibility(View.INVISIBLE);
        minLab.setVisibility(View.INVISIBLE);


       LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);
       startLoadTask(getActivity());


        OWButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://openweathermap.org";
                if(maxLab.getVisibility()!=View.INVISIBLE) {
                    url = url+"/city/" + weather.getCity().getId();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });



        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);



    }

    public void startLoadTask(Context c){
        if (isOnline()) {
            LoadData task = new LoadData();
            task.execute();
        } else {
            Toast.makeText(c, "Not online", Toast.LENGTH_LONG).show();
        }
    }
    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        //Get GPS Information here
        public void onLocationChanged(Location location) {
            weather.getCity().setLat(location.getLatitude());
            weather.getCity().setLon(location.getLongitude());
            //weathertext.setText(Double.toString(coord.getLat()));
            //temp.setText(Double.toString(location.getLatitude()));
            startLoadTask(getActivity());



        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private class LoadData extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;


        @Override
        protected void onPreExecute() {
            progressLoading.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        protected Long doInBackground(String... strings) {
            String BASE_URL= "http://api.openweathermap.org/data/2.5/weather?lat=";
            String BASE_URL2="&lon=";
            String BASE_URL3="mode=json&units=imperial";

//            String dataString = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api+key=" +
//                   Constants.API_KEY + "&per_page=" + Constants.NUM_PHOTOS + "&format=json&nojsoncallback=1";

            try {
                URL dataUrl = new URL(BASE_URL+weather.getCity().getLat()+BASE_URL2+weather.getCity().getLon()+BASE_URL3);
                Log.d("URL", BASE_URL+weather.getCity().getLat()+BASE_URL2+weather.getCity().getLon()+BASE_URL3);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                //Log.d("TAG", "status " + status);
                //if it is successful
                if (status == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();

                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String photoData = sb.toString();

                    // Log.d("TAG", photoData);
                    weather.parseInfo(photoData);

                    Log.d("AFTER PARSE", weather.getCity().getName());

                    picURL= new URL(weather.getWeather().getIcon_url());
                    InputStream content = (InputStream)picURL.getContent();
                    d = Drawable.createFromStream(content , "src");


                    return 0l;
                } else {
                    return 1l;
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (JSONException e) {
                Log.d("TAG", "failparse");
                e.printStackTrace();
                return 1l;
            } finally {
                if (connection != null)
                    connection.disconnect();
            }

        }
        @Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {

                /*DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.clearTable();
                dbHelper.addRows(photos);
                dbHelper.close();
                showList();*/
               /* List<day> tendays=weather.getTenDay();
                ArrayAdapter<day> tendayArrayAdapter =
                        new weatherArrayAdapter(MainActivity.this,0,tendays);
                ListView listview = (ListView) findViewById(android.R.id.list);
                listview.setAdapter(tendayArrayAdapter);*/
                city.setText(weather.getCity().getName());
                Temp.setText(Double.toString(weather.getTemp().getcTemp()));
                condition.setText(weather.getWeather().getDescrip());
                conDescrip.setText(weather.getWeather().getDescrip());

                    WIcon.setBackground(d);

                city.setVisibility(View.VISIBLE);
                WIcon.setVisibility(View.VISIBLE);
                Temp.setVisibility(View.VISIBLE);
                condition.setVisibility(View.VISIBLE);
                conDescrip.setVisibility(View.VISIBLE);

                deg.setVisibility(View.VISIBLE);
                deg1.setVisibility(View.VISIBLE);
                deg2.setVisibility(View.VISIBLE);
                maxLab.setVisibility(View.VISIBLE);
                minLab.setVisibility(View.VISIBLE);



            } else {
                Toast.makeText(getActivity(), "AsyncTask didn't complete", Toast.LENGTH_LONG).show();
            }
            progressLoading.setVisibility(View.GONE);
        }
    }

}
