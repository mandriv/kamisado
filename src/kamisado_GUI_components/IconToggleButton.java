package kamisado_GUI_components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class IconToggleButton extends JToggleButton{

	private static final long serialVersionUID = 1L;
	
	private Icon icon;
	private Icon selectedIcon;
	private JComponent componentVisibleIfSelected;

	public IconToggleButton(Icon icon, Icon selectedIcon, JComponent componentVisibleIfSelected) {
		super(icon);
		
		this.icon = icon;
		this.selectedIcon = selectedIcon;
		this.componentVisibleIfSelected = componentVisibleIfSelected;
		
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
		        AbstractButton thisButton = (AbstractButton) changeEvent.getSource();
		        ButtonModel buttonModel = thisButton.getModel();
		        if(buttonModel.isSelected()){
		        	thisButton.setIcon(selectedIcon);
		        	componentVisibleIfSelected.setVisible(true);
		        } else {
		        	thisButton.setIcon(icon);
		        	componentVisibleIfSelected.setVisible(false);
		        }
			}
		});
	}

}
