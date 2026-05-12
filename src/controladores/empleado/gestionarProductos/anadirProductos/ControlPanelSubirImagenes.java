package controladores.empleado.gestionarProductos.anadirProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.ControlCargaImagen;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarProductos.anadirProductos.PanelSubirImagenes;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

/**
 * Esta clase representa un panel para subir imágenes al archivo de la tienda
 */
public class ControlPanelSubirImagenes implements ActionListener{
	/** Panel que se controla */
	private final PanelSubirImagenes panel;
	
	/**
	 * Constructor del controlador del panel cargar fichero
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario qeu realiza la acción
	 * @param vista Ventana en la que se muestra
	 * @param padre Controlador de la ventana en la que se muestra
	 */
	public ControlPanelSubirImagenes(VentanaAnadirProductos vista, ControlAnadirProductos padre) {
		panel = new PanelSubirImagenes();
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case PanelSubirImagenes.ARCHIVOS_ACTION: 
			intentarSubir();
			break;
		}
	}
	
	/**
	 * Acción que se ejecuta al intentar subir una imagen
	 */
	private void intentarSubir() {
		if(TiendaFrame.getConfirmacionUsuario("¿Desea subir una nueva imagen al archivo de la tienda?")) {
			try {
				if(ControlCargaImagen.abrir() == null) {
					new VentanaMensaje("Ha habido algún problema cargando la imagen", 1);
					return;
				}
			} catch (Exception e) {
				new VentanaMensaje("Ha habido algún problema cargando la imagen", 1);
				return;
			}
			new VentanaMensaje("La imagen se ha subido al archivo de la tienda correctamente");
			return;
		}
		
	}

}
