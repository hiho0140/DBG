import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public abstract class SQLDialog extends JPanel{
	
	protected JComboBox tables;
	protected ArrayList<String> tableNames;
	protected ArrayList<String> niceNames;
	protected ArrayList<String> attribNames;
	protected ArrayList<Integer> attribTypes;
	protected String curTable, title;
	private JButton goButton;
	private SQLFrame parent;
	
	public SQLDialog(boolean includeViews, SQLFrame p){
		parent = p;
		try {
			tableNames = Core.core.getTableNames(includeViews);
		} catch (SQLException e) {
			tableNames = new ArrayList<String>();
		}
		
		tables = new JComboBox(new Vector<String>(tableNames));
		LinkedActionListener comboListener = new LinkedActionListener(this, LinkedActionListener.COMBOBOX);
		tables.addActionListener(comboListener);
		curTable = tableNames.get(tables.getSelectedIndex());
		
		goButton = new JButton("Go");
		LinkedActionListener goListener = new LinkedActionListener(this, LinkedActionListener.CLOSEBUTTON);
		goButton.addActionListener(goListener);
		goButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		this.setLayout(new BorderLayout());
		this.add(goButton, BorderLayout.SOUTH);
	}
	
	public SQLDialog(ArrayList<String> n, ArrayList<String> nn, String tn, SQLFrame p){
		parent = p;
		curTable = tn;
		attribNames = n;
		niceNames = nn;
		
		goButton = new JButton("Go");
		LinkedActionListener goListener = new LinkedActionListener(this, LinkedActionListener.CLOSEBUTTON);
		goButton.addActionListener(goListener);
		goButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		this.setLayout(new BorderLayout());
		this.add(goButton, BorderLayout.SOUTH);
	}
	
	public void updateAttribData(){
		try {
			attribNames = Core.core.getAttributeNames(curTable);
			attribTypes = Core.core.getDataTypes(curTable);
		} catch (SQLException e) {
			attribNames = new ArrayList<String>();
			attribTypes = new ArrayList<Integer>();
		}
	}
	
	public void updateQueryPanels(){
		curTable = tableNames.get(tables.getSelectedIndex());
		updateAttribData();
	}
	
	public String getTitle(){
		return title;
	}
	
	public abstract Query getQuery();
	
	public void finalize(){
		this.setVisible(true);
		parent.finalize();
	}
	
	public void close(){
		Core.core.runDialogQuery(getQuery(), curTable);
		if(getQuery().getType() != Query.SELECT){
			Core.core.runDialogQuery("SELECT * from " + curTable + ";", curTable);
		}
		parent.dispose();
	}
	
}
