package kamisado_GUI_components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;

public class MenuPasswordField extends JPasswordField{
	
	private static final long serialVersionUID = 1L;

	public MenuPasswordField(String text) {
		super(text);
		this.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.setCaretColor(new Color(200, 200, 200));
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
	}

}
