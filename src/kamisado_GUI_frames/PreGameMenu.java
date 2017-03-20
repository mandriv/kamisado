package kamisado_GUI_frames;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import kamisado_GUI_components.GUIButton;
import kamisado_GUI_components.IconToggleButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuTextField;
import kamisado_util.GameFactory;
import net.miginfocom.swing.MigLayout;

public class PreGameMenu extends MenuPanel{

	private static final long serialVersionUID = 1L;
	private final Image background;
	private final ImageIcon computer;
	private final ImageIcon human;
	private final ImageIcon computerSelected;
	private final ImageIcon humanSelected;
	private JFrame frame;
	
	public PreGameMenu(JFrame parent) {
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
		String[] rounds = {"Single round - 1", "Standard - 3", "Long - 7", "Marathon - 15"};
		String[] modes = {"Normal", "SPEED"};
		
		JComboBox<String> difficultyBox1 = new JComboBox<>(difficulties);
		JComboBox<String> difficultyBox2 = new JComboBox<>(difficulties);
		difficultyBox1.setVisible(false);
		difficultyBox2.setVisible(false);
		difficultyBox2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			
		JComboBox<String> roundsBox = new JComboBox<>(rounds);
		JComboBox<String> modesBox = new JComboBox<>(modes);
		
		JTextField nameField1 = new MenuTextField("Jack");
		JTextField nameField2 = new MenuTextField("Tom");
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
		human2TgBtn.setSelected(true);
		roundsBox.setSelectedIndex(1);
		
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
		
		startGameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(human1TgBtn.isSelected() && human2TgBtn.isSelected()) {
					String name1 = nameField1.getText();
					String name2 = nameField2.getText();
					int limit = 1;
					switch(roundsBox.getSelectedIndex()){
						case 0: limit = 1;  break;
						case 1: limit = 3;  break;
						case 2: limit = 7;  break;
						case 3: limit = 15; break;
					}
					if (modesBox.getSelectedIndex() == 1) {
						GameFactory.createPlayerVsPlayerSpeedLocalGame(name1, name2, limit, parent);
					} else {
						GameFactory.createPlayerVsPlayerNormalLocalGame(name1, name2, limit, parent);
					}
					parent.setVisible(false);
					frame.dispose();
				}
			}
		});
		
		this.add(content);
		
		frame = new JFrame("New game");
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
