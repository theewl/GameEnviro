package GameEnvironment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;

/* Main.java class for: 
 *   - Read list of games from Game folder
 *   - Load list of games into static savedScores HashMap
 *   - Print out savedScores HashMap
 *   - Start Game Launcher
 *   - Save savedScores HashMap into SavedScores.txt file
 *   - Terminate program
 */
public final class Main {
	
	//-------------------private global variables, used by Main class---------------------------------------------------------------------------------------
	private final static String GAME_FOLDER_NAME = "Game"; //name of folder that contains all the game packages
	private final static String SAVE_FILE_NAME = "SavedScores.txt"; //text file for saved game scores
	private final static String GAME_REGEX = "##$$"; //identify game name in SavedScores.txt file
	private final static String PLAYER_SCORE_REGEX = "@@!!"; //identify player name and score in SavedScores.txt file
	
	
	//-------------------static variables, used by package---------------------------------------------------------------------------------------------------
	static Map<String, HashMap<String, Integer>> savedScores;
	final static String GAME_ENVIR_DIRECTORY = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName() + "/"; //directory of Main package, GameEnvironment
	//Uncomment line below for Windows
	//final static String GAME_ENVIR_DIRECTORY = System.getProperty("user.dir") + "/" + new Object(){}.getClass().getPackage().getName() + "/";
	
	public static void main(String[] args) {
		System.out.println("Starting Game Environment...");
		start();		
	}
	
	//-------------------start game interface-----------------------------------------------------------------------------------------------------------------
	/* Read in list of game from Game folder
	 * Load scores into savedScores HashMap, print it out to console
	 * Run the Game Launcher
	 */
	private static void start() {
		String[] gameList = grabGameList();
		
		//If Game folder could not be found or is empty, print prompt and terminate program
		if (gameList == null) {
			System.out.println(Main.GAME_ENVIR_DIRECTORY + Main.GAME_FOLDER_NAME + "folder could not be found or folder is empty.");
			end(false);
		}
		
		//Store list of games into savedScores HashMap
		Main.savedScores = new TreeMap<String, HashMap<String, Integer>>();
		
		for (String gameName: gameList) 
			Main.savedScores.put(gameName, new HashMap<String, Integer>());		
		
		//Load saved game scores into savedScores HashMap, sort it, and print it to prompt
		loadGameScores();
		for (String gameName: gameList) 
			sortSavedScores(gameName);
		printSavedScores();	
		
		//Instantiate game launcher
		new GameGUI(gameList);
	}
	
	//-------------------start game helper classes-------------------------------------------------------------------------------------------------------------
	/* Read in the game lists/packages within the Game folder
	 * Used to determine what game is available
	 * @return a list of the games found in the folder, return null if folder does not exist or no game folder found
	 */
	private static String[] grabGameList() {
		System.out.println("Loading Game List...");
		File directory = new File(Main.GAME_ENVIR_DIRECTORY + Main.GAME_FOLDER_NAME);
		String[] subdirectory = directory.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		return subdirectory;
	}
	
	/* Load game scores from SavedScores.txt file into savedScroes HashMap
	 * @return boolean of whether or not saved game scores were loaded successfully
	 */
	private static boolean loadGameScores() {
		System.out.println("Loading Saved Scores...");
		
		//Check savedScores have at least one game (i.e. there are packages within Game Folder)
        if (Main.savedScores.size() < 1)
            return false;
        
        BufferedReader reader = null;
        
        try {
        	//Attempt to read SavedScores.txt file
        	reader = new BufferedReader(new FileReader(Main.GAME_ENVIR_DIRECTORY + Main.SAVE_FILE_NAME));
        	
        	String line, game = null;
        	String[] parse;
        	
        	//While there is a next line, read it
    		while ((line = reader.readLine()) != null) {
    			
    			//If String contains ##$$, it is the game name, store it into game String
    			if (line.contains(GAME_REGEX))
    				game = line.replace(GAME_REGEX, "");
    		
    			//If String contains @@!!, it is the player name and score, store it into savedScores HashMap
    			else if (line.contains(PLAYER_SCORE_REGEX)) {
    				parse = line.split(PLAYER_SCORE_REGEX, -2);
    				
    				//Attempt to put saved scores into savedScores HashMap, continue if failed
    				try {
    					Main.savedScores.get(game).put(parse[1], Integer.parseInt(parse[2]));
    				}
    				catch (NullPointerException e){
    					continue;
    				}
    			} 
    		}
        }
        
        //Failed to read from SavedScores.txt file
        catch (IOException e) {
            System.out.println("Failed to read player scores from SavedScores.txt file");
            return false;
        } finally {
        	
        	//Attempt to close reader
           try {reader.close();} catch (Exception ex) {/*do nothing*/}
        }
        return true;
	}
	
	/* Sort the savedScores HashMap, first by key alphabetically, then by the score of each player
	 * @param name of game
	 * Source: https://www.javacodegeeks.com/2017/09/java-8-sorting-hashmap-values-ascending-descending-order.html
	 */
	public static void sortSavedScores(String gameName) {
		HashMap<String, Integer> sorted = Main.savedScores.get(gameName)
		        .entrySet()
		        .stream()
		        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		        .collect(
		            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		                LinkedHashMap::new));
		Main.savedScores.put(gameName, sorted);
	}
	
	//-----------------------end game, clean up any messes------------------------------------------------------------------------------------------------
	
	/* Terminate program
	 * Free all instantiated objects in Main.java and highestScoreLabel, from GameGui.java
	 * @param boolean to whether or not the program should save savedScores HashMap to SavedScores.txt file
	 */
	public static void end(boolean saveGame) {
		System.out.println("Terminating Game Environment...");
		if (saveGame)
			saveGameScores();
		Main.savedScores = null;
		GameGUI.highestScoreLabel = null;
        System.exit(0);
	}
	
	//-----------------------end game helper functions----------------------------------------------------------------------------------------------------
	/* Save current game scores into SavedScores.txt file from savedScores HashMap
	 */
	private static boolean saveGameScores() { 
		System.out.println("Saving Game Scores...");
		
        //Check savedScores have at least one game (i.e. there are packages within Game Folder)
        if (Main.savedScores.size() < 1)
            return false;
        
        Writer writer = null;

        try {        	
        	//Attempt to create SavedScores.txt file
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(Main.GAME_ENVIR_DIRECTORY + Main.SAVE_FILE_NAME), "utf-8"));
            
            //Iterate through savedScores HashMap
            for (Map.Entry<String, HashMap<String, Integer>> gameList: Main.savedScores.entrySet()) {
            	
            	//Write game name and game regex into SavedScores.txt file
            	writer.write(Main.GAME_REGEX + gameList.getKey() + "\n");
            	
            	//Iterate through player and score HashMap
            	for (Map.Entry<String, Integer> playerScores : gameList.getValue().entrySet()) {     
            		
            		//Write player name, score, and name/score regex into SavedScores.txt file
                    writer.write(Main.PLAYER_SCORE_REGEX + playerScores.getKey());
                    writer.write(Main.PLAYER_SCORE_REGEX + playerScores.getValue().toString());
                    writer.write("\n");
            	}            	
            }
        } 
        
        //Failed to write to SavedScores.txt file
        catch (IOException ex) {
            System.out.println("Failed to write player scores out into SavedScores.txt file");
            return false;
        } finally {
        	
        	//Attempt to close writer
           try {writer.close();} catch (Exception ex) {/*do nothing*/}
        }
        return true;
	}
	
	
	/*
	 * Helper function to update players' scores for each game
	 * It is assumed that the player passed is the winning player: the last, current player when game ends
	 * @param name of game, name of winning player, score adjustment
	 */
	public static void addScore(String game, String player, int score) {
		// if player doesn't exist, add it to saved scores map
		if(!Main.savedScores.get(game).containsKey(player)) {
			Main.savedScores.get(game).put(player, 0);
		}
		
		// update score of existing players for chosen game
		Integer temp = Main.savedScores.get(game).get(player);
		Main.savedScores.get(game).put(player, temp + score);
	}	
	
	//-----------------------other helper function-------------------------------------------------------------------------------------------	
	/* Helper function to print out savedScores HashMap; used for debugging purposes
	 */
	private static void printSavedScores() {
		//Iterate through savedScores HashMap
        for (Map.Entry<String, HashMap<String, Integer>> gameList: Main.savedScores.entrySet()) {
        	
        	//Print game name
        	System.out.print(gameList.getKey() + ": {");
        	
        	//Iterate through player and score HashMap
        	for (Map.Entry<String, Integer> playerScores : gameList.getValue().entrySet()) {     
        		
        		//Print player name and score
        		System.out.print("(" + playerScores.getKey() + " , ");
        		System.out.print(playerScores.getValue().toString() + ")");
        	} 
        	System.out.print("}\n");
        }
	}
}
