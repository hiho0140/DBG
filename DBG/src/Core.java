import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Core {

	public static Core core;

	
	public ArrayList<String> tables, attributes, types;
	private Object[][] testdata = {{"herp","herp","herp","herp","herp"},{"derp","derp","derp","derp","derp"},{"wat","wat","wat","wat","wat"}};
	
	//GUI components
	public JButton newEntry, newTable, editEntry, editTable, delEntry, delTable, retEntry, retTable;
	public JTable table;
	public JFrame window;
	
	private ArrayList<SQLDialog> dialogs = new ArrayList<SQLDialog>();
	
	public Core(){
		
		//Connect to the database
		/*
	    Connection con;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(
			        "jdbc:postgresql://reddwarf.cs.rit.edu/p48501m",
			        "p48501m",
			        "Oakfr0125");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM Table1");
				
				while (rs.next()) {
				int x = rs.getInt("a");
				String s = rs.getString("b");
				float f = rs.getFloat("c");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//Initialize Shtuff
				tables = new ArrayList<String>();
				tables.add("Table 1");
				tables.add("Table 2");
				
		//set up a dummy array of attribute names
				attributes = new ArrayList<String>();
				types = new ArrayList<String>();
				attributes.add("attr1");
				attributes.add("attr2");
				attributes.add("attr3");
				attributes.add("attr4");
				attributes.add("attr5");
				types.add("type1");
				types.add("type2");
				types.add("type3");
				types.add("type4");
				types.add("type5");

	}
	
	public static void main(String[] args){
		
		core = new Core();
		core.initGUI();
		
		/*
		ArrayList<String> herp = new ArrayList<String>();
		herp.add(new String("herp"));
		herp.add(new String("herp"));
		ArrayList<String> derp = new ArrayList<String>();
		derp.add(new String("derp"));
		derp.add(new String("derp"));
		CreateDialog test1 = new CreateDialog(herp, derp);
		DeleteDialog test2 = new DeleteDialog(herp, derp);
		UpdateDialog test3 = new UpdateDialog(herp, derp);
		RetrieveDialog test4 = new RetrieveDialog(herp, derp);
		*/
	}
	
	private void initGUI(){
		
		window = new JFrame("herp derp test");
		window.setLayout(new BorderLayout());
		
		//========================//
		//======= MENU BAR =======//
		//========================//
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Core.core.quitProgram();
			}	
		});

		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		
		
		//=========================//
		//==== RESULTS DISPLAY ====//
		//=========================//
		
		//Put the table in a scrollpane so the column headers show up properly
		table = new JTable(new DefaultTableModel(testdata, attributes.toArray()));
		JScrollPane scroller = new JScrollPane(table);
		
		
		//========================//
		//===== CRUD BUTTONS =====//
		//========================//
		
			newEntry = new JButton("Add Entry");
			newEntry.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					Core.core.spawnDialog(1);
				}
			});
			
			editEntry = new JButton("Update Entries");
			editEntry.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					Core.core.spawnDialog(2);
				}
			});
			
			retEntry = new JButton("View Entries");
			retEntry.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					Core.core.spawnDialog(3);
				}
			});
			
			delEntry = new JButton("Delete Entries");
			delEntry.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					Core.core.spawnDialog(4);
				}
			});
			
			JPanel buttonPanel = new JPanel();
			GridLayout bpLayout = new GridLayout(2, 2);
			bpLayout.setHgap(10);
			bpLayout.setVgap(10);
			buttonPanel.setLayout(bpLayout);
			buttonPanel.add(newEntry);
			buttonPanel.add(retEntry);
			buttonPanel.add(editEntry);
			buttonPanel.add(delEntry);
		
		//========================//
		//====== FINALIZING ======//
		//========================//
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent arg0) {
				Core.core.quitProgram();
			}
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		window.add(scroller, BorderLayout.CENTER);
		window.add(buttonPanel, BorderLayout.SOUTH);
		window.setJMenuBar(menuBar);
		window.setSize(800, 800);
		window.setVisible(true);
				
	}
	
	public void spawnDialog(int type){
		//not sure if memory leak :<
		SQLDialog dia;
		switch(type){
			case 1:
				dia = new CreateDialog();
				dialogs.add(dia);
				break;
			case 2:
				dia = new UpdateDialog();
				dialogs.add(dia);
				break;
			case 3:
				dia = new RetrieveDialog();
				dialogs.add(dia);
				break;
			case 4:
				dia = new DeleteDialog();
				dialogs.add(dia);
				break;
			default:
				break;
		}
	}
	
	public void removeDialog(SQLDialog dia){
		dialogs.remove(dia);
	}
	
	public void closeDialog(SQLDialog dia){
		dia.close();
	}
	
	public ArrayList<String> getTableNames(){
		//This needs to query the database for all table names
		//and return a suitable arraylist of them.
		return tables;
	}
	
	public ArrayList<String> getAttributeNames(String name){
		//This needs to query the database for all attribute
		//names in the specified table and return a
		//suitable arraylist of them.
		return attributes;
	}
	
	public ArrayList<String> getDataTypes(String name){
		//This needs to query the database for all attribute
		//datatypes in the specified table and return a
		//suitable arraylist of them.
		return types;
	}
	
	public void quitProgram(){
		//ABANDON THREAD!! ABANDON THREAD!!
		// #rocketsnail
		
		//Shouldn't need any save checks here, since everything is
		//stored serverside. Maybe just a quick "are you sure?"
		
		for(SQLDialog d : dialogs){
			closeDialog(d);
		}
		
		window.dispose(); //lolnope
	}
	
}
