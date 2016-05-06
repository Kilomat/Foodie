package foodie.project_training.com.foodie.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by beau- on 04/05/2016.
 */
public class MyLocation implements LocationListener {

    private Context context;

    private LocationManager lm;
    private Location location;

    public String bestProvider;


    private double longitude;
    private double latitude;

    public MyLocation(Context context) {
        this.context = context;

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //DO OP WITH LOCATION SERVICE

            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            try {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    Log.e("TAG", "GPS is on");
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {
                    //This is what you need:
                    lm.requestLocationUpdates(bestProvider, 1000, 0, this);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCurrentCity() {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        StringBuilder builder = new StringBuilder();

        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
             String addressStr = addresses.get(0).getAddressLine(0);
            builder.append(addressStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            lm.removeUpdates(this);

        latitude = location.getLatitude();
        longitude = location.getLongitude();
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
}
