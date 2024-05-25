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
}
