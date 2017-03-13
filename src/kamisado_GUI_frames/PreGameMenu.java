package kamisado_GUI_frames;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.*;

import kamisado_GUI_components.GUIButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuTextField;
import net.miginfocom.swing.MigLayout;

public class PreGameMenu extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public PreGameMenu() {
		super("Game Setup");
		JPanel content = new MenuPanel(new MigLayout());
		
		ButtonGroup bg1 = new ButtonGroup();
		ButtonGroup bg2 = new ButtonGroup();
		
		JToggleButton computer1TgBtn = new JToggleButton("Computer");
		JToggleButton computer2TgBtn = new JToggleButton("Computer");
		JToggleButton human1TgBtn = new JToggleButton("Human");
		JToggleButton human2TgBtn = new JToggleButton("Human");
		
		human1TgBtn.setSelected(true);
		computer2TgBtn.setSelected(true);
		
		bg1.add(computer1TgBtn);
		bg1.add(human1TgBtn);
		bg2.add(computer2TgBtn);
		bg2.add(human2TgBtn);
		
		JLabel vsLabel = new MenuLabel("Vs.");
		JLabel roundsLabel = new MenuLabel("No. of Rounds");
		JLabel modeLabel = new MenuLabel("Mode");
		
		String[] difficulties = { "Easy", "Hard"};
		String[] rounds = {"1", "3", "5"};
		String[] modes = {"Normal", "SPEED"};
		
		JComboBox<String> difficultyBox1 = new JComboBox<>(difficulties);
		JComboBox<String> difficultyBox2 = new JComboBox<>(difficulties);
		JComboBox<String> roundsBox = new JComboBox<>(rounds);
		JComboBox<String> modesBox = new JComboBox<>(modes);
		
		JTextField nameField1 = new MenuTextField("");
		JTextField nameField2 = new MenuTextField("");
		
		JButton startGameBtn = new GUIButton("START GAME");
		
		content.add(computer1TgBtn);
		content.add(human1TgBtn, "align center");
		content.add(vsLabel, "span 2 2, align center");
		content.add(human2TgBtn, "align center");
		content.add(computer2TgBtn, "wrap");
		content.add(difficultyBox1);
		content.add(nameField1, "width 125px");
		content.add(nameField2,  "width 125px");
		content.add(difficultyBox2, "wrap");
		content.add(roundsLabel, "skip, span 2, align center");
		content.add(modeLabel, "span 2, wrap, align center");
		content.add(roundsBox, "skip, span 2, align center");
		content.add(modesBox, "span 2, wrap, align center");
		content.add(startGameBtn, "skip, span 4, align center");
		
		JPanel container = new MenuPanel(new GridBagLayout());
		container.add(content);
		container.setPreferredSize(new Dimension(600, 500));
		
		this.setContentPane(container);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		
	}

}
