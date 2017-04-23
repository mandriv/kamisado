package kamisado.GUIcomponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;

public class MenuLabel extends JLabel{

	private static final long serialVersionUID = 1L;

	public MenuLabel(String text) {
		super(text);
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setForeground(new Color(200, 200, 200));
	}
	
	public MenuLabel(String text, int size) {
		super(text);
		this.setFont(new Font("Tahoma", Font.BOLD, size));
		this.setForeground(new Color(200, 200, 200));
	}
	
	public MenuLabel(Icon image) {
		super(image);
	}
}
