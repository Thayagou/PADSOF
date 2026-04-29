package vistas.common;

import javax.swing.JPanel;

import controladores.ControlBarraTareas;

public abstract class BarraTareas extends JPanel{
	private static final long serialVersionUID = 1L;

	public abstract void setControlador(ControlBarraTareas c);
}
