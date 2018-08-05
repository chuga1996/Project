package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.model.Categories;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 99;
    private ImageView imgLocation;
    private ImageView imgZoomIn;
    private ImageView imgZoomOut;
    List<String> stringList = new ArrayList<>();;
    List<Address> listAddress;

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
        CameraPosition position = new CameraPosition(hanoi, 12, 0, 0);

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
        getCategories();



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
    ArrayList<Categories> listCategory;
    ProgressDialog dialog;

    public void getCategories() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        listCategory = new ArrayList<>();
        Call<JsonElement> callGetCategories = RestClient2.getApiInterface().getCategories(40);
        callGetCategories.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonArray listCategories = jsonElement.getAsJsonArray();
                for (int i = 0; i < listCategories.size(); i++) {
                    JsonObject cateogory = listCategories.get(i).getAsJsonObject();

                    int id = cateogory.get("id").getAsInt();
                    if (id != 1) {
                        String name = cateogory.get("name").getAsString();
                        listCategory.add(new Categories(id, name));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Không tải được");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                t.printStackTrace();
            }
        });
        getLocation();
    }

    public void getLocation(){
        Call<JsonElement> callGetAllRoom = RestClient2.getApiInterface().getPosts();
        callGetAllRoom.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dialog.dismiss();
                JsonArray listPost = response.body().getAsJsonArray();
                for (int i = 0; i < listPost.size(); i++){
                    JsonObject post = listPost.get(i).getAsJsonObject();
                    String address = post.get("title").getAsJsonObject().get("rendered").getAsString();
                    String district = "";
                    int idDistrict = post.get("categories").getAsJsonArray().get(0).getAsInt();
                    for (int j = 0; j < listCategory.size(); j++) {
                        if (idDistrict == listCategory.get(j).getId()) {
                            district = listCategory.get(j).getName();

                        }
                    }

                    stringList.add(address+", "+district);
                }
                for (int i = 0; i<stringList.size();i++){
                    Geocoder coder = new Geocoder(getContext());
                    try {
                        listAddress = coder.getFromLocationName(stringList.get(i),1);
                        if(listAddress.size()>0){
                            double latRoom =listAddress.get(0).getLatitude();
                            double longRoom = listAddress.get(0).getLongitude();
                            LatLng room = new LatLng(latRoom, longRoom);
                            mMap.addMarker(new MarkerOptions().position(room).title(stringList.get(i)));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
