import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class PlainDocumentMajuscule extends PlainDocument {
  private int tailleMax = -1;
 
  public PlainDocumentMajuscule() {
    this(-1);
  }
 
  public PlainDocumentMajuscule(int tailleMax) {
    this.tailleMax = tailleMax;
  }
 
  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
	  
    if (tailleMax > -1 && this.getLength() == tailleMax)
      return;
    
    if (tailleMax > -1 && (str.length() + this.getLength()) > tailleMax) {
      str = str.substring(0, tailleMax - this.getLength());
    }
    super.insertString(offs, str, a);
  }
}
