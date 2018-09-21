package co.appmigo.group.module.maps.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import co.appmigo.group.LoginActivity;
import co.appmigo.group.R;
import co.appmigo.group.common.Constants;
import co.appmigo.group.common.User;
import co.appmigo.group.common.Util;
import co.appmigo.group.common.Warning;
import co.appmigo.group.module.MainActivity;
import co.appmigo.group.module.maps.model.OnProcesdListener;
import co.appmigo.group.module.maps.presenter.StepperAdapter;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


import org.imperiumlabs.geofirestore.GeoFirestore;

import java.util.Date;

import static co.appmigo.group.common.Constants.TAG_;


public class RegisterIncidentActivity extends AppCompatActivity implements StepperLayout.StepperListener, OnProcesdListener {

    private StepperLayout stepperLayout;
    private StepperAdapter adapterSteper;

    private Warning registerWarnig;
    protected Fragment lastFragmentOpen;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference georef = FirebaseFirestore.getInstance().collection("incident");
    GeoFirestore geoFirestore = new GeoFirestore(georef);


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
        registerWarnig.setUsertoregister(FirebaseAuth.getInstance().getCurrentUser().getUid());
        registerWarnig.setLevel(Constants.LEVEL_LEVE);
        registerWarnig.setName("first-incident");
        registerWarnig.setDateIncident(new Date());

        db.collection("incident")
                .add(registerWarnig)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        geoFirestore.setLocation(documentReference.getId(), new GeoPoint(registerWarnig.getLatitude(),registerWarnig.getLongitude()));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG_,"Error", e);
                    }
                });
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
