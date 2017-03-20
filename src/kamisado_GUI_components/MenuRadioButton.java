package kamisado_GUI_components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JRadioButton;

public class MenuRadioButton extends JRadioButton{
	
	private static final long serialVersionUID = 1L;

	public MenuRadioButton(String text) {
		super(text);
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
	}
}
