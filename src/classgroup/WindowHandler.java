/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classgroup;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author JA
 */
public class WindowHandler extends JFrame{
    
    public void closeWindow() {
                WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
        }
    
  
   
    

}
