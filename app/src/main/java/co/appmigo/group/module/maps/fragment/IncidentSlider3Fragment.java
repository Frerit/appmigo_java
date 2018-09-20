package co.appmigo.group.module.maps.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.varunest.sparkbutton.SparkButton;


import co.appmigo.group.R;
import co.appmigo.group.common.Localization;
import co.appmigo.group.module.maps.model.OnProcesdListener;

import static android.content.Context.*;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.getSystemService;
import static co.appmigo.group.common.Constants.TAG_;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentSlider3Fragment extends Fragment implements BlockingStep {

    private Boolean selectionAlert;
    private OnProcesdListener onProcesdListener;
    private MapView mapLocalization;
    private LocationManager mLocationManager;
    private Location currenLocation;

    GoogleMap map;

    private LinearLayout selectPosition;
    private SparkButton iconselecPosition;

    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    public IncidentSlider3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.incident_slider3, container, false);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getCurrentLocation();
        initViews(view, savedInstanceState);
        initListener();
        return view;
    }

    private void initViews(View v, Bundle savedInstanceState) {
        mapLocalization = v.findViewById(R.id.mapLocale);
        selectPosition = v.findViewById(R.id.select_position);
        iconselecPosition = v.findViewById(R.id.locale_icon);
        mapLocalization.onCreate(savedInstanceState);
        mapLocalization.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        configMap();
    }

    private void initListener() {
        selectPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconselecPosition.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selectionAlert = true;
                        onProcesdListener.onProceed();
                        onProcesdListener.getRegisterWarnig().setLocalization(new Localization("LocaleIncident", currenLocation));
                    }
                }, 400L);
            }
        });
    }

    private void configMap() {

        mapLocalization.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setMyLocationEnabled(false);
                LatLng locale = new LatLng(currenLocation.getLatitude(), currenLocation.getLongitude());
                map.addMarker(new MarkerOptions().position(locale));


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(locale)
                        .zoom(16)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled))
            Snackbar.make(mapLocalization, "ERROR", Snackbar.LENGTH_INDEFINITE).show();
        else {
            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            Log.e(TAG_,String.format("getCurrentLocation(%f, %f)", location.getLatitude(),
                    location.getLongitude()));
            setLocation(location);
        }
    }

    public void setLocation(Location location) {
        currenLocation = location;
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.e(TAG_,String.format("%f, %f", location.getLatitude(), location.getLongitude()));
                mLocationManager.removeUpdates(mLocationListener);
            } else {
                Log.e(TAG_,"Location is null");
            }
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
    };

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProcesdListener) {
            onProcesdListener = (OnProcesdListener) context;
        } else {
            throw new IllegalStateException("Ilegal Excepio");
        }
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

}
