package package1.v14;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BoardPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The square grid of click-able buttons on the game board */
	private static JButton[][] board;

	/** A stack of disabled buttons that reflects the bottom row of 
	 * the board */
	private static JButton[] topButtons;

	/** A stack of disabled buttons that reflects the top row of the
	 * board */
	private static JButton[] bottomButtons;

	/** A stack of disabled buttons that reflects the right-most row of 
	 * the board */
	private static JButton[] leftButtons;

	/** A stack of disabled buttons that reflects the left-most row of 
	 * the board */
	private static JButton[] rightButtons;

	/** An instance of the game engine to control the game */
	private static SurroundGame game;

	/** Displays the win count for each player */
	private JPanel statusPanel;

	/** Displays the game board */
	private JPanel boardPanel;

	/** Displays the click-able buttons on the board */
	private JPanel buttonsPanel;

	/** Displays the helper buttons to the left of the board */
	private JPanel leftPanel;

	/** Displays the helper buttons to the right of the board */
	private JPanel rightPanel;

	/** Displays the helper buttons above of the board */
	private JPanel topPanel;

	/** Displays the helper buttons below the board */
	private JPanel bottomPanel;

	/** Displays each player's win count */
	private static JLabel[] playerWinLabels;

	/** Displays each player's status */
	private static JLabel[] playerStatusLabels;

	/** Displays the number of player who's turn it is */
	private static JLabel turns;

	/** Stores the square layout of the click-able buttons */
	private GridLayout grid;

	/** Stores the layout of the helper buttons to the left and right
	 * of the board */
	private GridLayout vertical;

	/** Stores the layout of the helper buttons above and below the 
	 * board */
	private GridLayout horizontal;

	/** Stores the default size of all the buttons */
	private Dimension buttonSize;

	/** Stores game information for easy access */
	private static int BDSIZE;

	private int totalPlayers;

	private int player;

	Image background;

	public BoardPanel(SurroundGame game) {

		BoardPanel.game = game;

//		try {
//			background = ImageIO.read(new File("C:\\Users\\Frank\\"
//					+ "workspace\\Surround\\src\\package1\\"
//					+ "bcktile.png"));
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "Image not found");
//		}

		BoardPanel.BDSIZE = game.getBDSIZE();
		this.totalPlayers = game.getTotalPlayers();
		this.player = game.getPlayer();

		setBoard();

		repaint();
		revalidate();

		while(game.getAI()) {
			int[] rowcol = game.AIClicker();
			board[rowcol[0]][rowcol[1]].setText(
					String.valueOf(game.getPlayer()));
			game.isLoser();
			clearLoser();
			addWinner();
		}
		
		//Maybe set status panel in separate method

	}

	/******************************************************************
	 * Displays a player's number when a player clicks on a button,
	 * and then switches turn if there is no winner. If a player loses,
	 * clears the buttons with that player's number.
	 *****************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {

		for (int r = 0; r < BDSIZE; r++) {
			for (int c = 0; c < BDSIZE; c++) {
				if (e.getSource() == board[r][c]) {
					//If the button clicked has not been previously 
					//selected
					if (game.setCell(r,c)) {
						board[r][c].setText(String.valueOf(
								game.getPlayer()));
						game.isLoser();
						clearLoser();
						addWinner();
						while(game.getAI()) {
							int[] rowcol = game.AIClicker();
							board[rowcol[0]][rowcol[1]].setText(
									String.valueOf(game.getPlayer()));
							game.isLoser();
							clearLoser();
							addWinner();
						}
					}
					else
						JOptionPane.showMessageDialog(null, 
								"Pick again.");
				}
			}
		}

	}

	/******************************************************************
	 * Instantiates and displays all buttons on the game board, 
	 * including the helper buttons, using specified values for the 
	 * size of the game board, the number of players in the game, and
	 * the number of the player who starts the game. Also sets up and 
	 * displays the number of wins for each player in the game.
	 *****************************************************************/
	private void setBoard() {

		Font font = new Font("Cooper Black", Font.BOLD, 18);

		//instantiates all necessary board panels
		boardPanel = new JPanel();
		statusPanel = new JPanel();
		boardPanel.setLayout(new BorderLayout());

		leftPanel = new JPanel();
		rightPanel = new JPanel();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		buttonsPanel = new JPanel();

		//sets the layouts of all board panels
		grid = new GridLayout(BDSIZE, BDSIZE);
		horizontal = new GridLayout(1,BDSIZE);
		vertical = new GridLayout(BDSIZE,1);

		buttonsPanel.setLayout(grid);
		leftPanel.setLayout(vertical);
		rightPanel.setLayout(vertical);
		bottomPanel.setLayout(horizontal);
		topPanel.setLayout(horizontal);

		topButtons = new JButton[BDSIZE+2];
		bottomButtons = new JButton[BDSIZE+2];
		leftButtons = new JButton[BDSIZE];
		rightButtons = new JButton[BDSIZE];

		//Sets default button dimension
		buttonSize = new Dimension(50,50);

		//Setting up the click-able buttons and adds them to the board
		board = new JButton[BDSIZE][BDSIZE];
		for (int r = 0; r < BDSIZE; r++) {
			for (int c = 0; c < BDSIZE; c++) {
				board[r][c] = new JButton("");
				board[r][c].setFont(font);
				board[r][c].setBackground(Color.white);
				board[r][c].setPreferredSize(buttonSize);
				board[r][c].addActionListener(this);

				buttonsPanel.add(board[r][c]);
			}
		}

		//Get these into separate method
		//Setting up the top row of helper buttons
		for(int i = 0; i < BDSIZE+2; i++) {

			topButtons[i] = new JButton("");
			topButtons[i].setFont(font);
			topButtons[i].setBackground(Color.lightGray);
			topButtons[i].setPreferredSize(buttonSize);
			topButtons[i].setEnabled(false);
			topPanel.add(topButtons[i]);
		}
		topButtons[0].setText("#");
		topButtons[BDSIZE+1].setText("#");

		//Setting up the bottom row of helper buttons
		for(int i = 0; i < BDSIZE+2; i++) {

			bottomButtons[i] = new JButton("");
			bottomButtons[i].setFont(font);
			bottomButtons[i].setBackground(Color.lightGray);
			bottomButtons[i].setPreferredSize(buttonSize);
			bottomButtons[i].setEnabled(false);
			bottomPanel.add(bottomButtons[i]);
		}
		bottomButtons[0].setText("#");
		bottomButtons[BDSIZE+1].setText("#");

		//Setting up the left column of helper buttons
		for(int i = 0; i < BDSIZE; i++) {

			leftButtons[i] = new JButton("");
			leftButtons[i].setFont(font);
			leftButtons[i].setBackground(Color.lightGray);
			leftButtons[i].setPreferredSize(buttonSize);
			leftButtons[i].setEnabled(false);
			leftPanel.add(leftButtons[i]);
		}

		//Setting up the right column of helper buttons
		for(int i = 0; i < BDSIZE; i++) {

			rightButtons[i] = new JButton("");
			rightButtons[i].setFont(font);
			rightButtons[i].setBackground(Color.lightGray);
			rightButtons[i].setPreferredSize(buttonSize);
			rightButtons[i].setEnabled(false);
			rightPanel.add(rightButtons[i]);
		}

		GridLayout stack = new GridLayout(totalPlayers + 2, 3);
		statusPanel.setLayout(stack);

		//Setting up the player status panel
		turns = new JLabel ("" + player);
		turns.setPreferredSize(new Dimension(100,50));
		turns.setFont(font);
		JLabel label = new JLabel("Player's turn: ");
		label.setFont(font);
		statusPanel.add(label);
		statusPanel.add(turns);
		statusPanel.add(new JLabel(""));
		JLabel wins = new JLabel("Player");
		wins.setFont(font);
		//wins.setBorder(BorderFactory.createLineBorder(Color.black));
		statusPanel.add(wins);
		JLabel winLabel = new JLabel("Wins");
		winLabel.setFont(font);
		statusPanel.add(winLabel);
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setFont(font);
		statusPanel.add(statusLabel);
		playerWinLabels = new JLabel[totalPlayers];
		playerStatusLabels = new JLabel[totalPlayers];
		//System.out.println(totalPlayers);
		//System.out.println(game.winCount.length);
		for(int i = 0; i < totalPlayers; i++) {
			int j = i+1;
			String count = ""+game.winCount[i];
			playerWinLabels[i] = new JLabel(count);
			playerWinLabels[i].setFont(font);
			playerStatusLabels[i] = new JLabel("In");
			playerStatusLabels[i].setFont(font);
			JLabel playerLabel = new JLabel("Player " + j);
			playerLabel.setFont(font);
			statusPanel.add(playerLabel);
			statusPanel.add(playerWinLabels[i]);
			statusPanel.add(playerStatusLabels[i]);
		}

		boardPanel.add(BorderLayout.CENTER, buttonsPanel);
		boardPanel.add(BorderLayout.NORTH, topPanel);
		boardPanel.add(BorderLayout.WEST, leftPanel);
		boardPanel.add(BorderLayout.EAST, rightPanel);
		boardPanel.add(BorderLayout.SOUTH, bottomPanel);

		add(statusPanel);
		add(boardPanel);

	}

	/******************************************************************
	 * Sets up the helper buttons for the game board. Helper buttons
	 * reflect the player numbers on the opposite side of the board,
	 * helping the player to see which players surround a particular
	 * button.
	 *****************************************************************/
	private static void setHelpers() {

		for (int row = 0; row < BDSIZE; row++) {
			for (int col = 0; col < BDSIZE; col++) {
				String number = board[row][col].getText();
				if(row == BDSIZE-1) {
					topButtons[col+1].setText(number);
				}
				if(row == 0) {
					bottomButtons[col+1].setText(number);
				}
				if(col == BDSIZE-1) {
					leftButtons[row].setText(number);
				}
				if(col == 0) {
					rightButtons[row].setText(number);
				}
			}
		}

	}

	/******************************************************************
	 * Color-codes all click-able buttons depending on how many
	 * opposing players have surrounded them. White means no other 
	 * players surrounding it (or the button is next to one that is 
	 * "owned" by the current player, making it safe). Cyan means one
	 * opposing player is next to the button. Orange means two 
	 * opposing players are next to the button. Red means three 
	 * opposing players surround the button. Black means the button is
	 * surrounded by opposing players - clicking on it is suicide.
	 *****************************************************************/
	private static void reColor() {

		for(int row = 0; row < BDSIZE; row++) {
			for(int col = 0; col < BDSIZE; col++) {
				if(game.getSurround(row, col)==0)
					board[row][col].setBackground(Color.white);
				if(game.getSurround(row, col)==1)
					board[row][col].setBackground(Color.cyan);
				if(game.getSurround(row, col)==2)
					board[row][col].setBackground(Color.orange);
				if(game.getSurround(row, col)==3)
					board[row][col].setBackground(Color.red);
				if(game.getSurround(row, col)==4)
					board[row][col].setBackground(Color.black);
				if(!game.select(row, col))
					board[row][col].setBackground(Color.white);
			}
		}

	}

	/******************************************************************
	 * Resets all buttons which displayed the player number of a player
	 * that has lost the game.
	 *****************************************************************/
	private static void clearLoser() {

		for(int row = 0; row < BDSIZE; row++) {
			for(int col = 0; col < BDSIZE; col++) {
				if(!board[row][col].getText().equals("")){
					String txt = board[row][col].getText();
					int player = Integer.parseInt(txt);
					if(game.getLosers().contains(player)) {
						board[row][col].setText("");
					}
				}
			}
		}

		//Updates status label whenever a player has been knocked out
		for(int i = 0; i < game.getLosers().size(); i++) {
			int player = game.getLosers().get(i)-1;
			playerStatusLabels[player].setText("Out");		
		}

		setHelpers();

	}

	/******************************************************************
	 * Switches player turn if there is no winner in the game, resets
	 * the game board, including helper buttons if there is a draw or 
	 * if a player has won, updating the player win count and player
	 * turn labels if necessary.
	 *****************************************************************/
	private static void addWinner() {

		if(game.isWinner()==-1){
			reColor();
			setHelpers();
			turns.setText("" + game.getPlayer());
			return;
		}
		if(game.isWinner()==0){
			JOptionPane.showMessageDialog(null, "This round resulted "
					+ "in a draw.");
			game.reset();
			reColor();
			for(int row = 0; row < BDSIZE; row++) {
				for(int col = 0; col < BDSIZE; col++) {
					board[row][col].setText("");
				}
			}
			for(int i = 0; i < game.getTotalPlayers(); i ++) {
				playerStatusLabels[i].setText("in");
			}
			setHelpers();
		}
		else{
			JOptionPane.showMessageDialog(null, "Player " + 
					game.isWinner() + " has won!");
			int i = game.isWinner()-1;
			game.winCount[i]++;
			playerWinLabels[i].setText(""+game.winCount[i]);
			game.reset();
			reColor();
			for(int row = 0; row < BDSIZE; row++) {
				for(int col = 0; col < BDSIZE; col++) {
					board[row][col].setText("");
				}
			}
			for(int j = 0; j < game.getTotalPlayers(); j ++) {
				playerStatusLabels[j].setText("in");
			}
			setHelpers();
		}
		
	}

//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.drawImage(background, 0, -500, null);
//	}

}
