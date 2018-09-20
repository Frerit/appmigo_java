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
import android.widget.ImageView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import co.appmigo.group.R;
import co.appmigo.group.common.Constants;
import co.appmigo.group.module.maps.activity.RegisterIncidentActivity;
import co.appmigo.group.module.maps.model.OnProcesdListener;

import static co.appmigo.group.common.Constants.TAG_;
import static co.appmigo.group.common.Constants.TYPE_PRECAUTION;
import static co.appmigo.group.common.Constants.TYPE_PREVENTION;


public class IncidentSlider1Fragment extends Fragment implements BlockingStep {

    RegisterIncidentActivity registerIncidentActivity;
    private boolean selectionAlert;
    private SparkButton btnPrecaucion;
    private SparkButton btnAdvertence;

    public IncidentSlider1Fragment() {
    }

    private OnProcesdListener onProcesdListener;

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


        btnPrecaucion.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            selectionAlert = true;
                            onProcesdListener.onProceed();
                            onProcesdListener.getRegisterWarnig().setType(TYPE_PRECAUTION);
                        }
                    }, 1000L);
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                btnAdvertence.setChecked(false);
            }
        });

        btnAdvertence.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            selectionAlert = true;
                            onProcesdListener.onProceed();
                            onProcesdListener.getRegisterWarnig().setType(TYPE_PREVENTION);
                        }
                    }, 1000L);
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                btnPrecaucion.setChecked(false);
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
                Log.d(TAG_,"call finish");
            }
        }, 2000L);
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
        Log.d(TAG_," onBackClicked");

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
