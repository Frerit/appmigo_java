package co.appmigo.group.module.maps.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import co.appmigo.group.R;
import co.appmigo.group.common.Localization;
import co.appmigo.group.module.MainActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private MainActivity mainActivity;
    MapView mapView;
    private GoogleMap mMap;
    private Circle circle;

    List<Localization> locations;
    LocationManager locationManager;
    Localization localization;
    boolean checkMap;

    Bitmap iconBit;
    private int drawerIcon;
    private int colorStroke;
    private int colorFill;

    private RadioButton rad1, rad2, rad3;
    private RadioGroup radiogroup;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


    public MapsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions((Activity) getContext(), new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 1);
        locationManager = (LocationManager) getContext().getSystemService( Context.LOCATION_SERVICE);
    }

    // Cuando la vista esta gardaca con los elementos soporta el MAPA
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        initViews(view);
        initListener(view);

        return view;
    }

    private void initViews(View view) {
        radiogroup = view.findViewById(R.id.mapsradioGroup);
        rad1 = view.findViewById(R.id.radioButton1);
        rad2 = view.findViewById(R.id.radioButton2);
        rad3 = view.findViewById(R.id.radioButton3);
    }

    public void animateElement(Object anima, float posiiton) {
       @SuppressLint("ObjectAnimatorBinding")
       ObjectAnimator animator = ObjectAnimator.ofFloat(anima,"translationY", posiiton);
       animator.setDuration(35);
       animator.start();
    }

    public void initListener(View view) {



        if (rad1.isChecked()) {

        }

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if (rad1.getId() == i) {
                    animateElement(rad1,-20);
                    animateElement(rad2,20);
                    animateElement(rad3,20);
                }

                if (rad2.getId() == i) {
                    animateElement(rad2,-20);
                    animateElement(rad1,20);
                    animateElement(rad3,20);
                }
                if (rad3.getId() == i) {
                    animateElement(rad3,-20);
                    animateElement(rad2,20);
                    animateElement(rad1,20);
                }


            }
        });
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private boolean isLocationEnabled() {
        boolean enableOrDis = locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER );
        boolean enOrdis =  locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER );
        Log.d( "Com ---- ", String.valueOf( enableOrDis ) );
        return  (enableOrDis || enOrdis);

    }
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }


    private void comenzarLocalizacion() {
        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getContext().getSystemService( context );

        Criteria crta = new Criteria();
        crta.setAccuracy( Criteria.ACCURACY_FINE );
        crta.setAltitudeRequired( false );
        crta.setBearingRequired( false );
        crta.setCostAllowed( true );
        crta.setPowerRequirement( Criteria.POWER_LOW );
        String provider = locationManager.getBestProvider( crta, true );

        if (ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation( provider );
        updateWithNewLocation(location);

        locationManager.requestLocationUpdates(provider, 15000, 0,
                locationListener);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }
        @Override
        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }
        @Override
        public void onProviderEnabled(String provider) { }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

    };
    private void updateWithNewLocation(Location location) {

        if (location != null) {
            localization = new Localization("Actual",location.getLatitude(),location.getLongitude());
            Log.e("LOCATION", "your Current Position is : " + localization.toNameString() );
        }
    }

    private void setUpMap() {
        checkLocation();

        if (ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            boolean success =  mMap.setMapStyle(  MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_appmigo) );
            if (!success)  Log.e("MapsActivityRaw", "Style parsing failed.");
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }

        mMap.setMyLocationEnabled( true );
        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
        comenzarLocalizacion();
        final LatLng position = new LatLng(localization.getLatitude(), localization.getLongitude());
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( position, 17) );
        mMap.getUiSettings().setCompassEnabled( true );

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        setUpMap();
        colorStroke = Color.argb( 80,210,180,222 );
        colorFill = Color.argb( 100,232,218,239 );

        circle = mMap.addCircle( new CircleOptions()
                .center( new LatLng( 6.2496173,-75.5695481 ) )
                .radius( 100 )
                .strokeWidth( 200 )
                .strokeColor( colorStroke )
                .fillColor( colorFill )
                .clickable( true ) );

        iconBit = getBitmapFromVectorDrawable( getContext(),drawerIcon );
        mMap.addMarker( new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap( iconBit ))
                .position( new LatLng(  6.2496173,-75.5695481 ) ).title( "Incident" ) );

//        Cargara los datos dependiendo de la posicion donde se encuentre


  //      Cargar las alertas sercanas en un rango mas grande

    }
    public Circle addCircle(String post) {

        switch (post) {
            case "Incident": colorStroke = Color.argb( 87,255,68,68 );  break;
            case "Alert":  colorStroke = Color.argb( 63,116,133,227 );   break;
        }
        return circle;

    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_add_location_24dp);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder( getContext() );
        dialog.setTitle( "Enable Location" )
                .setMessage( "Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app" )
                .setPositiveButton( "Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mi = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        startActivity( mi );
                    }
                } )
                .setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                } );
        dialog.show();
    }

}
