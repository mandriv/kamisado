package kamisado_GUI_frames;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mashape.unirest.http.exceptions.UnirestException;

import kamisado_GUI_components.GUIButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuPasswordField;
import kamisado_GUI_components.MenuTextField;
import kamisado_mp.MultiplayerClient;
import kamisado_mp.User;
import net.miginfocom.swing.MigLayout;

public class PasswordChangeFrame extends JFrame{
	
	public PasswordChangeFrame(MultiplayerClient mpClient) {
		super("Change password");
		JPanel content = new MenuPanel(new MigLayout("wrap 3, align center, gap 20px 20px"));
		JLabel oldPasswordLabel = new MenuLabel("Current password:");
		JTextField oldPasswordField = new MenuPasswordField("");
		JLabel newPasswordLabel = new MenuLabel("New password");
		JTextField newPasswordField = new MenuPasswordField("");
		JButton okBtn = new GUIButton("OK");
		JButton cancelBtn = new GUIButton("Cancel");

		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String oldPassword = oldPasswordField.getText();
				String newPassword = newPasswordField.getText();
				if(oldPassword.equals(newPassword)) {
					JOptionPane.showMessageDialog(null, "Passwords are the same!", "Password change", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if(!mpClient.changePassword(oldPassword, newPassword)){
							JOptionPane.showMessageDialog(null, "Password change successful", "Password change", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, mpClient.getLastMessage(), "Password change", JOptionPane.ERROR_MESSAGE);
						}
					} catch (HeadlessException | UnirestException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JPanel buttons = new MenuPanel();
		buttons.add(okBtn);
		buttons.add(cancelBtn);

		
		content.add(oldPasswordLabel, "sg labels");
		content.add(oldPasswordField, "width 250px, span 2, sg fields");
		content.add(newPasswordLabel, "sg labels");
		content.add(newPasswordField, "span 2, sg fields");
		content.add(buttons,"span 2, align center");
		
		this.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				oldPasswordField.setText("");
				newPasswordField.setText("");
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.add(content);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

}
