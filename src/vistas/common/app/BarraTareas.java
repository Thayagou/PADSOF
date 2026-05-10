package vistas.common.app;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class BarraTareas extends JPanel{
	private static final long serialVersionUID = 1L;

	public abstract void setControlador(ActionListener c);
}
