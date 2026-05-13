package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.common.components.PanelSeleccion;
import vistas.gestor.anadirDescuento.VentanaSeleccionRegalo;

/**
 * Clase controladora del panel correspondiente a la selección de un regalo a la hora de crear un descuento.
 */
public class ControlSeleccionarRegalo implements ControlGestionSeleccion<Producto>, ActionListener {
	
	/** Porcentaje de anchura de pantalla que ocupa el popup */
	private static double POPUP_WIDTH = 0.5;
	
	/** Porcentaje de altura de pantalla que ocupa el popup */
	private static double POPUP_HEIGHT = 0.5;
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Producto que se selecciona como regalo */
	private Producto regalo;
	
	/** Panel asociado al regalo seleccionado*/
	private PanelSeleccion panelRegalo;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaSeleccionRegalo vista;
	
	/** Popup que se muestra con la ventana de selección */
	private JDialog dialog;

	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param vistaInferior Vista sobre la que se muestra el popup. Sirve para centrarlo en pantalla
	 */
	public ControlSeleccionarRegalo(Tienda tienda, JPanel vistaInferior, Producto regalo) {
		this.tienda = tienda;
		this.vista = new VentanaSeleccionRegalo();
		this.regalo = regalo;

		anadirProductos();
		TiendaFrame frame = TiendaFrame.getInstance();
		int width = frame.getPixelsWidth(POPUP_WIDTH);
		int height = frame.getPixelsHeight(POPUP_HEIGHT);

		dialog = new JDialog(frame, "Productos populares", true);

		dialog.setContentPane(vista);
		dialog.setSize(width, height);
		dialog.setLocationRelativeTo(vistaInferior);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

	}

	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * 
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * Añade las opciones de producto al popup
	 */
	private void anadirProductos() {
		Producto[] catalogo = tienda.getAlmacen().getProductosCoincidentes("");

		for (Producto p : catalogo) {
			ControlPanelProductoSeleccion control = new ControlPanelProductoSeleccion(tienda, p, "Regalo", "Seleccionar", this, vista);
			if (this.regalo != null && this.regalo.equals(p)) {
				this.panelRegalo = control.getPanel();
				this.panelRegalo.toggleCheckBox();
			}
		}
	}

	/**
	 * Marca el producto como seleccionado y pasa junto a él su panel asociado
	 *
	 * @param elem Producto que se desea seleccionar
	 * @param panel Panel correspondiente a dicho elemento
	 * @param seleccionado Determina si está o no seleccionado
	 */
	@Override
	public void setSeleccionado(Producto elem, PanelSeleccion panel, boolean seleccionado) {
		if (this.regalo == null) {
			if (!seleccionado)
				return;

			this.regalo = elem;
			this.panelRegalo = panel;
			this.panelRegalo.toggleCheckBox();
		} else if (seleccionado) {
			this.panelRegalo.toggleCheckBox();
			panel.toggleCheckBox();
			this.panelRegalo = panel;
			this.regalo = elem;
		} else {
			if (!seleccionado) {
				if (this.regalo.equals(elem)) {
					this.panelRegalo.toggleCheckBox();
					this.regalo = null;
					this.panelRegalo = null;
				}
			} else {
				if (!this.regalo.equals(elem)) {
					this.panelRegalo.toggleCheckBox();
					panel.toggleCheckBox();
					this.panelRegalo = panel;
					this.regalo = elem;
				}

			}
		}
	}
	
	/**
	 * Getter del producto que se va a dar como regalo
	 *
	 * @return Producto a regalar en el descuento
	 */
	public Producto getRegalo() { return regalo; }

}
