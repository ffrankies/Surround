package package1.v14;

//import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SurroundFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;
	
	private JMenuItem newGameItem, quitGameItem;
	
	private SurroundGame game;
	
	private BoardPanelNew boardPanel;
	
	private Box box;
	
	private Image background;
	
	private JButton startButton, quitButton, helpButton;
	
	private boolean start = true;
	
	public SurroundFrame() {
		
		try {
			background = ImageIO.read(new File("C:\\Users\\Frank\\"
					+ "workspace\\Surround\\src\\package1\\"
					+ "boardBackground.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image not found");
		}
		
		menuBar = new JMenuBar();
		
		box = new Box(BoxLayout.Y_AXIS);
		
		newGameItem = new JMenuItem("New Game");
		quitGameItem = new JMenuItem("Quit Game");
		
		newGameItem.addActionListener(this);
		quitGameItem.addActionListener(this);
		
		menuBar.add(newGameItem);
		menuBar.add(quitGameItem);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		
		OptionsDialog dialog = new OptionsDialog(this);
		
		if(dialog.getCloseStatus() == true) {
			
			game = dialog.getGame();
			boardPanel = new BoardPanelNew(game, background);
			startButton = boardPanel.getStart();
			startButton.addActionListener(this);
			helpButton = boardPanel.getHelp();
			helpButton.addActionListener(this);
			quitButton = boardPanel.getQuit();
			quitButton.addActionListener(this);
			getContentPane().add(boardPanel);
			repaint();
			revalidate();
			
		}
		
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				new SurroundFrame();
				
			}
			
		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == quitButton) {
			System.exit(0);
		}
		if (e.getSource() == startButton) {
			
			OptionsDialog dialog = new OptionsDialog(this);
			
			if(dialog.getCloseStatus() == true) {
				
				game = dialog.getGame();
				
				getContentPane().remove(boardPanel);
				
				boardPanel = new BoardPanelNew(game, background);
				startButton = boardPanel.getStart();
				startButton.addActionListener(this);
				helpButton = boardPanel.getHelp();
				helpButton.addActionListener(this);
				quitButton = boardPanel.getQuit();
				quitButton.addActionListener(this);
				getContentPane().add(boardPanel);
				repaint();
				revalidate();
				
			}
			
		}
		
	}
	
//	public void paintComponent(Graphics g) {
//		super.paintComponents(g);
//		g.drawImage(background, 0, 0, null);
//	}
	
	public Box getBox() {
		return box;
	}
	
	public boolean getStart() {
		return start;
	}
	
	public void toggleStart() {
		start = false;
	}
	
}
