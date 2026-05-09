package vistas.common.app;

import javax.swing.JPanel;

import controladores.ControlBarraLateral;

public abstract class BarraLateral extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final double PERC_INDENTED=0.9;

	public abstract void setControlador(ControlBarraLateral c);
	
}
