import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LinkedActionListener implements ActionListener{

	public static final int BUTTON = 1, COMBOBOX = 2;
	
	private SQLDialog parent;
	private int type;
	
	public LinkedActionListener(SQLDialog p, int t){
		parent = p;
		type = t;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(type == BUTTON){
			parent.close();
		}else if(type == COMBOBOX){
			parent.updateQueryPanels();
		}
	}

}
