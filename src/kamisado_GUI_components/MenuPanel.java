package kamisado_GUI_components;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public MenuPanel() {
		super();
		setCustomOptions();
	}
	
	public MenuPanel(LayoutManager layout) {
		super(layout);
		setCustomOptions();
	}
	
	private void setCustomOptions() {
		this.setForeground(new Color(200, 200, 200));
		this.setBackground(new Color(31, 31, 31));
	}

	
	
}
