package kamisado_gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import kamisado_util.SinglePlayerGame;
import kamisado_util.SoundTrack;

public class MainMenu extends JPanel {

	private static final long serialVersionUID = -4430454280382331726L;

	private final Image menuBackground;

	private SoundTrack soundTrack;

	private JPanel btnPanel;
	private JPanel settingsPanel;
	private JPanel loginPanel;

	JFrame frame;

	public MainMenu(SoundTrack st) {

		soundTrack = st;

		// Sets the background and dimensions
		menuBackground = new ImageIcon(getClass().getResource("/kamisado_media/frameBackgrounds/mainmenu.png"))
				.getImage();
		this.setPreferredSize(new Dimension(menuBackground.getWidth(null), menuBackground.getHeight(null)));

		// Adds menu buttons to this panel
		this.setLayout(new GridBagLayout());
		addMenuButtons();
		addSettingsButtons();

		// create and show frame
		frame = new JFrame("Kamisado");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		// Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.menuBackground, 0, 0, null);
	}

	public void addMenuButtons() {
		// Create a new panel for buttons
		btnPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		btnPanel.setBackground(new Color(20, 20, 20));
		btnPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		// Add buttons
		JButton startBtn = new MenuButton("New game");
		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				@SuppressWarnings("unused")
				SinglePlayerGame game = new SinglePlayerGame();
			}
		});
		btnPanel.add(startBtn);

		JButton loadBtn = new MenuButton("Load game");
		btnPanel.add(loadBtn);

		JButton onlineBtn = new MenuButton("Play Online");
		onlineBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MultiplayerMenu mpMenu = new MultiplayerMenu();
			}
		});
		btnPanel.add(onlineBtn);

		JButton settingsBtn = new MenuButton("Settings");
		settingsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnPanel.setVisible(false);
				settingsPanel.setVisible(true);
			}
		});
		btnPanel.add(settingsBtn);

		JButton exitBtn = new MenuButton("Exit");
		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnPanel.add(exitBtn);

		// Add buttons panel to the centre of frame's container
		this.add(btnPanel);
	}

	public void addSettingsButtons() {
		// Create a new panel for settings
		settingsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		settingsPanel.setVisible(false);
		settingsPanel.setBackground(new Color(20, 20, 20));
		settingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		MenuCheckBox soundCB = new MenuCheckBox("Sounds", false);
		soundCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (soundCB.isSelected())
					soundTrack.playMainThemeOnLoop();
				else
					soundTrack.mute();
			}
		});
		settingsPanel.add(soundCB);

		MenuButton dummyBtn1 = new MenuButton("");
		MenuButton dummyBtn2 = new MenuButton("");
		MenuButton dummyBtn3 = new MenuButton("");
		dummyBtn1.setVisible(false);
		dummyBtn2.setVisible(false);
		dummyBtn3.setVisible(false);
		settingsPanel.add(dummyBtn1);
		settingsPanel.add(dummyBtn2);
		settingsPanel.add(dummyBtn3);

		MenuButton returnBtn = new MenuButton("Return to Main Menu");
		returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				settingsPanel.setVisible(false);
				btnPanel.setVisible(true);
			}
		});
		settingsPanel.add(returnBtn);

		// Add settings panel to the centre of frame's container
		this.add(settingsPanel);
	}

}
