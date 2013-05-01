import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuickPanel extends JPanel{

	private ArrayList<JTextField> fields;
	private ArrayList<String> origVals;
	private ArrayList<Integer> types;
	private ArrayList<String> attribNames;
	private JButton commit, revert, add, delete;
	private JPanel centerPanel, lowerPanel;
	private ResultBar resultBar;
	private String curTable, myQuery, listName;
	private boolean allowUpdate;
	
	
	//View Mode Constructor
	public QuickPanel(){
		origVals = new ArrayList<String>();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		
		resultBar = new ResultBar();
		fields = new ArrayList<JTextField>();
		
		centerPanel = new JPanel();
		
		add = new JButton("Add Entry");
		add.addActionListener(new QuickActionListener(this, QuickActionListener.ADDBUTTON));
		
		commit = new JButton("Apply Changes");
		commit.addActionListener(new QuickActionListener(this, QuickActionListener.APPLYBUTTON));
		
		revert = new JButton("Revert Changes");
		revert.addActionListener(new QuickActionListener(this, QuickActionListener.REVERTBUTTON));
		
		delete = new JButton("Delete Entry");
		delete.addActionListener(new QuickActionListener(this, QuickActionListener.DELETEBUTTON));

		lowerPanel = new JPanel();
		lowerPanel.add(add);
		lowerPanel.add(commit);
		lowerPanel.add(revert);
		lowerPanel.add(delete);
		
		this.add(resultBar, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);
		
	}
	
	public void setCanApply(boolean b){
		allowUpdate = b;
	}
	
	public void setTable(String tn){
		curTable = tn;
	}
	
	public void setListName(String ln){
		listName = ln;
	}
	
	public void updateList(ArrayList<String> nl){
		resultBar.setData(new Vector<String>(nl));
	}
	
	public void generateFields(ArrayList<String> nl, ArrayList<Integer> tl){
		
		attribNames = nl;
		types = tl;
	
		this.remove(centerPanel);
		
		centerPanel = new JPanel();
		fields.clear();
		
		for(int i = 0; i < nl.size(); i++){

			fields.add(new JTextField(15));
			JPanel fieldPanel = new JPanel();
			fieldPanel.setBorder(BorderFactory.createEtchedBorder());
			fieldPanel.add(new JLabel(Core.core.getNiceName(curTable,  nl.get(i)), JLabel.RIGHT));
			fieldPanel.add(fields.get(i));
			
			centerPanel.add(fieldPanel);
		}
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.validate();
	}
	
	public void populateFields(int index){
		Core.core.generateQuickPanel(index);
	}
	
	public void populateFields(ArrayList<String> vl){
		origVals = vl;
		for(int i = 0; i < vl.size(); i++){
			fields.get(i).setText(vl.get(i));
		}
	}
	
	public ArrayList<String> getValues(){
		ArrayList<String> values = new ArrayList<String>();
		for(JTextField f:fields){
			values.add(f.getText());
		}
		return values;
	}
	
	public void addChanges(){
		if(allowUpdate){
			Query q = new Query(Query.CREATE, curTable);
			for(int i = 0; i < fields.size(); i++){
				q.addValue(types.get(i), fields.get(i).getText());
			}
			Core.core.runQuery(q.toString());
			repeatQuery();
		}
	}
	
	public void applyChanges(){
		//form and fire an update query for this entry in this table with the current in-field values.
		if(allowUpdate){
			Query q = new Query(Query.UPDATE, curTable);
			for(int i = 0; i < fields.size(); i++){
				q.addCondition(types.get(i), attribNames.get(i), "=", origVals.get(i));
				q.addUpdate(types.get(i), attribNames.get(i), fields.get(i).getText());
			}
			Core.core.runQuery(q.toString());
			repeatQuery();
		}
	}
	
	public void revertChanges(){
		populateFields(resultBar.getSelectedIndex());
	}
	
	public void removeEntry(){
		if(allowUpdate){
			Query q = new Query(Query.DELETE, curTable);
			for(int i = 0; i < fields.size(); i++){
				q.addCondition(types.get(i), attribNames.get(i), "=", origVals.get(i));
			}
			Core.core.runQuery(q.toString());
			repeatQuery();
		}
	}
	
	public void repeatQuery(){
		int temp = resultBar.getSelectedIndex();
		Core.core.runQuickQuery(myQuery, listName, allowUpdate);
		resultBar.setSelectedIndex(temp);
	}

	public void setQuery(String q) {
		myQuery = q;
	}
	
}
