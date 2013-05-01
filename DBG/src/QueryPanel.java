import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// QueryPanel.java
//
// This module takes a pair of ArrayLists that contain the names
// of attributes in a table and their datatypes. It uses that information
// to create a panel full of labels and formatted text fields.
// Theoretically, it's compatible with any number of attributes
// and any number of different data types, so long as formatting
// rules get added to the source code for each datatype.


public class QueryPanel extends JPanel{
	
	private ArrayList<JTextField> fields;
	private ArrayList<JComboBox> operators;
	private ArrayList<String> attributes;
	private ArrayList<Integer> types;
	private String tableName;
	
	String[] boolOps = { "=", "!=" };
	String[] numberOps = { "=", "!=", "<", "<=", ">", ">=", "IN" };
	String[] stringOps = { "=", "!=", "LIKE", "NOT LIKE", "IN" };

	//Query Mode Constructor
	public QueryPanel(ArrayList<String> nl, ArrayList<Integer> tl, String tn, boolean noOperators) {
		fields = new ArrayList<JTextField>();
		operators = new ArrayList<JComboBox>();
		attributes = new ArrayList<String>(nl);
		types = new ArrayList<Integer>(tl);
		tableName = new String(tn);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < attributes.size(); i++){
			// Each field needs proper formatting based on the associated type
			// Toss in an if/switch block later for this
			fields.add(new JTextField(20));
			
			JPanel fieldPanel = new JPanel(new BorderLayout()), topPanel = new JPanel(), midPanel = new JPanel();
			topPanel.setLayout(new GridLayout(1, 1));
			topPanel.add(new JLabel(Core.core.getNiceName(tableName, attributes.get(i)), JLabel.LEFT));
			
			if(!noOperators){
				switch(types.get(i).intValue()){
					case Types.BIT:
						operators.add(new JComboBox(boolOps));
						break;
					case Types.BOOLEAN:
						operators.add(new JComboBox(boolOps));
						break;
					case Types.INTEGER:
						operators.add(new JComboBox(numberOps));
						break;
					case Types.DOUBLE:
						operators.add(new JComboBox(numberOps));
						break;
					case Types.VARCHAR:
						operators.add(new JComboBox(stringOps));
						break;
					default:
						break;
				}
			
				midPanel.add(operators.get(i));
			}
			
			midPanel.add(fields.get(i));
			
			fieldPanel.add(topPanel, BorderLayout.NORTH);
			fieldPanel.add(midPanel, BorderLayout.CENTER);
			
			this.add(fieldPanel);
		}
	}
	
	public ArrayList<String> getNames(){
		return attributes;
	}
	
	public ArrayList<String> getOperators(){
		ArrayList<String> ops = new ArrayList<String>();
		for(JComboBox cb : operators){
			ops.add(new String((String) cb.getItemAt(cb.getSelectedIndex())));
		}
		return ops;
	}
	
	public ArrayList<String> getValues(){
		ArrayList<String> values = new ArrayList<String>();
		for(JTextField f:fields){
			values.add(f.getText());
		}
		return values;
	}
	
}
