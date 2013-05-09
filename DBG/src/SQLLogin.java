/*
 * SQLLogin.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * SQLLogin is used by Core at startup to request login information
 * from the user for authentication with the SQL database.
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class SQLLogin extends JPanel{

	private JTextField nameField;
	private JPasswordField passField;
	private JLabel passLabel, nameLabel, messageLabel;
	private JPanel namePanel, passPanel, centerPanel;
	
	public SQLLogin(){
		this.setLayout(new BorderLayout());
		
		namePanel = new JPanel();
		passPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(2, 1));
		
		passLabel = new JLabel("Password: ");
		nameLabel = new JLabel("User Name: ");
		messageLabel = new JLabel("Please enter your login credentials:");
		
		nameField = new JTextField(20);
		passField = new JPasswordField(20);
		
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		passPanel.add(passLabel);
		passPanel.add(passField);
		
		centerPanel.add(namePanel, 0, 0);
		centerPanel.add(passPanel, 0, 1);
		
		this.add(messageLabel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public String getUsername(){
		return nameField.getText();
	}
	
	public String getPassword(){
		return new String(passField.getPassword());
	}
	
	
	
}
