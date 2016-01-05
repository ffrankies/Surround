package package1.v14;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OptionsDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SurroundGame game;

	private boolean closeStatus;

	private Image background, panelBackground, options;

	private Icon selected, notSelected, buttonIcon, cButtonIcon, 
	cDisabledButtonIcon;

	private JRadioButton[] brdSize, nbrPlayers, nbrHumans, startPlayer;

	private ButtonGroup brdSizeGroup, nbrPlayersGroup, nbrHumansGroup, 
	startPlayerGroup;

	private JPanel brdSizePanel, nbrPlayersPanel, nbrHumansPanel, 
	startPlayerPanel, buttonsPanel, title, sizePanel, playersPanel,
	humansPanel, startPanel;

	private BoxLayout box;

	private ImagePanel container;

	private JButton startButton, cancelButton, quitButton;
	
	private Font buttonFont, font;
	
	private SurroundFrame parent;

	/*
	 * Basically trying to create a top to bottom alignment of the 
	 * game options, including quit button, using box layout
	 * Using radio buttons instead of drop-down menus
	 * Tryna use label to hold radio buttons, aligned left-to-right
	 * Have to create icons for radio buttons and all 3 buttons. Or
	 * maybe just one button icon, add text. Hmmm....
	 */
	public OptionsDialog(SurroundFrame parent) {

		super(parent, true);

		game = new SurroundGame(10,2,1,1);
		
		this.parent = parent;

		setUndecorated(true);
		closeStatus = false;

		try {
			background = ImageIO.read(new File("C:\\Users\\Frank\\"
					+ "workspace\\Surround\\src\\package1\\"
					+ "bcktile.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image not found");
		}
		try {
			panelBackground = ImageIO.read(new File("C:\\Users\\Frank\\"
					+ "workspace\\Surround\\src\\package1\\"
					+ "panelBackground.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image not found");
		}
		try {
			options = ImageIO.read(new File("C:\\Users\\Frank\\"
					+ "workspace\\Surround\\src\\package1\\"
					+ "optionsBackground.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image not found");
		}

		buttonIcon = new ImageIcon("C:\\Users\\Frank\\"
				+ "workspace\\Surround\\src\\package1\\"
				+ "buttonIcon.png");
		cButtonIcon = new ImageIcon("C:\\Users\\Frank\\"
				+ "workspace\\Surround\\src\\package1\\"
				+ "cButtonIcon.png");
		cDisabledButtonIcon = new ImageIcon("C:\\Users\\Frank\\"
				+ "workspace\\Surround\\src\\package1\\"
				+ "cDisabledButtonIcon.png");
		selected = new ImageIcon("C:\\Users\\Frank\\"
				+ "workspace\\Surround\\src\\package1\\"
				+ "sel.png");
		notSelected = new ImageIcon("C:\\Users\\Frank\\"
				+ "workspace\\Surround\\src\\package1\\"
				+ "notSel.png");

		font = new Font("Cooper Black", Font.BOLD, 35 );
		buttonFont = new Font("Cooper Black", Font.BOLD, 70 );

		startButton = new JButton("Start");
		buttonSetUp(startButton);

		cancelButton = new JButton("Cancel");
		buttonSetUp(cancelButton);
		cancelButton.setPreferredSize(new Dimension(300,100));
		cancelButton.setIcon(cButtonIcon);
		cancelButton.setDisabledIcon(cDisabledButtonIcon);
		if(parent.getStart()) 
			cancelButton.setEnabled(false);

		quitButton = new JButton("Quit");
		buttonSetUp(quitButton);

		brdSizePanel = new JPanel();
		panelSetUp(brdSizePanel);

		nbrPlayersPanel = new JPanel();
		panelSetUp(nbrPlayersPanel);

		nbrHumansPanel = new JPanel();
		panelSetUp(nbrHumansPanel);

		startPlayerPanel = new JPanel();
		panelSetUp(startPlayerPanel);

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setOpaque(false);
		buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		buttonsPanel.add(startButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10,0)));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10,0)));
		buttonsPanel.add(quitButton);

		brdSizeGroup = new ButtonGroup();
		nbrPlayersGroup = new ButtonGroup();
		nbrHumansGroup = new ButtonGroup();
		startPlayerGroup = new ButtonGroup();

		title = new ImagePanel(options);
		JLabel titleLabel = new JLabel("Game Options", 
				SwingConstants.CENTER);
		titleLabel.setFont( new Font("Cooper Black", Font.BOLD, 80 ) );
		title.add(titleLabel);
		title.setOpaque(false);
		
		sizePanel = new ImagePanel(panelBackground);
		sizePanel.setLayout(
				new BoxLayout(sizePanel,BoxLayout.PAGE_AXIS));
		sizePanel.setPreferredSize(new Dimension(600,85));
		JLabel sizeLabel = new JLabel("Size of the Game Board:", 
				SwingConstants.CENTER);
		sizeLabel.setFont(font);
		sizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sizePanel.add(Box.createRigidArea(new Dimension(0,10)));
		sizePanel.add(sizeLabel);
		sizePanel.setOpaque(false);
		
		playersPanel = new ImagePanel(options);
		playersPanel.setLayout(
				new BoxLayout(playersPanel,BoxLayout.PAGE_AXIS));
		playersPanel.setPreferredSize(new Dimension(600,85));
		JLabel playersLabel = new JLabel("Number of Players:", 
				SwingConstants.CENTER);
		playersLabel.setFont(font);
		playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playersPanel.add(Box.createRigidArea(new Dimension(0,10)));
		playersPanel.add(playersLabel);
		playersPanel.setOpaque(false);
		
		humansPanel = new ImagePanel(options);
		humansPanel.setLayout(
				new BoxLayout(humansPanel,BoxLayout.PAGE_AXIS));
		humansPanel.setPreferredSize(new Dimension(600,85));
		JLabel humansLabel = new JLabel("Number of Human Players:",
				SwingConstants.CENTER);
		humansLabel.setFont(font);
		humansLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		humansPanel.add(Box.createRigidArea(new Dimension(0,10)));
		humansPanel.add(humansLabel);
		humansPanel.setOpaque(false);

		startPanel = new ImagePanel(options);
		startPanel.setLayout(
				new BoxLayout(startPanel,BoxLayout.PAGE_AXIS));
		startPanel.setPreferredSize(new Dimension(600,85));
		JLabel startLabel = new JLabel("Player that Starts the Game:",
				SwingConstants.CENTER);
		startLabel.setFont(font);
		startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		startPanel.add(Box.createRigidArea(new Dimension(0,10)));
		startPanel.add(startLabel);
		startPanel.setOpaque(false);
		
		brdSize = new JRadioButton[8];
		for(int i = 0; i < brdSize.length; i++) {
			int size = i + 4;
			brdSize[i] = new JRadioButton(size + " x " + size);
			radioSetUp(brdSize[i], i+4);
			brdSizePanel.add(brdSize[i]);
			brdSizeGroup.add(brdSize[i]);
		}
		
		sizePanel.add(brdSizePanel);
		
		nbrPlayers = new JRadioButton[8];
		for(int i = 0; i < nbrPlayers.length; i++) {
			nbrPlayers[i] = new JRadioButton(Integer.toString(i+2));
			radioSetUp(nbrPlayers[i],i+2);
			nbrPlayersPanel.add(nbrPlayers[i]);
			nbrPlayersGroup.add(nbrPlayers[i]);
		}
		
		playersPanel.add(nbrPlayersPanel);

		nbrHumans = new JRadioButton[9];
		for(int i = 0; i < nbrHumans.length; i++) {
			nbrHumans[i] = new JRadioButton(Integer.toString(i+1));
			radioSetUp(nbrHumans[i],i+1);
			nbrHumansPanel.add(nbrHumans[i]);
			nbrHumansGroup.add(nbrHumans[i]);
		}
		
		humansPanel.add(nbrHumansPanel);
		
		startPlayer = new JRadioButton[9];
		for(int i = 0; i < startPlayer.length; i++) {
			startPlayer[i] = new JRadioButton(Integer.toString(i+1));
			radioSetUp(startPlayer[i],i+1);
			startPlayerPanel.add(startPlayer[i]);
			startPlayerGroup.add(startPlayer[i]);
		}
		
		startPanel.add(startPlayerPanel);

		container = new ImagePanel(background);

		box = new BoxLayout(container, BoxLayout.PAGE_AXIS);

		container.setLayout(box);

		container.add(Box.createRigidArea(new Dimension(0,28)));
		container.add(title);
		container.add(Box.createRigidArea(new Dimension(0,18)));
		container.add(sizePanel);
		container.add(playersPanel);
		container.add(humansPanel);
		container.add(startPanel);
		container.add(buttonsPanel);

		getContentPane().add(container);
		
		setDefaults();

		//setAlwaysOnTop(true);

		setSize(parent.getSize());
		setLocation(parent.getLocation());

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i = 0; i < nbrPlayers.length; i++) {
			if(e.getSource() == nbrPlayers[i]) {
				enableAll();
				if(i == 6) {
					nbrHumans[8].setEnabled(false);
					startPlayer[8].setEnabled(false);
				}
				if(i < 6) {
					for(int j = i+2; j < nbrHumans.length; j++) {
						nbrHumans[j].setEnabled(false);
						startPlayer[j].setEnabled(false);
					}
				}
			}
		}
		
		if(e.getSource() == startButton) {
			
			String board = (String) brdSizeGroup.getSelection().
					getActionCommand();
			int BDSIZE = Integer.parseInt(board);
			String players = (String) nbrPlayersGroup.getSelection().
					getActionCommand();
			int totalPlayers = Integer.parseInt(players);
			String starting = (String) startPlayerGroup.getSelection().
					getActionCommand();
			int start = Integer.parseInt(starting);
			String humans = (String) nbrHumansGroup.getSelection().
					getActionCommand();
			int human = Integer.parseInt(humans);
			if(start > totalPlayers) {
				JOptionPane.showMessageDialog(null,"Starting player's"
						+ "number must be less than or equal to the "
						+ "number of players");
				return;
			}
			if(human > totalPlayers) {
				JOptionPane.showMessageDialog(null,"Starting player's"
						+ "number must be less than or equal to the "
						+ "number of players");
				return;
			}
			
			game.reset();
			game.newGame(BDSIZE, totalPlayers, start, human);
			game.reset();
			
			closeStatus = true;
			parent.toggleStart();
			dispose();
			
		}
		if(e.getSource() == quitButton) {
			System.exit(0);
		}
		if(e.getSource() == cancelButton) {
			dispose();
		}

	}

	public SurroundGame getGame() {
		return game;
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}
	
	private void buttonSetUp(JButton button) {
		button.setIcon(buttonIcon);
		button.setPreferredSize(new Dimension(250,100));
		button.setFont(buttonFont);
		button.setContentAreaFilled(false);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.addActionListener(this);
	}
	
	private void panelSetUp(JPanel panel) {
		panel.setPreferredSize(new Dimension(600,35));
		panel.setOpaque(false);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void radioSetUp(JRadioButton button, int i) {
		button.setFont(font);
		button.setOpaque(false);
		button.setFocusPainted(false);
		button.setSelectedIcon(selected);
		button.setIcon(notSelected);
		button.setActionCommand(Integer.toString(i));
		button.addActionListener(this);
	}
	
	private void enableAll() {
		for(int i = 0; i < nbrHumans.length; i++) {
			nbrHumans[i].setEnabled(true);
			startPlayer[i].setEnabled(true);
		}
	}
	
	private void setDefaults() {
		brdSize[2].setSelected(true);
		nbrPlayers[2].setSelected(true);
		for(int i = 4; i < nbrHumans.length; i++) {
			nbrHumans[i].setEnabled(false);
			startPlayer[i].setEnabled(false);
		}
		nbrHumans[3].setSelected(true);
		startPlayer[0].setSelected(true);
	}

}
