package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 99;
    private ImageView imgLocation;
    private ImageView imgZoomIn;
    private ImageView imgZoomOut;
    List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(MapsFragment.this);
        imgLocation = (ImageView) view.findViewById(R.id.img_location);
        imgZoomIn = (ImageView) view.findViewById(R.id.img_zoom_in);
        imgZoomOut = (ImageView) view.findViewById(R.id.img_zoom_out);

        stringList = new ArrayList<>();
        stringList.add("Ba Đình");
        stringList.add("Hoàn Kiếm");
        stringList.add("Cầu giấy");
        stringList.add("Đống Đa");

        return view;
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
        LatLng hanoi = new LatLng(21.0358, 105.8336);
        mMap = googleMap;
        CameraPosition position = new CameraPosition(hanoi, 10, 0, 0);

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(hanoi).title("Hà Nội"));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyLocation();
            }
        });

        imgZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        imgZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        LatLng vitri1 = new LatLng(21.0388, 105.8436);
        mMap.addMarker(new MarkerOptions().position(vitri1).title(stringList.get(0)));

        LatLng vitri2 = new LatLng(21.0488, 105.8436);
        mMap.addMarker(new MarkerOptions().position(vitri2).title(stringList.get(1)));

        LatLng vitri3 = new LatLng(21.0788, 105.8436);
        mMap.addMarker(new MarkerOptions().position(vitri3).title(stringList.get(2)));

        LatLng vitri4 = new LatLng(21.0578, 105.8436);
        mMap.addMarker(new MarkerOptions().position(vitri4).title(stringList.get(3)));

    }

    public void showMyLocation() {
        //cap quyen truy cap vi tri
        if (Build.VERSION.SDK_INT >= 20) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED){
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng vitri = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraPosition position = new CameraPosition(vitri, 14, 0, 0);

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                        mMap.addMarker(new MarkerOptions().position(vitri).title("Vị trí hiện tại"));
                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Không được cấp quyền!", Toast.LENGTH_SHORT).show();
        }
    }
}
