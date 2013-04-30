import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ResultBar extends JPanel{

	//ResultBar will take the results of a query performed via the Sidebar
	//and display them in some fashion for the user to select

	protected final JList theList;

	public ResultBar(){
		theList = new JList();
		theList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				Core.core.populateFields(theList.getSelectedIndex());
			}
		});
	}
	
	public void setData(Vector<String> names){
		theList.setListData(names);
	}
	
}
