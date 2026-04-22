package hu.david.giczi.mvmxpert.wrapper.controller;

public class AutoCadMacroFileController {

    private KMLWrapperController controller;
    public static final String[] AUTOCAD_OPTION ={
            "_TEXT",
            "_MULTIPLE _POINT",
            "_LINE",
            "_POLYLINE"
    };


    public void setController(KMLWrapperController controller) {
        this.controller = controller;
    }
}
