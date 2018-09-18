package co.appmigo.group.module.maps.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import co.appmigo.group.R;
import co.appmigo.group.module.maps.model.OnProcesdListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentSlider3Fragment extends Fragment implements BlockingStep {

    private Boolean selectionAlert;
    private OnProcesdListener onProcesdListener;

    public IncidentSlider3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.incident_slider3, container, false);
        initViews(view);
        initListener();
        return view;
    }

    private void initViews(View v) {

    }

    private void initListener() {

    }

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

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

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
