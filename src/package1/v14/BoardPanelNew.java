package package1.v14;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BoardPanelNew extends ImagePanel implements ActionListener{

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

	private ImageIcon white, cyan, yellow, red, black, leftLabel, 
	midLabel, rightLabel, whiteIcon;

	private GridBagConstraints c;

	private Font font;
	
	private JButton start, quit, help;

	public BoardPanelNew(SurroundGame game, Image image) {

		super(image);

		BoardPanelNew.game = game;

		font = new Font("Cooper Black", Font.BOLD, 25);

		white = loadImage("normalBox.png");
		cyan = loadImage("cyanBox.png");
		yellow = loadImage("yellowBox.png");
		red = loadImage("redBox.png");
		black = loadImage("blackBox.png");
		leftLabel = loadImage("leftLabel.png");
		midLabel = loadImage("midLabel.png");
		rightLabel = loadImage("rightLabel.png");
		whiteIcon = loadImage("whiteLabel.png");

		start = new JButton("New Game");
		buttonSetUp(start);
		help = new JButton("Help");
		buttonSetUp(help);
		quit = new JButton("Quit");
		buttonSetUp(quit);
		
		BoardPanelNew.BDSIZE = game.getBDSIZE();
		this.totalPlayers = game.getTotalPlayers();
		this.player = game.getPlayer();

		setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		setStatus();
		setBoard();

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.3;
		c.weighty = 1;
		c.gridheight = 3;
		add(statusPanel);
		c.gridx = 1;
		c.gridwidth = 2;
		c.weightx = 0.7;
		add(boardPanel);

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

		//instantiates all necessary board panels
		boardPanel = new JPanel();
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
				buttonSetUp(board[r][c],white,true,buttonsPanel);
				board[r][c].addActionListener(this);
			}
		}

		//Setting up the top row of helper buttons
		for(int i = 0; i < BDSIZE+2; i++) {
			topButtons[i] = new JButton("");
			buttonSetUp(topButtons[i],white,false,topPanel);
		}
		topButtons[0].setText("#");
		topButtons[BDSIZE+1].setText("#");

		//Setting up the bottom row of helper buttons
		for(int i = 0; i < BDSIZE+2; i++) {
			bottomButtons[i] = new JButton("");
			buttonSetUp(bottomButtons[i],white,false,bottomPanel);
		}
		bottomButtons[0].setText("#");
		bottomButtons[BDSIZE+1].setText("#");

		//Setting up the left column of helper buttons
		for(int i = 0; i < BDSIZE; i++) {
			leftButtons[i] = new JButton("");
			buttonSetUp(leftButtons[i],white,false,leftPanel);
		}

		//Setting up the right column of helper buttons
		for(int i = 0; i < BDSIZE; i++) {
			rightButtons[i] = new JButton("");
			buttonSetUp(rightButtons[i],white,false,rightPanel);
		}

		boardPanel.add(BorderLayout.CENTER, buttonsPanel);
		boardPanel.add(BorderLayout.NORTH, topPanel);
		boardPanel.add(BorderLayout.WEST, leftPanel);
		boardPanel.add(BorderLayout.EAST, rightPanel);
		boardPanel.add(BorderLayout.SOUTH, bottomPanel);
		boardPanel.setOpaque(false);

	}

	private void setStatus() {

		statusPanel = new JPanel();

		GridLayout stack = new GridLayout(totalPlayers + 3, 3);
		statusPanel.setLayout(stack);

		//Setting up the player status panel
		JLabel label = new JLabel("Player's turn: ");
		labelSetUp(label, leftLabel, false);
		statusPanel.add(label);

		turns = new JLabel ("" + player);
		labelSetUp(turns, rightLabel, true);
		statusPanel.add(turns);

		statusPanel.add(new JLabel("")); //Empty label for grid

		JLabel wins = new JLabel("Player");
		labelSetUp(wins, leftLabel, false);
		statusPanel.add(wins);

		JLabel winLabel = new JLabel("Wins");
		labelSetUp(winLabel, midLabel, false);
		statusPanel.add(winLabel);

		JLabel statusLabel = new JLabel("Status");
		labelSetUp(statusLabel, rightLabel, false);
		statusPanel.add(statusLabel);

		playerWinLabels = new JLabel[totalPlayers];
		playerStatusLabels = new JLabel[totalPlayers];

		for(int i = 0; i < totalPlayers; i++) {

			int j = i+1;
			JLabel playerLabel = new JLabel("Player " + j);
			labelSetUp(playerLabel, leftLabel, true);
			statusPanel.add(playerLabel);

			String count = ""+game.winCount[i];
			playerWinLabels[i] = new JLabel(count);
			labelSetUp(playerWinLabels[i], midLabel, false);
			statusPanel.add(playerWinLabels[i]);

			playerStatusLabels[i] = new JLabel("In");
			labelSetUp(playerStatusLabels[i], rightLabel, false);
			statusPanel.add(playerStatusLabels[i]);

		}
		
		statusPanel.add(start);
		statusPanel.add(help);
		statusPanel.add(quit);

		statusPanel.setOpaque(false);

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
	 * Also color-codes all clicked buttons to correspond with the
	 * different player numbers
	 *****************************************************************/
	private void reColor() {

		for(int row = 0; row < BDSIZE; row++) {
			for(int col = 0; col < BDSIZE; col++) {
				if(game.getSurround(row, col)==0)
					board[row][col].setIcon(white);
				if(game.getSurround(row, col)==1)
					board[row][col].setIcon(cyan);
				if(game.getSurround(row, col)==2)
					board[row][col].setIcon(yellow);
				if(game.getSurround(row, col)==3)
					board[row][col].setIcon(red);
				if(game.getSurround(row, col)==4)
					board[row][col].setIcon(black);
				if(!game.select(row, col))
					board[row][col].setIcon(white);

				String number = board[row][col].getText();
				if(number.equals("1"))
					board[row][col].setForeground(Color.blue);
				if(number.equals("2"))
					board[row][col].setForeground(Color.cyan);
				if(number.equals("3"))
					board[row][col].setForeground(Color.green);
				if(number.equals("4"))
					board[row][col].setForeground(Color.yellow);
				if(number.equals("5"))
					board[row][col].setForeground(Color.orange);
				if(number.equals("6"))
					board[row][col].setForeground(Color.red);
				if(number.equals("7"))
					board[row][col].setForeground(Color.pink);
				if(number.equals("8"))
					board[row][col].setForeground(Color.magenta);
				if(number.equals("9"))
					board[row][col].setForeground(Color.black);
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
	private void addWinner() {

		if(game.isWinner()==-1){
			reColor();
			setHelpers();
			turns.setText("" + game.getPlayer());
			String number = turns.getText();
			if(number.equals("1"))
				turns.setForeground(Color.blue);
			if(number.equals("2"))
				turns.setForeground(Color.cyan);
			if(number.equals("3"))
				turns.setForeground(Color.green);
			if(number.equals("4"))
				turns.setForeground(Color.yellow);
			if(number.equals("5"))
				turns.setForeground(Color.orange);
			if(number.equals("6"))
				turns.setForeground(Color.red);
			if(number.equals("7"))
				turns.setForeground(Color.pink);
			if(number.equals("8"))
				turns.setForeground(Color.magenta);
			if(number.equals("9"))
				turns.setForeground(Color.black);

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

	private ImageIcon loadImage(String imageName) {

		ImageIcon image = null;
		image = new ImageIcon("C:\\Users\\Frank\\"
				+ "workspace\\Surround\\src\\package1\\"
				+ imageName);
		return image;

	}

	private void buttonSetUp(JButton button, ImageIcon icon, 
			Boolean enabled, JPanel panel) {

		button.setFont(font);
		button.setIcon(icon);
		button.setContentAreaFilled(false);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setPreferredSize(buttonSize);
		button.setEnabled(enabled);
		panel.add(button);

	}

	private void labelSetUp(JLabel label, ImageIcon icon, 
			boolean code) {
		label.setIcon(icon);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setPreferredSize(new Dimension(200,50));
		label.setFont(font);
		if(code) {
			String number = label.getText();
			if(number.contains("1"))
				label.setForeground(Color.blue);
			if(number.contains("2"))
				label.setForeground(Color.cyan);
			if(number.contains("3"))
				label.setForeground(Color.green);
			if(number.contains("4"))
				label.setForeground(Color.yellow);
			if(number.contains("5"))
				label.setForeground(Color.orange);
			if(number.contains("6"))
				label.setForeground(Color.red);
			if(number.contains("7"))
				label.setForeground(Color.pink);
			if(number.contains("8"))
				label.setForeground(Color.magenta);
			if(number.contains("9"))
				label.setForeground(Color.black);
		}
	}
	
	private void buttonSetUp(JButton button) {
		
		button.setIcon(whiteIcon);
		button.setPreferredSize(new Dimension(200,50));
		button.setFont(font);
		button.setContentAreaFilled(false);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.addActionListener(this);
		
	}
	
	public JButton getStart() {
		return start;
	}
	
	public JButton getHelp() {
		return help;
	}
	
	public JButton getQuit() {
		return quit;
	}

}
