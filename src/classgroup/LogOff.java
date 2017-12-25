package classgroup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author java
 */
import java.io.IOException;

public class LogOff{
	/*public static final String SHOWDIALOG="-i";
	public static final String RESTART ="-r";
	public static final String SHUTDOWN="-s";
	public static final String LOGOFF = "-l";*/

public static void controlcomputer(final String args)throws IOException{

final String [] command = {"SHUTDOWN.EXE",args};

Runtime.getRuntime().exec(command);
}

/*public static void main(String [] args)
{
try{
	logoffWindowsXP(SHUTDOWN);
}catch(IOException e)
{
System.err.println("Logoff command failed"+e);
}}*/
}
    

