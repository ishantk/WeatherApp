package co.edureka.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    @InjectView(R.id.textView)
    TextView txtData;

    @InjectView(R.id.button)
    Button btnFetch;

    @InjectView(R.id.imageView)
    ImageView imageView;


    LocationManager locationManager;

    double latitude  = 28.5275198;
    double longitude = 77.068899;

    String url;

    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        btnFetch.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Initialize the request queue
        requestQueue = Volley.newRequestQueue(this);

    }

    void fetchWeather(){

        //url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=ebfcac32bda131ed5a160f2757938396";

        url = "https://api.themoviedb.org/3/movie/now_playing?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&page=undefined";

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Parse your JSON Response here...
                txtData.setText(response);
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtData.setText(error.toString());
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    void showImage(){
        // Fetch the image from a url into an ImageView using Picasso
        Picasso.with(this).load("https://image.tmdb.org/t/p/w780/xfWac8MTYDxujaxgPVcRD9yZaul.jpg").into(imageView);
    }

    @Override
    public void onLocationChanged(Location location) {

        // Geographical Cordinates...
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=ebfcac32bda131ed5a160f2757938396";

        // Once Location is Fetched, remove the updates.
        locationManager.removeUpdates(this);

        // Fetch the weather info for the same location
        // Request a WebService i.e. openweather api
        // volley
        fetchWeather();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {

            // Code here to fetch location...
            /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Please grant permissions to access location in settings",Toast.LENGTH_LONG).show();
            }else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 5, this);
            }*/

            //fetchWeather();
            showImage();

        }
    }
}
