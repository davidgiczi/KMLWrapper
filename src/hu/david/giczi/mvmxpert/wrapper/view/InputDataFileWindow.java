package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InputDataFileWindow {

    public JFrame jFrame;
    private final KMLWrapperController controller;
    private JPanel inputFileOptionPanel;
    private JPanel outputFileOptionPanel;
    private JComboBox<String> dataTypeComboBox;
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
                .getImage(getClass().getResource("")));
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


    private void addTitleLabel(){
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("A fájlbeli adatok beállításainak megadása");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        inputFileOptionPanel.add(panel);
    }

    private void addRadioButton(){
        JPanel panel = new JPanel();
        JRadioButton eovRadioBtn = new JRadioButton("EOV koordináták");
        eovRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(EOV_DATA_TYPE);
            dataTypeComboBox.setModel(model);
        });
        eovRadioBtn.setFont(plainFont);
        eovRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eovRadioBtn.setSelected(true);
        eovRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton wgsRadioBtn = new JRadioButton("WGS koordináták");
        wgsRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(WGS_DATA_TYPE);
            dataTypeComboBox.setModel(model);
        });
        wgsRadioBtn.setFont(plainFont);
        wgsRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wgsRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton listRadioBtn = new JRadioButton("AutoCad lista");
        listRadioBtn.addActionListener( e ->{
            String[] cadList = {"AutoCad lista koordináták"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            dataTypeComboBox.setModel(model);
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

    private void addComboBox(){
        JPanel panel = new JPanel();
        dataTypeComboBox = new JComboBox<>(EOV_DATA_TYPE);
        dataTypeComboBox.setPreferredSize(new Dimension(400, 35));
        dataTypeComboBox.setBackground(new Color(249, 249, 249));
        dataTypeComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dataTypeComboBox.setFont(new Font("Roboto", Font.PLAIN, 20));
        dataTypeComboBox.setForeground(Color.LIGHT_GRAY);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        dataTypeComboBox.setRenderer(renderer);
        panel.add(dataTypeComboBox);
        inputFileOptionPanel.add(panel);
    }

    private void addBrowseButton(){
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
        addTitleLabel();
        addRadioButton();
        addComboBox();
        addBrowseButton();
        jFrame.add(inputFileOptionPanel);
    }


    private void addTitleForOutputFileOptionPanel(){
        outputFileOptionPanel = new JPanel();
        JLabel contentTitleLabel = new JLabel("KML fájl beállításainak megadása");
        contentTitleLabel.setFont(boldFont);
        outputFileOptionPanel.add(contentTitleLabel);
    }

    private void addOutputFileOptionPanel(){
    addTitleForOutputFileOptionPanel();
    jFrame.add(outputFileOptionPanel);
    }

}
