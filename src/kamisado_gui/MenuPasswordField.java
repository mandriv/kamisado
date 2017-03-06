package kamisado_gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;

public class MenuPasswordField extends JPasswordField{
	
	public MenuPasswordField(String text) {
		super(text);
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setCaretColor(new Color(200, 200, 200));
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
	}

}
