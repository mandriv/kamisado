package kamisado_GUI_frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import kamisado_GUI_components.MenuButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_control.GameController;
import net.miginfocom.swing.MigLayout;

public class EndGameFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public EndGameFrame(String name, GameController control) {
		super();
		
		JLabel messageLabel = new MenuLabel("Congratulations " + name + ", you won the game!");
		JButton rematchButton = new MenuButton("Rematch");
		JButton exitBtn = new MenuButton("Main menu");
		
		JPanel container = new MenuPanel(new MigLayout("al center center"));
		
		container.add(messageLabel, "span 2, wrap");
		container.add(rematchButton);
		container.add(exitBtn);
		
		rematchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.resetBoard();
				dispose();
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.getGUIframe().dispose();
				control.getMenuFrame().setVisible(true);
				dispose();
			}
		});
		
		this.add(container);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.pack();
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

}
