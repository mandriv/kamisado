package kamisado_GUI_frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import kamisado.control.GameController;
import kamisado_GUI_components.GUIButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuRadioButton;
import kamisado_logic.Board;
import net.miginfocom.swing.MigLayout;

public class EndRoundFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public EndRoundFrame(GameController control, String winnerPlayer) {
		super("End round");	
		
		JPanel container = new MenuPanel(new MigLayout("align center center","","[][align center][]"));
		JPanel middlePanel = new MenuPanel(new MigLayout());
		JLabel describtionLabel = new MenuLabel("<html>Contratulations " + winnerPlayer
				+" you won this round.<br>Choose how you want to fill the board.</html>");
		JButton leftBtn = new GUIButton("Left");
		JRadioButton leftRadio = new MenuRadioButton("");
		JRadioButton rightRadio = new MenuRadioButton("");
		JButton rightBtn = new GUIButton("Right");
		ButtonGroup bg = new ButtonGroup();
		JButton submitBtn = new GUIButton("Next round");
		
		describtionLabel.setHorizontalAlignment(JLabel.CENTER);
		
		bg.add(leftRadio);
		bg.add(rightRadio);
		
		middlePanel.add(leftBtn,"sg");
		middlePanel.add(leftRadio);
		middlePanel.add(rightRadio);
		middlePanel.add(rightBtn,"sg");
		
		container.add(describtionLabel,"span 4, wrap");
		container.add(middlePanel,"align center, wrap");
		container.add(submitBtn, "span 4, align center");
		
		leftBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				leftRadio.setSelected(true);
			}
		});
		
		rightBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rightRadio.setSelected(true);
			}
		});
		
		leftRadio.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(leftRadio.isSelected()) {
					control.syncBoard();
					control.board.fillFromLeft();
				}
			}
		});
		
		rightRadio.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(rightRadio.isSelected()) {
					control.syncBoard();
					control.board.fillFromRight();
				}
			}
		});
		
		submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(leftRadio.isSelected() || rightRadio.isSelected()){
					dispose();
					control.board.endRound = false;
					control.board.nextRound();
					if(control.board.isCurrentPlayerAI()) {
						control.ai.requestMove(new Board(control.board), control.board.getCurrentPlayerAIDif());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select fill side", "Problem", JOptionPane.ERROR_MESSAGE);
				}
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
