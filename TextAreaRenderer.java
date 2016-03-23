
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ttran
 */
public class TextAreaRenderer extends JTextArea
    implements TableCellRenderer {

  public TextAreaRenderer() {
    setLineWrap(true);
    //setWrapStyleWord(true);
      setAlignmentY(CENTER_ALIGNMENT);
      setAlignmentX(CENTER_ALIGNMENT);
      
  }

  public Component getTableCellRendererComponent(JTable jTable,
      Object obj, boolean isSelected, boolean hasFocus, int row,
      int column) {
    setText((String)obj);
    return this;
  }

//    @Override
//    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
//      return null;
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
