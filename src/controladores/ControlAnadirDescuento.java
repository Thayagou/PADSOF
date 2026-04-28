package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaAnadirDescuento vista;
	
	public ControlAnadirDescuento(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaAnadirDescuento(tienda);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
