package kamisado_GUI_components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JToggleButton;

public class AvatarButton extends JToggleButton{

	public AvatarButton(Icon icon) {
		super(icon);
		this.setPreferredSize(new Dimension(90, 90));
		this.setBackground(new Color(30, 30,30));
		this.setBorder(BorderFactory.createLineBorder(new Color(138, 53, 57, 128)));
	}

}
