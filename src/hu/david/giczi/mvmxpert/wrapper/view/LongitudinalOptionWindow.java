package hu.david.giczi.mvmxpert.wrapper.view;
import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.utils.LongitudinalType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LongitudinalOptionWindow {

    public JFrame jFrame;
    private LongitudinalType type;
    private JTextField scaleStartValueField;
    private JTextField distortionValueField;
    private JTextField shiftOnScreenValueField;
    private JTextField preIDValue;
    private JTextField postIDValue;
    private static final Font BOLD_FONT = new Font("Roboto", Font.BOLD, 18);
    private static final Color FONT_COLOR = new Color(112,128,144);

    public LongitudinalOptionWindow(LongitudinalType type) {
        createWindow(type);
    }

    private void createWindow(LongitudinalType type){
        this.type = type;
        if( type == LongitudinalType.VERTICAL ){
            jFrame = new JFrame("Vertik�lis param�terek megad�sa");
        }
        else if( type == LongitudinalType.HORIZONTAL ){
            jFrame = new JFrame( "Horizont�lis param�terek megad�sa");
        }
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jFrame.setVisible(false);
            }
        });
        jFrame.setLayout(new GridLayout(5, 3));
        addLogo();
        addScaleStartValueInputPanel();
        addDistortionValueInputPanel();
        addShiftOnScreenValueInputPanel();
        addPrePostIDInputPanel();
        addOKButtonPanel();
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


    private void addScaleStartValueInputPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        scaleStartValueField = new JTextField(20);
        scaleStartValueField.setBackground(Color.WHITE);
        scaleStartValueField.setForeground(FONT_COLOR);
        scaleStartValueField.setFont(BOLD_FONT);
        scaleStartValueField.setHorizontalAlignment(JTextField.CENTER);
        if( type == LongitudinalType.VERTICAL ){
            scaleStartValueField.setBorder(
                    new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            "Vertik�lis l�pt�k indul� magass�ga [m]",
                            TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
        }
        else if( type == LongitudinalType.HORIZONTAL ){
            scaleStartValueField.setBorder(
                    new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            "Horizont�lis l�pt�k indul� t�vols�ga [m]",
                            TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
            scaleStartValueField.setText("0");
        }
        scaleStartValueField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(scaleStartValueField);
        jFrame.add(panel);
    }

    private void addDistortionValueInputPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        distortionValueField = new JTextField(20);
        distortionValueField.setBackground(Color.WHITE);
        distortionValueField.setForeground(FONT_COLOR);
        distortionValueField.setFont(BOLD_FONT);
        distortionValueField.setHorizontalAlignment(JTextField.CENTER);
        if( type == LongitudinalType.VERTICAL ){
            distortionValueField.setBorder(
                    new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            "Vertik�lis m�retar�ny M = 1 :",
                            TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
                            distortionValueField.setText("0.1");
        }
        else if( type == LongitudinalType.HORIZONTAL ){
            distortionValueField.setBorder(
                    new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            "Horizont�lis m�retar�ny M = 1 :",
                            TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
            distortionValueField.setText("1");
        }
        distortionValueField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(distortionValueField);
        jFrame.add(panel);
    }

    private void addShiftOnScreenValueInputPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        shiftOnScreenValueField = new JTextField(20);
        shiftOnScreenValueField.setBackground(Color.WHITE);
        shiftOnScreenValueField.setForeground(FONT_COLOR);
        shiftOnScreenValueField.setFont(BOLD_FONT);
        shiftOnScreenValueField.setHorizontalAlignment(JTextField.CENTER);
        if( type == LongitudinalType.VERTICAL ){
            shiftOnScreenValueField.setBorder(
                    new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            "Monitoron val� vertik�lis eltol�s [m]",
                            TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
            shiftOnScreenValueField.setText("10");
        }
        else if( type == LongitudinalType.HORIZONTAL ){
            shiftOnScreenValueField.setBorder(
                    new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            "Monitoron val� horizont�lis eltol�s [m]",
                            TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
            shiftOnScreenValueField.setText("10");
        }
        shiftOnScreenValueField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(shiftOnScreenValueField);
        jFrame.add(panel);
    }

    private void addPrePostIDInputPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        preIDValue = new JTextField(10);
        preIDValue.setBackground(Color.WHITE);
        preIDValue.setForeground(FONT_COLOR);
        preIDValue.setFont(BOLD_FONT);
        preIDValue.setHorizontalAlignment(JTextField.CENTER);
        postIDValue = new JTextField(10);
        postIDValue.setBackground(Color.WHITE);
        postIDValue.setForeground(FONT_COLOR);
        postIDValue.setFont(BOLD_FONT);
        postIDValue.setHorizontalAlignment(JTextField.CENTER);
        preIDValue.setBorder(
                new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        "Pontsz�m prefix",
                        TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));
        postIDValue.setBorder(
                new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        "Pontsz�m postfix",
                        TitledBorder.CENTER, TitledBorder.TOP, null, FONT_COLOR));

        preIDValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postIDValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(preIDValue);
        panel.add(postIDValue);
        jFrame.add(panel);
    }

    private void addOKButtonPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        JButton okButton = new JButton("OK");
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setFont(BOLD_FONT);
        okButton.setPreferredSize(new Dimension(80,40));
        okButton.addActionListener(e -> KMLWrapperController.TRANSFORMATION_2D_WINDOW.
                longitudinalProcessController.validateLongitudinalOptionsInputData());
        panel.add(okButton);
        jFrame.add(panel);
    }

    public JTextField getScaleStartValueField() {
        return scaleStartValueField;
    }

    public JTextField getDistortionValueField() {
        return distortionValueField;
    }

    public JTextField getShiftOnScreenValueField() {
        return shiftOnScreenValueField;
    }

    public JTextField getPreIDValue() {
        return preIDValue;
    }

    public JTextField getPostIDValue() {
        return postIDValue;
    }

}
