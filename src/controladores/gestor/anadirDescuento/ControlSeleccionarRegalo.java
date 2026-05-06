package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.PanelSeleccion;
import vistas.common.TiendaFrame;
import vistas.gestor.anadirDescuento.VentanaSeleccionRegalo;

public class ControlSeleccionarRegalo implements ControlGestionSeleccion<Producto>, ActionListener {
	private static double POPUP_WIDTH = 0.5;
	private static double POPUP_HEIGHT = 0.5;
	
	private Tienda tienda;
	private Producto regalo;
	private PanelSeleccion panelRegalo;
	private VentanaSeleccionRegalo vista;

	public ControlSeleccionarRegalo(Tienda tienda, JPanel vistaInferior) {
		this.tienda = tienda;
		this.vista = new VentanaSeleccionRegalo();

		anadirProductos();
		TiendaFrame frame = TiendaFrame.getInstance();
		int width = frame.getPixelsWidth(POPUP_WIDTH);
		int height = frame.getPixelsHeight(POPUP_HEIGHT);

		JDialog dialog = new JDialog(frame, "Productos populares", true);

		dialog.setContentPane(vista);
		dialog.setSize(width, height);
		dialog.setLocationRelativeTo(vistaInferior);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

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
	
	public Producto getRegalo() { return regalo; }

}
