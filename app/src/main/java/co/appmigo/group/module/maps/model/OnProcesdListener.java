package co.appmigo.group.module.maps.model;

import co.appmigo.group.common.Warning;

public interface OnProcesdListener {
     void onProceed();
     Warning getRegisterWarnig();
     void setRegisterWarnig(Warning registerWarnig);
}
