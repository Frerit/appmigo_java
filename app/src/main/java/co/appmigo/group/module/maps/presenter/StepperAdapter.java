package co.appmigo.group.module.maps.presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.appmigo.group.R;
import co.appmigo.group.module.maps.fragment.IncidentSlider1Fragment;
import co.appmigo.group.module.maps.fragment.IncidentSlider2Fragment;
import co.appmigo.group.module.maps.fragment.IncidentSlider3Fragment;
import co.appmigo.group.module.maps.fragment.IncidentSlider4Fragment;
import co.appmigo.group.module.maps.fragment.IncidentSlider5Fragment;

public class StepperAdapter extends AbstractFragmentStepAdapter  {

    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Fragment step = null;
        switch (position) {
           case 0:
               step = new IncidentSlider1Fragment();
               Bundle b1 = new Bundle();
               step.setArguments(b1);
               break;
           case 1:
               step = new IncidentSlider2Fragment();
               Bundle b2 = new Bundle();
               step.setArguments(b2);
               break;
           case 2:
                step = new IncidentSlider3Fragment();
                Bundle b3 = new Bundle();
                step.setArguments(b3);
                break;
           case 3:
                step = new IncidentSlider4Fragment();
                Bundle b4 = new Bundle();
                step.setArguments(b4);
                break;
            case 4:
                step = new IncidentSlider5Fragment();
                Bundle b5 = new Bundle();
                step.setArguments(b5);
                break;
        }
        return (Step) step;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context)
                .setTitle(R.id.tabs);


        switch (position){
            case 0:
                return builder
                        .setTitle("Tab 1")
                        .setBackButtonLabel("Cancelar")
                        .setEndButtonLabel("Continuar")
                        .create();
            case 1:
                return builder
                        .setTitle("Tab 2")
                        .setEndButtonLabel("Continuar")
                        .setBackButtonLabel("Regresar")
                        .create();
            case 2:
                return builder
                        .setTitle("Tab 3")
                        .setEndButtonLabel("Continuar")
                        .setBackButtonLabel("Regresar")
                        .create();
            case 3:
                return builder
                        .setTitle("Tab 4")
                        .setEndButtonLabel("Continuar")
                        .setBackButtonLabel("Regresar")
                        .create();
            case 4:
                return builder
                        .setTitle("Tab 5")
                        .setEndButtonLabel("Finalizar")
                        .setBackButtonLabel("Regresar")
                        .create();
        }
        return null;
    }
}
