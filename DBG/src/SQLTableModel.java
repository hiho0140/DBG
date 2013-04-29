import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SQLTableModel extends DefaultTableModel{

	public SQLTableModel(){
		super();
	}
	
	public SQLTableModel(Vector<?> data, Vector<?> columnNames){
		super(data, columnNames);
	}
	
	public void setValueAt(Object aValue, int row, int column){
		try {
			Core.core.setValueAt(aValue, row, column);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
}
