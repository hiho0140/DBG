/*
 * LinkedActionListener.java
 *
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LinkedActionListener implements ActionListener{

	public static final int CLOSEBUTTON = 1, COMBOBOX = 2, APPLYBUTTON = 3, REVERTBUTTON = 4;
	
	private SQLDialog parent;
	private int type;
	
	public LinkedActionListener(SQLDialog p, int t){
		parent = p;
		type = t;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(type == CLOSEBUTTON){
			parent.close();
		}else if(type == COMBOBOX){
			parent.updateQueryPanels();
		}
	}

}
