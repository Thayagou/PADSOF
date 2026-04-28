package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaGestionarProductosYCategorias;

public class ControlGestionarProductosYCategorias implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaGestionarProductosYCategorias vista;
	
	public ControlGestionarProductosYCategorias(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaGestionarProductosYCategorias(tienda);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
