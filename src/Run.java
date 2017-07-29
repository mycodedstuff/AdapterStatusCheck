
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aman
 */
public final class Run{

    /**
     *
     */
    private static int status = 1;
    
    public static void setStatus(){
        status = 1;
    }
    
    public static void clearStatus(){
        status = 0;
    }
    
    public static boolean execute(){
        while(true){
            try {
                FileOutputStream f = new FileOutputStream("./Data/report.txt");
                f.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                try{
                    Process exec = Runtime.getRuntime().exec("cmd /c WMIC Path Win32_Battery Get BatteryStatus>>./Data/report.txt");
                    Thread.sleep(500);
                    exec.destroy();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                }
                String s="";
                FileInputStream fis = new FileInputStream("./Data/report.txt");
                int i;
                while((i = fis.read()) != -1){
                    s+=(char)i;
                }
                fis.close();
                i = s.indexOf("1");
                if(i != -1){
                    if(!Main.isWarningActive()){
                        Main.showWarning();
                        Thread.sleep(700);
                    }
                }else{
                    if(Main.isWarningActive()){
                        Main.disposeWarning();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(300);
                if(status == 0){
                    File file = new File("./Data/report.txt");
                    return file.delete();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
