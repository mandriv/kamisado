package kamisado_gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	
	JPanel loginPanel;
	JTextField loginNameField;
	JTextField loginPassField;

	JPanel registerPanel;
	JTextField registerNameField;
	JTextField emailField;
	JTextField registerPassField;
	
	JFrame mainMenuFrame;

	public SignInUpFrame(JFrame parent) {
		
		super("Log in");
		
		mpClient = new MultiplayerClient();
		mainMenuFrame = parent;
		
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
	}
	
	private JPanel getLoginPanel(){
		loginPanel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel nameLabel = new MenuLabel("Username:");
		loginPanel.add(nameLabel, "sg");
		loginNameField = new MenuTextField("");	
		loginPanel.add(loginNameField, "sg");
		JLabel passwordLabel = new MenuLabel("Password:");
		loginPanel.add(passwordLabel, "sg");
		loginPassField = new MenuPasswordField("");
		loginPanel.add(loginPassField, "sg");
		JButton loginBtn = new MenuButton("Sing in");
		loginPanel.add(loginBtn, "sg");
		loginBtn.addActionListener(new LoginActionListener(loginNameField, loginPassField, mainMenuFrame, this, mpClient));
		loginNameField.addActionListener(new LoginActionListener(loginNameField, loginPassField, mainMenuFrame, this, mpClient));
		loginPassField.addActionListener(new LoginActionListener(loginNameField, loginPassField, mainMenuFrame, this, mpClient));
		return loginPanel;
	}
	
	private JPanel getRegisterPanel(){
		registerPanel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel nameLabel = new MenuLabel("Username:");
		registerPanel.add(nameLabel, "sg");
		registerNameField = new MenuTextField("");	
		registerPanel.add(registerNameField, "sg");
		JLabel emailLabel = new MenuLabel("E-mail:");
		registerPanel.add(emailLabel, "sg");
		emailField = new MenuTextField("");	
		registerPanel.add(emailField, "sg");
		JLabel passwordLabel = new MenuLabel("Password:");
		registerPanel.add(passwordLabel, "sg");
		registerPassField = new MenuPasswordField("");
		registerPanel.add(registerPassField, "sg");
		JButton registerBtn = new MenuButton("Register");
		registerPanel.add(registerBtn, "sg");
		registerNameField.addActionListener(new RegisterActionListener(registerNameField, emailField, registerPassField, mainMenuFrame, this, mpClient));
		emailField.addActionListener(new RegisterActionListener(registerNameField, emailField, registerPassField, mainMenuFrame, this, mpClient));
		registerPassField.addActionListener(new RegisterActionListener(registerNameField, emailField, registerPassField, mainMenuFrame, this, mpClient));
		registerBtn.addActionListener(new RegisterActionListener(registerNameField, emailField, registerPassField, mainMenuFrame, this, mpClient));
		return registerPanel;
	}
	
}
