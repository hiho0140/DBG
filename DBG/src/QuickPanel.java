import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuickPanel extends JPanel{

	private ArrayList<JTextField> fields;
	private JButton commit, revert;
	private JPanel centerPanel, lowerPanel;
	private ResultBar resultBar;
	
	//View Mode Constructor
	public QuickPanel(){
		fields = new ArrayList<JTextField>();
		this.setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		commit = new JButton("Apply");
		commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		revert = new JButton("Apply");
		revert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		lowerPanel = new JPanel();
	}
	
	public void updateFields(ArrayList<String> nl, ArrayList<String> vl){
	
		this.removeAll();
		fields.clear();
		
		for(int i = 0; i < nl.size(); i++){
			if(vl != null){
				fields.add(new JTextField(vl.get(i)));
			}else{
				fields.add(new JTextField());
			}
			JPanel fieldPanel = new JPanel();
			fieldPanel.add(new JLabel(nl.get(i), JLabel.RIGHT));
			fieldPanel.add(fields.get(i));
			
			this.add(fieldPanel);
		}
	}
	
	public void populateFields(int index){
		Core.core.populateFields(index);
	}
	
	public void populateFields(ArrayList<String> vl){
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
	
	public void applyChanges(){
		//form and fire an update query for this entry in this table with the current in-field values.
	}
	
	public void revertChanges(){
		
	}
	
}
