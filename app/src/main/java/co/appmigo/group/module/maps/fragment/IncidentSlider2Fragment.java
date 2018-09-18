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
import android.widget.ImageView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import co.appmigo.group.R;
import co.appmigo.group.module.maps.model.OnProcesdListener;

import static co.appmigo.group.common.Constants.TAG_;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentSlider2Fragment extends Fragment implements BlockingStep {

    private SparkButton tFire;
    private SparkButton tInundaiton;
    private SparkButton tHuracan;
    private SparkButton tTifon;
    private SparkButton tErupcion;
    private SparkButton tTerremoto;
    private Boolean selectionAlert;
    private OnProcesdListener onProcesdListener;

    public IncidentSlider2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incident_slider2, container, false);
        initViews(view);
        initListener();
        return view;
    }

    private void initViews(View v) {
        tFire = v.findViewById(R.id.type_fire);
        tInundaiton = v.findViewById(R.id.type_inundation);
        tHuracan = v.findViewById(R.id.type_huracan);
        tTifon = v.findViewById(R.id.type_tifon);
        tErupcion = v.findViewById(R.id.type_erupcion);
        tTerremoto = v.findViewById(R.id.type_terremoto);
    }

    private void initListener() {
        tFire.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                selectionAlert= true;
                onProcesdListener.onProceed();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                deseltec(tFire);
            }
        });
        tInundaiton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                selectionAlert= true;
                onProcesdListener.onProceed();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                deseltec(tInundaiton);
            }
        });
        tHuracan.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                selectionAlert= true;
                onProcesdListener.onProceed();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                deseltec(tHuracan);
            }
        });
        tTifon.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                selectionAlert= true;
                onProcesdListener.onProceed();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                deseltec(tTifon);
            }
        });
        tErupcion.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                onProcesdListener.onProceed();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                deseltec(tErupcion);
            }
        });
        tTerremoto.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                onProcesdListener.onProceed();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                deseltec(tTerremoto);
            }
        });
    }

    private void deseltec(SparkButton button) {
        tFire.setChecked(button == tFire);
        tErupcion.setChecked(button == tErupcion);
        tInundaiton.setChecked(button == tInundaiton);
        tHuracan.setChecked(button == tHuracan);
        tTifon.setChecked(button == tTifon);
        tTerremoto.setChecked(button == tTerremoto);
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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (selectionAlert) {
            Log.e(TAG_, "Lllega Aui");
            callback.goToNextStep();
        } else{
            Log.e(TAG_, "No ha habilitado Opcion" );
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
        if (context instanceof OnProcesdListener) {
            onProcesdListener = (OnProcesdListener) context;
        } else {
            throw new IllegalStateException("Ilegal Excepio");
        }
    }
}
