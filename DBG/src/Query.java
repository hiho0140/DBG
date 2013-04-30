import java.sql.Types;
import java.util.ArrayList;

public class Query {

	static final int UPDATE = 0;
	static final int DELETE = 1;
	static final int SELECT = 2;
	static final int CREATE = 3;
	
	private ArrayList<String> conditions = new ArrayList<String>(); //Conditions to be met for this query
	private ArrayList<String> values = new ArrayList<String>();
	private ArrayList<String> updates = new ArrayList<String>(); //Attributes and the values this query updates them to
	private String table;
	private int queryType;
	
	public Query(int t, String tn){
		queryType = t;
		table = tn;
	}
	
	//Add a condition that this query must meet
	//name = attribute name, cond = conditional phrase
	//Ex: addCondition("firstName", "=", "derp");
	public void addCondition(int type, String name, String op, String val){
		switch(type){
			case Types.BIT:
				conditions.add(new String(name + " " + op + " " + val));
				break;
			case Types.BOOLEAN:
				conditions.add(new String(name + " " + op + " " + val));
				break;
			case Types.INTEGER:
				conditions.add(new String(name + " " + op + " " + val));
				break;
			case Types.DOUBLE:
				conditions.add(new String(name + " " + op + " " + val));
				break;
			case Types.VARCHAR:
				conditions.add(new String(name + " " + op + " '" + val + "'"));
				break;
			default:
				break;
		}
	}
	
	//Add a value to update a matching attribute to
	//name = attribute name, op = operator, cond = conditional phrase
	//Ex: addUpdate("firstName", "=", "'derp'");
	public void addUpdate(int type, String name, String op, String val){
		switch(type){
		case Types.BIT:
			updates.add(new String(name + " " + op + " " + val));
			break;
		case Types.BOOLEAN:
			updates.add(new String(name + " " + op + " " + val));
			break;
		case Types.INTEGER:
			updates.add(new String(name + " " + op + " " + val));
			break;
		case Types.DOUBLE:
			updates.add(new String(name + " " + op + " " + val));
			break;
		case Types.VARCHAR:
			updates.add(new String(name + " " + op + " '" + val + "'"));
			break;
		default:
			break;
		}
	}
	
	public void addValue(int type, String val){
		switch(type){
		case Types.BIT:
			values.add(new String(val));
			break;
		case Types.BOOLEAN:
			values.add(new String(val));
			break;
		case Types.INTEGER:
			values.add(new String(val));
			break;
		case Types.DOUBLE:
			values.add(new String(val));
			break;
		case Types.VARCHAR:
			values.add(new String("'" + val + "'"));
			break;
		default:
			break;
		}
	}
	
	public int getType(){
		return queryType;
	}
	
	public void setType(int t){
		queryType = t;
	}
	
	public void setTable(String s){
		table = s;
	}
	
	public String toString(){
		String result = new String();
		
		switch(queryType){
			case UPDATE:
				result = "UPDATE " + table + " SET " + getUpdates();
				if(getConditions().length() > 0){
					result = result + " WHERE " + getConditions();
				}
				result = result + ";";
				break;
			case SELECT:
				result = "SELECT * FROM " + table;
				if(getConditions().length() > 0){
					result = result + " WHERE " + getConditions();
				}
				result = result + ";";
				break;
			case DELETE:
				result = "DELETE FROM " + table + " WHERE " + getConditions() + ";";
				break;
			case CREATE:
				result = "INSERT INTO " + table + " VALUES (" + getValues() + ");";
				break;
		}
		
		return result;
	}
	
	public String getValues(){
		String result = new String();
		
		for(int i = 0; i < values.size(); i++){
			result = result + values.get(i);
			if(i < values.size() - 1){
				result = result + ", ";
			}
		}
		
		return result;
	}
	
	public String getConditions(){
		String result  = new String();
		
		for(int i = 0; i < conditions.size(); i++){
			result = result + conditions.get(i);
			if(i < conditions.size() - 1){
				result = result + " and ";
			}
		}
		
		return result;
	}
	
	public String getUpdates(){
		String result = new String();
		
		for(int i = 0; i < updates.size(); i++){
			result = result + updates.get(i);
			if(i < updates.size() - 1){
				result = result + ", ";
			}
		}
		
		return result;
	}
	
}
