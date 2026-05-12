package controladores.empleado.gestionarProductos.anadirProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import controladores.GestorImagenes;
import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarProductos.anadirProductos.PanelCargarFichero;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

/**
 * Esta clase representa el controlador del panel para seleccionar un fichero de productos
 */
public class ControlPanelCargarFichero implements ActionListener {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Usuario que realiza la acción */
	private final Usuario usuario;
	/** Panel que se controla */
	private final PanelCargarFichero panel;
	/** Controlador de la ventana en la que se encuentra el panel */
	private final ControlAnadirProductos padre;
	
	/**
	 * Constructor del controlador del panel cargar fichero
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario qeu realiza la acción
	 * @param vista Ventana en la que se muestra
	 * @param padre Controlador de la ventana en la que se muestra
	 */
	public ControlPanelCargarFichero(Tienda tienda, Usuario usuario, VentanaAnadirProductos vista, ControlAnadirProductos padre) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.padre = padre;
		
		panel = new PanelCargarFichero();
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case PanelCargarFichero.CONFIRMAR_ACTION: 
			intentarCargar();
			break;
		}
	}
	
	/**
	 * Acción que se realiza al intentar cargar un fichero de productos
	 */
	private void intentarCargar() {
		String nombreFichero = panel.getNombreFichero();
		if(nombreFichero.length() < 1) {
			new VentanaMensaje("Seleccione un archivo válido", 1);
			return;
		}
		List<Producto> anadidos = new LinkedList<>();
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas cargar fichero de productos?")) {
			try {
				tienda.getAlmacen().anadirProductosDeFichero(usuario, nombreFichero, anadidos);
			} catch (DoubleDiscountException | InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			for(Producto p : anadidos) {
				String imagen = p.getImagen();
				File origen = new File("resources/productFilesImages/" + imagen);
				String imagenFinal = GestorImagenes.guardarImagen(origen, p.getNombre(), java.util.UUID.randomUUID().toString());
				p.setImagen(imagenFinal);
				
			}
			padre.mostrar();
			new VentanaMensaje("Se han añadido " + anadidos.size() + (anadidos.size() == 1? " producto" : " productos") + " desde el fichero correctamente");
		}
	}
}
