/*
 * NameManager.java
 * 
 * NameManager is the current datastore for "nice names" of attributes.
 * This could potentially be offloaded to a table within the database
 * being used or a file on the local filesystem, which could be loaded
 * at runtime. For now, this will do.
 */

import java.util.HashMap;

public class NameManager {

	HashMap<String, HashMap<String, String>> names = new HashMap<String, HashMap<String, String>>();
	public static final String STANDINGS = "standings", PLAYER = "player", MATCH_STATS = "match_stats",
			TEAM_STAT = "team_stat", MATCH = "match", PLAYER_STAT = "player_stat", TEAM = "team",
			SEASON = "season", PARTICIPATES = "participates", SCORE = "score";
	
	public NameManager(){
		names.put(STANDINGS, new HashMap<String, String>());
		names.put(PLAYER, new HashMap<String, String>());
		names.put(MATCH_STATS, new HashMap<String, String>());
		names.put(TEAM_STAT, new HashMap<String, String>());
		names.put(MATCH, new HashMap<String, String>());
		names.put(PLAYER_STAT, new HashMap<String, String>());
		names.put(TEAM, new HashMap<String, String>());
		names.put(SEASON, new HashMap<String, String>());
		names.put(PARTICIPATES, new HashMap<String, String>());
		names.put(SCORE, new HashMap<String, String>());
		
		HashMap<String, String> curMap;
		
		curMap = names.get(STANDINGS);
		curMap.put("season_no", "Season #");
		curMap.put("team_name", "Team Name");
		curMap.put("wins", "Wins");
		curMap.put("losses", "Losses");
		curMap.put("rounds", "Rounds");
		
		curMap = names.get(PLAYER);
		curMap.put("player_id", "Player ID");
		curMap.put("player_name", "Player Name");
		
		curMap = names.get(MATCH_STATS);
		curMap.put("match_id", "Match ID");
		curMap.put("team", "Team");
		curMap.put("player", "Player");
		curMap.put("points", "Points");
		curMap.put("ppm", "Points/Minute");
		curMap.put("dmg", "Damage");
		curMap.put("ddm", "Damage/Minute");
		curMap.put("f", "Frags");
		curMap.put("fpm", "Frags/Minute");
		curMap.put("a", "Assists");
		curMap.put("apm", "Assists/Minute");
		curMap.put("d", "Deaths");
		curMap.put("dpm", "Deaths/Minute");
		curMap.put("cpc", "Cap. Points Captured");
		curMap.put("cpb", "Captures Stopped");
		curMap.put("dom", "Dominations");
		curMap.put("rev", "Revenges");
		curMap.put("uc", "Ubercharges");
		curMap.put("ucd", "Ubercharge Drops");
		
		curMap = names.get(TEAM_STAT);
		curMap.put("team_id", "Team ID");
		curMap.put("match_id", "Match ID");
		curMap.put("round_wins", "Round Wins");
		curMap.put("result", "Result");
		
		curMap = names.get(MATCH);
		curMap.put("match_id", "Match ID");
		curMap.put("season_no", "Season #");
		curMap.put("map", "Map");
		curMap.put("length_hours", "Hours");
		curMap.put("length_minutes", "Minutes");
		curMap.put("length_seconds", "Seconds");
		
		curMap = names.get(PLAYER_STAT);
		curMap.put("player_id", "Player ID");
		curMap.put("points", "Points");
		curMap.put("ppm", "Points/Minute");
		curMap.put("dmg", "Damage");
		curMap.put("ddm", "Damage/Minute");
		curMap.put("f", "Frags");
		curMap.put("fpm", "Frags/Minute");
		curMap.put("a", "Assists");
		curMap.put("apm", "Assists/Minute");
		curMap.put("d", "Deaths");
		curMap.put("dpm", "Deaths/Minute");
		curMap.put("cpc", "Capture Points");
		curMap.put("cpb", "Captures Stopped");
		curMap.put("dom", "Dominations");
		curMap.put("rev", "Revenges");
		curMap.put("uc", "Ubercharges");
		curMap.put("ucd", "Ubercharge Drops");
		curMap.put("match_id", "Match ID");
		curMap.put("team_id", "Team ID");
		
		curMap = names.get(TEAM);
		curMap.put("team_id", "Team ID");
		curMap.put("team_name", "Team Name");
		
		curMap = names.get(SEASON);
		curMap.put("end_year", "End Year");
		curMap.put("end_month", "End Month");
		curMap.put("end_date", "End Day");
		curMap.put("start_year", "Start Year");
		curMap.put("start_month", "Start Month");
		curMap.put("start_date", "Start Day");
		curMap.put("season_no", "Season #");
		
		curMap = names.get(PARTICIPATES);
		curMap.put("season_no", "Season #");
		curMap.put("team_id", "Team ID");
		curMap.put("wins", "Wins");
		curMap.put("losses", "Losses");
		curMap.put("rounds", "Rounds");
		
		curMap = names.get(SCORE);
		curMap.put("match_id", "Match ID");
		curMap.put("season_no", "Season #");
		curMap.put("result", "Result");
		curMap.put("team", "Team");
		curMap.put("rf", "Rounds Won");
		curMap.put("ra", "Rounds Lost");
		curMap.put("opponent", "Opponent");
		curMap.put("map", "Map");

	}
	
	public String getNameFor(String tableName, String attributeName){
		if(hasNameFor(tableName, attributeName)){
			return names.get(tableName).get(attributeName);
		}else{
			return new String();
		}
	}
	
	public boolean hasNameFor(String tableName, String attributeName){
		if(names.containsKey(tableName)){
			if(names.get(tableName).containsKey(attributeName)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
}
