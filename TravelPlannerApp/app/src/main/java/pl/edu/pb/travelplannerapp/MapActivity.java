package pl.edu.pb.travelplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private EditText locationSearch;
    private Button locationButton;
    private GoogleMap mMap;
    public static final String EXTRA_EDIT_PLACE="pb.edu.pl.EXTRA_EDIT_PLACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationButton=(Button) findViewById(R.id.search_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Button search");
                EditText locationSearch = (EditText) findViewById(R.id.editText);
                String location=locationSearch.getText().toString();
                List<Address>addressList=null;
                Log.d("MainActivity", "location"+locationSearch);

                if(location!=null || !location.equals("")){
                    Geocoder geocoder=new Geocoder(MapActivity.this);
                    try{
                        addressList=geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address=addressList.get(0);
                    Log.d("MainActivity", "adress"+address);
                    LatLng latLng =new LatLng(address.getLatitude(), address.getLatitude());

                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });


    }
    public void onMapSearch(View view){
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location=locationSearch.getText().toString();
        List<Address>addressList=null;

        if(location!=null || !location.equals("")){
            Geocoder geocoder=new Geocoder(this);
            try{
                addressList=geocoder.getFromLocationName(location,1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address=addressList.get(0);
            LatLng latLng =new LatLng(address.getLatitude(), address.getLatitude());

            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getFeatureName()));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap=googleMap;
//        LatLng sydney=new LatLng(27.746974, 85.301582);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Kathmandu, Nepal"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
