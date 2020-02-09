package com.example.daliliadmin;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * HJR Es-saidi
 *
 * **/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    CoordsBackgroundTask traitement;
    private static Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        traitement = new CoordsBackgroundTask(this);
        traitement.execute();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //Log.i("coor", String.valueOf(traitement.lat_zones));
/*        for(int i=0;i<CoordsBackgroundTask.lat_zones.length;i++)
        {
            for(int j=0;j<CoordsBackgroundTask.long_zones.length;j++)
            {
                Log.i("coor",CoordsBackgroundTask.lat_zones[i]+" and "+CoordsBackgroundTask.long_zones[i]);
                LatLng zone = new LatLng(CoordsBackgroundTask.lat_zones[i], CoordsBackgroundTask.long_zones[i]);
                mMap.addMarker(new MarkerOptions().position(zone).title("Marker in Sydney"));
            }
        }*/
    }

    /////fct to draw cercle used in Coords class
    public static Circle drawingCercle(double lat, double lon,String nom){
        // Add a marker in latLng precised and move the camera
        LatLng place = new LatLng(lat,lon);   /////lag and lat
        mMap.addMarker(new MarkerOptions().position(place).title(nom));
        circle = mMap.addCircle(new CircleOptions()              //circle created arround home variable
                .center(place)
                .radius(20)      // 20 meters for the zone perimeter
                .fillColor(Color.TRANSPARENT)
                .strokeColor(Color.rgb(34,10,44)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14));
        return circle;

    }
}
