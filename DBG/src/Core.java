import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Core {

	public static Core core;
	private Connection con;
	private ResultSet curResults;
	
	public ArrayList<String> tables, attributes;
	public ArrayList<Integer> types;
	private Object[][] testdata = {{"herp","herp","herp","herp","herp"},{"derp","derp","derp","derp","derp"},{"wat","wat","wat","wat","wat"}};
	
	private NameManager nameMan = new NameManager();
	
	//GUI components
	private JButton newEntry, editEntry, delEntry, retEntry;
	private JTable table;
	private DefaultTableModel model;
	public JFrame window;
	
	private ArrayList<SQLDialog> dialogs = new ArrayList<SQLDialog>();
	
	public static void main(String[] args){
		
		core = new Core();
		core.initGUI();

	}
	
	public Core(){
		
		//Connect to the database
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(
			        "jdbc:postgresql://reddwarf.cs.rit.edu/p48501m",
			        "p48501m",
			        "herpderp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Initialize Shtuff
				tables = new ArrayList<String>();
				tables.add("Table 1");
				tables.add("Table 2");
				
		//set up a dummy array of attribute names
				attributes = new ArrayList<String>();
				types = new ArrayList<Integer>();
				attributes.add("attr1");
				attributes.add("attr2");
				attributes.add("attr3");
				attributes.add("attr4");
				attributes.add("attr5");
				types.add(1);
				types.add(2);
				types.add(3);
				types.add(4);
				types.add(5);

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
		model = new DefaultTableModel(testdata, attributes.toArray());
		table = new JTable(model);
		JScrollPane scroller = new JScrollPane(table);
		
		
		//========================//
		//====== SIDE PANEL ======//
		//========================//
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		
		
		
		
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
		dia.dispose();
	}
	
	public ArrayList<String> getTableNames() throws SQLException{
		ResultSet rs = runQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' and table_type != 'view';");
		tables.clear();
		
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		
		return tables;
	}
	
	public ArrayList<String> getAttributeNames(String name) throws SQLException{
		ResultSet rs = runQuery("SELECT column_name FROM information_schema.columns WHERE table_name = '" + name + "';");
		attributes.clear();
		ArrayList<String> temp = new ArrayList<String>();
		
		while(rs.next()){
			temp.add(rs.getString(1));
		}
		
		//Need to reverse the order of attribute names because the DB gives them to us backwards.
		for(int i = temp.size() - 1; i >= 0; i--){
			attributes.add(temp.get(i));
		}
		
		return attributes;
	}
	
	public ArrayList<Integer> getDataTypes(String name) throws SQLException{
		//This needs to query the database for all attribute
		//datatypes in the specified table and return a
		//suitable arraylist of them.
		
		ResultSet rs = runQuery("SELECT * from " + name + ";");
		ResultSetMetaData rsmd = rs.getMetaData();
		types.clear();
		
		for(int i = 1; i <= rsmd.getColumnCount(); i++){
			types.add(new Integer(rsmd.getColumnType(i)));
		}
		
		return types;
	}
	
	public synchronized ResultSet runQuery(Query q){
		return runQuery(q.toString());
	}
	
	public synchronized ResultSet runQuery(String q){
		Statement stmt;
		try {
			stmt = con.createStatement();
			curResults = stmt.executeQuery(q);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return curResults;
	}
	
	public void setValueAt(Object aValue, int row, int column) throws SQLException{
		if(curResults != null){
			
			curResults.first();
			for(int i = row - 1; i > 0; i--){
				curResults.next();
			}
			
			switch(curResults.getMetaData().getColumnType(column)){
				case Types.INTEGER:
					curResults.updateInt(column, ((Integer)aValue).intValue());
					break;
				case Types.DOUBLE:
					curResults.updateDouble(column, ((Double)aValue).doubleValue());
					break;
				case Types.VARCHAR:
					curResults.updateString(column, (String)aValue);
					break;
				case Types.BOOLEAN:
					curResults.updateBoolean(column, ((Boolean)aValue).booleanValue());
					break;
				case Types.BIT:
					curResults.updateBoolean(column, ((Boolean)aValue).booleanValue());
					break;
				default:
					break;
			}
			
			curResults.updateRow();
			
		}
	}
	
	public void updateTable(ResultSet rs){
		Vector<String> colNames = new Vector<String>();
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> next;
		
		try {
			while(rs.next()){
				next = new Vector<String>();
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
					next.add(rs.getString(i));
				}
				data.add(next);
			}
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
				colNames.add(rs.getMetaData().getColumnName(i));
			}
			updateTable(colNames, data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTable(Vector<String> colNames, Vector<Vector<String>> data){
		model.setDataVector(data, colNames);
		table.repaint();
	}
	
	public String getNiceName(String tableName, String attributeName){
		return nameMan.getNameFor(tableName, attributeName);
	}
	
	public void quitProgram(){
		//ABANDON THREAD!! ABANDON THREAD!!
		// #rocketsnail
		
		//Shouldn't need any save checks here, since everything is
		//stored serverside. Maybe just a quick "are you sure?"
		
		for(SQLDialog d : dialogs){
			d.dispose();
		}
		
		window.dispose(); //lolnope
	}
	
}
