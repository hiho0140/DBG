import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuickPanel extends JPanel{

	private ArrayList<JTextField> fields;
	
	//View Mode Constructor
	public QuickPanel(ArrayList<String> nl, ArrayList<String> vl){
		fields = new ArrayList<JTextField>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		updateFields(nl, vl);
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
	
	public ArrayList<String> getValues(){
		ArrayList<String> values = new ArrayList<String>();
		for(JTextField f:fields){
			values.add(f.getText());
		}
		return values;
	}
	
}
