package kamisado.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mashape.unirest.http.exceptions.UnirestException;

import kamisado_mp.MultiplayerClient;
import kamisado_mp.User;

public class RegisterActionListener implements ActionListener{
	
	JTextField registerNameField;
	JTextField emailField;
	JTextField registerPassField;
	JFrame mainMenuFrame;
	JFrame signInUpFrame;
	MultiplayerClient mpClient;

	public RegisterActionListener(JTextField registerNameField, JTextField emailField, JTextField registerPassField,
			JFrame mainMenuFrame, JFrame signInUpFrame, MultiplayerClient mpClient) {
		super();
		this.registerNameField = registerNameField;
		this.emailField = emailField;
		this.registerPassField = registerPassField;
		this.mainMenuFrame = mainMenuFrame;
		this.signInUpFrame = signInUpFrame;
		this.mpClient = mpClient;
	}

	//correct validation and error handling
	@Override
	public void actionPerformed(ActionEvent e) {
		String name = registerNameField.getText();
		String email = emailField.getText();
		String password = registerPassField.getText();
		if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
			JOptionPane.showMessageDialog(signInUpFrame, "Field cannot be empty!", "Register error", JOptionPane.ERROR_MESSAGE);
		} else {
			User user = new User(name, email, password);
			try {
				if(!mpClient.registerNewUser(user)){
					JOptionPane.showMessageDialog(signInUpFrame,
							mpClient.getLastMessage(),
							"Registered successful",
							JOptionPane.INFORMATION_MESSAGE);
					
				} else {
					JOptionPane.showMessageDialog(signInUpFrame,
							mpClient.getLastMessage(),
						    "Register unsuccessful",
						    JOptionPane.ERROR_MESSAGE);
				}
			} catch (UnirestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
