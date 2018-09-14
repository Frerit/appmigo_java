package co.appmigo.group.module.maps.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import co.appmigo.group.R;
import co.appmigo.group.module.maps.activity.RegisterIncidentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentSlider1Fragment extends Fragment implements BlockingStep {

    RegisterIncidentActivity registerIncidentActivity;
    private boolean selectionAlert;
    private Button btnPrecaucion;
    private Button btnAdvertence;
    public IncidentSlider1Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.incident_slider1, container, false);

        initView(v);
        initListener();
        return v;
    }

    private void initView(View view) {
        btnPrecaucion = view.findViewById(R.id.incidentPrecaution);
        btnAdvertence = view.findViewById(R.id.incidentAdvertenca);
    }

    private void initListener() {

        btnPrecaucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("|||", "Clic Preca");
                selectionAlert = true;
            }
        });

        btnAdvertence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("|||", "Clic Adver");
            }
        });
    }

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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (selectionAlert) {
            Log.e("|||", "Lllega Aui");
            callback.goToNextStep();
        } else{
            Log.e("|||", "Lllega Aui else" );
        }

    }

    @Override
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.complete();
                Log.d("APP_LOG","call finish");
            }
        }, 2000L);

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
        Log.d("APP_LOG"," onBackClicked");

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
