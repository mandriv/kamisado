package kamisado_gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import kamisado_mp.MultiplayerClient;
import kamisado_gui.MultiplayerMenu;
import net.miginfocom.swing.MigLayout;

public class SignInUpFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	MultiplayerClient mpClient;
	
	JTextField passField;
	JTextField nameField;
	
	JFrame parentFrame;

	public SignInUpFrame(JFrame parent) {
		
		super("Log in");
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Log in", getLoginPanel());
		tabbedPane.addTab("Register", getRegisterPanel());
		
		this.add(tabbedPane);
		this.setPreferredSize(new Dimension(300, 300));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		mpClient = new MultiplayerClient();
		parentFrame = parent;
	}
	
	private JPanel getLoginPanel(){
		JPanel panel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel nameLabel = new MenuLabel("Username:");
		panel.add(nameLabel, "sg");
		nameField = new MenuTextField();
		panel.add(nameField, "sg");
		JLabel passwordLabel = new MenuLabel("Password:");
		panel.add(passwordLabel, "sg");
		passField = new MenuPasswordField();
		panel.add(passField, "sg");
		JButton loginBtn = new MenuButton("Sing in");
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(passField.getText());
				if(mpClient.connectToAPI(nameField.getText(), passField.getText())){
					MultiplayerMenu mpMenu = new MultiplayerMenu(mpClient, parentFrame);
					parentFrame.setVisible(false);
					dispose();
				} else {
					JOptionPane.showMessageDialog(parentFrame,
						    "Invalid username or password",
						    "Login error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		panel.add(loginBtn, "sg");
		return panel;
	}
	
	private JPanel getRegisterPanel(){
		JPanel panel = new JPanel();
		return panel;
	}
	
}
