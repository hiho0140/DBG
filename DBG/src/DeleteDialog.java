import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DeleteDialog extends SQLDialog{

	public DeleteDialog() {
		super();
		
		QueryPanel main = new QueryPanel(names, types);
		
		JPanel topPanel = new JPanel();		
		tables = new JComboBox<String>(new Vector<String>(Core.core.tables));
		
		topPanel.add(new JLabel("Delete entries from"));
		topPanel.add(tables);
		topPanel.add(new JLabel("where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
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
