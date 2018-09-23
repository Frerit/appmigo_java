package co.appmigo.group.module;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import co.appmigo.group.R;
import co.appmigo.group.module.explore.fragment.ExploreAlertFragment;
import co.appmigo.group.module.maps.fragment.MapsFragment;
import co.appmigo.group.module.notification.fragment.NotificationFragment;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    createHomeFragment();
                    return true;
                case R.id.navigation_dashboard:
                    createExplorerFragment();
                    return true;
                case R.id.navigation_public:
                    createDasboardFragment();
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

    private void createDasboardFragment() {

    }

    private void createnotificationFragment() {
        NotificationFragment notifrag = new NotificationFragment();
        FragmentManager chageExplore = getSupportFragmentManager();
        chageExplore.beginTransaction().replace(R.id.contentLayout, notifrag).commit();
    }

    private void createHomeFragment() {
        MapsFragment mapsFragment = new MapsFragment();
        FragmentManager changeMaps = getSupportFragmentManager();
        changeMaps.beginTransaction().replace(R.id.contentLayout, mapsFragment).commit();
    }

    private void createSettingFragment() {

    }

    private void createExplorerFragment() {
        ExploreAlertFragment exploreFragment = new ExploreAlertFragment();
        FragmentManager changeExplorer = getSupportFragmentManager();
        changeExplorer.beginTransaction().replace(R.id.contentLayout, exploreFragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createHomeFragment();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
