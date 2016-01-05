package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SurroundFrame extends JFrame implements ActionListener{

	private JMenuBar menuBar;
	
	private JMenuItem newGameItem, quitGameItem;
	
	private JScrollPane scrollPane;
	
	private SurroundGame game;
	
	private BoardPanel boardPanel;
	
	public SurroundFrame() {
		
		setTitle("Surround");
		setName("Surround");
		
		menuBar = new JMenuBar();
		
		newGameItem = new JMenuItem("New Game");
		quitGameItem = new JMenuItem("Quit Game");
		
		newGameItem.addActionListener(this);
		quitGameItem.addActionListener(this);
		
		menuBar.add(newGameItem);
		menuBar.add(quitGameItem);
		
		setJMenuBar(menuBar);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		
		OptionsDialog dialog = new OptionsDialog(this);
		if(dialog.getCloseStatus() == true) {
			
			game = dialog.getGame();
			boardPanel = new BoardPanel(game);
			scrollPane = new JScrollPane(boardPanel);
			getContentPane().add(scrollPane);
			repaint();
			revalidate();
			
		}
		
	}
	
	public static void main(String[] args) {
		new SurroundFrame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == quitGameItem) {
			System.exit(0);
		}
		if (e.getSource() == newGameItem) {
			
			OptionsDialog dialog = new OptionsDialog(this,game);
			if(dialog.getCloseStatus() == true) {
				
				game.reset();
				getContentPane().remove(scrollPane);
				boardPanel = new BoardPanel(game);
				scrollPane = new JScrollPane(boardPanel);
				getContentPane().add(scrollPane);
				repaint();
				revalidate();
				
			}
		}
		
	}
	
}
