package package1.v13;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class OptionsPanel extends JPanel implements ActionListener{

	JComboBox<String> boardBox;
	JComboBox<String> playersBox;
	JComboBox<String> startBox;
	JComboBox<String> humansBox;

	JButton okButton;
	JButton cancelButton;

	public static boolean closeStatus;

	private SurroundGame game;
	
	private SurroundFrame parent;

	public OptionsPanel(SurroundFrame parent) {
		
		Font font2 = new Font("Cooper Black", Font.BOLD, 30 );
		
		setFont(font2);
		
		this.parent = parent;
		
		game = new SurroundGame(10,2,1,1);
		//parent.set

		closeStatus = false;

		okButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		JLabel title = new JLabel("Game Options", 
				SwingConstants.CENTER);
		Font font = new Font("Cooper Black", Font.BOLD, 60 );
		title.setFont(font);
		//title.setAlignmentX(CENTER_ALIGNMENT);
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, new JLabel("Game Options"));

		//Font font2 = new Font("Cooper Black", Font.BOLD, 30 );
		JPanel panel = new JPanel();
		panel.setName("Game Options");
		panel.setLayout(new GridLayout(5,2));
		String[] board = {"4","5","6","7","8","9","10","11","12"};
		boardBox = new JComboBox<String>(board);
		boardBox.setFont(font2);
		JLabel sizeLabel = new JLabel("Size of the board:");
		sizeLabel.setFont(font2);
		panel.add(sizeLabel);
		panel.add(boardBox);
		String[] players = {"2","3","4","5","6","7","8","9","10"};
		String[] turns = {"1","2","3","4","5","6","7","8","9","10"};
		playersBox = new JComboBox<String>(players);
		playersBox.setFont(font2);
		JLabel playersLabel = new JLabel("Number of players:");
		playersLabel.setFont(font2);
		panel.add(playersLabel);
		panel.add(playersBox);
		humansBox = new JComboBox<String>(turns);
		humansBox.setFont(font2);
		JLabel humansLabel = new JLabel("Number of human players:");
		humansLabel.setFont(font2);
		panel.add(humansLabel);
		panel.add(humansBox);
		startBox = new JComboBox<String>(turns);
		startBox.setFont(font2);
		JLabel startLabel = new JLabel("Starting player:");
		startLabel.setFont(font2);
		panel.add(startLabel);
		panel.add(startBox);

		panel.add(okButton);
		panel.add(cancelButton);

//		container.add(BorderLayout.CENTER, panel);
		add(BorderLayout.CENTER, panel);
		
		//getContentPane().add(container);

		//setAlwaysOnTop(true);
		//pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == okButton) {

			String board = (String) boardBox.getSelectedItem();
			int BDSIZE = Integer.parseInt(board);
			String players = (String) playersBox.getSelectedItem();
			int totalPlayers = Integer.parseInt(players);
			String starting = (String) startBox.getSelectedItem();
			int start = Integer.parseInt(starting);
			String humans = (String) humansBox.getSelectedItem();
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
			
			parent.getBox().remove(this);

		}

	}

	public SurroundGame getGame() {
		return game;
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}
	
	public void setCloseStatus() {
		closeStatus = false;
	}

}
