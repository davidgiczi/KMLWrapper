package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Transformation2DWindow {

    public JFrame jFrame;
    private JPanel inputDataOptionPanel;
    private JPanel transformationDataPanel;
    private JPanel transformDataPanel;
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
    public JTextField deltaDistanceXParamField;
    public JTextField deltaDistanceYParamField;
    public JTextField rotationParamField;
    public JTextField scaleParamField;
    public JTextField deltaElevationField;
    public JList<String> firstSystemDataList;
    public DefaultListModel<String> firstSystemDataListModel;
    public JList<String> secondSystemDataList;
    public DefaultListModel<String> secondSystemDataListModel;
    public JButton openFirstSystemPointDataBtn;
    public JButton saveSecondSystemPointDataBtn;
    public JRadioButton firstSystemRadioBtn;
    public JRadioButton secondSystemRadioBtn;
    public JRadioButton deltaElevationRadioBtn;
    private final KMLWrapperController controller;
    private final Font boldFont = new Font("Roboto",Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 18);
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
        addTransformationParamsPanel();
        addTransformDataPanel();
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setSize(1000, 860);
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
            onClickExchangeCommonPointsDataButton();
        });
        exchangeBtn.setToolTipText("Pont adatok cseréje");
        exchangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel point12Label = new JLabel("1. pont");
        point12Label.setFont(boldFont);
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
        panel.add(point11Label);
        panel.add(point11NumberField);
        panel.add(point11YField);
        panel.add(point11XField);
        panel.add(point11ZField);
        panel.add(Box.createHorizontalStrut(35));
        panel.add(exchangeBtn);
        panel.add(Box.createHorizontalStrut(35));
        panel.add(point12Label);
        panel.add(point21NumberField);
        panel.add(point21YField);
        panel.add(point21XField);
        panel.add(point21ZField);
        inputDataOptionPanel.add(panel);
    }

    private void addSecondCommonPointsData(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JLabel point21Label = new JLabel("2. pont");
        point21Label.setFont(boldFont);
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
        panel.add(point12NumberField);
        panel.add(point12YField);
        panel.add(point12XField);
        panel.add(point12ZField);
        panel.add(Box.createHorizontalStrut(150));
        panel.add(point22Label);
        panel.add(point22NumberField);
        panel.add(point22YField);
        panel.add(point22XField);
        panel.add(point22ZField);
        inputDataOptionPanel.add(panel);
    }

    private void onClickExchangeCommonPointsDataButton(){

        if( MessagePane.getYesNoOptionMessage("Közös pontok cseréje",
                "Biztos, hogy megcseréled a pontokat?", jFrame) == 1 ){
            return;
        }
        String point11Id = point11NumberField.getText();
        String point11Y = point11YField.getText();
        String point11X = point11XField.getText();
        String point11Z = point11ZField.getText();
        String point12Id = point12NumberField.getText();
        String point12Y = point12YField.getText();
        String point12X = point12XField.getText();
        String point12Z = point12ZField.getText();
        point11NumberField.setText(point21NumberField.getText());
        point11YField.setText(point21YField.getText());
        point11XField.setText(point21XField.getText());
        point11ZField.setText(point21ZField.getText());
        point21NumberField.setText(point11Id);
        point21YField.setText(point11Y);
        point21XField.setText(point11X);
        point21ZField.setText(point11Z);
        point12NumberField.setText(point22NumberField.getText());
        point12YField.setText(point22YField.getText());
        point12XField.setText(point22XField.getText());
        point12ZField.setText(point22ZField.getText());
        point22NumberField.setText(point12Id);
        point22YField.setText(point12Y);
        point22XField.setText(point12X);
        point22ZField.setText(point12Z);
    }

    private void addSettingInputDataButton(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JButton countBtn = new JButton("Paraméterek számítása");

        countBtn.addActionListener(e -> {
            controller.calcParamsForTransformation2D();
        });
        countBtn.setFont(boldFont);
        countBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(countBtn);
        inputDataOptionPanel.add(panel);
    }

    private void addTransformationParamsPanel(){
        transformationDataPanel = new JPanel();
        transformationDataPanel.setLayout(new GridLayout(2, 2));
        addTransformationParams1Panel();
        addTransformationParams2Panel();
        jFrame.add(transformationDataPanel);
    }

    private void addTransformationParams1Panel(){
        JPanel panel = new JPanel();
        panel.setBackground(GREEN);
        JLabel deltaDistance1ParamText = new JLabel("X eltolás:");
        deltaDistance1ParamText.setFont(boldFont);
        deltaDistanceXParamField = new JTextField();
        deltaDistanceXParamField.setToolTipText("A 1-2. vonatkozási rendszerek origóinak X irányú távolsága");
        deltaDistanceXParamField.setFont(boldFont);
        deltaDistanceXParamField.setBackground(new Color(249, 249, 249));
        deltaDistanceXParamField.setHorizontalAlignment(SwingConstants.CENTER);
        deltaDistanceXParamField.setPreferredSize(new Dimension(100, 35));
        deltaDistanceXParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel deltaDistance2ParamText = new JLabel("Y eltolás:");
        deltaDistance2ParamText.setFont(boldFont);
        deltaDistanceYParamField = new JTextField();
        deltaDistanceYParamField.setToolTipText("A 1-2. vonatkozási rendszerek origóinak Y irányú távolsága");
        deltaDistanceYParamField.setFont(boldFont);
        deltaDistanceYParamField.setBackground(new Color(249, 249, 249));
        deltaDistanceYParamField.setHorizontalAlignment(SwingConstants.CENTER);
        deltaDistanceYParamField.setPreferredSize(new Dimension(100, 35));
        deltaDistanceYParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel deltaDistance1UnitText = new JLabel("m");
        deltaDistance1UnitText.setFont(boldFont);
        JLabel deltaDistance2UnitText = new JLabel("m");
        deltaDistance2UnitText.setFont(boldFont);
        JLabel rotationParamText = new JLabel("Elforgatás:");
        rotationParamText.setFont(boldFont);
        rotationParamField = new JTextField();
        rotationParamField.setToolTipText("A 2. vonatkozási rendszer elforgatása az 1. vonatkozási rendszerhez képest");
        rotationParamField.setFont(boldFont);
        rotationParamField.setBackground(new Color(249, 249, 249));
        rotationParamField.setHorizontalAlignment(SwingConstants.CENTER);
        rotationParamField.setPreferredSize(new Dimension(200, 35));
        rotationParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(deltaDistance1ParamText);
        panel.add(deltaDistanceXParamField);
        panel.add(deltaDistance1UnitText);
        panel.add(Box.createHorizontalStrut(90));
        panel.add(deltaDistance2ParamText);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(deltaDistanceYParamField);
        panel.add(deltaDistance2UnitText);
        panel.add(Box.createHorizontalStrut(70));
        panel.add(rotationParamText);
        panel.add(rotationParamField);
        transformationDataPanel.add(panel);
    }

    private void addTransformationParams2Panel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(GREEN);
        JLabel scaleParamText = new JLabel("Méretarány:");
        scaleParamText.setFont(boldFont);
        scaleParamField = new JTextField();
        scaleParamField.setToolTipText("Az 1. és  2. vonatkozási rendszerek távolságegységének aránya");
        scaleParamField.setFont(boldFont);
        scaleParamField.setBackground(new Color(249, 249, 249));
        scaleParamField.setHorizontalAlignment(SwingConstants.CENTER);
        scaleParamField.setPreferredSize(new Dimension(100, 35));
        scaleParamField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel deltaElevationText = new JLabel("dM2 - dM1:");
        deltaElevationText.setFont(boldFont);
        deltaElevationField = new JTextField();
        deltaElevationField.setToolTipText("Az 1. és  2. vonatkozási rendszerek 1. és 2. pontjai magassákülönbségének különbsége");
        deltaElevationField.setFont(boldFont);
        deltaElevationField.setBackground(new Color(249, 249, 249));
        deltaElevationField.setHorizontalAlignment(SwingConstants.CENTER);
        deltaElevationField.setPreferredSize(new Dimension(100, 35));
        deltaElevationField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel deltaElevationUnitText = new JLabel("m");
        deltaElevationUnitText.setFont(boldFont);
        firstSystemRadioBtn = new JRadioButton("1.r. Mag.");
        firstSystemRadioBtn.setToolTipText("1. vonatkozási rendszerbeli pontok magasságát adja a " +
                "2. vonatkozási rendszer pontjainak");
        firstSystemRadioBtn.setBackground(GREEN);
        firstSystemRadioBtn.setFont(boldFont);
        firstSystemRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        secondSystemRadioBtn = new JRadioButton("2.r. Mag.");
        secondSystemRadioBtn.setToolTipText("2. vonatkozási rendszerbeli 1. pont magasságához " +
                "viszonyítva számítja a 2. vonatkozási rendszer pontjainak magasságát ");
        secondSystemRadioBtn.setBackground(GREEN);
        secondSystemRadioBtn.setFont(boldFont);
        secondSystemRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        secondSystemRadioBtn.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(firstSystemRadioBtn);
        group.add(secondSystemRadioBtn);
        deltaElevationRadioBtn = new JRadioButton("dM javítás");
        deltaElevationRadioBtn.setToolTipText("Adott értékkel javítja az 1. vonatkozási rendszer " +
                "vagy a 2. vonatkozási rendszer pontjainak magasságát");
        deltaElevationRadioBtn.setFont(boldFont);
        deltaElevationRadioBtn.setBackground(GREEN);
        deltaElevationRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(Box.createHorizontalStrut(20));
        panel.add(scaleParamText);
        panel.add(scaleParamField);
        panel.add(Box.createHorizontalStrut(110));
        panel.add(deltaElevationText);
        panel.add(deltaElevationField);
        panel.add(deltaElevationUnitText);
        panel.add(Box.createHorizontalStrut(70));
        panel.add(firstSystemRadioBtn);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(secondSystemRadioBtn);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(deltaElevationRadioBtn);
        transformationDataPanel.add(panel);
    }
    private void addTransformDataPanel(){
        transformDataPanel = new JPanel();
        transformDataPanel.setBackground(GREEN);
        addSystemDataLists();
        jFrame.add(transformDataPanel);
    }
    private void addSystemDataLists() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(GREEN);
        rightPanel.setPreferredSize(new Dimension(400, 600));
        JPanel mediumPanel = new JPanel();
        mediumPanel.setLayout(new BoxLayout(mediumPanel, BoxLayout.Y_AXIS));
        mediumPanel.setPreferredSize(new Dimension(100, 600));
        mediumPanel.setBackground(GREEN);
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setBackground(GREEN);
        firstSystemDataListModel = new DefaultListModel<>();
        firstSystemDataList = new JList<>(firstSystemDataListModel);
        firstSystemDataList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if( SwingUtilities.isRightMouseButton(e) ){
                    controller.transformFirstSystemData();
                }
                super.mouseClicked(e);
            }
        });
        firstSystemDataList.setToolTipText("1. vonatkozási rendszerben meghatározott pontok");
        firstSystemDataList.setCursor(new Cursor(Cursor.HAND_CURSOR));
        firstSystemDataList.setFont(plainFont);
        secondSystemDataListModel = new DefaultListModel<>();
        secondSystemDataList = new JList<>(secondSystemDataListModel);
        secondSystemDataList.setToolTipText("2. vonatkozási rendszerbe transzformált pontok");
        secondSystemDataList.setCursor(new Cursor(Cursor.HAND_CURSOR));
        secondSystemDataList.setFont(plainFont);
        openFirstSystemPointDataBtn = new JButton("Beolvas");
        openFirstSystemPointDataBtn.setToolTipText("1. vonatkozási rendszer pontjainak beolvasása");
        openFirstSystemPointDataBtn.setFont(boldFont);
        openFirstSystemPointDataBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openFirstSystemPointDataBtn.addActionListener(e -> {
            controller.fileProcess.openInputDataFile();
            if( FileProcess.FILE_NAME == null ){
                return;
            }
            jFrame.setTitle(FileProcess.FILE_NAME);
            String delimiter = MessagePane.getInputDataMessage(jFrame, null);
            KMLWrapperController.setDelimiter(delimiter);
            for (String inputData : FileProcess.INPUT_DATA_LIST) {
                setCommonPointDataById(inputData);
                firstSystemDataListModel.addElement(inputData);
            }
        });
        mediumPanel.add(Box.createVerticalStrut(5));
        mediumPanel.add(openFirstSystemPointDataBtn);
        saveSecondSystemPointDataBtn = new JButton("Mentés");
        saveSecondSystemPointDataBtn.setToolTipText("2. vonatkozási rendszer pontjainak mentése");
        saveSecondSystemPointDataBtn.setFont(boldFont);
        saveSecondSystemPointDataBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mediumPanel.add(Box.createVerticalStrut(380));
        mediumPanel.add(saveSecondSystemPointDataBtn);
        JScrollPane firstSystemDataScrollPane = new JScrollPane(firstSystemDataList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        firstSystemDataScrollPane.setPreferredSize(new Dimension(400, 450));
        leftPanel.add(firstSystemDataScrollPane);
        JButton deleteFirstSystemDataBtn = new JButton("Törlés");
        deleteFirstSystemDataBtn.addActionListener(e -> {
        if( MessagePane.getYesNoOptionMessage("Beolvasott adatok törlése",
                    "Biztos, hogy törlöd a kijelölt vagy az összes adatot?", jFrame) == 1 ){
                return;
            }
                List<String> selectedItems = firstSystemDataList.getSelectedValuesList();
                if( selectedItems.isEmpty() ){
                    firstSystemDataListModel.removeAllElements();
                }
                else{
                    for (String selectedItem : selectedItems) {
                        firstSystemDataListModel.removeElement(selectedItem);
                    }
                }
                if( firstSystemDataListModel.isEmpty() ){
                    jFrame.setTitle("2D transzformáció");
                }
        });
        deleteFirstSystemDataBtn.setToolTipText("1. vonatkozási rendszer kijelölt vagy összes adatainak törlése");
        deleteFirstSystemDataBtn.setFont(boldFont);
        deleteFirstSystemDataBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(deleteFirstSystemDataBtn);
        JScrollPane secondSystemScrollPane = new JScrollPane(secondSystemDataList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        secondSystemScrollPane.setPreferredSize(new Dimension(400, 450));
        rightPanel.add(secondSystemScrollPane);
        JButton deleteSecondSystemDataBtn = new JButton("Törlés");
        deleteSecondSystemDataBtn.addActionListener(e -> {
            if( MessagePane.getYesNoOptionMessage("Számított adatok törlése",
                    "Biztos, hogy törlöd a kijelölt vagy az összes adatot?", jFrame) == 1 ){
                return;
            }
            List<String> selectedItems = secondSystemDataList.getSelectedValuesList();
            if( selectedItems.isEmpty() ){
                secondSystemDataListModel.removeAllElements();
            }
            else{
                for (String selectedItem : selectedItems) {
                    secondSystemDataListModel.removeElement(selectedItem);
                }
            }
        });
        deleteSecondSystemDataBtn.setToolTipText("2. vonatkozási rendszer kijelölt vagy összes adatainak törlése");
        deleteSecondSystemDataBtn.setFont(boldFont);
        rightPanel.add(deleteSecondSystemDataBtn);
        transformDataPanel.add(leftPanel);
        transformDataPanel.add(mediumPanel, BorderLayout.CENTER);
        transformDataPanel.add(rightPanel);
    }

    private void setCommonPointDataById(String inputData){
        if( KMLWrapperController.DELIMITER == null ){
            return;
        }
        String[] pointData = inputData.split(KMLWrapperController.DELIMITER);
        if( 4 > pointData.length ){
            return;
        }
        if( pointData[0].equals(point11NumberField.getText()) ){
            if( !point11XField.getText().isEmpty() &&
                   !point11YField.getText().isEmpty() &&
                        !point11ZField.getText().isEmpty() ){
                return;
            }
                point11YField.setText(pointData[1]);
                point11XField.setText(pointData[2]);
                point11ZField.setText(pointData[3]);
        }
        else if( pointData[0].equals(point12NumberField.getText()) ){
            if( !point12XField.getText().isEmpty() &&
                    !point12YField.getText().isEmpty() &&
                        !point12ZField.getText().isEmpty() ){
                return;
            }
                point12YField.setText(pointData[1]);
                point12XField.setText(pointData[2]);
                point12ZField.setText(pointData[3]);
        }
        else if( pointData[0].equals(point21NumberField.getText()) ){
            if( !point21XField.getText().isEmpty() &&
                    !point21YField.getText().isEmpty() &&
                         !point21ZField.getText().isEmpty() ){
                return;
            }
                point21YField.setText(pointData[1]);
                point21XField.setText(pointData[2]);
                point21ZField.setText(pointData[3]);
        }
        else if( pointData[0].equals(point22NumberField.getText()) ){
            if( !point22XField.getText().isEmpty() &&
                    !point22YField.getText().isEmpty() &&
                        !point22ZField.getText().isEmpty() ){
                return;
            }
                point22YField.setText(pointData[1]);
                point22XField.setText(pointData[2]);
                point22ZField.setText(pointData[3]);
            }
        }
    }

