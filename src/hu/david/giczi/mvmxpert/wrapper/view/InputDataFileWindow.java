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
    private static final String INVALID_CHARACTERS = "[\\\\/:*?\"<>|]";
    public static final String[] EOV_DATA_TYPE = {
            "Form�tum v�laszt�sa",
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
            "Form�tum v�laszt�sa",
            "WGS84 (Psz,Sz�less�g,Hossz�s�g,Magass�g)",
            "WGS84 (Psz Sz�less�g Hossz�s�g Magass�g)",
            "WGS84 (Psz;Sz�less�g;Hossz�s�g;Magass�g)",
            "WGS84 (Psz,Hossz�s�g,Sz�less�g,Magass�g)",
            "WGS84 (Psz Hossz�s�g Sz�less�g Magass�g)",
            "WGS84 (Psz;Hossz�s�g;Sz�less�g;Magass�g)",
            "WGS84 (Psz,X,Y,Z)",
            "WGS84 (Psz X Y Z)",
            "WGS84 (Psz;X;Y;Z)",
            "WGS84 (Sz�less�g,Hossz�s�g,Magass�g)",
            "WGS84 (Sz�less�g Hossz�s�g Magass�g)",
            "WGS84 (Sz�less�g;Hossz�s�g;Magass�g)",
            "WGS84 (Hossz�s�g,Sz�less�g,Magass�g)",
            "WGS84 (Hossz�s�g Sz�less�g Magass�g)",
            "WGS84 (Hossz�s�g;Sz�less�g;Magass�g)",
            "WGS84 (X,Y,Z)",
            "WGS84 (X Y Z)",
            "WGS84 (X;Y;Z)"
    };

    public static final String[] KML_DATA_TYPE = {
            "Adatt�pus v�laszt�sa",
            "Pont.kml",
            "Vonal.kml",
            "Ker�let.kml",
            "Vonal+pontok.kml",
            "Ker�let+pontok.kml",
            "Ter�let �s t�vols�g sz�m�t�sa"};
    public static final String[] TXT_DATA_TYPE = {
            "Adatt�pus v�laszt�sa",
            "Beolvasott pontok: EOV (Y, X, M)",
            "Beolvasott pontok: WGS84 (Sz�less�g, Hossz�s�g, Magass�g)",
            "Beolvasott pontok: WGS84 (X, Y, Z)",
            "Beolvasott pontok: IUGG67 (X, Y, Z)",
            "Beolvasott pontok: IUGG67 (Sz�less�g, Hossz�s�g, Magass�g)",
            "Transzform�lt pontok: WGS84-EOV (Y, X, M)",
            "Transzform�lt pontok: EOV-WGS84 (Sz�less�g, Hossz�s�g, Magass�g)",
            "Transzform�lt pontok: EOV-WGS84 (X, Y, Z)",
            "K�z�s pontok: WGS84-EOV (Y, X, M)",
            "K�z�s pontok: EOV-WGS84 (X, Y, Z)",
            "K�z�s pontok: EOV-WGS84 (Sz�less�g, Hossz�s�g, Magass�g)",
            "Transzform�ci� param�terei: EOV-WGS84",
            "Transzform�ci� param�terei: WGS84-EOV",
            "Marad�k ellentmond�sok: EOV-WGS84 (dX, dY, dZ)",
            "Marad�k ellentmond�sok: WGS84-EOV (dY, dX, dM)"};

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
            "_kozephibak_WGS84.txt",
            "_kozephibak_EOV.txt",
            "_ter�let_t�vols�g.txt"};

    public InputDataFileWindow(KMLWrapperController controller) {
        this.controller = controller;
        createWindow();
    }
    private void createWindow(){
        jFrame = new JFrame("Adatok f�jlb�l beolvas�sa");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
               if( MessagePane.getYesNoOptionMessage("A program bez�r�sa",
                       "Kil�p a programb�l?", jFrame) == 0 ){
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
        JMenu optionMenu = new JMenu("Opci�k");
        optionMenu.setFont(boldFont);
        optionMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem transformationMenuItem = new JMenuItem("2D transzform�ci�");
        transformationMenuItem.addActionListener( e -> {
            jFrame.setVisible(false);
            if( KMLWrapperController.TRANSFORMATION_2D_WINDOW == null ){
                KMLWrapperController.TRANSFORMATION_2D_WINDOW = new Transformation2DWindow(controller);
            }
            else {
                KMLWrapperController.TRANSFORMATION_2D_WINDOW.jFrame.setVisible(true);
            }

        });
        transformationMenuItem.setFont(plainFont);
        transformationMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem manuallyInputMenuItem = new JMenuItem("K�zi adatbevitel");
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
        JMenuItem exitProgramMenuItem = new JMenuItem("Kil�p�s a programb�l");
        exitProgramMenuItem.addActionListener(e -> {
            if( MessagePane.getYesNoOptionMessage("A program bez�r�sa",
                    "Kil�p a programb�l?", jFrame) == 0 ){
                System.exit(0);
            }
        });
        exitProgramMenuItem.setFont(plainFont);
        exitProgramMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optionMenu.add(manuallyInputMenuItem);
        optionMenu.add(transformationMenuItem);
        optionMenu.addSeparator();
        optionMenu.add(exitProgramMenuItem);
        jMenuBar.add(optionMenu);
        jFrame.setJMenuBar(jMenuBar);
    }


    private void addTitleForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        JLabel contentTitleLabel = new JLabel("Bemeneti f�jl be�ll�t�sainak megad�sa");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        inputFileOptionPanel.add(panel);
    }

    private void addRadioButtonForInputFileOptionPanel(){
        JPanel panel = new JPanel();
        JRadioButton eovRadioBtn = new JRadioButton("EOV koordin�t�k");
        eovRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(EOV_DATA_TYPE);
            inputDataTypeComboBox.setModel(model);
        });
        eovRadioBtn.setFont(plainFont);
        eovRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eovRadioBtn.setSelected(true);
        eovRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton wgsRadioBtn = new JRadioButton("WGS84 koordin�t�k");
        wgsRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(WGS_DATA_TYPE);
            inputDataTypeComboBox.setModel(model);
        });
        wgsRadioBtn.setFont(plainFont);
        wgsRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wgsRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton listRadioBtn = new JRadioButton("AutoCad lista");
        listRadioBtn.addActionListener( e ->{
            String[] cadList = {"AutoCad lista koordin�t�k EOV (Y,X,M)"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            inputDataTypeComboBox.setModel(model);
            inputDataTypeComboBox.setForeground(Color.BLACK);
            controller.openInputDataFile();
           if( FileProcess.FILE_NAME == null ){
               return;
           }
            controller.openAutoCadListDataFile();
        });
        listRadioBtn.setFont(plainFont);
        listRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton kmlRadioBtn = new JRadioButton("kml f�jl");
        kmlRadioBtn.addActionListener(e ->{
            String[] cadList = {"kml f�jlb�l WGS84 f�ldrajzi koordin�t�k"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            inputDataTypeComboBox.setModel(model);
            inputDataTypeComboBox.setForeground(Color.BLACK);
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
        JLabel contentTitleLabel = new JLabel("Kimeneti f�jl be�ll�t�sainak megad�sa");
        contentTitleLabel.setFont(boldFont);
        contentTitleLabel.setBorder(new EmptyBorder(10,0,0,0));
        panel.add(contentTitleLabel);
        outputFileOptionPanel.add(panel);
    }

    private void addPointNumberDataForOutputFile(){
        JPanel panel = new JPanel();
        pointPreIdField = new JTextField();
        pointPreIdField.setToolTipText("Pontsz�m prefix");
        pointPreIdField.setFont(boldFont);
        pointPreIdField.setBackground(new Color(249, 249, 249));
        pointPreIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointPreIdField.setPreferredSize(new Dimension(130, 35));
        pointPreIdField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pointIdField = new JTextField();
        pointIdField.setToolTipText("Pontsz�m (1)");
        pointIdField.setFont(boldFont);
        pointIdField.setBackground(new Color(249, 249, 249));
        pointIdField.setHorizontalAlignment(SwingConstants.CENTER);
        pointIdField.setPreferredSize(new Dimension(130, 35));
        pointIdField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pointPostIdField = new JTextField();
        pointPostIdField.setToolTipText("Pontsz�m postfix");
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
        JRadioButton kmlRadioBtn = new JRadioButton("kml f�jl");
        kmlRadioBtn.setSelected(true);
        kmlRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(KML_DATA_TYPE);
            outputDataTypeComboBox.setModel(model);
            saveFileNameField.setText(null);
        });
        kmlRadioBtn.setFont(plainFont);
        kmlRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kmlRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton txtRadioBtn = new JRadioButton("txt f�jl");
        txtRadioBtn.addActionListener(e -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(TXT_DATA_TYPE);
            outputDataTypeComboBox.setModel(model);
            saveFileNameField.setText(null);
        });
        txtRadioBtn.setFont(plainFont);
        txtRadioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtRadioBtn.setBorder(new EmptyBorder(10,50,10,50));
        JRadioButton scrRadioBtn = new JRadioButton("scr f�jl");
        scrRadioBtn.addActionListener(e ->{
            String[] cadList = {"AutoCad scr f�jl (EOV Y, X, M)"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cadList);
            outputDataTypeComboBox.setModel(model);
            createFileNameForSaveOutputFile();
            outputDataTypeComboBox.setForeground(Color.BLACK);
            saveBtn.setEnabled(false);
            createFileNameForSaveOutputFile();
            if( isOkDisplayData("AutoCad") && controller.setIdForInputDataPoints("AutoCad") ){
                try{
                    controller.transformationInputPointData();
                    displayer = new DataDisplayerWindow("AutoCad scr f�jl", controller);
                }
                catch (IllegalArgumentException a){
                    MessagePane.getInfoMessage(a.getMessage(),
                            "Nem beolvasott adat vagy �rv�nytelen adatt�pus v�laszt�s." ,
                            KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                }
            }
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
                if( isOkDisplayData(selectedItem) && controller.setIdForInputDataPoints(selectedItem) ){
                    try{
                        controller.transformationInputPointData();
                        displayer = new DataDisplayerWindow(selectedItem,  controller);
                    }
                    catch (IllegalArgumentException a){
                        MessagePane.getInfoMessage(a.getMessage(),
                                "Nem beolvasott adat vagy �rv�nytelen adatt�pus v�laszt�s." ,
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
        JLabel contentTitleLabel = new JLabel("F�jln�v megad�sa");
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
        saveBtn = new JButton("Adatok ment�se");
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
            MessagePane.getInfoMessage("�rv�nytelen adatt�pus",
                    "Adatt�pus v�laszt�sa sz�ks�ges.", jFrame);
            return false;
        } else if( KMLWrapperController.INPUT_POINTS.isEmpty() ) {
            MessagePane.getInfoMessage("Nem tal�lhat� adat",
                    "Hozz�adott pont nem tal�lhat�.", jFrame);
            return false;
        }

        return true;
    }

    private boolean isOkSavingData(){
        String selectedItem = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
        if( selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[0])){
            MessagePane.getInfoMessage("Ment�s nem hajthat� v�gre",
                    "Adatt�pus v�laszt�sa sz�ks�ges.", jFrame);
            return false;
        }
       else if( (selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[2]) ||
                 selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[4])) &&
                displayer != null && displayer.getTableModel().getHowManyInputPointSaved() < 2 ){
                MessagePane.getInfoMessage("Ment�s nem hajthat� v�gre",
                    "Vonal ment�s�hez legal�bb k�t menteni k�v�nt pont sz�ks�ges.", jFrame);
            return false;
        }
        else if( (selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[3]) ||
                 selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[5])) &&
                displayer != null && displayer.getTableModel().getHowManyInputPointSaved() < 3 ){
            MessagePane.getInfoMessage("Ment�s nem hajthat� v�gre",
                    "Ker�let ment�s�hez legal�bb h�rom menteni k�v�nt pont sz�ks�ges.", jFrame);
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
       else if( selectedOption.startsWith("AutoCad") ){
          saveFileNameField.setText("_pontok.scr");
       }
       else if( KML_DATA_TYPE[6].equals(selectedOption) ){
           saveFileNameField.setText(FILE_NAME_OPTION[20]);
       }
    }

    public String getOutputFileName() {
        String selectedOption = Objects.requireNonNull(outputDataTypeComboBox.getSelectedItem()).toString();
        String addedFileNameByUser = saveFileNameField.getText().trim().replaceAll(INVALID_CHARACTERS, "_");
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
        }
        else if (selectedOption.equals(TXT_DATA_TYPE[1])) {
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
            else {
                fileName = addedFileNameByUser + "_pontok.scr";
            }
        }
        else if( selectedOption.equals(KML_DATA_TYPE[6]) ) {
            if (addedFileNameByUser.isEmpty()) {
                fileName = FILE_NAME_OPTION[20];
            } else if (addedFileNameByUser.endsWith(".txt")) {
                fileName = addedFileNameByUser;
            } else {
                fileName = addedFileNameByUser + FILE_NAME_OPTION[20];
            }
        }

        return fileName;
    }

}
