package package1.v13;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SurroundFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;
	
	private JMenuItem newGameItem, quitGameItem;
	
	private SurroundGame game;
	
	private BoardPanel boardPanel;
	
	private Box box;
	
	public SurroundFrame() {
		
		setTitle("Surround");
		setName("Surround");
		
		menuBar = new JMenuBar();
		
		box = new Box(BoxLayout.Y_AXIS);
		
		newGameItem = new JMenuItem("New Game");
		quitGameItem = new JMenuItem("Quit Game");
		
		newGameItem.addActionListener(this);
		quitGameItem.addActionListener(this);
		
		menuBar.add(newGameItem);
		menuBar.add(quitGameItem);
		
		setJMenuBar(menuBar);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setSize(600,600);
		setUndecorated(true);
		setVisible(true);
		
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setSize(new Dimension(1000,600));
		frame.setLocationRelativeTo(this);
		
		OptionsDialog dialog = new OptionsDialog(frame);
		
		frame.setVisible(true);
		
		if(dialog.getCloseStatus() == true) {
			
			frame.dispose();
			
			game = dialog.getGame();
			boardPanel = new BoardPanel(game);
			box.add(Box.createVerticalGlue());
			box.add(boardPanel);
			getContentPane().add(box);
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
			
			JFrame frame = new JFrame();
			frame.setAlwaysOnTop(true);
			frame.setUndecorated(true);
			frame.setSize(new Dimension(1000,600));
			frame.setLocationRelativeTo(this);
			
			OptionsDialog dialog = new OptionsDialog(frame);
			
			if(dialog.getCloseStatus() == true) {
				
				frame.dispose();
				
				game = dialog.getGame();
				
				getContentPane().remove(box);
				
				boardPanel = new BoardPanel(game);
				box = new Box(BoxLayout.Y_AXIS);
				box.add(Box.createVerticalGlue());
				box.add(boardPanel);
				getContentPane().add(box);
				repaint();
				revalidate();
				
			}
			
		}
		
	}
	
	public Box getBox() {
		return box;
	}
	
}
