package package1;

import java.util.ArrayList;
import java.util.Random;

//Create a auto-scrolling game log that shows what is happening ??
//EG: player 1 is out. \n It is player 2's turn. \n It is player 3's turn
//\n player 4 won! ??
//Add restrictions to size of board and nmr of players
//Create an AI
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
	public SurroundGame(int BDSIZE, int totalPlayers, int player) {
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

	public void newGame(int BDSIZE, int totalPlayers, int player) {
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
					return 1;
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
			boolean surround = false;
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
			if(getSurround(rowUp, col) == 3 || 
					getSurround(rowDown, col) == 3 ||
					getSurround(row, colLeft) == 3 ||
					getSurround(row, colRight) == 3 ||
					getSurround(row, col) >= 3)
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

	

}
