/**
 * 
 */
package package1;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Frank
 *
 */
public class OptionsDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JComboBox<String> boardBox;
	JComboBox<String> playersBox;
	JComboBox<String> startBox;

	JButton okButton;
	JButton cancelButton;

	public static boolean closeStatus;

	private SurroundGame game;

	public OptionsDialog(JFrame parent) {
		
		super(parent, true);

		game = new SurroundGame(10,2,1);

		setTitle("Game Options");
		setUndecorated(true);
		closeStatus = false;

		okButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setName("Game Options");
		panel.setLayout(new GridLayout(4,2));
		String[] board = {"4","5","6","7","8","9","10","11","12"};
		boardBox = new JComboBox<String>(board);
		panel.add(new JLabel("Size of the board:"));
		panel.add(boardBox);
		String[] players = {"2","3","4","5","6","7","8","9","10"};
		String[] turns = {"1","2","3","4","5","6","7","8","9","10"};
		playersBox = new JComboBox<String>(players);
		panel.add(new JLabel("Number of players:"));
		panel.add(playersBox);
		startBox = new JComboBox<String>(turns);
		panel.add(new JLabel("Starting player:"));
		panel.add(startBox);

		panel.add(okButton);
		panel.add(cancelButton);

		getContentPane().add(panel);

		setAlwaysOnTop(true);
		pack();
		setVisible(true);

	}
	public OptionsDialog(JFrame parent, SurroundGame game) {	

		super(parent, true);

		this.game = game;

		setTitle("Game Options");
		setUndecorated(true);
		closeStatus = false;

		okButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setName("Game Options");
		panel.setLayout(new GridLayout(4,2));
		String[] board = {"4","5","6","7","8","9","10","11","12","12",
				"14","15"};
		boardBox = new JComboBox<String>(board);
		panel.add(new JLabel("Size of the board:"));
		panel.add(boardBox);
		String[] players = {"2","3","4","5","6","7","8","9","10"};
		String[] turns = {"1","2","3","4","5","6","7","8","9","10"};
		playersBox = new JComboBox<String>(players);
		panel.add(new JLabel("Number of players:"));
		panel.add(playersBox);
		startBox = new JComboBox<String>(turns);
		panel.add(new JLabel("Starting player:"));
		panel.add(startBox);

		panel.add(okButton);
		panel.add(cancelButton);

		getContentPane().add(panel);

		setAlwaysOnTop(true);
		pack();
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
			if(start > totalPlayers) {
				JOptionPane.showMessageDialog(null,"Starting player's"
						+ "number must be less than or equal to the "
						+ "number of players");
				return;
			}
			game.reset();
			game.newGame(BDSIZE, totalPlayers, start);
			game.reset();

			closeStatus = true;

		}

		dispose();

	}

	public SurroundGame getGame() {
		return game;
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}

}
