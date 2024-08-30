package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.service.Transformation2D;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Transformation2DWindow {

    public JFrame jFrame;
    private JPanel inputDataOptionPanel;
    private JPanel transformationDataPanel;
    public JTextField point11NumberField;
    public JTextField point11YField;
    public JTextField point11XField;
    public JTextField point11ZField;
    public JTextField point12NumberField;
    public JTextField point12YField;
    public JTextField point12XField;
    public JTextField point12ZField;
    public JTextField point21NumberField;
    public JTextField point21YField;
    public JTextField point21XField;
    public JTextField point21ZField;
    public JTextField point22NumberField;
    public JTextField point22YField;
    public JTextField point22XField;
    public JTextField point22ZField;
    public JTextField distanceParamField;
    public JTextField rotationParamField;
    public JTextField scaleParamField;
    private KMLWrapperController controller;
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Color GREEN = new Color(193, 225, 193);
    private final Image iconImage = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("/icon/transfer.png"));


    public Transformation2DWindow(KMLWrapperController controller) {
        this.controller = controller;
        createWindow();
    }

    private void createWindow(){
        jFrame = new JFrame("2D transzformáció");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jFrame.setVisible(false);
                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame.setVisible(true);
            }
        });
        addLogo();
        addMenu();
        addInputDataOptionPanel();
        addTransformationDataPanel();
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
        JMenuItem inputDataFileMenuItem = new JMenuItem("Adatok fájlból beolvasása");
        inputDataFileMenuItem.addActionListener(e -> {
            jFrame.setVisible(false);
            KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame.setVisible(true);
            KMLWrapperController.setWindowTitle();
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

    private void addInputDataOptionPanel(){
        inputDataOptionPanel = new JPanel();
        inputDataOptionPanel.setLayout(new GridLayout(4, 1));
        addTitleForInputDataOptionPanel();
        addFirstCommonPointsData();
        addSecondCommonPointsData();
        addSettingInputDataButton();
        jFrame.add(inputDataOptionPanel);
    }
    private void addTitleForInputDataOptionPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JLabel system1Label = new JLabel("1. vonatkozási rendszer");
        system1Label.setFont(boldFont);
        system1Label.setBorder(new EmptyBorder(10,0,0,400));
        JLabel system2Label = new JLabel("2. vonatkozási rendszer");
        system2Label.setFont(boldFont);
        system2Label.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(system1Label);
        panel.add(system2Label);
        inputDataOptionPanel.add(panel);
    }

    private void addFirstCommonPointsData(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JLabel point11Label = new JLabel("1. pont");
        point11Label.setFont(boldFont);
        point11NumberField = new JTextField();
        point11NumberField.setToolTipText("Pontszám");
        point11NumberField.setFont(boldFont);
        point11NumberField.setBackground(new Color(249, 249, 249));
        point11NumberField.setHorizontalAlignment(SwingConstants.CENTER);
        point11NumberField.setPreferredSize(new Dimension(50, 35));
        point11NumberField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point11YField = new JTextField();
        point11YField.setToolTipText("1. koordináta");
        point11YField.setFont(boldFont);
        point11YField.setBackground(new Color(249, 249, 249));
        point11YField.setHorizontalAlignment(SwingConstants.CENTER);
        point11YField.setPreferredSize(new Dimension(100, 35));
        point11YField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point11XField = new JTextField();
        point11XField.setToolTipText("2. koordináta");
        point11XField.setFont(boldFont);
        point11XField.setBackground(new Color(249, 249, 249));
        point11XField.setHorizontalAlignment(SwingConstants.CENTER);
        point11XField.setPreferredSize(new Dimension(100, 35));
        point11XField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point11ZField = new JTextField();
        point11ZField.setToolTipText("Magasság");
        point11ZField.setFont(boldFont);
        point11ZField.setBackground(new Color(249, 249, 249));
        point11ZField.setHorizontalAlignment(SwingConstants.CENTER);
        point11ZField.setPreferredSize(new Dimension(80, 35));
        point11ZField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton exchangeBtn =
                new JButton(new ImageIcon(iconImage.getScaledInstance(35, 30, Image.SCALE_DEFAULT)));
        exchangeBtn.addActionListener(e -> {

        });
        exchangeBtn.setToolTipText("Pont adatok cseréje");
        exchangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel point12Label = new JLabel("1. pont");
        point12Label.setFont(boldFont);
        point12NumberField = new JTextField();
        point12NumberField.setToolTipText("Pontszám");
        point12NumberField.setFont(boldFont);
        point12NumberField.setBackground(new Color(249, 249, 249));
        point12NumberField.setHorizontalAlignment(SwingConstants.CENTER);
        point12NumberField.setPreferredSize(new Dimension(50, 35));
        point12NumberField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point12YField = new JTextField();
        point12YField.setToolTipText("1. koordináta");
        point12YField.setFont(boldFont);
        point12YField.setBackground(new Color(249, 249, 249));
        point12YField.setHorizontalAlignment(SwingConstants.CENTER);
        point12YField.setPreferredSize(new Dimension(100, 35));
        point12YField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point12XField = new JTextField();
        point12XField.setToolTipText("2. koordináta");
        point12XField.setFont(boldFont);
        point12XField.setBackground(new Color(249, 249, 249));
        point12XField.setHorizontalAlignment(SwingConstants.CENTER);
        point12XField.setPreferredSize(new Dimension(100, 35));
        point12XField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point12ZField = new JTextField();
        point12ZField.setToolTipText("Magasság");
        point12ZField.setFont(boldFont);
        point12ZField.setBackground(new Color(249, 249, 249));
        point12ZField.setHorizontalAlignment(SwingConstants.CENTER);
        point12ZField.setPreferredSize(new Dimension(80, 35));
        point12ZField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(point11Label);
        panel.add(point11NumberField);
        panel.add(point11YField);
        panel.add(point11XField);
        panel.add(point11ZField);
        panel.add(Box.createHorizontalStrut(35));
        panel.add(exchangeBtn);
        panel.add(Box.createHorizontalStrut(35));
        panel.add(point12Label);
        panel.add(point12NumberField);
        panel.add(point12YField);
        panel.add(point12XField);
        panel.add(point12ZField);
        inputDataOptionPanel.add(panel);
    }

    private void addSecondCommonPointsData(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JLabel point21Label = new JLabel("2. pont");
        point21Label.setFont(boldFont);
        point21NumberField = new JTextField();
        point21NumberField.setToolTipText("Pontszám");
        point21NumberField.setFont(boldFont);
        point21NumberField.setBackground(new Color(249, 249, 249));
        point21NumberField.setHorizontalAlignment(SwingConstants.CENTER);
        point21NumberField.setPreferredSize(new Dimension(50, 35));
        point21NumberField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point21YField = new JTextField();
        point21YField.setToolTipText("1. koordináta");
        point21YField.setFont(boldFont);
        point21YField.setBackground(new Color(249, 249, 249));
        point21YField.setHorizontalAlignment(SwingConstants.CENTER);
        point21YField.setPreferredSize(new Dimension(100, 35));
        point21YField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point21XField = new JTextField();
        point21XField.setToolTipText("2. koordináta");
        point21XField.setFont(boldFont);
        point21XField.setBackground(new Color(249, 249, 249));
        point21XField.setHorizontalAlignment(SwingConstants.CENTER);
        point21XField.setPreferredSize(new Dimension(100, 35));
        point21XField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point21ZField = new JTextField();
        point21ZField.setToolTipText("Magasság");
        point21ZField.setFont(boldFont);
        point21ZField.setBackground(new Color(249, 249, 249));
        point21ZField.setHorizontalAlignment(SwingConstants.CENTER);
        point21ZField.setPreferredSize(new Dimension(80, 35));
        point21ZField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton exchangeBtn =
                new JButton(new ImageIcon(iconImage.getScaledInstance(35, 30, Image.SCALE_DEFAULT)));
        exchangeBtn.addActionListener(e -> {

        });
        exchangeBtn.setToolTipText("Pont adatok cseréje");
        exchangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel point22Label = new JLabel("2. pont");
        point22Label.setFont(boldFont);
        point22NumberField = new JTextField();
        point22NumberField.setToolTipText("Pontszám");
        point22NumberField.setFont(boldFont);
        point22NumberField.setBackground(new Color(249, 249, 249));
        point22NumberField.setHorizontalAlignment(SwingConstants.CENTER);
        point22NumberField.setPreferredSize(new Dimension(50, 35));
        point22NumberField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point22YField = new JTextField();
        point22YField.setToolTipText("1. koordináta");
        point22YField.setFont(boldFont);
        point22YField.setBackground(new Color(249, 249, 249));
        point22YField.setHorizontalAlignment(SwingConstants.CENTER);
        point22YField.setPreferredSize(new Dimension(100, 35));
        point22YField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point22XField = new JTextField();
        point22XField.setToolTipText("2. koordináta");
        point22XField.setFont(boldFont);
        point22XField.setBackground(new Color(249, 249, 249));
        point22XField.setHorizontalAlignment(SwingConstants.CENTER);
        point22XField.setPreferredSize(new Dimension(100, 35));
        point22XField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        point22ZField = new JTextField();
        point22ZField.setToolTipText("Magasság");
        point22ZField.setFont(boldFont);
        point22ZField.setBackground(new Color(249, 249, 249));
        point22ZField.setHorizontalAlignment(SwingConstants.CENTER);
        point22ZField.setPreferredSize(new Dimension(80, 35));
        point22ZField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(point21Label);
        panel.add(point21NumberField);
        panel.add(point21YField);
        panel.add(point21XField);
        panel.add(point21ZField);
        panel.add(Box.createHorizontalStrut(35));
        panel.add(exchangeBtn);
        panel.add(Box.createHorizontalStrut(35));
        panel.add(point22Label);
        panel.add(point22NumberField);
        panel.add(point22YField);
        panel.add(point22XField);
        panel.add(point22ZField);
        inputDataOptionPanel.add(panel);
    }

    private void addSettingInputDataButton(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JButton countBtn = new JButton("Paraméterek számítása");
        countBtn.addActionListener(e -> {
            new Transformation2D(
            point11NumberField.getText(), point11YField.getText(), point11XField.getText(), point11ZField.getText(),
            point12NumberField.getText(), point12YField.getText(), point12XField.getText(), point12ZField.getText(),
            point21NumberField.getText(), point21YField.getText(), point21XField.getText(), point21ZField.getText(),
            point22NumberField.getText(), point22YField.getText(), point22XField.getText(), point22ZField.getText());
        });
        countBtn.setFont(boldFont);
        countBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(countBtn);
        inputDataOptionPanel.add(panel);
    }

    private void addTransformationDataPanel(){
        transformationDataPanel = new JPanel();
        transformationDataPanel.setLayout(new GridLayout(4, 1));
        addTransformationParamsPanel();
        jFrame.add(transformationDataPanel);
    }

    private void addTransformationParamsPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JLabel distanceParamText = new JLabel("Eltolás:");
        distanceParamText.setFont(boldFont);
        distanceParamField = new JTextField();
        distanceParamField.setToolTipText("A 2. vonatkozási rendszer origójának távolsága " +
                "az 1. vontakozási rendszer origójához képest");
        distanceParamField.setFont(boldFont);
        distanceParamField.setBackground(new Color(249, 249, 249));
        distanceParamField.setHorizontalAlignment(SwingConstants.CENTER);
        distanceParamField.setPreferredSize(new Dimension(100, 35));
        distanceParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel distanceUnitText = new JLabel("m");
        distanceUnitText.setFont(boldFont);
        JLabel rotationParamText = new JLabel("Elforgatás:");
        rotationParamText.setFont(boldFont);
        rotationParamField = new JTextField();
        rotationParamField.setToolTipText("A 2. vonatkozási rendszer elforgatása az 1. vonatkozási rendszerhez képest");
        rotationParamField.setFont(boldFont);
        rotationParamField.setBackground(new Color(249, 249, 249));
        rotationParamField.setHorizontalAlignment(SwingConstants.CENTER);
        rotationParamField.setPreferredSize(new Dimension(200, 35));
        rotationParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel scaleParamText = new JLabel("Méretarány:");
        scaleParamText.setFont(boldFont);
        scaleParamField = new JTextField();
        scaleParamField.setToolTipText("Az 1. és  2. vonatkozási rendszerek távolságegységének aránya");
        scaleParamField.setFont(boldFont);
        scaleParamField.setBackground(new Color(249, 249, 249));
        scaleParamField.setHorizontalAlignment(SwingConstants.CENTER);
        scaleParamField.setPreferredSize(new Dimension(100, 35));
        scaleParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(distanceParamText);
        panel.add(distanceParamField);
        panel.add(distanceUnitText);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(rotationParamText);
        panel.add(rotationParamField);
        panel.add(Box.createHorizontalStrut(120));
        panel.add(scaleParamText);
        panel.add(scaleParamField);
        transformationDataPanel.add(panel);
    }

}
