package vistas;

import java.awt.BorderLayout;

public class FondoGestor extends FondoGradiente{
	private static final long serialVersionUID = 1L;

	protected BarraGestor opciones = new BarraGestor();

	public FondoGestor() {
		super();
		add(opciones, BorderLayout.WEST);
	}
}
