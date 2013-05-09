/*
 * SQLTableModel.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 */

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
	
	
}
