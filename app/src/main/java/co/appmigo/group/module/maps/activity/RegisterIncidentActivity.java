package co.appmigo.group.module.maps.activity;

import androidx.appcompat.app.AppCompatActivity;
import co.appmigo.group.R;
import co.appmigo.group.module.maps.presenter.StepperAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


public class RegisterIncidentActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout stepperLayout;
    private StepperAdapter adapterSteper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_incident);
        stepperLayout = findViewById(R.id.stepperLayout);

        adapterSteper = new StepperAdapter(getSupportFragmentManager(), this);

        stepperLayout.setAdapter(adapterSteper);
        stepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
