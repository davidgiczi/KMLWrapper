package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.ManuallyInputDataWindow;

public class KMLWrapperController {

    public InputDataFileWindow inputDataFileWindow;
    public ManuallyInputDataWindow manuallyInputDataWindow;

    public KMLWrapperController() {
        this.inputDataFileWindow = new InputDataFileWindow(this);
    }
}
