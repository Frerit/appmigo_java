package co.appmigo.group.module.maps.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import co.appmigo.group.R;
import co.appmigo.group.module.maps.model.OnProcesdListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentSlider4Fragment extends Fragment implements BlockingStep {

    private OnProcesdListener onProcesdListener;
    private Uri imageUri;

    private SparkButton captureImage;
    private int REQUEST_IMAGE_CAPTURE;

    private ImageView imageview;
    private ImageView incidentImage;
    private EditText incidentDescrip;


    public IncidentSlider4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.incident_slider4, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        captureImage = view.findViewById(R.id.incidentCaptureFoto);
        incidentImage = view.findViewById(R.id.incidentImage);
        incidentDescrip = view.findViewById(R.id.incidentDescription);
    }

    private void initListener() {
        captureImage.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    takePhoto();
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        onProcesdListener.getRegisterWarnig().setDesciption(incidentDescrip.getText().toString());
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProcesdListener) {
            onProcesdListener = (OnProcesdListener) context;
        } else {
            throw new IllegalStateException("Ilegal Eception");
        }
    }
    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        getActivity().startActivityForResult(intent, 100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageview.setImageBitmap(photo);
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri selectedImage = getImageUri(getActivity(), photo);
                String realPath=getRealPathFromURI(selectedImage);
                selectedImage = Uri.parse(realPath);
                incidentImage.setVisibility(View.VISIBLE);
                incidentImage.setImageBitmap(photo);


            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
