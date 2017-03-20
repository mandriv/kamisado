package kamisado_GUI_components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class MenuSpinner extends JSpinner{
	
	private static final long serialVersionUID = 1L;

	public MenuSpinner(SpinnerModel model) {
		super(model);
		Component c = this.getEditor().getComponent(0);
		c.setForeground(new Color(200, 200, 200));
		c.setBackground(new Color(31, 31, 31));
	}

}
