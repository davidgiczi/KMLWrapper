package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ManuallyInputDataWindow {
    public JFrame jFrame;
    private final KMLWrapperController controller;
    private JPanel inputDataPanel;
    private JComboBox<String> inputDataTypeComboBox;
    public JTextField pointIdFieldForEOV;
    public JTextField y_EOV_field;
    public JTextField x_EOV_field;
    public JTextField h_EOV_field;
    public JTextField pointIdFieldForWGS84DecimalFormat;
    public  JTextField fi_WGS84_field;
    public JTextField lambda_WGS84_field;
    public JTextField h_WGS84_field;
    public JTextField pointIdFieldForWGS84AngleMinSecFormat;
    public JTextField fiAngleField;
    public JTextField fiMinField;
    public JTextField fiSecField;
    public JTextField lambdaAngleField;
    public JTextField lambdaMinField;
    public JTextField lambdaSecField;
    public JTextField h_angle_min_sec_WGS84_field;
    public JTextField pointIdFieldForWGS84XYZFormat;
    public JTextField x_WGS84_field;
    public JTextField y_WGS84_field;
    public JTextField z_WGS84_field;
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Boolean[] IS_INPUT_DATA_TYPE = {false, false, false, false, false};

    private final String[] INPUT_DATA_TYPE = {
            "Bemeneti adattípus választása",
            "EOV (Y,X,H)",
            "WGS84 (decimális)",
            "WGS84 (fok,perc,mperc)",
            "WGS84 (X,Y,Z)"};

    public ManuallyInputDataWindow(KMLWrapperController controller) {
        this.controller = controller;
        createWindow(INPUT_DATA_TYPE[0]);
    }

    private void createWindow(String selectedItem){
        jFrame = new JFrame(controller.getWindowTitle());
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jFrame.setVisible(false);
               controller.inputDataFileWindow.jFrame.setVisible(true);
            }
        });
        addInputFileOptionPanel(selectedItem);
        if( INPUT_DATA_TYPE[0].equals(selectedItem) ) {
            inputDataTypeComboBox.setForeground(Color.LIGHT_GRAY);
        }
        else {
            inputDataTypeComboBox.setForeground(Color.BLACK);
        }
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setSize(1000, 250);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    private void addInputFileOptionPanel(String selectedItem){
        inputDataPanel = new JPanel();
        inputDataPanel.setLayout(new GridLayout(4, 1));
        addLogo();
        addMenu();
        addTitleForInputDataOptionPanel();
        addComboBoxForInputDataPanel(selectedItem);
        if( selectedItem.equals(INPUT_DATA_TYPE[0])) {
            inputDataPanel.add(new JPanel());
            IS_INPUT_DATA_TYPE[0] = true;
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[1])){
            getInputDataPanelForEOVData();
            IS_INPUT_DATA_TYPE[1] = true;

        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[2])){
            getInputDataPanelForWGSDecimalFormat();
            IS_INPUT_DATA_TYPE[2] = true;
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[3])){
            getInputDataPanelForWGSAngleSecMinFormat();
            IS_INPUT_DATA_TYPE[3] = true;
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[4])){
            getInputDataPanelForWGSXYZFormat();
            IS_INPUT_DATA_TYPE[4] = true;
        }
        addDataButton();
        jFrame.add(inputDataPanel);
    }

    private void addTitleForInputDataOptionPanel() {
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("Bemeneti adatok típusának megadása");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        inputDataPanel.add(panel);
    }

    private void addComboBoxForInputDataPanel(String selectedItem){
        JPanel panel = new JPanel();
        inputDataTypeComboBox = new JComboBox<>(INPUT_DATA_TYPE);
        if( selectedItem != null ){
            inputDataTypeComboBox.setSelectedItem(selectedItem);
        }
        inputDataTypeComboBox.addItemListener(e -> reCreateWindow());
        inputDataTypeComboBox.setPreferredSize(new Dimension(400, 35));
        inputDataTypeComboBox.setBackground(new Color(249, 249, 249));
        inputDataTypeComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        inputDataTypeComboBox.setFont(new Font("Roboto", Font.PLAIN, 20));
        inputDataTypeComboBox.setForeground(Color.LIGHT_GRAY);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        inputDataTypeComboBox.setRenderer(renderer);
        panel.add(inputDataTypeComboBox);
        inputDataPanel.add(panel);
    }

    private void reCreateWindow(){
        jFrame.setVisible(false);
        String selectedItem = Objects.requireNonNull(inputDataTypeComboBox.getSelectedItem()).toString();
        createWindow(selectedItem);
    }

    private void getInputDataPanelForEOVData(){
        JPanel panel = new JPanel();
        JLabel pointIDLabel = new JLabel("Pontszám");
        pointIDLabel.setFont(boldFont);
        panel.add(pointIDLabel);
        pointIdFieldForEOV = new JTextField();
        pointIdFieldForEOV.setFont(boldFont);
        pointIdFieldForEOV.setBackground(new Color(249, 249, 249));
        pointIdFieldForEOV.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdFieldForEOV.setPreferredSize(new Dimension(150, 35));
        pointIdFieldForEOV.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(pointIdFieldForEOV);
        JLabel yLabel = new JLabel("Y:");
        yLabel.setFont(boldFont);
        panel.add(yLabel);
        y_EOV_field = new JTextField();
        y_EOV_field.setFont(boldFont);
        y_EOV_field.setBackground(new Color(249, 249, 249));
        y_EOV_field.setHorizontalAlignment(SwingConstants.CENTER);
        y_EOV_field.setPreferredSize(new Dimension(150, 35));
        y_EOV_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(y_EOV_field);
        JLabel xLabel = new JLabel("X:");
        xLabel.setFont(boldFont);
        panel.add(xLabel);
        x_EOV_field = new JTextField();
        x_EOV_field.setFont(boldFont);
        x_EOV_field.setBackground(new Color(249, 249, 249));
        x_EOV_field.setHorizontalAlignment(SwingConstants.CENTER);
        x_EOV_field.setPreferredSize(new Dimension(150, 35));
        x_EOV_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(x_EOV_field);
        JLabel hLabel = new JLabel("H:");
        hLabel.setFont(boldFont);
        panel.add(hLabel);
        h_EOV_field = new JTextField();
        h_EOV_field.setFont(boldFont);
        h_EOV_field.setBackground(new Color(249, 249, 249));
        h_EOV_field.setHorizontalAlignment(SwingConstants.CENTER);
        h_EOV_field.setPreferredSize(new Dimension(150, 35));
        h_EOV_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(h_EOV_field);
        inputDataPanel.add(panel);
    }

    private void getInputDataPanelForWGSDecimalFormat(){
        JPanel panel = new JPanel();
        JLabel pointIDLabel = new JLabel("Pontszám");
        pointIDLabel.setFont(boldFont);
        panel.add(pointIDLabel);
        pointIdFieldForWGS84DecimalFormat = new JTextField();
        pointIdFieldForWGS84DecimalFormat.setFont(boldFont);
        pointIdFieldForWGS84DecimalFormat.setBackground(new Color(249, 249, 249));
        pointIdFieldForWGS84DecimalFormat.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdFieldForWGS84DecimalFormat.setPreferredSize(new Dimension(150, 35));
        pointIdFieldForWGS84DecimalFormat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(pointIdFieldForWGS84DecimalFormat);
        JLabel fiLabel = new JLabel("Szélesség:");
        fiLabel.setFont(boldFont);
        panel.add(fiLabel);
        fi_WGS84_field = new JTextField();
        fi_WGS84_field.setFont(boldFont);
        fi_WGS84_field.setBackground(new Color(249, 249, 249));
        fi_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        fi_WGS84_field.setPreferredSize(new Dimension(150, 35));
        fi_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(fi_WGS84_field);
        JLabel lambdaLabel = new JLabel("Hosszúság:");
        lambdaLabel.setFont(boldFont);
        panel.add(lambdaLabel);
        lambda_WGS84_field = new JTextField();
        lambda_WGS84_field.setFont(boldFont);
        lambda_WGS84_field.setBackground(new Color(249, 249, 249));
        lambda_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        lambda_WGS84_field.setPreferredSize(new Dimension(150, 35));
        lambda_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(lambda_WGS84_field);
        JLabel hLabel = new JLabel("h:");
        hLabel.setFont(boldFont);
        panel.add(hLabel);
        h_WGS84_field = new JTextField();
        h_WGS84_field.setFont(boldFont);
        h_WGS84_field.setBackground(new Color(249, 249, 249));
        h_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        h_WGS84_field.setPreferredSize(new Dimension(150, 35));
        h_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(h_WGS84_field);
        inputDataPanel.add(panel);
    }
    private void getInputDataPanelForWGSAngleSecMinFormat(){
        JPanel panel = new JPanel();
        pointIdFieldForWGS84AngleMinSecFormat = new JTextField();
        pointIdFieldForWGS84AngleMinSecFormat.setText("Pontszám");
        pointIdFieldForWGS84AngleMinSecFormat.setFont(plainFont);
        pointIdFieldForWGS84AngleMinSecFormat.setBackground(new Color(249, 249, 249));
        pointIdFieldForWGS84AngleMinSecFormat.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdFieldForWGS84AngleMinSecFormat.setPreferredSize(new Dimension(80, 35));
        pointIdFieldForWGS84AngleMinSecFormat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pointIdFieldForWGS84AngleMinSecFormat.setForeground(Color.LIGHT_GRAY);
        panel.add(pointIdFieldForWGS84AngleMinSecFormat);
        JLabel fiLabel = new JLabel("Szélesség:");
        fiLabel.setFont(boldFont);
        panel.add(fiLabel);
        fiAngleField = new JTextField();
        fiAngleField.setText("fok");
        fiAngleField.setFont(plainFont);
        fiAngleField.setBackground(new Color(249, 249, 249));
        fiAngleField.setHorizontalAlignment(SwingConstants.CENTER);
        fiAngleField.setPreferredSize(new Dimension(80, 35));
        fiAngleField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fiAngleField.setForeground(Color.LIGHT_GRAY);
        panel.add(fiAngleField);
        fiMinField = new JTextField();
        fiMinField.setText("perc");
        fiMinField.setFont(plainFont);
        fiMinField.setBackground(new Color(249, 249, 249));
        fiMinField.setHorizontalAlignment(SwingConstants.CENTER);
        fiMinField.setPreferredSize(new Dimension(80, 35));
        fiMinField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fiMinField.setForeground(Color.LIGHT_GRAY);
        panel.add(fiMinField);
        fiSecField = new JTextField();
        fiSecField.setText("mperc");
        fiSecField.setFont(plainFont);
        fiSecField.setBackground(new Color(249, 249, 249));
        fiSecField.setHorizontalAlignment(SwingConstants.CENTER);
        fiSecField.setPreferredSize(new Dimension(80, 35));
        fiSecField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fiSecField.setForeground(Color.LIGHT_GRAY);
        panel.add(fiSecField);
        JLabel lambdaLabel = new JLabel("Hosszúság:");
        lambdaLabel.setFont(boldFont);
        panel.add(lambdaLabel);
        lambdaAngleField = new JTextField();
        lambdaAngleField.setText("fok");
        lambdaAngleField.setFont(plainFont);
        lambdaAngleField.setBackground(new Color(249, 249, 249));
        lambdaAngleField.setHorizontalAlignment(SwingConstants.CENTER);
        lambdaAngleField.setPreferredSize(new Dimension(80, 35));
        lambdaAngleField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lambdaAngleField.setForeground(Color.LIGHT_GRAY);
        panel.add(lambdaAngleField);
        lambdaMinField = new JTextField();
        lambdaMinField.setText("perc");
        lambdaMinField.setFont(plainFont);
        lambdaMinField.setBackground(new Color(249, 249, 249));
        lambdaMinField.setHorizontalAlignment(SwingConstants.CENTER);
        lambdaMinField.setPreferredSize(new Dimension(80, 35));
        lambdaMinField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lambdaMinField.setForeground(Color.LIGHT_GRAY);
        panel.add(lambdaMinField);
        lambdaSecField = new JTextField();
        lambdaSecField.setText("mperc");
        lambdaSecField.setFont(plainFont);
        lambdaSecField.setBackground(new Color(249, 249, 249));
        lambdaSecField.setHorizontalAlignment(SwingConstants.CENTER);
        lambdaSecField.setPreferredSize(new Dimension(80, 35));
        lambdaSecField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lambdaSecField.setForeground(Color.LIGHT_GRAY);
        panel.add(lambdaSecField);
        JLabel hLabel = new JLabel("h:");
        hLabel.setFont(boldFont);
        panel.add(hLabel);
        h_angle_min_sec_WGS84_field = new JTextField();
        h_angle_min_sec_WGS84_field.setFont(boldFont);
        h_angle_min_sec_WGS84_field.setBackground(new Color(249, 249, 249));
        h_angle_min_sec_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        h_angle_min_sec_WGS84_field.setPreferredSize(new Dimension(80, 35));
        h_angle_min_sec_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(h_angle_min_sec_WGS84_field);
        inputDataPanel.add(panel);
    }

    private void getInputDataPanelForWGSXYZFormat(){
        JPanel panel = new JPanel();
        JLabel pointIDLabel = new JLabel("Pontszám");
        pointIDLabel.setFont(boldFont);
        panel.add(pointIDLabel);
        pointIdFieldForWGS84XYZFormat = new JTextField();
        pointIdFieldForWGS84XYZFormat.setFont(boldFont);
        pointIdFieldForWGS84XYZFormat.setBackground(new Color(249, 249, 249));
        pointIdFieldForWGS84XYZFormat.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdFieldForWGS84XYZFormat.setPreferredSize(new Dimension(150, 35));
        pointIdFieldForWGS84XYZFormat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(pointIdFieldForWGS84XYZFormat);
        JLabel xLabel = new JLabel("X:");
        xLabel.setFont(boldFont);
        panel.add(xLabel);
        x_WGS84_field = new JTextField();
        x_WGS84_field.setFont(boldFont);
        x_WGS84_field.setBackground(new Color(249, 249, 249));
        x_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        x_WGS84_field.setPreferredSize(new Dimension(150, 35));
        x_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(x_WGS84_field);
        JLabel yLabel = new JLabel("Y:");
        yLabel.setFont(boldFont);
        panel.add(yLabel);
        y_WGS84_field = new JTextField();
        y_WGS84_field.setFont(boldFont);
        y_WGS84_field.setBackground(new Color(249, 249, 249));
        y_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        y_WGS84_field.setPreferredSize(new Dimension(150, 35));
        y_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(y_WGS84_field);
        JLabel zLabel = new JLabel("Z:");
        zLabel.setFont(boldFont);
        panel.add(zLabel);
        z_WGS84_field = new JTextField();
        z_WGS84_field.setFont(boldFont);
        z_WGS84_field.setBackground(new Color(249, 249, 249));
        z_WGS84_field.setHorizontalAlignment(SwingConstants.CENTER);
        z_WGS84_field.setPreferredSize(new Dimension(150, 35));
        z_WGS84_field.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(z_WGS84_field);
        inputDataPanel.add(panel);
    }

    private void addDataButton(){
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Hozzáad");
        addBtn.addActionListener(a ->{onClickAddDataButton();});
        addBtn.setFont(boldFont);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(addBtn);
        inputDataPanel.add(panel);
    }

    private void onClickAddDataButton(){
        int inpuDataTypeIndex = 0;
        for (int i = 0; i < IS_INPUT_DATA_TYPE.length; i++) {
            if( IS_INPUT_DATA_TYPE[i] ){
                inpuDataTypeIndex = i;
        }
}
        switch ( inpuDataTypeIndex ){
            case 0 :
                MessagePane.getInfoMessage("Érvénytelen bevitel","Formátum választása szükséges.", jFrame);
                break;
            case 1 :
                controller.validationManuallyInputDataForEOV();
                break;
            case 2:
                controller.validationManuallyInputDataForWGS84DecimalFormat();
                break;
            case 3:
                controller.validationManuallyInputDataForWGS84AngleMinSecFormat();
                break;
            case 4:
                controller.validationManuallyInputDataForWGS84XYZFormat();
                break;
            default:
        }
        jFrame.setTitle(controller.getWindowTitle());
    }

    private void addLogo(){
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
    }
    private void addMenu(){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu optionMenu = new JMenu("Opciók");
        optionMenu.setFont(boldFont);
        optionMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem inputDataFileMenuItem = new JMenuItem("Adatok fájlból beolvasása");
        inputDataFileMenuItem.addActionListener(e -> {
            jFrame.setVisible(false);
            controller.inputDataFileWindow.jFrame.setVisible(true);
            controller.inputDataFileWindow.jFrame.setTitle(controller.getWindowTitle());
        });
        inputDataFileMenuItem.setFont(plainFont);
        inputDataFileMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem exitProgramMenuItem = new JMenuItem("Kilépés a programból");
        exitProgramMenuItem.addActionListener(e -> {
            if( MessagePane.getYesNoOptionMessage("A program bezárása",
                    "Kilép a programból?", jFrame) == 0 ){
                System.exit(0);
            }
        });
        exitProgramMenuItem.setFont(plainFont);
        exitProgramMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optionMenu.add(inputDataFileMenuItem);
        optionMenu.addSeparator();
        optionMenu.add(exitProgramMenuItem);
        jMenuBar.add(optionMenu);
        jFrame.setJMenuBar(jMenuBar);
    }
}
