package kamisado_GUI_frames;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import kamisado_GUI_components.GUIButton;
import kamisado_GUI_components.IconToggleButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuTextField;
import net.miginfocom.swing.MigLayout;

public class PreGameMenu extends MenuPanel{

	private static final long serialVersionUID = 1L;
	private final Image background;
	private  ImageIcon computer;
	private final ImageIcon human;
	private final ImageIcon computerSelected;
	private final ImageIcon humanSelected;
	
	public PreGameMenu() {
		UIManager.put("Button.focusInputMap", new UIDefaults.LazyInputMap(new
				Object[] {
				    "ENTER", "pressed",
				    "released ENTER", "released"
				}));
		UIManager.put("ToggleButton.focusInputMap", new UIDefaults.LazyInputMap(new
				Object[] {
				    "ENTER", "pressed",
				    "released ENTER", "released"
				}));
		
		
		background = new ImageIcon(getClass().getResource("/kamisado_media/frameBackgrounds/newGame.png")).getImage();
		this.setPreferredSize(new Dimension(background.getWidth(null), background.getHeight(null)));
		
		JPanel content = new MenuPanel(new MigLayout("","[sg][sg][sg][sg][sg]","200[][]30[][]30[]"));
		content.setOpaque(false);
		
		computer = new ImageIcon(getClass().getResource("/kamisado_media/other/computerBtn.png"));
		human = new ImageIcon(getClass().getResource("/kamisado_media/other/humanBtn.png"));
		computerSelected = new ImageIcon(getClass().getResource("/kamisado_media/other/computerBtn_selected.png"));
		humanSelected = new ImageIcon(getClass().getResource("/kamisado_media/other/humanBtn_selected.png"));
		
		JLabel roundsLabel = new MenuLabel("No. of Rounds");
		JLabel modeLabel = new MenuLabel("Mode");
		
		String[] difficulties = { "Easy", "Hard"};
		String[] rounds = {"1", "3", "5"};
		String[] modes = {"Normal", "SPEED"};
		
		JComboBox<String> difficultyBox1 = new JComboBox<>(difficulties);
		JComboBox<String> difficultyBox2 = new JComboBox<>(difficulties);
		difficultyBox1.setVisible(false);
		difficultyBox2.setVisible(false);
		difficultyBox2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			
		JComboBox<String> roundsBox = new JComboBox<>(rounds);
		JComboBox<String> modesBox = new JComboBox<>(modes);
		
		JTextField nameField1 = new MenuTextField("Enter your name");
		JTextField nameField2 = new MenuTextField("Enter your name");
		nameField1.setVisible(false);
		nameField2.setVisible(false);
		nameField1.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				nameField1.setText("");
				
			}
		});
		
		nameField2.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				nameField2.setText("");
				
			}
		});
		
		JButton startGameBtn = new GUIButton("START GAME");
		
		ButtonGroup bg1 = new ButtonGroup();
		ButtonGroup bg2 = new ButtonGroup();
		
		JToggleButton computer1TgBtn = new IconToggleButton(computer, computerSelected, difficultyBox1);
		JToggleButton computer2TgBtn = new IconToggleButton(computer, computerSelected, difficultyBox2);
		JToggleButton human1TgBtn = new IconToggleButton(human, humanSelected, nameField1);
		JToggleButton human2TgBtn = new IconToggleButton(human, humanSelected, nameField2);
		
		human1TgBtn.setSelected(true);
		computer2TgBtn.setSelected(true);
		
		bg1.add(computer1TgBtn);
		bg1.add(human1TgBtn);
		bg2.add(computer2TgBtn);
		bg2.add(human2TgBtn);
		
		content.add(computer1TgBtn);
		content.add(human1TgBtn);
		content.add(human2TgBtn,"skip");
		content.add(computer2TgBtn, "wrap");
		content.add(difficultyBox1, "align center");
		content.add(nameField1, "width 125px, align center");
		content.add(nameField2,  "skip, width 125px, align center");
		content.add(difficultyBox2, "wrap, align center");
		content.add(roundsLabel, "skip, align center");
		content.add(modeLabel, "skip, wrap, align center");
		content.add(roundsBox, "skip, align center");
		content.add(modesBox, "skip, wrap, align center");
		content.add(startGameBtn, "growx, skip, span 3, align center");
		
		
		
		this.add(content);
		
		JFrame frame = new JFrame("New game");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.background, 0, 0, null);
	}

}
