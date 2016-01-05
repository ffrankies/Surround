package package1.v13;

import java.util.ArrayList;
import java.util.Random;

//Make first turn not allow kills - Done!
//Include a turn counter - Done!
//Include a status thingy to show which players are out - Done!
//Make it full-sized - Done!
//Create an AI - Done!
//Current AI Searches for all available cells, clicks on one
//Make menu multi-faceted. Or whatever. The thing from Database - Done!
//Center align the game board and dialog - Done!

//Use a JPanel instead of a JDialog?? - prolly not
//Add restrictions to size of board and nmr of players
//Create a auto-scrolling game log that shows what is happening ??
//EG: player 1 is out. \n It is player 2's turn. \n It is player 3's turn
//\n player 4 won! ??
//Ideas: 1) Method for search kill, setup, danger, and safe
//2) Randomly pick one of the 4 methods
//3) Search all cells that satisfy picked category
//4) Randomly pick one of those and click on it
//5) If no cells, pick another method
//Include difficulty levels
//Difficulty decided by how often a method is chosen
//Can be achieved by changing number of 0s for kill, 1s for setup, etc
//Add different themes

//Add instructions for the game
//Comment whole project
//Create in new executable

//Complete to-do-list project
//Create the minion clock thing you thought of making before

public class SurroundGame {

	/** The game board containing cells */
	private Cell[][] board;

	/** The number of the last row on the board */
	private int rowEnd;

	/** The number of the last column on the board */
	private int colEnd;

	/** The size of the board. It must be between 3 and 20?*/
	private int BDSIZE;

	/** The number of the player whose turn it is */
	private int player; 

	/** Keeps track of which player is AI and which is human */
	private Player[] players;

	/** The total number of players. It must be between 2 and 10 */
	private int totalPlayers;

	/** Keeps track of the number of wins for each player */
	public int[] winCount;

	/** Keeps track of which players are kicked out in each round */
	private ArrayList <Integer> losers;

	/** Keeps track of which turn in the game it is */
	private int turn;

	/******************************************************************
	 * Instantiates a game with a specified board size, number of 
	 * players, and starting player
	 * @param BDSIZE is the size of the board. It can be any integer
	 * value from 3 to 20
	 * @param totalPlayers is the number of players. It can be any 
	 * integer from 2 to 10
	 * @param player is the number of the player who starts the game.
	 * It can be any integer value from 1 to the totalPlayers value
	 *****************************************************************/
	public SurroundGame(int BDSIZE, int totalPlayers, int player, 
			int human) {
		this.BDSIZE = BDSIZE;
		board = new Cell[BDSIZE][BDSIZE];
		rowEnd = BDSIZE-1;
		colEnd = BDSIZE-1;
		this.totalPlayers = totalPlayers;
		this.player = player;
		losers = new ArrayList <Integer>();
		winCount = new int[totalPlayers];
		for(int i = 0; i < totalPlayers; i++){
			winCount[i] = 0;
		}
		turn = 1;
		players = new Player[totalPlayers];
		for(int i = 0; i < totalPlayers; i++) {
			if(i < human)
				players[i] = new Player(false);
			else
				players[i] = new Player(true);
		}
	}

	/******************************************************************
	 * Obtains the list of players who have been kicked out in this 
	 * round
	 * @return an arraylist containing the numbers of all players 
	 * knocked out in this round
	 *****************************************************************/
	public ArrayList <Integer> getLosers() {
		return losers;
	}

	public void newGame(int BDSIZE, int totalPlayers, int player, 
			int human) {
		this.BDSIZE = BDSIZE;
		board = new Cell[BDSIZE][BDSIZE];
		rowEnd = BDSIZE-1;
		colEnd = BDSIZE-1;
		this.totalPlayers = totalPlayers;
		this.player = player;
		losers = new ArrayList <Integer>();
		winCount = new int[totalPlayers];
		for(int i = 0; i < totalPlayers; i++){
			winCount[i] = 0;
		}
		turn = 1;
		players = new Player[totalPlayers];
		for(int i = 0; i < totalPlayers; i++) {
			if(i < human)
				players[i] = new Player(false);
			else
				players[i] = new Player(true);
		}
	}

	/******************************************************************
	 * Sets a particular cell in the board is empty
	 * @param row is the number of the row in which the cell is found
	 * @param col is the number of the column in which the cell is found
	 * @return true if the cell is empty, false if it is not
	 *****************************************************************/
	public Boolean select (int row, int col) {
		return board[row][col] == null;
	}

	/******************************************************************
	 * Sets all cells to a null (empty) value, and resets the arrayList
	 * of losers
	 *****************************************************************/
	public void reset() {
		for(int r = 0; r < BDSIZE; r++) {
			for(int c = 0; c < BDSIZE; c++) {
				board[r][c] = null;
			}
		}
		losers = new ArrayList<Integer>();
		turn = 1;
	}

	/******************************************************************
	 * Obtains the number of the player whose turn it is
	 * @return the number of the player whose turn it is
	 *****************************************************************/
	public int getPlayer() {
		return this.player;
	}

	/******************************************************************
	 * Check if the current player is AI or human-controlled
	 * @return true if current player is AI, false if current player
	 * is human-controlled
	 *****************************************************************/
	public boolean getAI() {
		return players[player-1].getAI();
	}

	/******************************************************************
	 * Obtains the total number of players in the game
	 * @return the total number of players in the game
	 *****************************************************************/
	public int getTotalPlayers() {
		return this.totalPlayers;
	}

	/******************************************************************
	 * Obtains the size of the board
	 * @return the size of the board
	 *****************************************************************/
	public int getBDSIZE() {
		return this.BDSIZE;
	}

	/******************************************************************
	 * Removes a particular player from the board
	 * @param r is the row in which the player's cell is found
	 * @param c is the column in which the player's cell is found
	 *****************************************************************/
	public void removeLoser(int r, int c) {
		int loser = board[r][c].getPlayerNumber();
		for(int row = 0; row <= rowEnd; row++) {
			for(int col = 0; col <= colEnd; col++) {
				if(!select(row,col)){
					if(board[row][col].getPlayerNumber() ==
							loser){
						board[row][col]=null;
					}
				}
			}
		}
	}

	/******************************************************************
	 * Goes through each cell on the board, and if the cell is
	 * surrounded by other players' cells, the player who owns the cell
	 * is declared a loser and removed from the board
	 *****************************************************************/
	public void isLoser() {
		for(int row = 0; row <= rowEnd; row++) {
			for(int col = 0; col <= colEnd; col++) {
				if(!select(row,col)) {
					int rowUp = row-1; //rowUp is for cells above
					if(rowUp<0)			//current cell
						rowUp = rowEnd;
					int rowDown = row+1; //rowDown is for cells below
					if(rowDown>rowEnd)		//current cell
						rowDown = 0;
					int colLeft = col -1; //colLeft is for cells to the
					if(colLeft<0)			//left of current cell
						colLeft = colEnd;
					int colRight = col +1; //colRight is for cells to
					if(colRight>colEnd)		//the right of current cell
						colRight = 0;
					if(!select(rowUp,col) && !select(rowDown,col) &&
							!select(row,colLeft) && 
							!select(row,colRight) 
							&& board[row][colLeft].getPlayerNumber()
							!= board[row][col].getPlayerNumber()
							&& board[row][colRight].getPlayerNumber()
							!= board[row][col].getPlayerNumber()
							&& board[rowUp][col].getPlayerNumber()
							!= board[row][col].getPlayerNumber()
							&& board[rowDown][col].getPlayerNumber()
							!= board[row][col].getPlayerNumber()) {
						losers.add(board[row][col].getPlayerNumber());
						removeLoser(row,col);
					}
				}
			}
		}
	}

	/******************************************************************
	 * Checks if there is a winner in the game
	 * @return 1 if there is a winner, 0 if it is a draw, -1 if there
	 * is no winner
	 *****************************************************************/
	public int isWinner() {
		if(losers.size() == totalPlayers - 1) {
			for(int p = 1; p <= totalPlayers; p++) {
				if(!losers.contains(p))
					return p;
			}
		}
		int count = 0;
		for(int row = 0; row < BDSIZE; row++) {
			for(int col = 0; col < BDSIZE; col++) {
				if(!select(row,col))
					count++;
			}
		}
		if(count == BDSIZE*BDSIZE)
			return 0;
		nextPlayer();
		return -1;
	}

	/******************************************************************
	 * Switches turn to next player. Skips all players who have already
	 * been knocked out in this round
	 * @return the number of the player who's turn it is
	 *****************************************************************/
	public int nextPlayer() {
		player = (player + 1) % totalPlayers;
		if(player == 0)
			player = totalPlayers;
		if(losers.contains(player))
			nextPlayer();
		if(player == 0)
			player = totalPlayers;
		turn++;
		return player;
	}

	/******************************************************************
	 * Changes the 'ownership' of a specified cell to the current
	 * player
	 * @param row is the row in which the cell is found
	 * @param col is the column in which the cell is found
	 *****************************************************************/
	public boolean setCell(int row, int col) {

		//If cell already has a value
		if(board[row][col] != null)
			return false;

		//Does not allow kills during first two round
		if(turn <= totalPlayers * 2) {
			int rowUp = row-1;
			if(rowUp<0)
				rowUp = rowEnd;
			int rowDown = row+1;
			if(rowDown>rowEnd)
				rowDown = 0;
			int colLeft = col -1;
			if(colLeft<0)
				colLeft = colEnd;
			int colRight = col +1;
			if(colRight>colEnd)
				colRight = 0;
			//With getSurround, does not disallow users from doing this
			//With getKill, game freezes if surround AI in first turn
			if(getKill(rowUp, col) == 3 || 
					getKill(rowDown, col) == 3 ||
					getKill(row, colLeft) == 3 ||
					getKill(row, colRight) == 3 ||
					getKill(row, col) >= 3)
				return false;
		}

		//Normal placement of cell
		board[row][col] = new Cell(player);
		return true;
	}

	/******************************************************************
	 * Checks how many opposing players' cells surround a particular
	 * cell
	 * @param row is the row in which the cell is found
	 * @param col is the column in which the cell is found
	 * @return an integer between 0 and 4, representing the count of 
	 * the number of opposing players' cells surrounding the cell in 
	 * question
	 *****************************************************************/
	public int getSurround(int row, int col) {
		int count = 0;
		int rowUp = row-1;
		if(rowUp<0)
			rowUp = rowEnd;
		int rowDown = row+1;
		if(rowDown>rowEnd)
			rowDown = 0;
		int colLeft = col -1;
		if(colLeft<0)
			colLeft = colEnd;
		int colRight = col +1;
		if(colRight>colEnd)
			colRight = 0;
		if( !select(row,colLeft) && 
				board[row][colLeft].getPlayerNumber() != player)
			count++;
		if( !select(row,colRight) && 
				board[row][colRight].getPlayerNumber() != player)
			count++;
		if( !select(rowUp,col) && board[rowUp][col].getPlayerNumber()
				!= player)
			count++;
		if( !select(rowDown,col) && 
				board[rowDown][col].getPlayerNumber() != player)
			count++;
		if( !select(rowDown,col) && 
				board[rowDown][col].getPlayerNumber()==player || 
				!select(rowUp,col) && 
				board[rowUp][col].getPlayerNumber()==player || 
				!select(row,colLeft) &&
				board[row][colLeft].getPlayerNumber()==player || 
				!select(row,colRight) && 
				board[row][colRight].getPlayerNumber()==player )
			count = 0;
		return count;
	}

	/******************************************************************
	 * Checks how many opposing players' cells surround a particular
	 * cell
	 * @param row is the row in which the cell is found
	 * @param col is the column in which the cell is found
	 * @return an integer between 0 and 4, representing the count of 
	 * the number of opposing players' cells surrounding the cell in 
	 * question
	 *****************************************************************/
	public int getKill(int row, int col) {
		int count = 0;
		int center;
		int rowUp = row-1;
		if(rowUp<0)
			rowUp = rowEnd;
		int rowDown = row+1;
		if(rowDown>rowEnd)
			rowDown = 0;
		int colLeft = col -1;
		if(colLeft<0)
			colLeft = colEnd;
		int colRight = col +1;
		if(colRight>colEnd)
			colRight = 0;
		if(select(row,col))
			return -1;
		center = board[row][col].getPlayerNumber();
		if(center != player) {
			if( !select(row,colLeft) && 
					board[row][colLeft].getPlayerNumber() != center)
				count++;
			if( !select(row,colRight) && 
					board[row][colRight].getPlayerNumber() != center)
				count++;
			if( !select(rowUp,col) && board[rowUp][col].getPlayerNumber()
					!= center)
				count++;
			if( !select(rowDown,col) && 
					board[rowDown][col].getPlayerNumber() != center)
				count++;
			if( !select(rowDown,col) && 
					board[rowDown][col].getPlayerNumber()==center || 
					!select(rowUp,col) && 
					board[rowUp][col].getPlayerNumber()==center || 
					!select(row,colLeft) &&
					board[row][colLeft].getPlayerNumber()==center || 
					!select(row,colRight) && 
					board[row][colRight].getPlayerNumber()==center )
				count = 0;
		}
		return count;
	}

	/******************************************************************
	 * Sets the size of the board to a specified value
	 * @param BDSIZE is the size of the board
	 *****************************************************************/
	public void setBDSIZE(int BDSIZE) {
		this.BDSIZE = BDSIZE;
	}

	/******************************************************************
	 * Sets the total number of players to a specified value
	 * @param totalPlayers is the total number of players playing
	 * the game
	 *****************************************************************/
	public void setTotalPlayers(int totalPlayers) {
		this.totalPlayers = totalPlayers;
	}

	/******************************************************************
	 * Sets the player who starts the game
	 * @param player is the number of the player that starts the game
	 *****************************************************************/
	public void setPlayer(int player) {
		this.player = player;
	}

	/******************************************************************
	 * Simulates an AI clicking on an available cell
	 * @return an array containing the row and column numbers,
	 * respectively, of the cell the AI 'clicks' on
	 *****************************************************************/
	public int[] AIClicker() {

		//Holds coordinates of all empty or 'clickable' cells
		ArrayList<Integer> clickable = new ArrayList<Integer>();

		//Searches for all cells of AI player about to get killed
		//Plays it safe
		clickable = searchSafe();

		//Searches for all cells of other players it can kill and adds
		//them to clickable
		if(clickable.size() == 0)
			clickable = searchKill();
		
		if(clickable.size() == 0)
			clickable = searchSetUp();

		//If there are no 'killing' cells, then adds all empty cells 
		//to clickable
		if(clickable.size() == 0) {

			//Adds said coordinates to the arrayList
			for(int row = 0; row < BDSIZE; row++) {
				for(int col = 0; col < BDSIZE; col++) {
					if(select(row,col)) {
						clickable.add(row);
						clickable.add(col);
					}
				}
			}

		}

		Random generator = new Random();

		boolean worked = false;

		int random, row = 0, col = 0;

		//Picks a random set of coordinates and tries calling the 
		//setCell method. If that returns false, tries again with
		//a different set of coordinates.
		while(worked == false) {
			random = generator.nextInt(clickable.size()/2);
			row = clickable.get(random*2);
			col = clickable.get((random*2) + 1);
			worked = setCell(row,col);
		}

		if(worked) {
			int[] output = {row,col};
			return output;
		}
		else
			return null;

	}

	/******************************************************************
	 * Searches for all cases where the AI can knock a player out of 
	 * the game
	 * @return an ArrayList containing the row and column numbers of 
	 * all cells, by clicking on which, the AI player knocks someone
	 * out of the game
	 *****************************************************************/
	private ArrayList<Integer> searchKill() {

		ArrayList<Integer> output = new ArrayList<Integer>();

		if(turn >= totalPlayers*2) {

			for(int row = 0; row < BDSIZE; row++) {
				for(int col = 0; col < BDSIZE; col++) {

					if(getKill(row,col) == 3) {

						int rowUp = row-1;
						if(rowUp<0)
							rowUp = rowEnd;
						int rowDown = row+1;
						if(rowDown>rowEnd)
							rowDown = 0;
						int colLeft = col -1;
						if(colLeft<0)
							colLeft = colEnd;
						int colRight = col +1;
						if(colRight>colEnd)
							colRight = 0;

						if(select(rowUp, col)) {
							output.add(rowUp);
							output.add(col);
						}

						if(select(rowDown, col)) {
							output.add(rowDown);
							output.add(col);
						}

						if(select(row, colLeft)) {
							output.add(row);
							output.add(colLeft);
						}

						if(select(row, colRight)) {
							output.add(row);
							output.add(colRight);
						}

					}

				}

			}

		}

		return output;

	}

	/******************************************************************
	 * AI searches for all of the cells it 'owns' that are about to get
	 * surrounded
	 * @return an ArrayList containing the row and column numbers of
	 * all of its own cells about to get surrounded
	 *****************************************************************/
	private ArrayList<Integer> searchSafe() {

		ArrayList<Integer> output = new ArrayList<Integer>();

		for(int row = 0; row < BDSIZE; row++) {
			for(int col = 0; col < BDSIZE; col++) {

				if(!select(row,col) && getSurround(row,col) == 3) {

					int rowUp = row-1;
					if(rowUp<0)
						rowUp = rowEnd;
					int rowDown = row+1;
					if(rowDown>rowEnd)
						rowDown = 0;
					int colLeft = col -1;
					if(colLeft<0)
						colLeft = colEnd;
					int colRight = col +1;
					if(colRight>colEnd)
						colRight = 0;

					if(select(rowUp, col)) {
						output.add(rowUp);
						output.add(col);
					}

					if(select(rowDown, col)) {
						output.add(rowDown);
						output.add(col);
					}

					if(select(row, colLeft)) {
						output.add(row);
						output.add(colLeft);
					}

					if(select(row, colRight)) {
						output.add(row);
						output.add(colRight);
					}

				}

			}

		}

		return output;

	}

	/******************************************************************
	 * Searches for all cases where the AI can click nearby and put an
	 * opponent's cell in danger
	 * @return an ArrayList containing the row and column numbers of 
	 * all cells, by clicking on which, the AI player puts an 
	 * opponent's cell in danger
	 *****************************************************************/
	private ArrayList<Integer> searchSetUp() {

		ArrayList<Integer> output = new ArrayList<Integer>();

		for(int row = 0; row < BDSIZE; row++) {
			for(int col = 0; col < BDSIZE; col++) {

				if(getKill(row,col) == 2 || getKill(row,col) == 1) {

					int rowUp = row-1;
					if(rowUp<0)
						rowUp = rowEnd;
					int rowDown = row+1;
					if(rowDown>rowEnd)
						rowDown = 0;
					int colLeft = col -1;
					if(colLeft<0)
						colLeft = colEnd;
					int colRight = col +1;
					if(colRight>colEnd)
						colRight = 0;

					if(select(rowUp, col)) {
						output.add(rowUp);
						output.add(col);
					}

					if(select(rowDown, col)) {
						output.add(rowDown);
						output.add(col);
					}

					if(select(row, colLeft)) {
						output.add(row);
						output.add(colLeft);
					}

					if(select(row, colRight)) {
						output.add(row);
						output.add(colRight);
					}

				}

			}

		}

		return output;

	}

}
