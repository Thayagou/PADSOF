package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticuloEnCartera;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaCartera;
import vistas.common.app.TiendaFrame;

public class ControlManejoCartera implements ControladorPantalla {
	
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private ClienteRegistrado dueno;
	private VentanaCartera vista;

	public ControlManejoCartera(Tienda tienda, ClienteRegistrado cliente, ClienteRegistrado dueno) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.dueno = dueno;
		
		if(dueno == cliente)
			this.vista = new VentanaCartera(null);
		else 
			this.vista = new VentanaCartera(dueno.getNombre());
		
		vista.setControlador(this);
		
		cargarArticulos();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	private void cargarArticulos() {
		vista.limpiarDisplays();
		
		if(cliente.equals(dueno)) {
			for(ArticuloSegundaMano a : dueno.getCartera().getArticulos()) {
				new ControlPanelArticuloEnCartera(tienda, cliente, a, vista);
			}
		} else {
			for(ArticuloSegundaMano a : dueno.getCartera().getArticulosDisponibles()) {
				new ControlPanelArticuloEnCartera(tienda, cliente, a, vista);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaCartera.OFFER_ACTION:
			SwingUtilities.invokeLater(() -> new ControlHacerOferta(tienda, cliente, dueno));
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	@Override
	public void mostrar() {
		cargarArticulos();
		TiendaFrame.getInstance().refresh();
	}

	@Override
	public String getExplicacion() {
		return "Aquí se muestran los artículos de una cartera. Puedes ver los objetos del dueño de la cartera.";
	}
}
