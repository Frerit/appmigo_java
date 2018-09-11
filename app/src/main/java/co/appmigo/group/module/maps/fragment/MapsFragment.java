package co.appmigo.group.module.maps.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import co.appmigo.group.R;
import co.appmigo.group.module.MainActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MapsFragment extends Fragment  {

    private MainActivity mainActivity;
    public MapsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        initViews();
        initListener();

        return view;
    }

    private void initViews() {

    }

    private void initListener() {

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

}
