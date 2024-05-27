package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InputDataFileWindow {

    public JFrame jFrame;
    private final KMLWrapperController controller;
    private JPanel inputFileOptionPanel;
    private JPanel outputFileOptionPanel;
    private JComboBox<String> inputDataTypeComboBox;
    private JComboBox<String> outputDataTypeComboBox;
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final String[] EOV_DATA_TYPE = {
            "Formátum választása",
            "Y,X,H",
            "Y,X",
            "Y X H",
            "Y X",
            "Y;X;H",
            "Y;X",
            "X,Y,H",
            "X,Y",
            "X Y H",
            "X Y",
            "X;Y;H",
            "X;Y"};
    private final String[] WGS_DATA_TYPE = {
            "Formátum választása",
            "Fi,Lambda,h",
            "Fi,Lambda",
            "Fi Lambda h",
            "Fi Lambda",
            "Fi;Lambda;h",
            "Fi;Lambda",
            "Lambda,Fi,h",
            "Lambda,Fi",
            "Lambda Fi h",
            "Lambda Fi",
            "Lambda;Fi;h",
            "Lambda;Fi",
            "X,Y,Z",
            "X Y Z",
            "X;Y;Z"};
    private final String[] KML_DATA_TYPE = {
            "Adattípus választása",
            "Pont",
            "Vonal",
            "Kerület",
            "Vonal+pontok",
            "Kerület+pontok"};
    private final String[] TXT_DATA_TYPE = {
            "Adattípus választása",
            "Beolvasott pontok",
            "Közös pontok: EOV",
            "Közös pontok: Fi,Lambda,h",
            "Közös pontok: X,Y,Z",
            "Transzformáció paraméterei",
            "Maradék ellentmondások"};

    public InputDataFileWindow(KMLWrapperController controller) {
        this.controller = controller;
        createWindow();
    }

    private void createWindow(){
        jFrame = new JFrame("Adatok fájlból beolvasása");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
               if( MessagePane.getYesNoOptionMessage("A program bezárása",
                       "Kilép a programból?", jFrame) == 0 ){
                   System.exit(0);
               }
            }
        });
        addLogo();
        addMenu();
        addInputFileOptionPanel();
        addOutputFileOptionPanel();
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setLayout(new GridLayout(3, 1));
        jFrame.setSize(1000, 750);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
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
        JMenuItem manuallyInputMenuItem = new JMenuItem("Kézi adatbevitel");
        manuallyInputMenuItem.addActionListener(e -> {
            jFrame.setVisible(false);
            if( controller.manuallyInputDataWindow == null ){
                controller.manuallyInputDataWindow = new ManuallyInputDataWindow(controller);
            }
            else {
                controller.manuallyInputDataWindow.jFrame.setVisible(true);
            }
        });
        manuallyInputMenuItem.setFont(plainFont);
        manuallyInputMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem exitProgramMenuItem = new JMenuItem("Kilépés a programból");
        exitProgramMenuItem.addActionListener(e -> {
            if( MessagePane.getYesNoOptionMessage("A program bezárása",
                    "Kilép a programból?", jFrame) == 0 ){
                System.exit(0);
            }
        });
        exitProgramMenuItem.setFont(plainFont);
        exitProgramMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optionMenu.add(manuallyInputMenuItem);
        optionMenu.addSeparator();
        optionMenu.add(exitProgramMenuItem);
        jMenuBar.add(optionMenu);
        jFrame.setJMenuBar(jMenuBar);
    }


    private void addTitleForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("Bemeneti fájl beállításainak megadása");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        inputFileOptionPanel.add(panel);
    }

    private void addRadioButtonForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        JRadioButton eovRadioBtn = new JRadioButton("EOV koordináták");
        eovRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(EOV_DATA_TYPE);
            inputDataTypeComboBox.setModel(model);
        });
        eovRadioBtn.setFont(plainFont);
        eovRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eovRadioBtn.setSelected(true);
        eovRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton wgsRadioBtn = new JRadioButton("WGS koordináták");
        wgsRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(WGS_DATA_TYPE);
            inputDataTypeComboBox.setModel(model);
        });
        wgsRadioBtn.setFont(plainFont);
        wgsRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wgsRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton listRadioBtn = new JRadioButton("AutoCad lista");
        listRadioBtn.addActionListener( e ->{
            String[] cadList = {"AutoCad lista koordináták"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            inputDataTypeComboBox.setModel(model);
        });
        listRadioBtn.setFont(plainFont);
        listRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        ButtonGroup radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(eovRadioBtn);
        radioBtnGroup.add(wgsRadioBtn);
        radioBtnGroup.add(listRadioBtn);
        panel.add(eovRadioBtn);
        panel.add(wgsRadioBtn);
        panel.add(listRadioBtn);
        inputFileOptionPanel.add(panel);
    }

    private void addComboBoxForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        inputDataTypeComboBox = new JComboBox<>(EOV_DATA_TYPE);
        inputDataTypeComboBox.setPreferredSize(new Dimension(400, 35));
        inputDataTypeComboBox.setBackground(new Color(249, 249, 249));
        inputDataTypeComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        inputDataTypeComboBox.setFont(new Font("Roboto", Font.PLAIN, 20));
        inputDataTypeComboBox.setForeground(Color.LIGHT_GRAY);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        inputDataTypeComboBox.setRenderer(renderer);
        panel.add(inputDataTypeComboBox);
        inputFileOptionPanel.add(panel);
    }

    private void addBrowseButtonForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        JButton browseBtn = new JButton("Tallózás");
        browseBtn.setFont(boldFont);
        browseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(browseBtn);
        inputFileOptionPanel.add(panel);
    }

    private void addInputFileOptionPanel(){
        inputFileOptionPanel = new JPanel();
        inputFileOptionPanel.setLayout(new GridLayout(4, 1));
        addTitleForInputFileOptionPanel();
        addRadioButtonForInputFileOptionPanel();
        addComboBoxForInputFileOptionPanel();
        addBrowseButtonForInputFileOptionPanel();
        jFrame.add(inputFileOptionPanel);
    }


    private void addTitleForOutputFileOptionPanel(){
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("Kimeneti fájl beállításainak megadása");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        outputFileOptionPanel.add(panel);
    }

    private void addOutputFileOptionPanel(){
    outputFileOptionPanel = new JPanel();
    outputFileOptionPanel.setLayout(new GridLayout(4, 1));
    addTitleForOutputFileOptionPanel();
    addPointNumberDataForOutputFile();
    addRadioButtonForOutputFileOptionPanel();
    addComboBoxForOutputFileOptionPanel();
    jFrame.add(outputFileOptionPanel);
    }

    private void addPointNumberDataForOutputFile(){
        JPanel panel = new JPanel();
        JTextField pointPreIdField = new JTextField();
        pointPreIdField.setFont(plainFont);
        pointPreIdField.setBackground(new Color(249, 249, 249));
        pointPreIdField.setForeground(Color.LIGHT_GRAY);
        pointPreIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointPreIdField.setPreferredSize(new Dimension(110, 35));
        JTextField pointIdField = new JTextField();
        pointIdField.setFont(plainFont);
        pointIdField.setBackground(new Color(249, 249, 249));
        pointIdField.setForeground(Color.LIGHT_GRAY);
        pointIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdField.setPreferredSize(new Dimension(110, 35));
        JTextField pointPostIdField = new JTextField();
        pointPostIdField.setFont(plainFont);
        pointPostIdField.setBackground(new Color(249, 249, 249));
        pointPostIdField.setForeground(Color.LIGHT_GRAY);
        pointPostIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointPostIdField.setPreferredSize(new Dimension(110, 35));
        panel.add(pointPreIdField);
        panel.add(pointIdField);
        panel.add(pointPostIdField);
        outputFileOptionPanel.add(panel);

    }
    private void addRadioButtonForOutputFileOptionPanel(){
        JPanel panel = new JPanel();
        JRadioButton kmlRadioBtn = new JRadioButton("KML file");
        kmlRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(KML_DATA_TYPE);
            outputDataTypeComboBox.setModel(model);
        });
        kmlRadioBtn.setFont(plainFont);
        kmlRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kmlRadioBtn.setSelected(true);
        kmlRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton txtRadioBtn = new JRadioButton("txt file");
        txtRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(TXT_DATA_TYPE);
            outputDataTypeComboBox.setModel(model);
        });
        txtRadioBtn.setFont(plainFont);
        txtRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        ButtonGroup radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(kmlRadioBtn);
        radioBtnGroup.add(txtRadioBtn);
        panel.add(kmlRadioBtn);
        panel.add(txtRadioBtn);
        outputFileOptionPanel.add(panel);
    }

    private void addComboBoxForOutputFileOptionPanel(){
        JPanel panel = new JPanel();
        outputDataTypeComboBox = new JComboBox<>(KML_DATA_TYPE);
        outputDataTypeComboBox.setPreferredSize(new Dimension(400, 35));
        outputDataTypeComboBox.setBackground(new Color(249, 249, 249));
        outputDataTypeComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        outputDataTypeComboBox.setFont(new Font("Roboto", Font.PLAIN, 20));
        outputDataTypeComboBox.setForeground(Color.LIGHT_GRAY);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        outputDataTypeComboBox.setRenderer(renderer);
        panel.add(outputDataTypeComboBox);
        outputFileOptionPanel.add(panel);
    }

}
