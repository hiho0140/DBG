/*
 * ResultBarSelectionListener.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 */

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ResultBarSelectionListener implements ListSelectionListener{

	private ResultBar parent;
	private int mode;
	
	public static final int TABLE = 1, VIEW = 2;
	
	public ResultBarSelectionListener(ResultBar p, int m){
		parent = p;
		mode = m;
	}
	
	public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
		if(mode == TABLE){
			Core.core.populateQuickPanel(parent.getSelectedIndex());
		}else if(mode == VIEW){
			Core.core.populateViewPanel(parent.getSelectedIndex());
		}
	}
}
