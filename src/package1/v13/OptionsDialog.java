/**
 * 
 */
package package1.v13;

import java.awt.BorderLayout;
import java.awt.Font;
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
	JComboBox<String> humansBox;

	//Image background;
	
	JButton okButton;
	JButton cancelButton;

	public static boolean closeStatus;

	private SurroundGame game;

	public OptionsDialog(JFrame parent) {

		super(parent, true);
		
		game = new SurroundGame(10,2,1,1);

		setTitle("Game Options");
		setUndecorated(true);
		closeStatus = false;
		
		Font font2 = new Font("Cooper Black", Font.BOLD, 30 );
		
		okButton = new JButton("OK");
		okButton.setFont(font2);
		cancelButton = new JButton("CANCEL");
		cancelButton.setFont(font2);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		JLabel title = new JLabel("Game Options", 
				SwingConstants.CENTER);
		Font font = new Font("Cooper Black", Font.BOLD, 60 );
		title.setFont(font);
		//title.setAlignmentX(CENTER_ALIGNMENT);
		container.add(BorderLayout.NORTH, title);
		
		JPanel panel = new JPanel();
		panel.setName("Game Options");
		panel.setLayout(new GridLayout(5,2));
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
		dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
		String[] board = {"4 x 4","5 x 5","6 x 6","7 x 7","8 x 8",
				"9 x 9","10 x 10","11 x 11","12 x 12"};
		boardBox = new JComboBox<String>(board);
		boardBox.setFont(font2);
		boardBox.setRenderer(dlcr);
		JLabel sizeLabel = new JLabel("Size of the board:");
		sizeLabel.setFont(font2);
		panel.add(sizeLabel);
		panel.add(boardBox);
		String[] players = {"2","3","4","5","6","7","8","9","10"};
		String[] turns = {"1","2","3","4","5","6","7","8","9","10"};
		playersBox = new JComboBox<String>(players);
		playersBox.setFont(font2);
		playersBox.setRenderer(dlcr);
		JLabel playersLabel = new JLabel("Number of players:");
		playersLabel.setFont(font2);
		panel.add(playersLabel);
		panel.add(playersBox);
		humansBox = new JComboBox<String>(turns);
		humansBox.setFont(font2);
		humansBox.setRenderer(dlcr);
		JLabel humansLabel = new JLabel("Number of human players:");
		humansLabel.setFont(font2);
		panel.add(humansLabel);
		panel.add(humansBox);
		startBox = new JComboBox<String>(turns);
		startBox.setFont(font2);
		startBox.setRenderer(dlcr);
		JLabel startLabel = new JLabel("Starting player:");
		startLabel.setFont(font2);
		panel.add(startLabel);
		panel.add(startBox);

		panel.add(okButton);
		panel.add(cancelButton);

		//container.setPreferredSize(new Dimension(700,700));
		container.add(BorderLayout.CENTER, panel);
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalGlue());
		box.add(container);

		getContentPane().add(box);
		//getContentPane().add(container);

		setAlwaysOnTop(true);
		setSize(parent.getSize()); 
		setLocation(parent.getLocation());
		
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == okButton) {

			String board = (String) boardBox.getSelectedItem();
			int BDSIZE = Integer.parseInt(
					board.substring(0,board.indexOf(" ")));
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
