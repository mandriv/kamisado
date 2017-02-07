package kamisado;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class BoardGui extends JComponent{
	
	private static final long serialVersionUID = 1505365109891735935L;
	private static final int MARGIN = 45;
	private static final int TILE_OFFSET = 90;
	
	private Image boardBackground;
	private List<Tower> towers;
	private String[] towerColorSeq= new String[] {"orange","blue","turquoise","pink","yellow","red","green","brown"};
	
	public BoardGui(){
		
		//Add background
		boardBackground = new ImageIcon("src/Kamisado-Board.png").getImage();
		
		addTowers();
		
		//create and show frame
		JFrame frame = new JFrame("Kamisado");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(this.boardBackground.getWidth(null), this.boardBackground.getHeight(null));
		//Centre the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(this.boardBackground, 0, 0, null);
		for (Tower tower : this.towers) {
			g.drawImage(tower.getImage(), tower.getX(), tower.getY(), null);
		}
	}
	
	public void addTowers(){
		towers = new ArrayList<Tower>();
		for(int i=0; i<8; i++){
			towers.add(new Tower(MARGIN+TILE_OFFSET*i,MARGIN,towerColorSeq[i],"B"));
		}
	}
	
}
