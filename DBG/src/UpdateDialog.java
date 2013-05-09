/*
 * UpdateDialog.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * A subclass of SQLDialog designed to facilitate the creation of UPDATE queries.
 * 
 * Allows only for updates to entries within tables, not to the tables themselves.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UpdateDialog extends SQLDialog{
	
	private BuilderPanel conditions, values;
	private JScrollPane condScroll, valScroll;
	private JPanel main, topPanel, leftPanel, rightPanel;
	
	public UpdateDialog(SQLFrame p) {
		super(false, p);
		
		title = "Update Entries";
		
		main = new JPanel(new GridLayout(1, 2));
		topPanel = new JPanel();
		leftPanel = new JPanel(new BorderLayout());
		rightPanel = new JPanel(new BorderLayout());
		
		topPanel.add(new JLabel("Update entries in"));
		topPanel.add(tables);
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		leftPanel.add(new JLabel("Where:"), BorderLayout.NORTH);
		rightPanel.setBorder(BorderFactory.createEtchedBorder());
		rightPanel.add(new JLabel("Update To:"), BorderLayout.NORTH);
		
		main.add(leftPanel, 0, 0);
		main.add(rightPanel, 0, 1);
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}
	
	public UpdateDialog(ArrayList<String> n, ArrayList<String> nn, String tn, SQLFrame p) {
		super(n, nn, tn, p);
		
		main = new JPanel(new GridLayout(1, 2));
		topPanel = new JPanel();
		leftPanel = new JPanel(new BorderLayout());
		
		topPanel.add(new JLabel("Update entries in " + curTable));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		leftPanel.add(new JLabel("Where:"), BorderLayout.NORTH);
		rightPanel.setBorder(BorderFactory.createEtchedBorder());
		rightPanel.add(new JLabel("Update To:"), BorderLayout.NORTH);
		
		main.add(leftPanel, 0, 0);
		main.add(rightPanel, 0, 1);
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}
	
	public void close(){
		// get the values from any contained QueryPanels
		// and do something with them
		if(doUpdateConfirm() == JOptionPane.YES_OPTION){
			super.close();
		}else{
			//this.dispose();
		}
	}

	public void updateQueryPanels() {
		super.updateQueryPanels();
		
		if(leftPanel.isAncestorOf(condScroll)){
			leftPanel.remove(condScroll);
		}
		if(rightPanel.isAncestorOf(valScroll)){
			rightPanel.remove(valScroll);
		}
		
		values = new BuilderPanel(attribNames, attribTypes, curTable, true);
		conditions = new BuilderPanel(attribNames, attribTypes, curTable, false);
		
		valScroll = new JScrollPane(values);
		condScroll = new JScrollPane(conditions);
		
		leftPanel.add(condScroll, BorderLayout.CENTER);
		rightPanel.add(valScroll, BorderLayout.CENTER);
		this.finalize();
		
	}
	
	public Query getQuery(){
		Query q = new Query(Query.UPDATE, curTable);
		ArrayList<String> conOps = conditions.getOperators();
		ArrayList<String> conValues = conditions.getValues();
		ArrayList<String> updValues = values.getValues();
		
		for(int i = 0; i < attribNames.size(); i++){
			if(conValues.get(i).length() > 0){
				q.addCondition(attribTypes.get(i).intValue(), attribNames.get(i), conOps.get(i), conValues.get(i));
			}
			if(updValues.get(i).length() > 0){
				q.addUpdate(attribTypes.get(i).intValue(), attribNames.get(i), updValues.get(i));
			}
		}
		
		return q;
	}
	
	private int doUpdateConfirm(){
		if(getQuery().getUpdates().length() > 0){
			return JOptionPane.showConfirmDialog(null, "This will update " + getUpdateCount() + " rows. Continue?",
						"Confirm Action", JOptionPane.YES_NO_OPTION);
		}else{
			return JOptionPane.NO_OPTION;
		}
	}
	
	private int getUpdateCount(){
		ResultSet rs = Core.core.runQuery("SELECT COUNT(*) from " + curTable + " " + getQuery().getConditions() + ";");
		try {
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
}
