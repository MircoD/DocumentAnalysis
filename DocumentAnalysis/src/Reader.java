import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.rtf.RTFEditorKit;

/**
 * Reads the .rtf and creates the Review objects.
 * 
 */
public class Reader {

	public static void main(String[] args) {

		File file = new File("E:/Downloads/new.rtf");
		RTFEditorKit kit= new RTFEditorKit();
		PlainDocument doc = new PlainDocument();

		try {
			 FileInputStream fi = new FileInputStream(file);
			    kit.read(fi, doc, 0);

		} catch (IOException e) {
			System.out.println("I/O error");
		} catch (BadLocationException e){
			System.out.println("Location error");
		}
	}
}
