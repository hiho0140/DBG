import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinkedActionListener implements ActionListener{

	private SQLDialog parent;
	
	public LinkedActionListener(SQLDialog p){
		parent = p;
	}
	
	public void actionPerformed(ActionEvent e) {
		parent.close();
	}

}
