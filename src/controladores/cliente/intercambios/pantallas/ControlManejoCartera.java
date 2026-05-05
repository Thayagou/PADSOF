package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.cliente.intercambios.ControlPanelArticulo;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.common.TiendaFrame;
import vistas.cliente.*;
import vistas.cliente.intercambios.pantallas.VentanaCartera;

public class ControlManejoCartera implements ActionListener {
	
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private VentanaCartera vista;

	public ControlManejoCartera(Tienda tienda, ClienteRegistrado cliente, ClienteRegistrado dueno) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		if(dueno == cliente)
			this.vista = new VentanaCartera(null);
		else 
			this.vista = new VentanaCartera(dueno.getNombre());
		
		for(ArticuloSegundaMano a : dueno.getCartera().getArticulos()) {
			new ControlPanelArticulo(tienda, dueno, a, vista);
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
