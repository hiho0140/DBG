import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.*;

public class ResultBar extends JPanel{

	//ResultBar will take the results of a query performed via the Sidebar
	//and display them in some fashion for the user to select

	private JList theList;
	private JLabel titleLabel;
	private JScrollPane scroller;
	
	public ResultBar(int m){
		theList = new JList();
		theList.setFixedCellWidth(100);
		scroller = new JScrollPane(theList);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		theList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theList.addListSelectionListener(new ResultBarSelectionListener(this, m));
		titleLabel = new JLabel();
		
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);
	}
	
	public void setData(Vector<String> names, String[] title){
		if(names.size() > 0){
			theList.setListData(names);
			theList.setSelectedIndex(0);
		}
		String temp = new String();
		for(int i = 0; i < title.length; i++){
			temp = temp + title[i];
			if(i < title.length - 1){
				temp = temp + ", ";
			}
		}
		if(this.isAncestorOf(titleLabel)){
			this.remove(titleLabel);
		}
		titleLabel = new JLabel(temp);
		this.add(titleLabel, BorderLayout.NORTH);
		this.validate();
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
