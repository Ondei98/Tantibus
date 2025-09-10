package com.project.tantibus.levels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.tantibus.R;

import java.text.DecimalFormat;

public class Level5_MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container,false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        //When clicked
                        //Initialize marker option
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        //create variables to store coordinates
                        float selected_latitude = (float) latLng.latitude;
                        float selected_longitude = (float) latLng.longitude;

                        //send coordinates to level5_activity
                        Level5_Activity get_Data = (Level5_Activity) getActivity();
                        assert get_Data != null;
                        get_Data.get_coordinates(selected_latitude, selected_longitude);

                        //set title of marker with 3 decimal places
                        DecimalFormat df = new DecimalFormat("#.000");
                        markerOptions.title(Float.parseFloat(df.format(latLng.latitude)) + " : " + Float.parseFloat(df.format(latLng.longitude)));
                        //markerOptions.title(latLng.latitude + " : " +latLng.longitude);

                        //Remove all marker
                        googleMap.clear();
                        //zoom
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        //Add marker on map
                        googleMap.addMarker(markerOptions);

                    }
                });
            }
        });

        //Return view
        return view;

    }

}