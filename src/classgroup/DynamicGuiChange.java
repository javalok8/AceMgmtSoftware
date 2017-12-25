/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author JA
 */
public class DynamicGuiChange{
    
    public void GuiChange(String GuiTypeName){
        //
        try{
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
                if(GuiTypeName.equalsIgnoreCase(info.getName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.put("nimbusBase", new Color(205,205,255));
                    javax.swing.UIManager.put("nimbusBlueGrey", new Color(205,205,255));
                    javax.swing.UIManager.put("control", new Color(205,205,255));
                    break;
                }
            }
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            JOptionPane.showMessageDialog(null,"Exception Lok: "+cnfe.getMessage(),"Exception Caught!",JOptionPane.WARNING_MESSAGE);
        }
        catch(javax.swing.UnsupportedLookAndFeelException ulafe){
            ulafe.printStackTrace();
            JOptionPane.showMessageDialog(null,"Exception Lok: "+ulafe.getMessage(),"Exception Caught!",JOptionPane.WARNING_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
    
}
