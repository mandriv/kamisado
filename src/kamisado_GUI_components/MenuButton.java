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

public class MenuButton extends GUIButton {

	private static final long serialVersionUID = 1L;

	public MenuButton(String label) {
		super(label.toUpperCase());
		this.setPreferredSize(new Dimension(200, 50));
	}

}
