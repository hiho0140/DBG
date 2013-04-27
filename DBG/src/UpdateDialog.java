import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UpdateDialog extends SQLDialog{

	public UpdateDialog() {
		super();
		
		QueryPanel conditions = new QueryPanel(names, types);
		QueryPanel values = new QueryPanel(names, types);
		
		JPanel main = new JPanel(new GridLayout(2, 1));
		JPanel topPanel = new JPanel();
		JPanel lowerPanel = new JPanel(new BorderLayout());
		
		tables = new JComboBox<String>(new Vector<String>(Core.core.tables));
		
		topPanel.add(new JLabel("Update entries in"));
		topPanel.add(tables);
		topPanel.add(new JLabel("where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		lowerPanel.add(new JLabel("Update to"), BorderLayout.NORTH);
		lowerPanel.add(values, BorderLayout.CENTER);
		lowerPanel.setBorder(BorderFactory.createEtchedBorder());
		
		main.add(conditions);
		main.add(lowerPanel);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.finalize();
	}
	
	public void close(){
		// get the values from any contained QueryPanels
		// and do something with them
		
		super.close();
	}
	
}
