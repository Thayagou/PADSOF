package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.cliente.intercambios.ControlPanelArticulo;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaBuscarArticulos;

/**
 * Controlador de la ventana de búsqueda de artículos de segunda mano.
 */
public class ControlBuscarArticulos implements ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	VentanaBuscarArticulos vista;
	
	/**
	 * Constructor del controlador.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que realiza la búsqueda.
	 */
	public ControlBuscarArticulos(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		this.vista = new VentanaBuscarArticulos();
		this.vista.setControlador(this);
		
		for(ArticuloSegundaMano a : tienda.getAlmacen().getArticulosParaCliente(cliente)) {
			new ControlPanelArticulo(tienda, cliente, a, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	@Override
	public void mostrar() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones para esta ventana */
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran todos los artículos subidos por otros usuarios que han sido valorados. Para ver información detallada sobre uno de ellos pincha sobre él.";
	}

}