package kamisado_GUI_frames;

import java.awt.Dimension;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.mashape.unirest.http.exceptions.UnirestException;

import kamisado.control.LoginActionListener;
import kamisado.control.RegisterActionListener;
import kamisado_GUI_components.MenuButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuPasswordField;
import kamisado_GUI_components.MenuTextField;
import kamisado_mp.MultiplayerClient;
import net.miginfocom.swing.MigLayout;

public class SignInUpFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	MultiplayerClient mpClient;
	
	JTextField loginNameField;
	JTextField loginPassField;

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
		tabbedPane.addTab("Reset password", getResetPasswordPanel());
		
		this.add(tabbedPane);
		this.setPreferredSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}
	
	private JPanel getLoginPanel(){
		JPanel loginPanel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel nameLabel = new MenuLabel("Username:");
		loginPanel.add(nameLabel, "sg");
		loginNameField = new MenuTextField("admin");	
		loginPanel.add(loginNameField, "sg");
		JLabel passwordLabel = new MenuLabel("Password:");
		loginPanel.add(passwordLabel, "sg");
		loginPassField = new MenuPasswordField("admin");
		loginPanel.add(loginPassField, "sg");
		JButton loginBtn = new MenuButton("Sing in");
		loginPanel.add(loginBtn, "sg");
		loginBtn.addActionListener(new LoginActionListener(loginNameField, loginPassField, mainMenuFrame, this, mpClient));
		loginNameField.addActionListener(new LoginActionListener(loginNameField, loginPassField, mainMenuFrame, this, mpClient));
		loginPassField.addActionListener(new LoginActionListener(loginNameField, loginPassField, mainMenuFrame, this, mpClient));
		return loginPanel;
	}
	
	private JPanel getRegisterPanel(){
		JPanel registerPanel = new MenuPanel(new MigLayout("flowy, align center"));
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
	
	private JPanel getResetPasswordPanel(){
		JPanel resetPasswordPanel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel emailLabel = new MenuLabel("Enter e-mail address connected to your account");
		resetPasswordPanel.add(emailLabel, "sg");
		JTextField emailField = new MenuTextField("");
		resetPasswordPanel.add(emailField, "sg");
		JButton submitBtn = new MenuButton("Reset password");
		submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(mpClient.resetPassword(emailField.getText()))
							JOptionPane.showMessageDialog(null, mpClient.getLastMessage(), "Password reset", JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, mpClient.getLastMessage(), "Password reset", JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException | UnirestException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		resetPasswordPanel.add(submitBtn, "sg");
		return resetPasswordPanel;
	}
	
}
