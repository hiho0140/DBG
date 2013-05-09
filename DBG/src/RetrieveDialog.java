/*
 * RetrieveDialog.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * A subclass of SQLDialog designed to facilitate the creation of SELECT queries.
 * 
 */

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RetrieveDialog extends SQLDialog{
	
	private BuilderPanel main;
	private JScrollPane mainScroll;
	
	public RetrieveDialog(SQLFrame p) {
		super(true, p);
		title = "View Entries";

		JPanel topPanel = new JPanel();		
		topPanel.add(new JLabel("Retrieve entries from"));
		topPanel.add(tables);
		topPanel.add(new JLabel("where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.finalize();
	}
	
	public RetrieveDialog(ArrayList<String> n, ArrayList<String> nn, String tn, SQLFrame p) {
		super(n, nn, tn, p);
		
		JPanel topPanel = new JPanel();		
		topPanel.add(new JLabel("Retrieve entries from " + curTable + " where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.finalize();
	}
	
	public void close(){
		// get the values from any contained QueryPanels
		// and do something with them
		super.close();
	}

	public void updateQueryPanels() {
		super.updateQueryPanels();
		
		if(this.isAncestorOf(mainScroll)){
			this.remove(mainScroll);
		}
		main = new BuilderPanel(attribNames, attribTypes, curTable, false);
		mainScroll = new JScrollPane(main);
		this.add(mainScroll, BorderLayout.CENTER);
		this.finalize();
	}

	public Query getQuery(){
		Query q = new Query(Query.SELECT, curTable);
		ArrayList<String> ops = main.getOperators();
		ArrayList<String> values = main.getValues();
		
		for(int i = 0; i < attribNames.size(); i++){
			if(values.get(i).length() > 0){
				q.addCondition(attribTypes.get(i).intValue(), attribNames.get(i), ops.get(i), values.get(i));
			}
		}
		
		return q;
	}

}
