/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author java lok
 */
public class JavaValidation extends javax.swing.JFrame {

    private int status_count;

    public boolean checkEmptyTextField(JTextField... args) {
        List<Boolean> array = new ArrayList<Boolean>();
        for (JTextField arg : args) {
            if (arg.getText().equals("")) {
                array.add(false);
            } else {
                array.add(true);
            }
        }
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == false) {
                status_count++;

            }
        }

        if (status_count > 0) {
            return false;
        }
        return true;
    }

    //method to chekc not selected JComboBox
    public boolean checkNotSelectedComboBox(JComboBox... args) {
        List<Boolean> array = new ArrayList<Boolean>();
        for (JComboBox arg : args) {
            if (arg.getSelectedIndex() == 0) {
                array.add(false);
            } else {
                array.add(true);
            }
        }
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == false) {
                status_count++;
            }
        }

        if (status_count > 0) {
            return false;
        }
        return true;
    }

    //method for decimal fomat
    public double getDecimalFormat(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        double newValue = Double.parseDouble(decimalFormat.format(value));
        return newValue;
    }

    //method for date
    public void getDateValue(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_MINUS) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_BACK_SPACE))) {
            this.getToolkit().beep();
            evt.consume();
        }
    }

    //method to get number and character only from the textfield
    public void getNumberCharacterValue(KeyEvent evt){
          char c = evt.getKeyChar();
        if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) ||
                  (Character.isLetter(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ||
                  (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_SHIFT)))){

            this.getToolkit().beep();
            evt.consume();
        }
    }

    //method to get number only from the textfield
    public void getNumberValue(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
            this.getToolkit().beep();
            evt.consume();
        }
    }


    //method to get character only form the textfield
    public void getCharacterValue(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isLetter(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_SHIFT))) {
            this.getToolkit().beep();
            evt.consume();
        }

    }

    //this method sould accept decimal value but vk_period shoud be trigger once or not but not more than one ex: 45.00 not like: 23.00.22
    public void getDoubleValue(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || ((c == KeyEvent.VK_PERIOD)))) {
            this.getToolkit().beep();
            evt.consume();
        }
    }

    //method to validate Email Id
    public static boolean getEmailValidation(String emailId) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(emailId);
        return m.matches();
    }
}
