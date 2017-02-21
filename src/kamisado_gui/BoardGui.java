package kamisado_gui;

import java.awt.*;
import javax.swing.*;

import kamisado_logic.BoardGrid;
import kamisado_logic.Square;

public class BoardGui extends JComponent{

	private static final long serialVersionUID = 1505365109891735935L;
	private static final int MARGIN_LEFT = 270;
	private static final int MARGIN_TOP = 55;
	private static final int TILE_OFFSET = 5;
	private static final int TILE_LENGTH = 80;
	
	private static final Image possibleTileImage = new ImageIcon("media/tiles/focusedTileOverlay.png").getImage();

	private Image boardBackground;
	public BoardGrid boardGrid;

	public BoardGui(BoardGrid bg){
		
		boardGrid = bg;

		//Set background image and frame container dimensions
		boardBackground = new ImageIcon("media/frameBackgrounds/board.png").getImage();
		this.setPreferredSize(new Dimension(boardBackground.getWidth(null),boardBackground.getHeight(null)));
		
		//Set mouse click and key press listeners
		this.addMouseListener(new MouseClickListener(this));
		this.addKeyListener(new KeyPressListener(this));

		//create and show frame
		JFrame frame = new JFrame("Kamisado");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setFocusable(true);
		this.requestFocus();
		frame.setResizable(false);

		//Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

	}

	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(this.boardBackground, 0, 0, null);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Square square = boardGrid.getSquare(i,j);
				//coordinates of tiles and towers
				int x = MARGIN_LEFT+TILE_LENGTH*j+TILE_OFFSET*j;
				int y = MARGIN_TOP+TILE_LENGTH*i+TILE_OFFSET*i;
				square.setX(x);
				square.setY(y);				
				//draw tiles
				g.drawImage(square.getImage(),x,y, null);
				//draw focused or possible moves tiles
				if(square.isFocused() || square.isPossible()){
					g.drawImage(possibleTileImage, x, y, null);
				}
				//draw towers
				if(boardGrid.getSquare(i,j).isOccupied()){
					g.drawImage(square.getTower().getImage(),x,y,null);
				}
			}
		}
	}
	
	



}
