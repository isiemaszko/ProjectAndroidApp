package pl.edu.pb.travelplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ImageView locationButton;
    private ImageView backButton;
    private GoogleMap mMap;

    public static final String EXTRA_EDIT_PLACE_MAP="pb.edu.pl.EXTRA_EDIT_PLACE_MAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationSearch=(EditText) findViewById(R.id.editText);
      if(getIntent().hasExtra(EXTRA_EDIT_PLACE_MAP)){
          locationSearch.setText((getIntent().getSerializableExtra(EXTRA_EDIT_PLACE_MAP).toString()));
      }

        locationButton=(ImageView) findViewById(R.id.search_button);
        locationButton.setImageResource(R.drawable.ic_search_black_24dp);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location=locationSearch.getText().toString();
                List<Address>addressList=null;


                if(location!=null || !location.equals("")){
                    Geocoder geocoder=new Geocoder(MapActivity.this);
                    try{
                        addressList=geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address=addressList.get(0);

                    LatLng latLng =new LatLng(address.getLatitude(), address.getLongitude());

                    mMap.addMarker(new MarkerOptions().position(latLng).title(address.getFeatureName()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });
        backButton=(ImageView) findViewById(R.id.back_button);
        backButton.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent=new Intent();
                if(TextUtils.isEmpty(locationSearch.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    String place=locationSearch.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_PLACE_MAP, place);
                    setResult(RESULT_OK, replyIntent);
                }
             finish();
            }

        });

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
        String location=locationSearch.getText().toString();
        List<Address>addressList=null;
        mMap = googleMap;
        if(TextUtils.isEmpty((locationSearch.getText()))){
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        else
        {
            if(location!=null || !location.equals("")){
                Geocoder geocoder=new Geocoder(MapActivity.this);
                try{
                    addressList=geocoder.getFromLocationName(location,1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address=addressList.get(0);

                LatLng latLng =new LatLng(address.getLatitude(), address.getLongitude());

                mMap.addMarker(new MarkerOptions().position(latLng).title(address.getFeatureName()));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }

        // Add a marker in Sydney and move the camera

    }
}
