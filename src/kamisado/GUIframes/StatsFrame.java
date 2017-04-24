package kamisado.GUIframes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import kamisado.GUIcomponents.GUIButton;
import kamisado.GUIcomponents.MenuLabel;
import kamisado.GUIcomponents.MenuPanel;
import kamisado.util.GameStat;
import kamisado.util.Stats;
import net.miginfocom.swing.MigLayout;

public class StatsFrame extends JFrame{
	
	//this thing won't open if there is no file, it's not a bug, it's a feature
	
	public StatsFrame() {
		super("Statistics");
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("General", getGeneralPanel());
		tabbedPane.addTab("Games", getGamesPanel());
		
		this.add(tabbedPane);
		this.setPreferredSize(new Dimension(600, 500));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
	}
	
	private JPanel getGeneralPanel() {
		JPanel genPanel = new MenuPanel(new MigLayout(""));
		
		int numberOfGames = Stats.getNumberOfGames();
		int numberOfWins  = Stats.getNumberOfWins();
		int numberOfLoses = numberOfGames - numberOfWins;
		double ratio;
		if(numberOfLoses == 0)
			ratio = 0;
		else
			ratio = numberOfWins/numberOfLoses;
		GameStat biggestWin  = Stats.getBiggestWin();
		GameStat longestGame = Stats.getLongestGame();
		
		JLabel nogLabel = new MenuLabel("Number of games : "+numberOfGames);
		JLabel nowLabel = new MenuLabel("Number of wins  : "+numberOfWins);
		JLabel nolLabel = new MenuLabel("Number of loses : "+numberOfLoses);
		JLabel ratLabel = new MenuLabel("W/L ratio : "+ratio);
		int score1 = biggestWin.player1.getScore();
		int score2 = biggestWin.player2.getScore();
		String score = score1 + " - " + score2;
		JLabel biWLabel = new MenuLabel("Best win  : "+score);
		JButton shwBtn1 = new GUIButton("Show more");
		int rounds = longestGame.getHuman().getMoveCount();
		JLabel loGlabel = new MenuLabel("Longest game : "+rounds);
		JButton shwBtn2 = new GUIButton("Show more");
		
		genPanel.add(nogLabel, "wrap");
		genPanel.add(nowLabel, "wrap");
		genPanel.add(nolLabel, "wrap");
		genPanel.add(ratLabel, "wrap");
		genPanel.add(biWLabel);
		genPanel.add(shwBtn1, "wrap");
		genPanel.add(loGlabel);
		genPanel.add(shwBtn2);
		
		return genPanel;
	}

	private Component getGamesPanel() {
		JPanel glPanel = new MenuPanel(new MigLayout("debug, wrap 5, fillx, align center"));
		
		JScrollPane gameListSP = new JScrollPane(glPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gameListSP.setBorder(null);
		
		for(GameStat stat: Stats.getStats()) {
			
			int score1 = stat.player1.getScore();
			int score2 = stat.player2.getScore();
			String name1 = stat.player1.getName();
			String name2 = stat.player2.getName();
			if(stat.player1.isAI())
				name1 = name1 + "[" + stat.player1.getAIdiff() + "]";
			if(stat.player2.isAI())
				name2 = name2 + "[" + stat.player2.getAIdiff() + "]";
			if(score1 > score2)
				name1 = "<html><b>"+name1+"</b></html>";
			else
				name2 = "<html><b>"+name2+"</b></html>";
			String score = score1 + " - " + score2;
			
			JLabel dateLabel  = new MenuLabel(stat.getDate());
			JLabel name1Label = new MenuLabel(name1);
			JLabel scoreLabel = new MenuLabel(score);
			JLabel name2Label = new MenuLabel(name2);
			JButton showBtn   = new GUIButton("Show more");
			
			glPanel.add(dateLabel);
			glPanel.add(name1Label);
			glPanel.add(scoreLabel);
			glPanel.add(name2Label);
			glPanel.add(showBtn);
											
			
		}
		return gameListSP;
	}
}
