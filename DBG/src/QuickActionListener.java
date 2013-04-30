import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuickActionListener implements ActionListener {

	public static final int CLOSEBUTTON = 1, COMBOBOX = 2, APPLYBUTTON = 3, REVERTBUTTON = 4;
	
	private QuickPanel parent;
	private int type;
	
	public QuickActionListener(QuickPanel p, int t){
		parent = p;
		type = t;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(type == APPLYBUTTON){
			parent.applyChanges();
		}else if(type == REVERTBUTTON){
			parent.revertChanges();
		}
	}

}
