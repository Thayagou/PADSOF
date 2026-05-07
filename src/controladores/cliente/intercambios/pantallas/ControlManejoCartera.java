package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticuloEnCartera;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaCartera;
import vistas.common.app.TiendaFrame;

public class ControlManejoCartera implements ActionListener, ControladorPantalla {
	
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
		
		refresh();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	private void refresh() {
		vista.limpiarDisplays();
		
		for(ArticuloSegundaMano a : dueno.getCartera().getArticulos()) {
			new ControlPanelArticuloEnCartera(tienda, cliente, a, vista);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Hacer oferta":
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
		refresh();
	}
}
