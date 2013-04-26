import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public abstract class SQLDialog extends JFrame{
	
	protected JComboBox<String> tables;
	protected ArrayList<String> tableNames;
	protected ArrayList<String> names;
	protected ArrayList<String> types;
	private JButton goButton;
	
	public SQLDialog(){
		tableNames = Core.core.getTableNames();
		tables = new JComboBox<String>(new Vector<String>(tableNames));
		names = Core.core.getAttributeNames(tableNames.get(0));
		types = Core.core.getDataTypes(tableNames.get(0));
		
		goButton = new JButton("Go");
		LinkedActionListener goListener = new LinkedActionListener(this);
		goButton.addActionListener(goListener);
		goButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		this.setLayout(new BorderLayout());
		this.add(goButton, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setActionListener(ActionListener al){
		goButton.addActionListener(al);
	}
	
	public void finalize(){
		this.pack();
		this.setVisible(true);
	}
	
	public void close(){
		this.dispose();
	}
}
