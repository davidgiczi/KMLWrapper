package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ManuallyInputDataWindow {
    public JFrame jFrame;
    private final KMLWrapperController controller;
    private JPanel inputDataPanel;
    private JComboBox<String> inputDataTypeComboBox;
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);

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
        jFrame = new JFrame("Kézi adatbevitel");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jFrame.setVisible(false);
               controller.inputDataFileWindow.jFrame.setVisible(true);
            }
        });
        addInputFileOptionPanel(selectedItem);
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
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[1])){
            getInputDataPanelForEOVData();
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[2])){
            getInputDataPanelForWGSDecimalFormat();
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[3])){
            getInputDataPanelForWGSAngleSecMinFormat();
        }
        else if (selectedItem.equals(INPUT_DATA_TYPE[4])){
            getInputDataPanelForWGSXYZFormat();
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
        inputDataTypeComboBox.addItemListener(e ->{reCreateWindow();});
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
        JLabel yLabel = new JLabel("Y:");
        yLabel.setFont(boldFont);
        panel.add(yLabel);
        JTextField yField = new JTextField();
        yField.setFont(boldFont);
        yField.setBackground(new Color(249, 249, 249));
        yField.setHorizontalAlignment(SwingConstants.CENTER);
        yField.setPreferredSize(new Dimension(150, 35));
        yField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(yField);
        JLabel xLabel = new JLabel("X:");
        xLabel.setFont(boldFont);
        panel.add(xLabel);
        JTextField xField = new JTextField();
        xField.setFont(boldFont);
        xField.setBackground(new Color(249, 249, 249));
        xField.setHorizontalAlignment(SwingConstants.CENTER);
        xField.setPreferredSize(new Dimension(150, 35));
        xField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(xField);
        JLabel hLabel = new JLabel("H:");
        hLabel.setFont(boldFont);
        panel.add(hLabel);
        JTextField hField = new JTextField();
        hField.setFont(boldFont);
        hField.setBackground(new Color(249, 249, 249));
        hField.setHorizontalAlignment(SwingConstants.CENTER);
        hField.setPreferredSize(new Dimension(150, 35));
        hField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(hField);
        inputDataPanel.add(panel);
    }

    private void getInputDataPanelForWGSDecimalFormat(){
        JPanel panel = new JPanel();
        inputDataPanel.add(panel);
    }
    private void getInputDataPanelForWGSAngleSecMinFormat(){
        JPanel panel = new JPanel();
        inputDataPanel.add(panel);
    }

    private void getInputDataPanelForWGSXYZFormat(){
        JPanel panel = new JPanel();
        inputDataPanel.add(panel);
    }

    private void addDataButton(){
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Hozzáad");
        addBtn.setFont(boldFont);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(addBtn);
        inputDataPanel.add(panel);
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
        inputDataFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                controller.inputDataFileWindow.jFrame.setVisible(true);
            }
        });
        inputDataFileMenuItem.setFont(plainFont);
        inputDataFileMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem exitProgramMenuItem = new JMenuItem("Kilépés a programból");
        exitProgramMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( MessagePane.getYesNoOptionMessage("A program bezárása",
                        "Kilép a programból?", jFrame) == 0 ){
                    System.exit(0);
                }
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
