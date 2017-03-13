package kamisado_GUI_frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import kamisado_GUI_components.MenuButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuRadioButton;
import kamisado_GUI_components.MenuSpinner;
import kamisado_GUI_components.MenuTextField;
import kamisado_mp.GameListing;
import kamisado_mp.MultiplayerClient;
import kamisado_mp.User;
import net.miginfocom.swing.MigLayout;

public class NewMpHostFrame extends JFrame{
	
	MultiplayerClient mpClient;
	User user;
	
	public NewMpHostFrame(MultiplayerClient mpClient, User user) {
		
		super("Create new game");
		
		this.mpClient = mpClient;
		this.user = user;
		
		JPanel panel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel nameLabel = new MenuLabel("Name:");
		panel.add(nameLabel, "sg");
		JTextField nameField = new MenuTextField(user.name+"'s game");	
		panel.add(nameField, "sg");
		
		JLabel modeLabel = new MenuLabel("Mode:");
		panel.add(modeLabel, "sg");
		JPanel modePanel = new MenuPanel();
		ButtonGroup bg = new ButtonGroup();
		JRadioButton normalMode = new MenuRadioButton("Normal");
		normalMode.setSelected(true);
		bg.add(normalMode);
		JRadioButton speedMode = new MenuRadioButton("Speed");
		bg.add(speedMode);
		modePanel.add(normalMode);
		modePanel.add(speedMode);
		panel.add(modePanel);
		
		Integer[] items = new Integer[]{1,3,5};
		SpinnerListModel model = new SpinnerListModel(items);
		JSpinner spinner = new MenuSpinner(model);
		panel.add(spinner, "sg");
		
		JLabel passwordLabel = new MenuLabel("Password:");
		panel.add(passwordLabel, "sg");
		JTextField passField = new MenuTextField("");
		panel.add(passField, "sg");
		
		JButton submitBtn = new MenuButton("Create new game");
		submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String mode;
				if (normalMode.isSelected()){
					mode = "normal";
				} else {
					mode = "speed";
				}
				int limit = (Integer) spinner.getValue();
				String password = passField.getText();
				int rank = user.elo;
				
				GameListing game = new GameListing(name, mode, limit, password, rank);
				try {
					mpClient.addGame(game);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				
			}
		});
		panel.add(submitBtn, "sg");
		
		
		this.add(panel);
		this.setPreferredSize(new Dimension(300, 300));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

}
