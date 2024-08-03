package hu.david.giczi.mvmxpert.wrapper.view;
import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class InputDataFileWindow {

    public JFrame jFrame;
    public JTextField pointPreIdField;
    public JTextField pointIdField;
    public JTextField pointPostIdField;
    private final KMLWrapperController controller;
    private JPanel inputFileOptionPanel;
    private JPanel outputFileOptionPanel;
    private JPanel saveOutputFileOptionPanel;
    private JTextField saveFileNameField;
    public JComboBox<String> inputDataTypeComboBox;
    public JComboBox<String> outputDataTypeComboBox;
    public JButton saveBtn;
    public DataDisplayerWindow displayer;
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    public static final String[] EOV_DATA_TYPE = {
            "Formátum választása",
            "EOV (Psz,Y,X,M)",
            "EOV (Psz Y X M)",
            "EOV (Psz;Y;X;M)",
            "EOV (Psz,X,Y,M)",
            "EOV (Psz X Y M)",
            "EOV (Psz;X;Y;M)",
            "EOV (Y,X,M)",
            "EOV (Y X M)",
            "EOV (Y;X;M)",
            "EOV (X,Y,M)",
            "EOV (X Y M)",
            "EOV (X;Y;M)"};
    private static final String[] WGS_DATA_TYPE = {
            "Formátum választása",
            "WGS84 (Psz,Szélesség,Hosszúság,Magasság)",
            "WGS84 (Psz Szélesség Hosszúság Magasság)",
            "WGS84 (Psz;Szélesség;Hosszúság;Magasság)",
            "WGS84 (Psz,Hosszúság,Szélesség,Magasság)",
            "WGS84 (Psz Hosszúság Szélesség Magasság)",
            "WGS84 (Psz;Hosszúság;Szélesség;Magasság)",
            "WGS84 (Psz,X,Y,Z)",
            "WGS84 (Psz X Y Z)",
            "WGS84 (Psz;X;Y;Z)",
            "WGS84 (Szélesség,Hosszúság,Magasság)",
            "WGS84 (Szélesség Hosszúság Magasság)",
            "WGS84 (Szélesség;Hosszúság;Magasság)",
            "WGS84 (Hosszúság,Szélesség,Magasság)",
            "WGS84 (Hosszúság Szélesség Magasság)",
            "WGS84 (Hosszúság;Szélesség;Magasság)",
            "WGS84 (X,Y,Z)",
            "WGS84 (X Y Z)",
            "WGS84 (X;Y;Z)"
    };

    public static final String[] KML_DATA_TYPE = {
            "Adattípus választása",
            "Pont.kml",
            "Vonal.kml",
            "Kerület.kml",
            "Vonal+pontok.kml",
            "Kerület+pontok.kml"};
    public static final String[] TXT_DATA_TYPE = {
            "Adattípus választása",
            "Beolvasott pontok: EOV (Y, X, M)",
            "Beolvasott pontok: WGS84 (Szélesség, Hosszúság, Magasság)",
            "Beolvasott pontok: WGS84 (X, Y, Z)",
            "Beolvasott pontok: IUGG67 (X, Y, Z)",
            "Beolvasott pontok: IUGG67 (Szélesség, Hosszúság, Magasság)",
            "Transzformált pontok: WGS84-EOV (Y, X, M)",
            "Transzformált pontok: EOV-WGS84 (Szélesség, Hosszúság, Magasság)",
            "Transzformált pontok: EOV-WGS84 (X, Y, Z)",
            "Közös pontok: WGS84-EOV (Y, X, M)",
            "Közös pontok: EOV-WGS84 (X, Y, Z)",
            "Közös pontok: EOV-WGS84 (Szélesség, Hosszúság, Magasság)",
            "Transzformáció paraméterei: EOV-WGS84",
            "Transzformáció paraméterei: WGS84-EOV",
            "Maradék ellentmondások: EOV-WGS84 (dX, dY, dZ)",
            "Maradék ellentmondások: WGS84-EOV (dY, dX, dM)"};

    private static final String[] FILE_NAME_OPTION = {
            "_pont.kml",
            "_vonal.kml",
            "_kerulet.kml",
            "_vonal_pontok.kml",
            "_kerulet_pontok.kml",
            "_EOV_pontok.txt",
            "_WGS84_pontok_foldrajzi.txt",
            "_WGS84_pontok_XYZ.txt",
            "_pontok_IUGG67_XYZ.txt",
            "_pontok_IUGG67_foldrajzi.txt",
            "_tr_pontok_EOV.txt",
            "_tr_pontok_WGS84_foldrajzi.txt",
            "_tr_pontok_WGS84_XYZ.txt",
            "_kozos-pontok_EOV.txt",
            "_kozos-pontok_WGS84_XYZ.txt",
            "_kozos-pontok_WGS84_foldrajzi.txt",
            "_EOV-WGS84_tr_prm.txt",
            "_WGS84-EOV_tr_prm.txt",
            "_kozephibak_EOV.txt",
            "_kozephibak_WGS84.txt"};

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
        addSaveOutputFileOptionPanel();
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
    private void addInputFileOptionPanel(){
        inputFileOptionPanel = new JPanel();
        inputFileOptionPanel.setLayout(new GridLayout(4, 1));
        addTitleForInputFileOptionPanel();
        addRadioButtonForInputFileOptionPanel();
        addComboBoxForInputFileOptionPanel();
        jFrame.add(inputFileOptionPanel);
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

    private void addSaveOutputFileOptionPanel() {
        saveOutputFileOptionPanel = new JPanel();
        saveOutputFileOptionPanel.setLayout(new GridLayout(4, 1));
        addTitleForOutputFileName();
        addFileNameForOutputFile();
        addSaveButtonForOutputFile();
        jFrame.add(saveOutputFileOptionPanel);
    }
    private void addMenu(){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu optionMenu = new JMenu("Opciók");
        optionMenu.setFont(boldFont);
        optionMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenu transformationMenu = new JMenu("2D transzformáció");
        transformationMenu.setFont(boldFont);
        transformationMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem manuallyInputMenuItem = new JMenuItem("Kézi adatbevitel");
        manuallyInputMenuItem.addActionListener(e -> {
            jFrame.setVisible(false);
            if( KMLWrapperController.MANUALLY_INPUT_DATA_WINDOW == null ){
                KMLWrapperController.MANUALLY_INPUT_DATA_WINDOW = new ManuallyInputDataWindow(controller);
            }
            else {
                KMLWrapperController.MANUALLY_INPUT_DATA_WINDOW.jFrame.setVisible(true);
            }
            KMLWrapperController.setWindowTitle();
            if( displayer != null && displayer.jFrame.isVisible() ){
                displayer.getTableModel().setSaveInputPoint();
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
        jMenuBar.add(transformationMenu);
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
        JRadioButton wgsRadioBtn = new JRadioButton("WGS84 koordináták");
        wgsRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(WGS_DATA_TYPE);
            inputDataTypeComboBox.setModel(model);
        });
        wgsRadioBtn.setFont(plainFont);
        wgsRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wgsRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton listRadioBtn = new JRadioButton("AutoCad lista");
        listRadioBtn.addActionListener( e ->{
            String[] cadList = {"AutoCad lista koordináták EOV (Y,X,M)"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            inputDataTypeComboBox.setModel(model);
            controller.openInputDataFile();
           if( FileProcess.FILE_NAME == null ){
               return;
           }
            controller.openAutoCadListDataFile();
        });
        listRadioBtn.setFont(plainFont);
        listRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton kmlRadioBtn = new JRadioButton("kml fájl");
        kmlRadioBtn.addActionListener(e ->{
            String[] cadList = {"kml fájlból WGS84 földrajzi koordináták"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            inputDataTypeComboBox.setModel(model);
            controller.openInputDataFile();
            if( FileProcess.FILE_NAME == null ){
                return;
            }
            controller.openKMLDataFile();
        });
        kmlRadioBtn.setFont(plainFont);
        kmlRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kmlRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        ButtonGroup radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(eovRadioBtn);
        radioBtnGroup.add(wgsRadioBtn);
        radioBtnGroup.add(listRadioBtn);
        radioBtnGroup.add(kmlRadioBtn);
        panel.add(eovRadioBtn);
        panel.add(wgsRadioBtn);
        panel.add(listRadioBtn);
        panel.add(kmlRadioBtn);
        inputFileOptionPanel.add(panel);
    }

    private void addComboBoxForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        inputDataTypeComboBox = new JComboBox<>(EOV_DATA_TYPE);
        inputDataTypeComboBox.addActionListener(e -> {
            String selectedItem = Objects.requireNonNull(inputDataTypeComboBox.getSelectedItem()).toString();
            if( selectedItem.equals(EOV_DATA_TYPE[0]) ){
                inputDataTypeComboBox.setForeground(Color.LIGHT_GRAY);
            }
            else {
                inputDataTypeComboBox.setForeground(Color.BLACK);
                controller.openInputDataFile();
            }
        });
        inputDataTypeComboBox.setPreferredSize(new Dimension(500, 35));
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


    private void addTitleForOutputFileOptionPanel(){
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("Kimeneti fájl beállításainak megadása");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        outputFileOptionPanel.add(panel);
    }

    private void addPointNumberDataForOutputFile(){
        JPanel panel = new JPanel();
        pointPreIdField = new JTextField();
        pointPreIdField.setToolTipText("Pontszám prefix");
        pointPreIdField.setFont(boldFont);
        pointPreIdField.setBackground(new Color(249, 249, 249));
        pointPreIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointPreIdField.setPreferredSize(new Dimension(130, 35));
        pointPreIdField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pointIdField = new JTextField();
        pointIdField.setToolTipText("Pontszám (1)");
        pointIdField.setFont(boldFont);
        pointIdField.setBackground(new Color(249, 249, 249));
        pointIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdField.setPreferredSize(new Dimension(130, 35));
        pointIdField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pointPostIdField = new JTextField();
        pointPostIdField.setToolTipText("Pontszám postfix");
        pointPostIdField.setFont(boldFont);
        pointPostIdField.setBackground(new Color(249, 249, 249));
        pointPostIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointPostIdField.setPreferredSize(new Dimension(130, 35));
        pointPostIdField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(pointPreIdField);
        panel.add(pointIdField);
        panel.add(pointPostIdField);
        outputFileOptionPanel.add(panel);

    }
    private void addRadioButtonForOutputFileOptionPanel(){
        JPanel panel = new JPanel();
        JRadioButton kmlRadioBtn = new JRadioButton("kml fájl");
        kmlRadioBtn.setSelected(true);
        kmlRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(KML_DATA_TYPE);
            outputDataTypeComboBox.setModel(model);
            saveFileNameField.setText(null);
        });
        kmlRadioBtn.setFont(plainFont);
        kmlRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kmlRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton txtRadioBtn = new JRadioButton("txt fájl");
        txtRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(TXT_DATA_TYPE);
            outputDataTypeComboBox.setModel(model);
            saveFileNameField.setText(null);
        });
        txtRadioBtn.setFont(plainFont);
        txtRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton scrRadioBtn = new JRadioButton("scr fájl");
        scrRadioBtn.addActionListener(e ->{
            String[] cadList = {"AutoCad scr fájl (EOV Y, X, M)"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            outputDataTypeComboBox.setModel(model);
            saveFileNameField.setText("_pontok.scr");
            String selectedItem = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
            if( isOkDisplayData(selectedItem) ){
                saveBtn.setEnabled(true);
            };
        });
        scrRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        scrRadioBtn.setFont(plainFont);
        scrRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ButtonGroup radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(kmlRadioBtn);
        radioBtnGroup.add(txtRadioBtn);
        radioBtnGroup.add(scrRadioBtn);
        panel.add(kmlRadioBtn);
        panel.add(txtRadioBtn);
        panel.add(scrRadioBtn);
        outputFileOptionPanel.add(panel);
    }

    private void addComboBoxForOutputFileOptionPanel(){
        JPanel panel = new JPanel();
        outputDataTypeComboBox = new JComboBox<>(KML_DATA_TYPE);
        outputDataTypeComboBox.addActionListener( e -> {
           saveBtn.setEnabled(false);
           String selectedItem = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
            if( selectedItem.equals(TXT_DATA_TYPE[0]) ){
                outputDataTypeComboBox.setForeground(Color.LIGHT_GRAY);
                saveFileNameField.setText(null);
            }
            else  {
                outputDataTypeComboBox.setForeground(Color.BLACK);
                createFileNameForSaveOutputFile();
                if( isOkDisplayData(selectedItem) && controller.setIdForInputDataPoints() ){
                    try{
                        controller.transformationInputPointData();
                        displayer = new DataDisplayerWindow(selectedItem);
                    }
                    catch (IllegalArgumentException a){
                        MessagePane.getInfoMessage(a.getMessage(),
                                "Nem beolvasott adat vagy érvénytelen adattípus választás." ,
                                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                    }
                }
            }
        });
        outputDataTypeComboBox.setPreferredSize(new Dimension(700, 35));
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

    private void addTitleForOutputFileName(){
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("Fájlnév megadása");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        saveOutputFileOptionPanel.add(new JPanel());
        saveOutputFileOptionPanel.add(panel);
    }

    private void addFileNameForOutputFile(){
        JPanel panel = new JPanel();
        saveFileNameField = new JTextField();
        saveFileNameField.setFont(boldFont);
        saveFileNameField.setBackground(new Color(249, 249, 249));
        saveFileNameField.setHorizontalAlignment(SwingConstants.CENTER);
        saveFileNameField.setPreferredSize(new Dimension(700, 35));
        saveFileNameField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(saveFileNameField);
        saveOutputFileOptionPanel.add(panel);
    }

    private void addSaveButtonForOutputFile(){
        JPanel panel = new JPanel();
        saveBtn = new JButton("Adatok mentése");
        saveBtn.addActionListener(e -> {
            if( isOkSavingData() ) {
                String selectedItem = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
                controller.saveDataFile(selectedItem);
            }
        });
        saveBtn.setFont(boldFont);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setEnabled(false);
        panel.add(saveBtn);
        saveOutputFileOptionPanel.add(panel);
    }

    private boolean isOkDisplayData(String selectedItem){

        if (selectedItem.equals(TXT_DATA_TYPE[0])) {
            MessagePane.getInfoMessage("Érvénytelen adattípus",
                    "Adattípus választása szükséges.", jFrame);
            return false;
        } else if( KMLWrapperController.INPUT_POINTS.isEmpty() ) {
            MessagePane.getInfoMessage("Nem található adat",
                    "Hozzáadott pont nem található.", jFrame);
            return false;
        }

        return true;
    }

    private boolean isOkSavingData(){
        String selectedItem = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
        if( selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[0])){
            MessagePane.getInfoMessage("Mentés nem hajtható végre",
                    "Adattípus választása szükséges.", jFrame);
            return false;
        }
       else if( (selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[2]) ||
                 selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[4])) &&
                displayer != null && displayer.getTableModel().getHowManyInputPointSaved() < 2 ){
                MessagePane.getInfoMessage("Mentés nem hajtható végre",
                    "Vonal mentéséhez legalább két menteni kívánt pont szükséges.", jFrame);
            return false;
        }
        else if( (selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[3]) ||
                 selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[5])) &&
                displayer != null && displayer.getTableModel().getHowManyInputPointSaved() < 3 ){
            MessagePane.getInfoMessage("Mentés nem hajtható végre",
                    "Kerület mentéséhez legalább három menteni kívánt pont szükséges.", jFrame);
            return false;
        }
        return true;
    }

    private void createFileNameForSaveOutputFile(){
        String selectedOption = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
       if( KML_DATA_TYPE[1].equals(selectedOption) ){
           saveFileNameField.setText(FILE_NAME_OPTION[0]);
       }
       else if( KML_DATA_TYPE[2].equals(selectedOption) ){
            saveFileNameField.setText(FILE_NAME_OPTION[1]);
        }
       else if( KML_DATA_TYPE[3].equals(selectedOption) ){
            saveFileNameField.setText(FILE_NAME_OPTION[2]);
        }
       else if( KML_DATA_TYPE[4].equals(selectedOption) ){
            saveFileNameField.setText(FILE_NAME_OPTION[3]);
        }
       else if( KML_DATA_TYPE[5].equals(selectedOption) ){
            saveFileNameField.setText(FILE_NAME_OPTION[4]);
        }
       else if( TXT_DATA_TYPE[1].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[5]);
       }
       else if( TXT_DATA_TYPE[2].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[6]);
       }
       else if( TXT_DATA_TYPE[3].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[7]);
       }
       else if( TXT_DATA_TYPE[4].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[8]);
       }
       else if( TXT_DATA_TYPE[5].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[9]);
       }
       else if( TXT_DATA_TYPE[6].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[10]);
       }
       else if( TXT_DATA_TYPE[7].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[11]);
       }
       else if( TXT_DATA_TYPE[8].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[12]);
       }
       else if( TXT_DATA_TYPE[9].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[13]);
       }
       else if( TXT_DATA_TYPE[10].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[14]);
       }
       else if( TXT_DATA_TYPE[11].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[15]);
       }
       else if( TXT_DATA_TYPE[12].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[16]);
       }
       else if( TXT_DATA_TYPE[13].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[17]);
       }
       else if( TXT_DATA_TYPE[14].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[18]);
       }
       else if( TXT_DATA_TYPE[15].equals(selectedOption)){
           saveFileNameField.setText(FILE_NAME_OPTION[19]);
       }
    }

    public String getOutputFileName() {
        String selectedOption = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
        String addedFileNameByUser = saveFileNameField.getText();
        String fileName = null;
        if (selectedOption.equals(KML_DATA_TYPE[1])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[0];
            } else if (addedFileNameByUser.endsWith(".kml")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[0];
            }
        } else if (selectedOption.equals(KML_DATA_TYPE[2])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[1];
            } else if (addedFileNameByUser.endsWith(".kml")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[1];
            }
        } else if (selectedOption.equals(KML_DATA_TYPE[3])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[2];
            } else if (addedFileNameByUser.endsWith(".kml")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[2];
            }
        } else if (selectedOption.equals(KML_DATA_TYPE[4])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[3];
            } else if (addedFileNameByUser.endsWith(".kml")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[3];
            }
        } else if (selectedOption.equals(KML_DATA_TYPE[5])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[4];
            } else if (addedFileNameByUser.endsWith(".kml")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[4];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[1])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[5];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[5];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[2])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[6];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[6];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[3])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[7];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[7];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[4])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[8];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[8];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[5])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[9];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[9];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[6])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[10];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[10];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[7])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[11];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[11];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[8])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[12];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[12];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[9])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[13];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[13];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[10])) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[14];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[14];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[11])) {

            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[15];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[15];
            }
        } else if (selectedOption.equals(TXT_DATA_TYPE[12])) {

        if (addedFileNameByUser.isEmpty()) {
            fileName = FILE_NAME_OPTION[16];
        } else if (addedFileNameByUser.endsWith(".txt")) {
            fileName = addedFileNameByUser;
        } else {
            fileName = addedFileNameByUser + FILE_NAME_OPTION[16];
        }

    } else if( selectedOption.equals(TXT_DATA_TYPE[13]) ){

            if( addedFileNameByUser.isEmpty() ){
                fileName = FILE_NAME_OPTION[17];
            }
            else if( addedFileNameByUser.endsWith(".txt") ){
                fileName = addedFileNameByUser;
            }
            else{
                fileName = addedFileNameByUser + FILE_NAME_OPTION[17];
            }
        } else if( selectedOption.equals(TXT_DATA_TYPE[14]) ){
            if( addedFileNameByUser.isEmpty() ){
                fileName = FILE_NAME_OPTION[18];
            }
            else if( addedFileNameByUser.endsWith(".txt") ){
                fileName = addedFileNameByUser;
            }
            else{
                fileName = addedFileNameByUser + FILE_NAME_OPTION[18];
            }
        } else if( selectedOption.equals(TXT_DATA_TYPE[15]) ){
            if( addedFileNameByUser.isEmpty() ){
                fileName = FILE_NAME_OPTION[19];
            }
            else if( addedFileNameByUser.endsWith(".txt") ){
                fileName = addedFileNameByUser;
            }
            else{
                fileName = addedFileNameByUser + FILE_NAME_OPTION[19];
            }
        }
        else if( selectedOption.startsWith("AutoCad") ){

            if( addedFileNameByUser.isEmpty() ){
                fileName = "_pontok.scr";
            }
            else if( addedFileNameByUser.endsWith(".scr") ){
                fileName = addedFileNameByUser;
            }
            else{
                fileName = addedFileNameByUser + "_pontok.scr";
            }
        }

        return fileName;
    }

}
