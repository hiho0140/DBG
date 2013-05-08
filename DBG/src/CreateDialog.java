/*
 * CreateDialog.java
 * 
 * A subclass of SQLDialog designed to facilitate the creation of INSERT queries.
 * 
 * Ironically, it does -not- create CREATE queries.
 */

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CreateDialog extends SQLDialog{

	private BuilderPanel main;
	private JScrollPane mainScroll;
	
	public CreateDialog(SQLFrame p) {
		super(false, p);
		title = "Create an Entry";

		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Create new entry in"));
		topPanel.add(tables);
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.finalize();
	}
	
	public CreateDialog(ArrayList<String> n, ArrayList<String> nn, String tn, SQLFrame p) {
		super(n, nn, tn, p);

		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Create new entry in " + curTable));
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

	public void updateQueryPanels(){
		super.updateQueryPanels();
		
		if(this.isAncestorOf(mainScroll)){
			this.remove(mainScroll);
		}
		main = new BuilderPanel(attribNames, attribTypes, curTable, true);
		mainScroll = new JScrollPane(main);
		this.add(mainScroll, BorderLayout.CENTER);
		this.finalize();
	}

	public Query getQuery(){
		Query q = new Query(Query.CREATE, curTable);
		ArrayList<String> values = main.getValues();
		
		for(int i = 0; i < attribNames.size(); i++){
			q.addValue(attribTypes.get(i).intValue(), values.get(i));
		}
		
		return q;
	}
	
}
