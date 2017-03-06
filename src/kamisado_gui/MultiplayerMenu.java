package kamisado_gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.miginfocom.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import kamisado_mp.GameListing;
import kamisado_mp.MultiplayerClient;
import kamisado_mp.User;

public class MultiplayerMenu extends MenuPanel {

	private static final long serialVersionUID = 1L;

	private MultiplayerClient mpClient;
	private JFrame previousFrame;
	
	private JFrame frame;
	private JPanel contentPanel;
	private JPanel gameList;
	private JPanel homePanel;
	private CardLayout cards;	
	
	private User user;
	private String name;
	private ImageIcon avatar;
	private int gamesPlayed;
	private int gamesWon;
	private int rank;


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

		name = user.name;
		avatar = new ImageIcon(getClass().getResource("/kamisado_media/multiplayer/TCP/TCP-Human-1.jpg"));
		gamesPlayed = user.gamesPlayed;
		gamesWon = user.gamesWon;
		rank = user.elo;
		
		
		this.setLayout(new MigLayout("insets 0 0 0 0"));
		
		generateUI();
		
		
		// create and show frame
		frame = new JFrame("Kamisado Online");
		frame.setResizable(false);
		//frame.setPreferredSize(new Dimension(1000, 800));
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
		JLabel nameLabel = new MenuLabel(name, 20);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		playerPanel.add(nameLabel, "span 2, wrap, align center");
		JLabel avatarLabel = new MenuLabel(avatar);
		playerPanel.add(avatarLabel, "");
		
		JPanel playerQuickStatsPanel = new MenuPanel();
		playerQuickStatsPanel.setLayout(new MigLayout());
		JLabel gamesPlayedLabel = new MenuLabel("Games played: "+gamesPlayed);
		playerQuickStatsPanel.add(gamesPlayedLabel, "wrap");
		JLabel gamesWonLabel = new MenuLabel("Games won: "+gamesWon);
		playerQuickStatsPanel.add(gamesWonLabel, "wrap");
		JLabel rankLabel = new MenuLabel("Rank: "+rank);
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
		JButton homeBtn = new MenuButton("Kamisado Home");
		homeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "home");
			}
		});
		btnPanel.add(homeBtn, "sg");
		JButton gameBrowserBtn = new MenuButton("Game Browser");
		gameBrowserBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "games");
				refreshGameList();
			}
		});
		btnPanel.add(gameBrowserBtn, "sg");
		JButton settingsBtn = new MenuButton("Account settings");
		btnPanel.add(settingsBtn, "sg");
		JButton laddersBtn = new MenuButton("Ladders");
		btnPanel.add(laddersBtn, "sg");
		JButton chatBtn = new MenuButton("Chat");
		btnPanel.add(chatBtn, "sg");
		JButton returnBtn = new MenuButton("Return to main menu");
		returnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				previousFrame.setVisible(true);
				
			}
		});
		btnPanel.add(returnBtn, "sg");
		this.add(btnPanel);
		
		refreshHomePanel();
		refreshGameList();
		
		cards = new CardLayout(); 
		contentPanel = new JPanel(cards);
		contentPanel.setPreferredSize(new Dimension(750, 630));
		contentPanel.add(homePanel,"home");
		contentPanel.add(gameList, "games");

		this.add(contentPanel);
	}
	
	private void refreshHomePanel(){
		homePanel = new MenuPanel();
	}
	
	private void refreshGameList(){
		gameList = new MenuPanel(new MigLayout("fillx, wrap 15"));
		gameList.setPreferredSize(new Dimension(750, 630));
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
		try {
			for(GameListing game: mpClient.getGames()){
				JLabel gameNameLabel = new MenuLabel(game.getName());
				gameList.add(gameNameLabel, "span 6");
				JLabel protectedLabel = new MenuLabel("");
				if(game.isProtected()){
					protectedLabel = new MenuLabel(new ImageIcon(getClass().getResource("/kamisado_media/multiplayer/lock.png")));
				}
				gameList.add(protectedLabel);
				JLabel gameModeLabel = new MenuLabel(game.getMode());
				gameList.add(gameModeLabel, "span 2");
				JLabel gameRankLabel = new MenuLabel(game.getRank());
				gameList.add(gameRankLabel, "span 2");
				JLabel gameStatusLabel = new MenuLabel(game.getStatus());
				gameList.add(gameStatusLabel, "span 2");
				JButton joinBtn = new MenuButton("Join");
				gameList.add(joinBtn, "span 2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
