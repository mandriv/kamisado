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

public class MultiplayerMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private JPanel contentPanel;
	private JPanel gameList;
	private JPanel homePanel;
	private CardLayout cards;
	private MultiplayerClient mpClient;
	private String name;
	private ImageIcon avatar;
	private int gamesPlayed;
	private int gamesWon;
	private String rank;

	public MultiplayerMenu(MultiplayerClient client) {
		//Online connection
		mpClient = client;

		name = "Test name";
		avatar = new ImageIcon(getClass().getResource("/kamisado_media/sampleavatar.jpg"));
		gamesPlayed = 120;
		gamesWon = 100;
		rank = "Master Sergeant";
		
		homePanel = new JPanel();
		
		this.setLayout(new MigLayout());
		generateUI();
		
		
		// create and show frame
		frame = new JFrame("Kamisado Online");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(1000, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		// Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}
	
	private void generateUI(){
		JPanel playerPanel = new JPanel();
		//playerPanel.setPreferredSize(new Dimension(250, 170));
		playerPanel.setLayout(new MigLayout());
		JLabel nameLabel = new JLabel(name);
		playerPanel.add(nameLabel, "span 2, wrap, align center");
		JLabel avatarLabel = new JLabel(avatar);
		playerPanel.add(avatarLabel, "");
		JPanel playerQuickStatsPanel = new JPanel();
		playerQuickStatsPanel.setLayout(new MigLayout());
		JLabel gamesPlayedLabel = new JLabel("Games played: "+gamesPlayed);
		playerQuickStatsPanel.add(gamesPlayedLabel, "wrap");
		JLabel gamesWonLabel = new JLabel("Games won: "+gamesWon);
		playerQuickStatsPanel.add(gamesWonLabel, "wrap");
		JLabel rankLabel = new JLabel("Rank: "+rank);
		playerQuickStatsPanel.add(rankLabel, "wrap");
		playerPanel.add(playerQuickStatsPanel);
		
		this.add(playerPanel);
		
		JPanel graphicHeader = new JPanel();
		graphicHeader.setLayout(new MigLayout());
		graphicHeader.setPreferredSize(new Dimension(750, playerPanel.getPreferredSize().height));
		this.add(graphicHeader, "wrap");
		
		MigLayout layout = new MigLayout("flowy, align center");
		JPanel btnPanel = new JPanel(layout);
		btnPanel.setPreferredSize(new Dimension(playerPanel.getPreferredSize().width, 630));
		JButton homeBtn = new JButton("Kamisado Home");
		homeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "home");
			}
		});
		btnPanel.add(homeBtn, "sg");
		JButton gameBrowserBtn = new JButton("Game Browser");
		gameBrowserBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(contentPanel, "games");
				refreshGameList();
			}
		});
		btnPanel.add(gameBrowserBtn, "sg");
		JButton settingsBtn = new JButton("Account settings");
		btnPanel.add(settingsBtn, "sg");
		JButton laddersBtn = new JButton("Ladders");
		btnPanel.add(laddersBtn, "sg");
		JButton chatBtn = new JButton("Chat");
		btnPanel.add(chatBtn, "sg");
		JButton returnBtn = new JButton("Return to main menu");
		btnPanel.add(returnBtn, "sg");
		this.add(btnPanel);
		
		gameList = new JPanel(new MigLayout("fillx, wrap 15"));
		gameList.setPreferredSize(new Dimension(750, 630));
		JLabel nameGameLabel = new JLabel("Name");
		gameList.add(nameGameLabel, "span 7");
		JLabel modeLabel = new JLabel("Mode");
		gameList.add(modeLabel, "span 2");
		JLabel rankGameLabel = new JLabel("Rank");
		gameList.add(rankGameLabel, "span 2");
		JLabel statusLabel = new JLabel("status");
		gameList.add(statusLabel, "span 2");
		JLabel emptyLabel = new JLabel("");
		gameList.add(emptyLabel, "span 2");
		
		cards = new CardLayout(); 
		contentPanel = new JPanel(cards);
		contentPanel.setPreferredSize(new Dimension(750, 630));
		contentPanel.add(homePanel,"home");
		contentPanel.add(gameList, "games");
		this.add(contentPanel);
	}
	
	private void refreshGameList(){
		try {
			for(GameListing game: mpClient.getGames()){
				JLabel gameNameLabel = new JLabel(game.getName());
				gameList.add(gameNameLabel, "span 6");
				JLabel protectedLabel;
				if(game.isProtected())
					protectedLabel = new JLabel("Y");
				else
					protectedLabel = new JLabel("N");
				gameList.add(protectedLabel);
				JLabel gameModeLabel = new JLabel(game.getMode());
				gameList.add(gameModeLabel, "span 2");
				JLabel gameRankLabel = new JLabel(game.getRank());
				gameList.add(gameRankLabel, "span 2");
				JLabel gameStatusLabel = new JLabel(game.getStatus());
				gameList.add(gameStatusLabel, "span 2");
				JButton joinBtn = new JButton("Join");
				gameList.add(joinBtn, "span 2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
