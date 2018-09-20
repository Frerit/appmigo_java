package co.appmigo.group.module.maps.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import co.appmigo.group.R;
import co.appmigo.group.common.Warning;
import co.appmigo.group.module.maps.model.OnProcesdListener;
import co.appmigo.group.module.maps.presenter.StepperAdapter;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import static co.appmigo.group.common.Constants.TAG_;


public class RegisterIncidentActivity extends AppCompatActivity implements StepperLayout.StepperListener, OnProcesdListener {

    private StepperLayout stepperLayout;
    private StepperAdapter adapterSteper;

    private Warning registerWarnig;
    protected Fragment lastFragmentOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_incident);
        stepperLayout = findViewById(R.id.stepperLayout);

        adapterSteper = new StepperAdapter(getSupportFragmentManager(), this);
        registerWarnig = new Warning();

        stepperLayout.setAdapter(adapterSteper,0);
        stepperLayout.setShowErrorStateEnabled(true);
        stepperLayout.isTabNavigationEnabled();
        stepperLayout.setTabNavigationEnabled(true);
        stepperLayout.setListener(this);

    }

    @Override
    public void onCompleted(View completeButton) {

        

        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onProceed() {
        stepperLayout.proceed();
    }

    @Override
    public Warning getRegisterWarnig() {
        return registerWarnig;
    }

    public void setRegisterWarnig(Warning registerWarnig) {
        this.registerWarnig = registerWarnig;
    }


    @Override
    public void onBackPressed() {
        if (stepperLayout.getCurrentStepPosition() == 0) {
            super.onBackPressed();
        } else {
            stepperLayout.onBackClicked();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG_,"Entro al activiti se puede guardar directo");
    }
}
