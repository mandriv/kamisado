package kamisado_GUI_components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class MenuCheckBox extends JCheckBox {

	private static final long serialVersionUID = 1192333479756669469L;

	public MenuCheckBox(String label, boolean selected) {
		super(label.toUpperCase(), selected);
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
		this.setHorizontalTextPosition(SwingConstants.LEFT);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorderPainted(true);
		this.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128)));
		this.setPreferredSize(new Dimension(200, 50));
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

}
