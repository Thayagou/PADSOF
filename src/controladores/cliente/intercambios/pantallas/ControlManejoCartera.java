package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticulo;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.common.TiendaFrame;
import vistas.cliente.intercambios.pantallas.VentanaCartera;

public class ControlManejoCartera implements ActionListener, ControladorPantalla {
	
	@SuppressWarnings("unused")
	private Tienda tienda;
	@SuppressWarnings("unused")
	private ClienteRegistrado cliente;
	private VentanaCartera vista;

	public ControlManejoCartera(Tienda tienda, ClienteRegistrado cliente, ClienteRegistrado dueno) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		if(dueno == cliente)
			this.vista = new VentanaCartera(null);
		else 
			this.vista = new VentanaCartera(dueno.getNombre());
		
		vista.setControlador(this);
		
		for(ArticuloSegundaMano a : dueno.getCartera().getArticulos()) {
			new ControlPanelArticulo(tienda, cliente, a, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Hacer oferta":
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
