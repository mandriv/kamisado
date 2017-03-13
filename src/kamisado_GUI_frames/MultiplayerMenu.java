package kamisado_GUI_frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import kamisado_GUI_components.GUIButton;
import kamisado_GUI_components.MenuButton;
import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_components.MenuTextField;
import kamisado_mp.ChatController;
import kamisado_mp.GameListing;
import kamisado_mp.MultiplayerClient;
import kamisado_mp.User;
import kamisado_mp.WebSocketsHandler;

public class MultiplayerMenu extends MenuPanel {

	private static final long serialVersionUID = 1L;

	private MultiplayerClient mpClient;
	private WebSocketsHandler sockHandler;
	private JFrame previousFrame;
	
	private JFrame frame;
	private JPanel contentPanel;
	private CardLayout cards;	
	
	private JPanel gameListPanel;
	private JPanel profilePanel;
	private JPanel chatPanel;
	private JTextArea conversationArea;
	
	private User user;
	JLabel avatarLabel;
	JLabel profilePanelAvatar;
	
	private ArrayList<GameListing> games;


	public MultiplayerMenu(MultiplayerClient client, JFrame previousFrame) {
		//Online connection
		mpClient = client;
		this.previousFrame = previousFrame; 
		
		try {
			user = client.getUserByID(client.userID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			games = mpClient.getGames();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.setLayout(new MigLayout("insets 0 0 0 0"));
		
		generateUI();
		
		sockHandler = new WebSocketsHandler(new ChatController(conversationArea));
		
		// create and show frame
		frame = new JFrame("Kamisado Online");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);

		// Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}
	
	private void generateUI(){
		JPanel playerPanel = new MenuPanel();
		playerPanel.setLayout(new MigLayout());
		JLabel nameLabel = new MenuLabel(user.name, 20);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		playerPanel.add(nameLabel, "span 2, wrap, align center");
		avatarLabel = new MenuLabel(user.avatar);
		playerPanel.add(avatarLabel, "");
		
		JPanel playerQuickStatsPanel = new MenuPanel();
		playerQuickStatsPanel.setLayout(new MigLayout());
		JLabel gamesPlayedLabel = new MenuLabel("Games played: "+user.gamesPlayed);
		playerQuickStatsPanel.add(gamesPlayedLabel, "wrap");
		JLabel gamesWonLabel = new MenuLabel("Games won: "+user.gamesWon);
		playerQuickStatsPanel.add(gamesWonLabel, "wrap");
		JLabel rankLabel = new MenuLabel("Rank: "+user.elo);
		playerQuickStatsPanel.add(rankLabel, "wrap");
		
		playerPanel.add(playerQuickStatsPanel);		
		this.add(playerPanel);
		
		JPanel graphicHeader = new MenuPanel();
		graphicHeader.setLayout(new MigLayout());
		graphicHeader.setPreferredSize(new Dimension(750, playerPanel.getPreferredSize().height));
		this.add(graphicHeader, "wrap");
		
		MigLayout layout = new MigLayout("flowy, align center");
		JPanel btnPanel = new MenuPanel(layout);
		btnPanel.setPreferredSize(new Dimension(playerPanel.getPreferredSize().width, 630));
		JButton gameBrowserBtn = new MenuButton("Game Browser");
		gameBrowserBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "games");
			}
		});
		btnPanel.add(gameBrowserBtn, "sg");
		JButton profileBtn = new MenuButton("My Profile");
		profileBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "profile");
			}
		});
		btnPanel.add(profileBtn, "sg");
		JButton laddersBtn = new MenuButton("Ladders");
		btnPanel.add(laddersBtn, "sg");
		JButton chatBtn = new MenuButton("Chat");
		chatBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "chat");
			}
		});
		btnPanel.add(chatBtn, "sg");
		JButton returnBtn = new MenuButton("Return to main menu");
		returnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sockHandler.closeConnection();
				frame.dispose();
				previousFrame.setVisible(true);
				
			}
		});
		btnPanel.add(returnBtn, "sg");
		this.add(btnPanel);
		
		cards = new CardLayout(); 
		contentPanel = new JPanel(cards);
		contentPanel.setPreferredSize(new Dimension(750, 630));
		
		MigLayout gamePanelLayout = new MigLayout("");
		gameListPanel = new MenuPanel(gamePanelLayout);
		
		createGameContent();
		createProfileContent();
		createChatContent();

		contentPanel.add(gameListPanel, "games");
		contentPanel.add(profilePanel, "profile");
		contentPanel.add(chatPanel, "chat");
		

		this.add(contentPanel);
	}
	
	private void createGameContent(){

		JPanel gameList = new MenuPanel(new MigLayout("fillx, wrap 15"));
		
		if (games.isEmpty()){
			JLabel emptyGameListLabel = new MenuLabel("There are currently no available games, create your own");
			gameList.add(emptyGameListLabel, "span 15, align center");
		} else {		
			JLabel nameGameLabel = new MenuLabel("Name");
			gameList.add(nameGameLabel, "span 7");
			JLabel modeLabel = new MenuLabel("Mode");
			gameList.add(modeLabel, "span 2");
			JLabel rankGameLabel = new MenuLabel("Rank");
			gameList.add(rankGameLabel, "span 2");
			JLabel statusLabel = new MenuLabel("status");
			gameList.add(statusLabel, "span 2");
			JLabel emptyLabel = new MenuLabel("");
			gameList.add(emptyLabel, "span 2");
			
			for(GameListing game: games){
				JLabel gameNameLabel = new MenuLabel(game.getName());
				gameList.add(gameNameLabel, "span 6");
				JLabel protectedLabel = new MenuLabel("");
				if(game.isProtected()){
					protectedLabel = new MenuLabel(new ImageIcon(getClass().getResource("/kamisado_media/multiplayer/lock.png")));
				}
				gameList.add(protectedLabel);
				JLabel gameModeLabel = new MenuLabel(game.getMode());
				gameList.add(gameModeLabel, "span 2");
				JLabel gameRankLabel = new MenuLabel(game.getRank()+"");
				gameList.add(gameRankLabel, "span 2");
				JLabel gameStatusLabel = new MenuLabel(game.getStatus());
				gameList.add(gameStatusLabel, "span 2");
				JButton joinBtn = new GUIButton("Join");
				gameList.add(joinBtn, "span 2");
			}
		}	
		JButton newGameBtn = new MenuButton("Host game");
		newGameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				NewGameFrame frame = new NewGameFrame(mpClient, user);
				
			}
		});
		JButton refreshBtn = new MenuButton("Refresh game list");
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					games = mpClient.getGames();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gameListPanel.removeAll();
				createGameContent();
				gameListPanel.revalidate();
				gameListPanel.repaint();
			}
		});
		
		JScrollPane gameListSP = new JScrollPane(gameList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gameListSP.setBorder(null);
		
		Dimension d = gameList.getPreferredSize();
		gameList.setPreferredSize(new Dimension(750, d.height));
		
		gameListPanel.add(gameListSP, "wrap");
		
		JPanel bottomBtnsPanel = new MenuPanel();
		bottomBtnsPanel.add(newGameBtn);
		bottomBtnsPanel.add(refreshBtn);
		gameListPanel.add(bottomBtnsPanel, "align center");
	}
	
	private void createProfileContent() {
		LayoutManager layout = new MigLayout("wrap 4, align center");
		profilePanel = new MenuPanel(layout);
		
		JLabel profileLabel = new MenuLabel("Player's information");
		profilePanelAvatar = new MenuLabel(user.avatar);

		JPanel infoPanel = new MenuPanel(new MigLayout("flowy, align center"));
		JLabel usernameLabel = new MenuLabel("Username: "+user.name);
		JLabel joinedLabel = new MenuLabel("Member since: "+user.getJoinedDateFormattedString());
		JLabel rankLabel = new MenuLabel("Current elo rating: "+user.elo);
		infoPanel.add(usernameLabel);
		infoPanel.add(joinedLabel);
		infoPanel.add(rankLabel);
		
		JButton avatarBtn = new GUIButton("Avatar");
		JButton emailBtn = new GUIButton("E-mail");
		JButton passwordBtn = new GUIButton("Password");
		
		profilePanel.add(profileLabel, "span 4, align center");
		profilePanel.add(profilePanelAvatar);
		profilePanel.add(infoPanel, "span 3");
		
		profilePanel.add(avatarBtn);
		profilePanel.add(emailBtn);
		profilePanel.add(passwordBtn);
		
		AvatarChangeFrame avatarFrame = new AvatarChangeFrame(user, mpClient, this);
		avatarBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				avatarFrame.setVisible(true);
			}
		});
		
		EmailChangeFrame emailFrame = new EmailChangeFrame(user, mpClient);
		emailBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				emailFrame.setVisible(true);
				
			}
		});
		
		PasswordChangeFrame passFrame = new PasswordChangeFrame(mpClient);
		passwordBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				passFrame.setVisible(true);
			}
		});
	}

	private void createChatContent() {
		chatPanel = new MenuPanel(new BorderLayout());
		
		conversationArea = new JTextArea();
		conversationArea.setForeground(new Color(200, 200, 200));
		conversationArea.setBackground(new Color(31, 31, 31));
		conversationArea.setLineWrap(true);
		conversationArea.setEditable(false);
		conversationArea.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128)));
		conversationArea.setFont(new Font("Tahoma", Font.BOLD, 12));
		JScrollPane convoScroll = new JScrollPane(conversationArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		convoScroll.setBorder(null);
		JTextField chatInput = new MenuTextField("");
		
		chatPanel.add(convoScroll, BorderLayout.CENTER);
		chatPanel.add(chatInput, BorderLayout.PAGE_END);
		
		chatInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!chatInput.getText().isEmpty()){
					sockHandler.sendChatMessage(user.name, chatInput.getText());
					chatInput.setText("");
				}
			}
		});
	}
	
	public void updateAvatar(ImageIcon newAvatar) {
		user.avatar = newAvatar;
		avatarLabel.setIcon(user.avatar);
		profilePanelAvatar.setIcon(user.avatar);
	}
	
	
	

}
