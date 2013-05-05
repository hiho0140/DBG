import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class DeleteDialog extends SQLDialog{

	private QueryPanel main;
	private JScrollPane mainScroll;
	
	public DeleteDialog(SQLFrame p) {
		super(false, p);
		title = "Delete Entries";
		
		JPanel topPanel = new JPanel();		
		topPanel.add(new JLabel("Delete entries from"));
		topPanel.add(tables);
		topPanel.add(new JLabel("where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.finalize();
	}
	
	public DeleteDialog(ArrayList<String> n, ArrayList<String> nn, String tn, SQLFrame p) {
		super(n, nn, tn, p);
		
		JPanel topPanel = new JPanel();		
		topPanel.add(new JLabel("Delete entries from " + curTable + " where"));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		updateQueryPanels();
		
		this.add(topPanel, BorderLayout.NORTH);
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
		
		if(this.isAncestorOf(mainScroll)){
			this.remove(mainScroll);
		}
		main = new QueryPanel(attribNames, attribTypes, curTable, false);
		mainScroll = new JScrollPane(main);
		this.add(mainScroll, BorderLayout.CENTER);
		this.finalize();
	}
	
	public Query getQuery(){
		Query q = new Query(Query.DELETE, curTable);
		ArrayList<String> ops = main.getOperators();
		ArrayList<String> values = main.getValues();
		
		for(int i = 0; i < attribNames.size(); i++){
			if(values.get(i).length() > 0){
				q.addCondition(attribTypes.get(i).intValue(), attribNames.get(i), ops.get(i), values.get(i));
			}
		}
		
		return q;
	}
	
	private int doUpdateConfirm(){
		if(getQuery().getConditions().length() > 0){
			return JOptionPane.showConfirmDialog(null, "This will delete " + getUpdateCount() + " rows. Continue?",
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
