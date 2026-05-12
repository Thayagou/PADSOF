package controladores.noRegistrado;

import java.awt.event.*;
import java.util.Arrays;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.venta.productos.*;
import vistas.common.app.TiendaFrame;
import vistas.noRegistrado.*;

/**
 * Esta clase representa el controlador de uan ventana de inicio para un usuario sin registrar
 */
public class ControlInicioSinRegistrar implements ActionListener, ControladorPantalla {
	/** Ventana que se muestra */
	private VentanaInicioSinRegistrar vista;
	
	/**
	 * Constructor de la ventana de inicio sin registrar
	 * @param tienda Modelo de la tienda
	 */
	public ControlInicioSinRegistrar(Tienda tienda) {
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

		ControlBarraTareasNoRegistrado ctrlBarraTareas = new ControlBarraTareasNoRegistrado(tienda);
		BarraTareasNoRegistrado barraTareas = new BarraTareasNoRegistrado();
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);

		tiendaFrame.removeBarraLateral();
		
		Producto[] populares = tienda.getAlmacen().getProductosCoincidentes("");

		Arrays.sort(populares, (a,b)->Double.compare(b.getPuntuacionMedia(), a.getPuntuacionMedia()));
		
		this.vista = new VentanaInicioSinRegistrar();
		for(Producto p : populares) {
			new ControlPanelProductoNoRegistrado(tienda, p, vista);
		}
		tiendaFrame.resetearNavegacion(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones en esta ventana */
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Esta es la ventana de inicio de la tienda. Para hacer compras inicia sesión o registrate como cliente.";
	}
}