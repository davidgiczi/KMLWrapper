package hu.david.giczi.mvmxpert.wrapper.view;

import javax.swing.*;
import java.awt.*;

public class MessagePane {
    private static final Font boldFont = new Font("Roboto", Font.BOLD, 14);

    public static void getInfoMessage(String title, String message, JFrame frame) {
        JOptionPane.showMessageDialog(frame, "<html><h3>" + message + "</h3></html>", title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int getYesNoOptionMessage(String title, String message, JFrame frame) {
        Object[] options = {"<html><b style=font-size:11px>Igen</b></html>",
                "<html><b style=font-size:11px>Nem</b></html>"};
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(boldFont);
        JLabel titleLabel = new JLabel(title);
        messageLabel.setFont(boldFont);
        return JOptionPane.showOptionDialog(frame, messageLabel, titleLabel.getText(),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) ;
    }

    public static String getInputDataMessage(JFrame jFrame, String initData){
        JLabel messageLabel = new JLabel("Elválasztó karakter:");
        messageLabel.setFont(boldFont);
                JLabel titleLabel = new JLabel("Elválasztó hozzáadása");
        return (String) JOptionPane.showInputDialog(jFrame, messageLabel, titleLabel.getText(),
                JOptionPane.QUESTION_MESSAGE, null, null, initData);
    }

    public static String getWhatTextReplaceMessage(JFrame jFrame){
        JLabel messageLabel = new JLabel("Melyik szövegrészt cseréli:");
        messageLabel.setFont(boldFont);
        JLabel titleLabel = new JLabel("Karakterek cseréje");
        return (String) JOptionPane.showInputDialog(jFrame, messageLabel, titleLabel.getText(),
                JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    public static String getWhichTextReplaceWithMessage(JFrame jFrame){
        JLabel messageLabel = new JLabel("Milyen szövegrészre cseréli:");
        messageLabel.setFont(boldFont);
        JLabel titleLabel = new JLabel("Karakterek cseréje");
        return (String) JOptionPane.showInputDialog(jFrame, messageLabel, titleLabel.getText(),
                JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    public static String getFileNameMessage(JFrame jFrame){
        JLabel messageLabel = new JLabel("Fájlnév megadása:");
        messageLabel.setFont(boldFont);
        JLabel titleLabel = new JLabel("Fájl mentése");
        return (String) JOptionPane.showInputDialog(jFrame, messageLabel, titleLabel.getText(),
                JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    public static String setPointIdMessage(String title, JFrame jFrame){
        JLabel messageLabel = new JLabel("Új pontszám megadása:");
        messageLabel.setFont(boldFont);
        JLabel titleLabel = new JLabel(title);
        return (String) JOptionPane.showInputDialog(jFrame, messageLabel, titleLabel.getText(),
                JOptionPane.QUESTION_MESSAGE, null, null, null);
    }
}
