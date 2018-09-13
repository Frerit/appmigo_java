package co.appmigo.group.module.maps.presenter;

import android.content.Context;
import android.os.Bundle;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import co.appmigo.group.module.maps.fragment.IncidentSlider1Fragment;

public class StepperAdapter extends AbstractFragmentStepAdapter  {

    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
       switch (position) {
           case 0:
               final IncidentSlider1Fragment slide1 = new IncidentSlider1Fragment();
               Bundle b1 = new Bundle();
               b1.putInt(CURRENT_STEP_POSITION_KEY, position);
               slide1.setArguments(b1);
               return slide1;
           case 1:
               final IncidentSlider1Fragment slide2 = new IncidentSlider1Fragment();
               Bundle b2 = new Bundle();
               b2.putInt(CURRENT_STEP_POSITION_KEY, position);
               slide2.setArguments(b2);
               return slide2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        switch (position){
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle("Tabs 1") //can be a CharSequence instead
                        .create();
            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle("Tabs 2") //can be a CharSequence instead
                        .create();
        }
        return null;
    }
}
