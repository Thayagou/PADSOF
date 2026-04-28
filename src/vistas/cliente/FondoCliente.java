package vistas.cliente;

import java.awt.BorderLayout;

import vistas.FondoGradiente;

public class FondoCliente extends FondoGradiente {
	private static final long serialVersionUID = 1L;

	protected BarraCliente opciones = new BarraCliente();

	public FondoCliente() {
		super();
		add(opciones, BorderLayout.WEST);
	}

}
