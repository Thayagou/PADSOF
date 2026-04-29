package vistas;

import javax.swing.JPanel;

import controladores.ControlBarraLateral;

public abstract class BarraLateral extends JPanel{
	private static final long serialVersionUID = 1L;

	public abstract void setControlador(ControlBarraLateral c);
}
