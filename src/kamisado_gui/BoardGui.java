package kamisado_gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import kamisado_logic.BoardGrid;
import kamisado_logic.Tower;

public class BoardGui extends JComponent{

	private static final long serialVersionUID = 1505365109891735935L;
	private static final int MARGIN_LEFT = 270;
	private static final int MARGIN_TOP = 55;
	private static final int TILE_OFFSET = 5;
	private static final int TILE_LENGTH = 80;

	private Image boardBackground;
	private BoardGrid boardGrid;

	public BoardGui(){
		
		boardGrid = new BoardGrid();

		//Set background image and frame container dimensions
		boardBackground = new ImageIcon("src/kamisado_media/board.png").getImage();
		this.setPreferredSize(new Dimension(boardBackground.getWidth(null),boardBackground.getHeight(null)));


		//create and show frame
		JFrame frame = new JFrame("Kamisado");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
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
				g.drawImage(boardGrid.getSquare(i,j).getTileImage(),
						MARGIN_LEFT+TILE_LENGTH*j+TILE_OFFSET*j,
						MARGIN_TOP+TILE_LENGTH*i+TILE_OFFSET*i, null);
			}
		}
	}
	



}
