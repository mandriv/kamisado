package kamisado_GUI_frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kamisado_GUI_components.MenuLabel;
import kamisado_GUI_components.MenuPanel;
import kamisado_GUI_listeners.KeyPressListener;
import kamisado_GUI_listeners.MouseClickListener;
import kamisado_logic.Board;
import kamisado_logic.PlayerColor;
import kamisado_logic.Square;
import net.miginfocom.swing.MigLayout;

public class BoardGUI extends MenuPanel {

	private static final long serialVersionUID = 1505365109891735935L;
	private static final int MARGIN_LEFT = 270;
	private static final int MARGIN_TOP = 55;
	private static final int TILE_OFFSET = 5;
	private static final int TILE_LENGTH = 80;

	private final Image possibleTileImage;
	private final Image focusedTileImage;
	private final Image boardBackground;

	public Board boardGrid;
	
	public JLabel nameLabel1;
	public JLabel scoreLabel1;
	public JLabel nameLabel2;
	public JLabel scoreLabel2;
	public JLabel roundLabel;

	public BoardGUI(Board bg) {

		boardGrid = bg;

		// Loads possible / focused square ring image
		possibleTileImage = new ImageIcon(getClass().getResource("/kamisado_media/tiles/possibleTileOverlay.png")).getImage();
		focusedTileImage = new ImageIcon(getClass().getResource("/kamisado_media/tiles/focusedTileOverlay.png")).getImage();
		// Set background image and frame container dimensions
		boardBackground = new ImageIcon(getClass().getResource("/kamisado_media/frameBackgrounds/board.png")).getImage();
		this.setPreferredSize(new Dimension(boardBackground.getWidth(null), boardBackground.getHeight(null)));
		this.setLayout(new MigLayout("insets 0"));
		
		// Set mouse click and key press listeners
		this.addMouseListener(new MouseClickListener(this));
		this.addKeyListener(new KeyPressListener(this));

		JPanel sidePanel = new MenuPanel(new MigLayout("wrap 2, fillx, insets 0","[align center][align center]","100[]200[]200[]"));
		nameLabel2 = new MenuLabel(boardGrid.getPlayerNames(PlayerColor.BLACK));
		scoreLabel2 = new MenuLabel(boardGrid.getPlayerScore(PlayerColor.BLACK)+"");
		roundLabel = new MenuLabel("Round " + boardGrid.getRoundNumber());
		nameLabel1 = new MenuLabel(boardGrid.getPlayerNames(PlayerColor.WHITE));
		scoreLabel1 = new MenuLabel(boardGrid.getPlayerScore(PlayerColor.WHITE)+"");
		
		sidePanel.add(nameLabel2);
		sidePanel.add(scoreLabel2);
		sidePanel.add(roundLabel, "span 2");
		sidePanel.add(nameLabel1);
		sidePanel.add(scoreLabel1);
		sidePanel.setOpaque(false);
		
		
		this.add(sidePanel, "width 215px, height 800px");
		
		// create and show frame
		JFrame frame = new JFrame("Kamisado");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);

		// Enable keyboard focus
		//frame.setFocusable(true);
		//this.requestFocus();

		// Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.boardBackground, 0, 0, null);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Square square = boardGrid.getSquare(i, j);
				// coordinates of tiles and towers
				int x = MARGIN_LEFT + TILE_LENGTH * j + TILE_OFFSET * j;
				int y = MARGIN_TOP + TILE_LENGTH * i + TILE_OFFSET * i;
				square.setX(x);
				square.setY(y);
				// draw tiles
				g.drawImage(square.getImage(), x, y, null);
				// draw focused or possible moves tiles
				if (square.isPossible() && !square.isFocused()) {
					g.drawImage(possibleTileImage, x, y, null);
				}
				if (square.isFocused()) {
					g.drawImage(focusedTileImage, x, y, null);
				}
				// draw towers
				if (boardGrid.getSquare(i, j).isOccupied()) {
					g.drawImage(square.getTower().getImage(), x, y, null);
				}
			}
		}
		nameLabel2.setText(boardGrid.getPlayerNames(PlayerColor.BLACK));
		scoreLabel2.setText(boardGrid.getPlayerScore(PlayerColor.BLACK)+"");
		roundLabel.setText("Round " + boardGrid.getRoundNumber());
		nameLabel1.setText(boardGrid.getPlayerNames(PlayerColor.WHITE));
		scoreLabel1.setText(boardGrid.getPlayerScore(PlayerColor.WHITE)+"");
	}

}
