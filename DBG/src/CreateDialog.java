import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateDialog extends SQLDialog{

	private QueryPanel main;
	
	public CreateDialog() {
		super();

		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Create new entry in"));
		topPanel.add(tables);
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.finalize();
	}
	
	public CreateDialog(ArrayList<String> n, ArrayList<String> nn, String tn) {
		super(n, nn, tn);

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
		
		if(this.isAncestorOf(main)){
			this.remove(main);
		}
		main = new QueryPanel(attribNames, attribTypes, curTable);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}

}
