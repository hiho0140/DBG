import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UpdateDialog extends SQLDialog{
	
	private QueryPanel conditions, values;
	private JPanel main, topPanel, lowerPanel;

	public UpdateDialog() {
		super();
		
		main = new JPanel(new GridLayout(2, 1));
		topPanel = new JPanel();
		lowerPanel = new JPanel(new BorderLayout());
		
		topPanel.add(new JLabel("Update entries in"));
		topPanel.add(tables);
		topPanel.add(new JLabel("where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		lowerPanel.setBorder(BorderFactory.createEtchedBorder());
		lowerPanel.add(new JLabel("Update to:"), BorderLayout.NORTH);
		main.add(lowerPanel, 1, 0);
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}
	
	public UpdateDialog(ArrayList<String> n, ArrayList<String> nn, String tn) {
		super(n, nn, tn);
		
		main = new JPanel(new GridLayout(2, 1));
		topPanel = new JPanel();
		lowerPanel = new JPanel(new BorderLayout());
		
		topPanel.add(new JLabel("Update entries in " + curTable + " where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		lowerPanel.setBorder(BorderFactory.createEtchedBorder());
		lowerPanel.add(new JLabel("Update to:"), BorderLayout.NORTH);
		main.add(lowerPanel, 1, 0);
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}
	
	public void close(){
		// get the values from any contained QueryPanels
		// and do something with them
		
		super.close();
	}

	public void updateQueryPanels() {
		super.updateQueryPanels();
		
		if(lowerPanel.isAncestorOf(values)){
			lowerPanel.remove(values);
		}
		if(main.isAncestorOf(conditions)){
			main.remove(conditions);
		}
		
		values = new QueryPanel(attribNames, attribTypes, curTable);
		conditions = new QueryPanel(attribNames, attribTypes, curTable);
		
		lowerPanel.add(values, BorderLayout.CENTER);
		main.add(conditions, 0, 0);
		this.finalize();
		
	}
	
	public Query getQuery(){
		Query q = new Query(Query.UPDATE, curTable);
		ArrayList<String> conOps = conditions.getOperators();
		ArrayList<String> conValues = conditions.getValues();
		ArrayList<String> updOps = values.getOperators();
		ArrayList<String> updValues = values.getValues();
		
		for(int i = 0; i < attribNames.size(); i++){
			if(conValues.get(i).length() > 0){
				q.addCondition(attribTypes.get(i).intValue(), attribNames.get(i), conOps.get(i), conValues.get(i));
			}
			if(updValues.get(i).length() > 0){
				q.addUpdate(attribTypes.get(i).intValue(), attribNames.get(i), updOps.get(i), updValues.get(i));
			}
		}
		
		return q;
	}
	
}
