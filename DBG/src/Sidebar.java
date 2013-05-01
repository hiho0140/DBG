import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Sidebar extends JPanel{
	
	//Sidebar will contain a bunch of buttons that perform common queries

	private JPanel buttons;
	private JButton viewPlayers, viewTeams, viewSeasons;
	
	public Sidebar(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		
		viewPlayers = new JButton("View Players");
		viewPlayers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from player;", "player_name", true);
			}
		});
		buttons.add(viewPlayers);
		
		viewTeams = new JButton("View Teams");
		viewTeams.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from team;", "team_name", true);
			}
		});
		buttons.add(viewTeams);
		
		viewSeasons = new JButton("View Seasons");
		viewSeasons.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from season;", "season_no", true);
			}
		});
		buttons.add(viewSeasons);
		
		this.add(new JLabel("Quick Queries:"), BorderLayout.NORTH);
		this.add(buttons, BorderLayout.CENTER);
	}
	
}
