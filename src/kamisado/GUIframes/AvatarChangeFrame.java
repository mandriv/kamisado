package kamisado.GUIframes;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.mashape.unirest.http.exceptions.UnirestException;

import kamisado.GUIcomponents.AvatarButton;
import kamisado.GUIcomponents.MenuButton;
import kamisado.GUIcomponents.MenuLabel;
import kamisado.GUIcomponents.MenuPanel;
import kamisado.mp.MultiplayerClient;
import kamisado.mp.User;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import net.miginfocom.swing.MigLayout;

public class AvatarChangeFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private ImageIcon currentAvatar;
	private JLabel currentAvatarLabel;
	@SuppressWarnings("unused")
	private MultiplayerClient mpClient;

	public AvatarChangeFrame(User user, MultiplayerClient mpClient, MultiplayerMenu menu) {
		super("Change avatar");
	
		currentAvatar = user.avatar;
		this.mpClient = mpClient;
		
		List<ImageIcon> avatars = new ArrayList<>();
		for(int i = 1 ; i <= 108 ; i++) {
			ImageIcon avatar = new ImageIcon(getClass().getResource("/kamisado_media/multiplayer/TCP/avatar"+i+".jpg"));
			avatar.setDescription(i+"");
			avatars.add(avatar);
		}
		
		JPanel avatarPanel = new MenuPanel(new MigLayout("wrap 5"));
		ButtonGroup avGroup = new ButtonGroup();
		JToggleButton avBtn;
		for (ImageIcon avatar: avatars){
			avBtn = new AvatarButton(avatar);
			avBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					currentAvatarLabel.setIcon(avatar);
				}
			});
			avGroup.add(avBtn);
			avatarPanel.add(avBtn);
		}
		JScrollPane avatarPanelSP = new JScrollPane(avatarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		avatarPanelSP.setBorder(null);
		int width = avatarPanel.getPreferredSize().width;
		avatarPanelSP.setPreferredSize(new Dimension(width+10, 270));
		
		JPanel btnPanel = new MenuPanel(new MigLayout());
		currentAvatarLabel = new MenuLabel(currentAvatar);
		JButton okBtn = new MenuButton("OK");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon newAvatar = (ImageIcon) currentAvatarLabel.getIcon();
				try {
					mpClient.changeAvatar(Integer.parseInt(newAvatar.getDescription()));
					menu.updateAvatar(newAvatar);
					dispose();
				} catch (NumberFormatException | UnirestException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton cancelBtn = new MenuButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnPanel.add(currentAvatarLabel);
		btnPanel.add(okBtn);
		btnPanel.add(cancelBtn);
		
		JPanel content = new MenuPanel(new MigLayout("flowy, align center"));
		content.add(avatarPanelSP);
		content.add(btnPanel);
		
		
		this.add(content);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

}
