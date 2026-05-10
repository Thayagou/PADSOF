package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;
import vistas.cliente.intercambios.pantallas.VentanaVerMisOfertas;
import vistas.common.app.TiendaFrame;

public class ControlVerMisOfertas implements ControladorPantalla {
	
	protected Tienda tienda;
	protected ClienteRegistrado cliente;
	protected VentanaVerMisOfertas vista;
	protected String cabecera;
	
	public void crearPaneles() {
		Intercambio[] ofertas = cliente.getCartera().getIntercambiosPendientesSolicitados();
		for(int i = ofertas.length - 1; i >= 0 ; i--) {
			if(ofertas[i].getEmisor().getDueno().equals(cliente))
				new ControlPanelOferta(tienda, cliente, ofertas[i], vista, this);
		}
	}
	
	public ControlVerMisOfertas(Tienda tienda, ClienteRegistrado cliente) {
		this(tienda, cliente, "Ver mis ofertas de intercambio");
	}
	
	protected ControlVerMisOfertas(Tienda tienda, ClienteRegistrado cliente, String cabecera) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.cabecera = cabecera;
		
		vista = new VentanaVerMisOfertas(cabecera);
		vista.setControlador(this);
		
		crearPaneles();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	public void refrescar() {
		vista = new VentanaVerMisOfertas(cabecera);
		vista.setControlador(this);
		
		crearPaneles();
		
		TiendaFrame.getInstance().recargarPantallaActual(this);
	}
	
	@Override
	public void mostrar() {
		vista = new VentanaVerMisOfertas(cabecera);
		vista.setControlador(this);
		
		crearPaneles();
		
		TiendaFrame.getInstance().refresh();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones para esta ventana */
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Aquí se muestran las ofertas que usted ha realizado. Para cancelar una oferta, haz clic sobre ella y continúa en la nueva ventana o usa los botones que se muestran a la derecha.";
	}

}
