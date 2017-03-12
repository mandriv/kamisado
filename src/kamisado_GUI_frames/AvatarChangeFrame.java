package kamisado_GUI_frames;

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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import kamisado_GUI_components.AvatarButton;
import kamisado_GUI_components.MenuButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import net.miginfocom.swing.MigLayout;

public class AvatarChangeFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private ImageIcon currentAvatar;
	JLabel currentAvatarLabel;

	public AvatarChangeFrame(ImageIcon currentAvatar) {
		super("Change avatar");
	
		this.currentAvatar = currentAvatar;
		
		List<ImageIcon> avatars = new ArrayList<>();
		for(int i = 1 ; i <= 108 ; i++) {
			ImageIcon avatar = new ImageIcon(getClass().getResource("/kamisado_media/multiplayer/TCP/avatar"+i+".jpg"));
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
		JButton cancelBtn = new MenuButton("Cancel");
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
