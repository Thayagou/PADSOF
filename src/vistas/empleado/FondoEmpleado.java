package vistas.empleado;

import java.awt.BorderLayout;

import vistas.FondoGradiente;

public class FondoEmpleado extends FondoGradiente{
	private static final long serialVersionUID = 1L;
	

	protected BarraEmpleado opciones = new BarraEmpleado();
	
	public FondoEmpleado() {
		super();
	    add(opciones, BorderLayout.WEST);
	}

}
