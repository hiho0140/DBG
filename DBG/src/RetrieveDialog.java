import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RetrieveDialog extends SQLDialog{
	
	private QueryPanel main;

	public RetrieveDialog() {
		super();
		
		JPanel topPanel = new JPanel();		
		topPanel.add(new JLabel("Retrieve entries from"));
		topPanel.add(tables);
		topPanel.add(new JLabel("where"));
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
		updateAttribData();
		
		if(this.isAncestorOf(main)){
			this.remove(main);
		}
		main = new QueryPanel(attribNames, attribTypes);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}

}
