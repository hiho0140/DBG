import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public abstract class SQLDialog extends JFrame{
	
	protected JComboBox<String> tables;
	protected ArrayList<String> tableNames;
	protected ArrayList<String> attribNames;
	protected ArrayList<String> attribTypes;
	private JButton goButton;
	
	public SQLDialog(){
		try {
			tableNames = Core.core.getTableNames();
		} catch (SQLException e) {
			tableNames = new ArrayList<String>();
		}
		tables = new JComboBox<String>(new Vector<String>(tableNames));
		LinkedActionListener comboListener = new LinkedActionListener(this, LinkedActionListener.COMBOBOX);
		tables.addActionListener(comboListener);
		
		goButton = new JButton("Go");
		LinkedActionListener goListener = new LinkedActionListener(this, LinkedActionListener.BUTTON);
		goButton.addActionListener(goListener);
		goButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		this.setLayout(new BorderLayout());
		this.add(goButton, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void updateAttribData(){
		try {
			attribNames = Core.core.getAttributeNames(tableNames.get(tables.getSelectedIndex()));
			attribTypes = Core.core.getDataTypes(tableNames.get(tables.getSelectedIndex()));
		} catch (SQLException e) {
			attribNames = new ArrayList<String>();
			attribTypes = new ArrayList<String>();
		}
	}
	
	public abstract void updateQueryPanels();
	
	public void finalize(){
		this.pack();
		this.setVisible(true);
	}
	
	public void close(){
		this.dispose();
	}
}
