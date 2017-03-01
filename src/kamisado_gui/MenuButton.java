package kamisado_gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MenuButton extends JButton {

	private static final long serialVersionUID = 1L;

	public MenuButton(String label) {
		super(label.toUpperCase());
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
		this.setFocusable(false);
		this.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128)));
		this.setPreferredSize(new Dimension(200, 50));
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(new Color(43, 43, 43));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(31, 31, 31));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(53, 53, 53));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

}
