package kamisado.GUIcomponents;

import java.awt.Dimension;

public class MenuButton extends GUIButton {

	private static final long serialVersionUID = 1L;

	public MenuButton(String label) {
		super(label.toUpperCase());
		this.setPreferredSize(new Dimension(200, 50));
	}

}
