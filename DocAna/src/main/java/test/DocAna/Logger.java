package test.DocAna;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * For test purposes.
 * Stolen from the Internet
 * 
 */
public class Logger {
    public static void log(String message, String name) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(name + ".txt", true), true);
            out.write(message + System.lineSeparator());
            out.close();
        } catch ( IOException e){
        }
    }

}