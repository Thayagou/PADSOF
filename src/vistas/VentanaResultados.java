package vistas;

import javax.swing.*;

import modelo.exceptions.*;
import modelo.sistema.Tienda;
import modelo.venta.productos.*;

import java.awt.*;

public class VentanaResultados extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaResultados(Tienda tienda, Producto[] resultados) {
		StringBuilder st = new StringBuilder();
		int i = 1;
		for(Producto p : resultados) {
			st.append(i++ + ") " + p);
		}
		new VentanaMensaje(st.toString());
	}

}
