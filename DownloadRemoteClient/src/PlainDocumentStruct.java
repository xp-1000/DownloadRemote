import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class PlainDocumentStruct extends PlainDocument {
  private int tailleMax = -1;
 
  public PlainDocumentStruct() {
    this(-1);
  }
 
  public PlainDocumentStruct(int tailleMax) {
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
