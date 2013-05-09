/*
 * Sidebar.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * Displays a set of quick function buttons, each of which calls
 * a specific, predefined query - each of these causes either a
 * QuickPanel or a ViewPanel to be displayed within the main GUI
 * frame, depending on whether the query was called on a table or
 * a view.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Sidebar extends JPanel{
	
	//Sidebar will contain a bunch of buttons that perform common queries

	private JPanel tableButtons, topPanel, viewButtons, bottomPanel;
	private JLabel tableLabel, viewLabel;
	private JButton tablePlayers, tableTeams, tableSeasons, tableMatch, tableParticipates, 
		tableTeamStats, tablePlayerStats, viewScore, viewStandings, viewMatchStats;	
	
	public Sidebar(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEtchedBorder());
		
		topPanel = new JPanel(new BorderLayout());
		tableLabel = new JLabel("Tables:");
		tableButtons = new JPanel();
		tableButtons.setLayout(new GridLayout(7, 1));
		tableButtons.setBorder(BorderFactory.createEtchedBorder());
		
		bottomPanel = new JPanel(new BorderLayout());
		viewLabel = new JLabel("Views:");
		viewButtons = new JPanel();
		viewButtons.setLayout(new GridLayout(3, 1));
		viewButtons.setBorder(BorderFactory.createEtchedBorder());
		
		tablePlayers = new JButton("Players");
		tablePlayers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from player;", "player_name", true);
			}
		});
		tableButtons.add(tablePlayers);
		
		tableTeams = new JButton("Teams");
		tableTeams.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from team;", "team_name", true);
			}
		});
		tableButtons.add(tableTeams);
		
		tableSeasons = new JButton("Seasons");
		tableSeasons.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from season;", "season_no", true);
			}
		});
		tableButtons.add(tableSeasons);
		
		tableMatch = new JButton("Matches");
		tableMatch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from match;", "match_id", true);
			}
		});
		tableButtons.add(tableMatch);
		
		tableTeamStats = new JButton("Team Stats");
		final String[] tspk = {"team_id", "match_id"};
		tableTeamStats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from team_stat;", tspk, true);
			}
		});
		tableButtons.add(tableTeamStats);
		
		tablePlayerStats = new JButton("Player Stats");
		final String[] pspk = {"player_id", "match_id"};
		tablePlayerStats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from player_stat;", pspk, true);
			}
		});
		tableButtons.add(tablePlayerStats);
		
		tableParticipates = new JButton("Season Reg.");
		final String[] ppk = {"season_no", "team_id"};
		tableParticipates.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickQuery("SELECT * from participates;", ppk, true);
			}
		});
		tableButtons.add(tableParticipates);
		
		
		
		
		viewMatchStats = new JButton("Match Stats");
		viewMatchStats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickView("match_stats", "match_id");
			}
		});
		viewButtons.add(viewMatchStats);
		
		viewScore = new JButton("Scores");
		viewScore.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickView("score", "match_id");
			}
		});
		viewButtons.add(viewScore);
		
		viewStandings = new JButton("Standings");
		final String[] spk = {"season_no", "team_name"};
		viewStandings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Core.core.runQuickView("standings", spk);
			}
		});
		viewButtons.add(viewStandings);
		
		
		topPanel.add(tableLabel, BorderLayout.NORTH);
		topPanel.add(tableButtons, BorderLayout.CENTER);
		
		bottomPanel.add(viewLabel, BorderLayout.NORTH);
		bottomPanel.add(viewButtons, BorderLayout.CENTER);
		
		this.add(topPanel);
		this.add(bottomPanel);
	}
	
}
