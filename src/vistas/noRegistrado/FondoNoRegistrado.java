package vistas.noRegistrado;

import java.awt.BorderLayout;

import controladores.noRegistrado.ControlBarraNoRegistrado;
import vistas.FondoGradiente;

public class FondoNoRegistrado extends FondoGradiente {
	private static final long serialVersionUID = 1L;

	protected BarraNoRegistrado opciones;

	public FondoNoRegistrado() {
		super();
	}

	public void initBarra(ControlBarraNoRegistrado ctrlBarra) {
        opciones = new BarraNoRegistrado();
        opciones.setControlador(ctrlBarra);
        add(opciones, BorderLayout.WEST);
	}
}
