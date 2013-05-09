/*
 * SQLFrame.java
 * 
 * Team Members:
 * Steven Zuchowski
 * Kevin Mahoney
 * Ian Smudde
 * 
 * A simple extension of JFrame that displays a single SQLDialog
 * based on the last CRUD function button the user clicked.
 * 
 * Implemented to solve issues with multiple SQLDialogs being spawned
 * when a CRUD function builder button was clicked.
 */

import java.awt.Dimension;

import javax.swing.JFrame;

public class SQLFrame extends JFrame{

	private SQLDialog dia;
	
	public SQLFrame(){
		
	}
	
	public SQLFrame(SQLDialog d){
		this.setDialog(d);
	}
	
	public void setDialog(SQLDialog d){
		if(dia != null){
			this.remove(dia);
		}
		dia = d;
		this.add(dia);
		this.setTitle(dia.getTitle());
		this.finalize();
	}
	
	public void finalize(){
		this.pack();
		if(this.getHeight() > 500){
			this.setSize(this.getWidth(), 500);
		}
		this.setVisible(true);
	}
	
}
