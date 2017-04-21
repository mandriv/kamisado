package kamisado.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kamisado_GUI_frames.MultiplayerMenu;
import kamisado_mp.MultiplayerClient;

public class LoginActionListener implements ActionListener{
	
	JTextField nameField;
	JTextField passField;
	JFrame mainMenuFrame;
	JFrame signInUpFrame;
	MultiplayerClient mpClient;
	
	
	public LoginActionListener(JTextField nameField, JTextField passField, JFrame parentFrame, JFrame signInUpFrame, MultiplayerClient mpClient) {
		super();
		this.nameField = nameField;
		this.passField = passField;
		this.mainMenuFrame = parentFrame;
		this.signInUpFrame = signInUpFrame;
		this.mpClient = mpClient;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

			if(mpClient.connectToAPI(nameField.getText(), passField.getText())){
				@SuppressWarnings("unused")
				MultiplayerMenu mpMenu = new MultiplayerMenu(mpClient, mainMenuFrame);
				mainMenuFrame.setVisible(false);
				signInUpFrame.dispose();
			} else {
				JOptionPane.showMessageDialog(signInUpFrame,
					    mpClient.getLastMessage(),
					    "Login error",
					    JOptionPane.ERROR_MESSAGE);
			}
		
	}

}
