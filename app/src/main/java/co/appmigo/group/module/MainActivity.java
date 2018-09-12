package co.appmigo.group.module;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapFragment;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import co.appmigo.group.R;
import co.appmigo.group.module.explore.fragment.ExploreAlertFragment;
import co.appmigo.group.module.maps.fragment.MapsFragment;
import co.appmigo.group.module.notification.fragment.NotificationFragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MapFragment.OnMapViewReadyCallback, PermissionsListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private FrameLayout contentLayout;
    private SupportMapFragment mapFragment;
    private FragmentTransaction transaction;
    private LocationLayerPlugin locationLayerPlugin;
    private PermissionsManager permissionsManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    createHomeFragment(null);
                    return true;
                case R.id.navigation_dashboard:
                    createExplorerFragment();
                    return true;
                case R.id.navigation_notifications:
                    createnotificationFragment();
                    return true;
                case R.id.navigation_settig:
                    createSettingFragment();
                    return true;
            }
            return false;
        }
    };

    private void createnotificationFragment() {
        mapView.setVisibility(View.GONE);
        NotificationFragment notifrag = new NotificationFragment();
        FragmentManager chageExplore = getSupportFragmentManager();
        chageExplore.beginTransaction().replace(R.id.contentLayout, notifrag).commit();
    }

    private void createHomeFragment(Bundle savedInstanceState) {
        MapsFragment mapsFragment = new MapsFragment();

        FragmentManager changeMap = getSupportFragmentManager();
        changeMap.beginTransaction().replace(R.id.contentLayout, mapsFragment).commit();
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            LatLng patagonia = new LatLng(6.24962, -75.5695);

            MapboxMapOptions options = new MapboxMapOptions();
            options.styleUrl(Style.LIGHT);
            options.camera(new CameraPosition.Builder()
                    .target(patagonia)
                    .zoom(20)
                    .build());

            mapFragment = SupportMapFragment.newInstance(options);
            transaction.add(R.id.frame_mapbox, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MainActivity.this.mapboxMap = mapboxMap;
                enableLocationPlugin();
            }
        });
    }

    private void createSettingFragment() {

    }

    private void createExplorerFragment() {
        mapView.setVisibility(View.GONE);
        ExploreAlertFragment exploreFragment = new ExploreAlertFragment();
        FragmentManager changeExplorer = getSupportFragmentManager();
        changeExplorer.beginTransaction().replace(R.id.contentLayout, exploreFragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Mapbox.getInstance(this, getString(R.string.acess_token) );
        mapView = findViewById(R.id.mapMain);
        createHomeFragment(savedInstanceState);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onMapViewReady(MapView mapView) {
        this.mapView = mapView;
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            locationLayerPlugin = new LocationLayerPlugin(mapView, mapboxMap);
            locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
            locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
            getLifecycle().addObserver(locationLayerPlugin);
        } else {

            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
        getLifecycle().addObserver(locationLayerPlugin);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "Necesitamos unos permisos", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationPlugin();
        } else {
            Toast.makeText(this, "Autorizado" , Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            getLifecycle().removeObserver(locationLayerPlugin);
        }
    }
}
