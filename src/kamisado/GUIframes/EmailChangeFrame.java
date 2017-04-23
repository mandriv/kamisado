package kamisado.GUIframes;

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

import kamisado.GUIcomponents.GUIButton;
import kamisado.GUIcomponents.MenuLabel;
import kamisado.GUIcomponents.MenuPanel;
import kamisado.GUIcomponents.MenuTextField;
import kamisado.mp.MultiplayerClient;
import kamisado.mp.User;
import net.miginfocom.swing.MigLayout;

public class EmailChangeFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public EmailChangeFrame(User user, MultiplayerClient mpClient) {
		super("Change e-mail");
		
		JPanel content = new MenuPanel(new MigLayout("wrap 3, align center, gap 20px 20px"));
		JLabel oldEmailLabel = new MenuLabel("Current e-mail:");
		JTextField oldEmailField = new MenuTextField("");
		JLabel newEmailLabel = new MenuLabel("New e-mail address");
		JTextField newEmailField = new MenuTextField("");
		JButton okBtn = new GUIButton("OK");
		JButton cancelBtn = new GUIButton("Cancel");

		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String oldEmail = oldEmailField.getText();
				String newEmail = newEmailField.getText();
				if(!oldEmail.equals(user.email)) {
					JOptionPane.showMessageDialog(null, "Current e-mail address does not match!", "E-mail change", JOptionPane.ERROR_MESSAGE);
				} else if(oldEmail.equals(newEmail)) {
					JOptionPane.showMessageDialog(null, "E-mail addresses are the same!", "E-mail change", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if(!mpClient.changeEmail(newEmail)){
							JOptionPane.showMessageDialog(null, "E-mail address change successful", "E-mail change", JOptionPane.INFORMATION_MESSAGE);
							user.email = newEmail;
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, mpClient.getLastMessage(), "E-mail change", JOptionPane.ERROR_MESSAGE);
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

		
		content.add(oldEmailLabel, "sg labels");
		content.add(oldEmailField, "width 250px, span 2, sg fields");
		content.add(newEmailLabel, "sg labels");
		content.add(newEmailField, "span 2, sg fields");
		content.add(buttons,"span 2, align center");
		
		this.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				oldEmailField.setText("");
				newEmailField.setText("");
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
