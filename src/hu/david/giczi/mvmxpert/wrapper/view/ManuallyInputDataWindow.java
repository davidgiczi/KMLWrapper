package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManuallyInputDataWindow {
    public JFrame jFrame;
    private final KMLWrapperController controller;
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);

    public ManuallyInputDataWindow(KMLWrapperController controller) {
        this.controller = controller;
        createWindow();
    }

    private void createWindow(){
        jFrame = new JFrame("Kézi adatbevitel");
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
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
