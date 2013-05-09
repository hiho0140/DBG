/*
 * ViewPanel.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * Used by Core as the interface for quick functions called on
 * views. Views may not be edited, and typically have composite
 * primary keys, so a table view is used instead of text fields.
 * The ResultBar instance is populated with all unique values for
 * a specified key, such as (first_name, last_name). Selecting
 * a single item within the ResultBar's list would cause the
 * ViewPanel's table to display all entries in the given view
 * that matched those conditions.
 */

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ViewPanel extends JPanel {

	private JTable viewTable;
	private DefaultTableModel model;
	private JScrollPane viewScroller;
	private ArrayList<String> attribNames;
	private ArrayList<String> groupValues;
	private ResultBar resultBar;
	private String myView;
	private String[] groupBy;
	

	public ViewPanel(){
		resultBar = new ResultBar(ResultBarSelectionListener.VIEW);
		model = new DefaultTableModel();
		viewTable = new JTable(model);
		viewTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		viewScroller = new JScrollPane(viewTable);
		
		this.setLayout(new BorderLayout());
		this.add(resultBar, BorderLayout.EAST);
		this.add(viewScroller, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public void generateList(String vn, String gb){
		myView = vn;
		groupBy = new String[1];
		groupBy[0] = gb;
		attribNames = Core.core.getAttributeNames();
		groupValues = Core.core.getViewList(groupBy[0]);
		resultBar.setData(new Vector<String>(groupValues), groupBy);
	}
	
	public void generateList(String vn, String[] gb){
		myView = vn;
		groupBy = gb;
		attribNames = Core.core.getAttributeNames();
		groupValues = Core.core.getViewList(groupBy[0]);
		resultBar.setData(new Vector<String>(groupValues), groupBy);
	}
	
	public void populateTable(Vector<Vector<String>> data){
		model.setDataVector(data, new Vector<String>(Core.core.getNiceNames(myView, attribNames)));
	}
	
	public String getGroupName(){
		return groupBy[0];
	}
	
	public String getGroupValue(int index){
		
		return groupValues.get(index);
	}
	
}
