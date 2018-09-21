package co.appmigo.group.module.maps.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import co.appmigo.group.R;
import co.appmigo.group.common.Constants;
import co.appmigo.group.common.Localization;
import co.appmigo.group.common.Warning;
import co.appmigo.group.module.MainActivity;
import co.appmigo.group.module.maps.activity.RegisterIncidentActivity;
import timber.log.Timber;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.geometry.LatLngQuad;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.HeatmapLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.ImageSource;
import com.mapbox.mapboxsdk.style.sources.VectorSource;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryDataEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static co.appmigo.group.common.Constants.TAG_;
import static co.appmigo.group.common.Constants.TYPE_PRECAUTION;
import static co.appmigo.group.common.Constants.TYPE_PREVENTION;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.heatmapDensity;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleStrokeColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleStrokeWidth;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapIntensity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapWeight;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private MainActivity mainActivity;

    // Mapa Google
    MapView mapView;
    private GoogleMap mMap;
    private Circle circle;

    // Mapa Mapbox
    private com.mapbox.mapboxsdk.maps.MapView mapBobView;
    private com.mapbox.mapboxsdk.maps.MapView mapBox;
    private Handler handler;

    //    MAP DERECHO
    public static final String ID_SOURCE = "moji-source";
    public static final String ID_LAYER = "moji-layer";
    public static final String SOURCE_URL = "mapbox://shenhongissky.6vm8ssjm";
    private FillLayer layer;
    private int index = 1;
    private RefreshGeoJsonRunnable refreshGeoJsonRunnable;

    //   MAP IZQ
    private static final String EARTHQUAKE_SOURCE_URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
    private static final String EARTHQUAKE_SOURCE_ID = "earthquakes";
    private static final String HEATMAP_LAYER_ID = "earthquakes-heat";
    private static final String HEATMAP_LAYER_SOURCE = "earthquakes";
    private static final String CIRCLE_LAYER_ID = "earthquakes-circle";



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
    private Button btnIncident;
    private ImageView closemodalMap;
    private CardView mapContentFragment;


    CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("incident");
    GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);

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
        animateModal();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        Mapbox.getInstance(getActivity(), "pk.eyJ1IjoiYXBwbWlnbyIsImEiOiJjamxxeHAwbWswMW1xM3h0Nm11NGZ0aTd1In0.TOrPfpVQ66MKxP1SfQ37eg");

        initViews(view);
        initListener(view, savedInstanceState);
        return view;
    }

    private void initViews(View view) {
        radiogroup = view.findViewById(R.id.mapsradioGroup);
        rad1 = view.findViewById(R.id.radioButton1);
        rad2 = view.findViewById(R.id.radioButton2);
        rad3 = view.findViewById(R.id.radioButton3);
        btnIncident = view.findViewById(R.id.btnpublicateIncident);
        mapContentFragment = view.findViewById(R.id.mapContentFragment);
        closemodalMap = view.findViewById(R.id.closemodalMap);
        handler = new Handler();
        colorStroke = Color.argb( 80,210,180,222 );
        colorFill = Color.argb( 100,232,218,239 );
    }

    public void animateElement(Object anima, float posiiton) {
       @SuppressLint("ObjectAnimatorBinding")
       ObjectAnimator animator = ObjectAnimator.ofFloat(anima,"translationY", posiiton);
       animator.setDuration(35);
       animator.start();
    }

    public void initListener(final View view, final Bundle bunde) {
        mapContentFragment.setVisibility(View.GONE);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (rad1.getId() == i) {
                    animateElement(rad1,-20);
                    animateElement(rad2,20);
                    animateElement(rad3,20);
                    openMapHealt(view, bunde);
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
                    openMapTimeLapse(view, bunde);
                }
            }
        });

        btnIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getActivity(), RegisterIncidentActivity.class);
              startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeModal();
                    }
                }, 200L);
            }
        });

        closemodalMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeModal();
            }
        });
    }

    private void openMapTimeLapse(View view, Bundle savedInstanceState) {
         mapBobView = view.findViewById(R.id.mapsBox);
         mapBobView.setVisibility(View.VISIBLE);
         mapBobView.onCreate(savedInstanceState);
         mapBobView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
             @Override
             public void onMapReady(MapboxMap mapboxMap) {
                 /// cuadro para el clima

                 addRadar(mapboxMap);
                 refreshGeoJsonRunnable = new RefreshGeoJsonRunnable();
                 do {
                     handler.postDelayed(refreshGeoJsonRunnable, 1000);
                 }
                 while (index == 39);

                 // Qingzhen  Guiyang, Guizhou, China
                 CameraPosition position = new CameraPosition.Builder()
                         .target(new com.mapbox.mapboxsdk.geometry.LatLng(28.779297, 106.922151))
                         .zoom(6)
                         .tilt(20)
                         .build();
                 mapboxMap.moveCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position));

             }
         });
    }

    private void addRadar(MapboxMap mapboxMap) {
        VectorSource vectorSource = new VectorSource(
                ID_SOURCE,
                SOURCE_URL
        );
        mapboxMap.addSource(vectorSource);
        layer = mapboxMap.getLayerAs(ID_LAYER);
        if (layer == null) {
            layer = new FillLayer(ID_LAYER, ID_SOURCE);
            layer.withSourceLayer("whole");
            layer.setFilter(eq((get("idx")), literal(0)));
            layer.setProperties(PropertyFactory.visibility(VISIBLE),
                    fillColor(interpolate(Expression.exponential(1f),
                            get("value"),
                            stop(8, rgb(20, 160, 240)),
                            stop(18, rgb(20, 190, 240)),
                            stop(36, rgb(20, 220, 240)),
                            stop(54, rgb(20, 250, 240)),
                            stop(72, rgb(20, 250, 160)),
                            stop(90, rgb(135, 250, 80)),
                            stop(108, rgb(250, 250, 0)),
                            stop(126, rgb(250, 180, 0)),
                            stop(144, rgb(250, 110, 0)),
                            stop(162, rgb(250, 40, 0)),
                            stop(180, rgb(180, 40, 40)),
                            stop(198, rgb(110, 40, 80)),
                            stop(216, rgb(80, 40, 110)),
                            stop(234, rgb(50, 40, 140)),
                            stop(252, rgb(20, 40, 170))
                            )
                    ),
                    PropertyFactory.fillOpacity(0.7f));
            mapboxMap.addLayer(layer);
        }
    }

    private void openMapHealt(View view, Bundle savedInstanceState) {
        mapBobView = view.findViewById(R.id.mapsBox);
        mapBobView.setVisibility(View.VISIBLE);
        mapBobView.onCreate(savedInstanceState);
        mapBobView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                /// cuadro para el clima
                addEarthquakeSource(mapboxMap);
                addHeatmapLayer(mapboxMap);
                addCircleLayer(mapboxMap);

                // Qingzhen  Guiyang, Guizhou, China
                CameraPosition position = new CameraPosition.Builder()
                        .target(new com.mapbox.mapboxsdk.geometry.LatLng(28.779297, 106.922151))
                        .zoom(6)
                        .tilt(20)
                        .build();
                mapboxMap.moveCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position));

            }
        });
    }

    private void addHeatmapLayer(MapboxMap mapboxMap) {
        HeatmapLayer layer = new HeatmapLayer(HEATMAP_LAYER_ID, EARTHQUAKE_SOURCE_ID);
        layer.setMaxZoom(9);
        layer.setSourceLayer(HEATMAP_LAYER_SOURCE);
        layer.setProperties(
                heatmapColor(
                        interpolate(
                                linear(), heatmapDensity(),
                                literal(0), rgba(33, 102, 172, 0),
                                literal(0.2), rgb(103, 169, 207),
                                literal(0.4), rgb(209, 229, 240),
                                literal(0.6), rgb(253, 219, 199),
                                literal(0.8), rgb(239, 138, 98),
                                literal(1), rgb(178, 24, 43)
                        )
                ),
                heatmapWeight(
                        interpolate(
                                linear(), get("mag"),
                                stop(0, 0),
                                stop(6, 1)
                        )
                ),
                heatmapIntensity(
                        interpolate(
                                linear(), zoom(),
                                stop(0, 1),
                                stop(9, 3)
                        )
                ),
                heatmapRadius(
                        interpolate(
                                linear(), zoom(),
                                stop(0, 2),
                                stop(9, 20)
                        )
                ),
                heatmapOpacity(
                        interpolate(
                                linear(), zoom(),
                                stop(7, 1),
                                stop(9, 0)
                        )
                )
        );
        mapboxMap.addLayerAbove(layer, "waterway-label");
    }


    private void addCircleLayer(MapboxMap mapboxMap) {
        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, EARTHQUAKE_SOURCE_ID);
        circleLayer.setProperties(

        // Size circle radius by earthquake magnitude and zoom level
                circleRadius(
                        interpolate(
                                linear(), zoom(),
                                literal(7), interpolate(
                                        linear(), get("mag"),
                                        stop(1, 1),
                                        stop(6, 4)
                                ),
                                literal(16), interpolate(
                                        linear(), get("mag"),
                                        stop(1, 5),
                                        stop(6, 50)
                                )
                        )
                ),

                // Color circle by earthquake magnitude
                circleColor(
                        interpolate(
                                linear(), get("mag"),
                                literal(1), rgba(33, 102, 172, 0),
                                literal(2), rgb(103, 169, 207),
                                literal(3), rgb(209, 229, 240),
                                literal(4), rgb(253, 219, 199),
                                literal(5), rgb(239, 138, 98),
                                literal(6), rgb(178, 24, 43)
                        )
                ),

            // Transition from heatmap to circle layer by zoom level
                circleOpacity(
                        interpolate(
                                linear(), zoom(),
                                stop(7, 0),
                                stop(8, 1)
                        )
                ),
                circleStrokeColor("white"),
                circleStrokeWidth(1.0f)
        );

        mapboxMap.addLayerBelow(circleLayer, HEATMAP_LAYER_ID);
    }


    private void addEarthquakeSource(MapboxMap mapboxMap) {
        try {
            mapboxMap.addSource(new GeoJsonSource(EARTHQUAKE_SOURCE_ID, new URL(EARTHQUAKE_SOURCE_URL)));
        } catch (MalformedURLException malformedUrlException) {
            Timber.e(malformedUrlException, "That's not an url... ");
        }
    }


    private class RefreshGeoJsonRunnable implements Runnable {
        @Override
        public void run() {
            layer.setFilter(eq((get("idx")), literal(index)));
            index++;
            if (index == 40) {
                index = 0;
            }
            handler.postDelayed(this, 1000);
        }
    }

    private void animateModal() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapContentFragment.setAlpha(0f);
                mapContentFragment.setVisibility(View.VISIBLE);

                mapContentFragment.animate()
                        .alpha(1f)
                        .setDuration(100L)
                        .setListener(null);

            }
        },2000L);
    }
    private void closeModal() {
        mapContentFragment.animate()
                .alpha(0f)
                .scaleY(1f)
                .setDuration(100L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mapContentFragment.setVisibility(View.GONE);
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

        locationManager.requestLocationUpdates(provider, 5000, 0,
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
        mMap.getUiSettings().setMapToolbarEnabled(false);

        showMyEntorno(position);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        setUpMap();


    }
    public Circle addCircle(Warning warning) {

        switch (warning.getType()) {
            case TYPE_PREVENTION : colorStroke = Color.argb( 87,255,68,68 );  break;
            case TYPE_PRECAUTION :  colorStroke = Color.argb( 63,116,133,227 );   break;
        }

        circle = mMap.addCircle( new CircleOptions()
                .center( new LatLng( warning.getLatitude(), warning.getLongitude() ) )
                .radius( 150 )
                .strokeWidth( 150 )
                .strokeColor( colorStroke )
                .fillColor( colorFill )
                .clickable( true ) );

        BitmapDescriptor locationMarker = getBitmapFromVector(getContext(), R.drawable.ic_advertencia  );
        mMap.addMarker( new MarkerOptions()
                .icon(locationMarker)
                .anchor(0.5f, 0.6f)
                .position( new LatLng(  warning.getLatitude(), warning.getLongitude()  ) ).title( warning.getCategory() ) );


        return circle;

    }

    public static BitmapDescriptor getBitmapFromVector(@NonNull Context context,
                                                       @DrawableRes int vectorResourceId) {

        Log.e(TAG_, String.valueOf(vectorResourceId));
        Drawable vectorDrawable = ResourcesCompat.getDrawable(
                context.getResources(), vectorResourceId, null);
        if (vectorDrawable == null) {
            Log.e(TAG_, "Requested vector resource was not found");
            return BitmapDescriptorFactory.defaultMarker();
        }
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    private void showMyEntorno(LatLng position) {
        GeoQuery appmigoShow = geoFirestore.queryAtLocation(new GeoPoint(position.latitude,position.longitude),0.6);
        appmigoShow.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
            @Override
            public void onDocumentEntered(DocumentSnapshot documentSnapshot, GeoPoint location) {
                Log.e(TAG_, String.format("Document %s entered the search area at [%f,%f]", documentSnapshot, location.getLatitude(), location.getLongitude()));
                DocumentReference dorRef = geoFirestoreRef.document(documentSnapshot.getId());

                if (dorRef != null) {
                    Warning warning = new Warning(documentSnapshot);
                   addCircle(warning);
                }
            }

            @Override
            public void onDocumentExited(DocumentSnapshot documentSnapshot) {
                Log.e(TAG_, String.format("Document %s is no longer in the search area", documentSnapshot));
            }

            @Override
            public void onDocumentMoved(DocumentSnapshot documentSnapshot, GeoPoint location) {
                Log.e(TAG_, String.format("Document %s moved within the search area to [%f,%f]", documentSnapshot, location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onDocumentChanged(DocumentSnapshot documentSnapshot, GeoPoint geoPoint) {
            }

            @Override
            public void onGeoQueryReady() {
                Log.e(TAG_, "All initial data has been loaded and events have been fired!");

            }

            @Override
            public void onGeoQueryError(Exception e) {
                Log.e(TAG_, "There was an error with this query: " + e.getLocalizedMessage());
            }
        });
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
