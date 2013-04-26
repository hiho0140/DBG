import java.util.ArrayList;

import javax.swing.BoxLayout;
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
	private ArrayList<String> names;
	private ArrayList<String> types;

	public QueryPanel(ArrayList<String> n, ArrayList<String> t) {
		fields = new ArrayList<JTextField>();
		names = new ArrayList<String>(n);
		types = new ArrayList<String>(t);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < names.size(); i++){
			// Each field needs proper formatting based on the associated type
			// Toss in an if/switch block later for this
			fields.add(new JTextField(20));
			
			JPanel fieldPanel = new JPanel();
			fieldPanel.add(new JLabel(names.get(i), JLabel.RIGHT));
			fieldPanel.add(fields.get(i));
			
			this.add(fieldPanel);
		}
	}
	
	public ArrayList<String> getValues(){
		ArrayList<String> values = new ArrayList<String>();
		for(JTextField f:fields){
			values.add(f.getText());
		}
		return values;
	}
	
}
