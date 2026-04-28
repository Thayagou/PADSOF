package vistas.noRegistrado;

import javax.swing.*;

import controladores.ControlBarraNoRegistrado;
import vistas.TiendaFrame;

import java.awt.BorderLayout;

public class VentanaInicioSinRegistrar extends FondoNoRegistrado {

	private static final long serialVersionUID = 1L;

	public VentanaInicioSinRegistrar(ControlBarraNoRegistrado ctrlBarra) {
		super();

		//Crear componentes
		JLabel title = new JLabel("Tienda mega friki (just for onion smelling fat twatts...)");
		title.setFont(TiendaFrame.getInstance().getTitleFont());
		
		//Contenido de esta ventana
		JPanel contenido = new JPanel(new BorderLayout(10, 10));
		contenido.setOpaque(false);
	    contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contenido.add(title, BorderLayout.NORTH);
		
		add(contenido, BorderLayout.CENTER);
		
		initBarra(ctrlBarra);
		
	}
}
