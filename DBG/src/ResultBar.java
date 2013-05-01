import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.*;

public class ResultBar extends JPanel{

	//ResultBar will take the results of a query performed via the Sidebar
	//and display them in some fashion for the user to select

	private JList theList;
	private JScrollPane scroller;
	
	public ResultBar(){
		theList = new JList();
		theList.setFixedCellWidth(100);
		scroller = new JScrollPane(theList);
		theList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theList.addListSelectionListener(new ResultBarSelectionListener(this));
		
		this.setLayout(new GridLayout(1, 1));
		this.add(scroller);
	}
	
	public void setData(Vector<String> names){
		if(names.size() > 0){
			theList.setListData(names);
			theList.setSelectedIndex(0);
		}
	}
	
	public int getSelectedIndex(){
		return theList.getSelectedIndex();
	}

	public void setSelectedIndex(int index) {
		if(index < theList.getModel().getSize()){
			theList.setSelectedIndex(index);
		}else{
			theList.setSelectedIndex(0);
		}
	}
	
}
