import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Sidebar extends JPanel{
	
	//Sidebar will contain a bunch of buttons that perform common queries

	private JButton viewPlayers, viewTeams, viewSeasons;
	
	public Sidebar(){
		viewPlayers = new JButton("View Players");
		viewPlayers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from player;");
			}
		});
		
		this.add(viewPlayers);
	}
	
}
