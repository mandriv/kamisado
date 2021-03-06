package kamisado.GUIframes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activity.InvalidActivityException;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import kamisado.GUIcomponents.GUIButton;
import kamisado.GUIcomponents.MenuLabel;
import kamisado.GUIcomponents.MenuPanel;
import kamisado.control.GameController;
import kamisado.control.KeyPressListener;
import kamisado.control.MouseClickListener;
import kamisado.control.MouseListener;
import kamisado.logic.Board;
import kamisado.logic.GameTimeProgressBar;
import kamisado.logic.PlayerColor;
import kamisado.logic.Square;
import net.miginfocom.swing.MigLayout;

public class BoardGUI extends MenuPanel implements ActionListener{

	private static final long serialVersionUID = 1505365109891735935L;
	private static final int MARGIN_LEFT = 270;
	private static final int MARGIN_TOP = 55;
	private static final int TILE_OFFSET = 5;
	private static final int TILE_LENGTH = 80;

	private final Image possibleTileImage;
	private final Image focusedTileImage;
	private final Image hoveredTileImage;
	private final Image boardBackground;

	private Board board;
	private GameController control;
	
	private JLabel nameLabel1;
	private JLabel scoreLabel1;
	private JLabel nameLabel2;
	private JLabel scoreLabel2;
	private JLabel roundLabel;
	private JButton resignButton;
	private MenuPanel blackPlayerPanel;
	private MenuPanel whitePlayerPanel;
	private JFrame frame;
	
	private Timer refresherTimer;


	public BoardGUI(GameController gc) {

		this.control =gc;
		board = gc.board;
		
		gc.setGUI(this);
		
		refresherTimer = new Timer(100, this);
		refresherTimer.start();
		// Loads possible / focused square ring image
		possibleTileImage = new ImageIcon(getClass().getResource("/kamisado_media/tiles/possibleTileOverlay.png")).getImage();
		focusedTileImage = new ImageIcon(getClass().getResource("/kamisado_media/tiles/focusedTileOverlay.png")).getImage();
		hoveredTileImage = new ImageIcon(getClass().getResource("/kamisado_media/tiles/hoveredTileOverlay.png")).getImage();
		// Set background image and frame container dimensions
		boardBackground = new ImageIcon(getClass().getResource("/kamisado_media/frameBackgrounds/board.png")).getImage();
		this.setPreferredSize(new Dimension(boardBackground.getWidth(null), boardBackground.getHeight(null)));
		this.setLayout(new MigLayout("insets 0, fillx"));
		
		// Set mouse click and key press listeners
		KeyPressListener kpl = new KeyPressListener(gc);
		this.addMouseListener(new MouseClickListener(gc));
		this.addMouseMotionListener(new MouseListener(gc));
		this.addKeyListener(kpl);

		JPanel sidePanel = new MenuPanel(new MigLayout("flowy, fillx, insets 0, al center","15px[align center]15px","10%[]13%[]20%[]20%[]"));
		JProgressBar progressBar = new GameTimeProgressBar(gc.SPEED_MODE_TIME);
		blackPlayerPanel = new MenuPanel(new MigLayout("al center"));
		whitePlayerPanel = new MenuPanel(new MigLayout("al center"));
		nameLabel2 = new MenuLabel(board.player2.getName());
		scoreLabel2 = new MenuLabel(board.player2.getScore()+"");
		roundLabel = new MenuLabel("Round " + board.getRoundNumber());
		nameLabel1 = new MenuLabel(board.player1.getName());
		scoreLabel1 = new MenuLabel(board.player1.getScore()+"");
		
		blackPlayerPanel.add(nameLabel2);
		blackPlayerPanel.add(scoreLabel2);
		
		whitePlayerPanel.add(nameLabel1);
		whitePlayerPanel.add(scoreLabel1);
		
		sidePanel.add(progressBar);
		sidePanel.add(blackPlayerPanel, "sg");
		sidePanel.add(roundLabel);
		sidePanel.add(whitePlayerPanel, "sg");
		
		sidePanel.setOpaque(false);
		
		if(gc.board.isSpeedMode()){
			gc.setProgressBar(progressBar);
		} else {
			progressBar.setVisible(false);
		}
		
		
		this.add(sidePanel, "width 215px, height 800px, dock west");
		
		JPanel rightBtnPanel = new MenuPanel(new MigLayout("insets 0, fillx, flowy, al center center","[al center]","[][]"));
		resignButton = new GUIButton("Resign");
		JButton saveButton = new GUIButton("Save");
		JButton undoButton = new GUIButton("Undo");
		JButton redoButton = new GUIButton("Redo");
		
		resignButton.addKeyListener(kpl);
		saveButton.addKeyListener(kpl);
		undoButton.addKeyListener(kpl);
		redoButton.addKeyListener(kpl);
		
		rightBtnPanel.add(resignButton, "sg");
		rightBtnPanel.add(saveButton, "sg");
		rightBtnPanel.add(undoButton, "sg");
		rightBtnPanel.add(redoButton, "sg");
		rightBtnPanel.setOpaque(false);
		
		resignButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gc.getMenuFrame().setVisible(true);
				frame.dispose();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Kamisado saves", "ksv"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				int userSelection = fileChooser.showSaveDialog(frame);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("ksv")) { //ksv == kamisado save (file) :)
					    // filename is OK as-is
					} else {
					    file = new File(file.toString() + ".ksv");  // append .ksv if "foo.jpg.ksv" is OK
					    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".ksv"); // ALTERNATIVELY: remove the extension (if any) and replace it with ".ksv"
					}
					Path path = Paths.get(file.getAbsolutePath());
				    Charset charset = Charset.forName("US-ASCII");
				    String s = board.getJSON().toString();
				    try (BufferedWriter writer = 
				    		Files.newBufferedWriter(path,charset)) {
				        writer.write(s, 0, s.length());
				    } catch (IOException x) {
				        System.err.format("IOException: %s%n", x);
				    }
				}
			}
		});
		
		undoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				gc.undo();
				System.out.println(gc.history.currentState);
			}
		});
		
		redoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gc.redo();
				System.out.println(gc.history.currentState);
			}
		});	
		
		this.add(rightBtnPanel, "width 215px, height 800px, dock east");
		
		// create and show frame
		frame = new JFrame("Kamisado");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);

		gc.setGUIframe(frame);
		
		// Enable keyboard focus
		frame.setFocusable(true);
		this.requestFocus();

		// Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

	}

	@Override
	protected void paintComponent(Graphics g) {
		
		g.drawImage(this.boardBackground, 0, 0, null);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Square square = board.getSquare(i, j);
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
				if (square.isHovered()) {
					g.drawImage(hoveredTileImage, x, y, null);
				}
				// draw towers
				if (board.getSquare(i, j).isOccupied()) {
					g.drawImage(square.getTower().getImage(), x, y, null);
					if(square.getTower().getSumoLevel()>0)
						g.drawImage(square.getTower().getSumoImage(), x, y, null);
				}
			}
		}
		nameLabel2.setText(board.player2.getName());
		scoreLabel2.setText(board.player2.getScore()+"");
		roundLabel.setText("Round " + board.getRoundNumber());
		nameLabel1.setText(board.player1.getName());
		scoreLabel1.setText(board.player1.getScore()+"");
		
		if(board.getCurrentPlayerValue() == PlayerColor.BLACK) {
			blackPlayerPanel.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128),5));
			whitePlayerPanel.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40, 128),5));
		} else {
			whitePlayerPanel.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128),5));
			blackPlayerPanel.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40, 128),5));
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getSource()==refresherTimer){
			board = control.board;
			repaint();// this will call at every 0.1 second
		}
	}
	
	public void focusOnFirstButton() {
		resignButton.requestFocusInWindow();
	}
	 public void exitBtns() {
		 this.requestFocusInWindow();
	 }
	

}
