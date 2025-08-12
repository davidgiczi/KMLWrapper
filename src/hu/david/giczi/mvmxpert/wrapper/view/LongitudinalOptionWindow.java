package hu.david.giczi.mvmxpert.wrapper.view;
import hu.david.giczi.mvmxpert.wrapper.utils.LongitudinalType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LongitudinalOptionWindow {

    public JFrame jFrame;
    private JTextField scaleStartValueField;
    private JTextField distortionValueField;
    private JTextField shiftOnScreenValueField;
    private JTextField preIDValue;
    private JTextField postIDValue;

    public LongitudinalOptionWindow(LongitudinalType type) {
        createWindow(type);
    }

    private void createWindow(LongitudinalType type){
        if( type == LongitudinalType.VERTICAL ){
            jFrame = new JFrame("Vertikális paraméterek megadása");
        }
        else if( type == LongitudinalType.HORIZONTAL ){
            jFrame = new JFrame( "Horizontális paraméterek megadása");
        }
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jFrame.setVisible(false);
            }
        });

        addLogo();
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setSize(500, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    private void addLogo(){
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
    }

}
