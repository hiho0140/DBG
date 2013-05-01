import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ResultBarSelectionListener implements ListSelectionListener{

	private ResultBar parent;
	
	public ResultBarSelectionListener(ResultBar p){
		parent = p;
	}
	
	public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
		Core.core.generateQuickPanel(parent.getSelectedIndex());
	}
}
