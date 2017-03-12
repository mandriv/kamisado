package kamisado_GUI_components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GUIButton extends JButton {

	private static final long serialVersionUID = 1L;

	public GUIButton(String label) {
		super(label.toUpperCase());
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
		this.setFocusPainted(false);
		Dimension d = getPreferredSize();
		this.setPreferredSize(new Dimension(d.width+10, d.height+10));
		this.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128)));
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				//setBackground(new Color(43, 43, 43));

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
		this.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(new Color(31, 31, 31));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				setBackground(new Color(53, 53, 53));
			}
		});
	}

}
