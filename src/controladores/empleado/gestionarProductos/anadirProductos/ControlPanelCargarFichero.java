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

public class ControlPanelCargarFichero implements ActionListener {
	private final Tienda tienda;
	private final Usuario usuario;
	private final PanelCargarFichero panel;
	private final ControlAnadirProductos padre;
	
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
	
	private void intentarCargar() {
		String nombreFichero = panel.getNombreFichero();
		if(nombreFichero.length() < 1) {
			new VentanaMensaje("Seleccione un archivo válido", 1);
			return;
		}
		List<Producto> anadidos = new LinkedList<>();
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas esta fichero de productos?")) {
			try {
				tienda.getAlmacen().anadirProductosDeFichero(usuario, nombreFichero, anadidos);
			} catch (DoubleDiscountException | InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			for(Producto p : anadidos) {
				String imagen = p.getImagen();
				File origen = new File("resources/gui/" + imagen);
				String imagenFinal = GestorImagenes.guardarImagen(origen, imagen, java.util.UUID.randomUUID().toString());
				p.setImagen(imagenFinal);
				
			}
			padre.mostrar();
			new VentanaMensaje("Se han añadido los productos en el fichero correctamente");
		}
	}
}
