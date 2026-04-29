package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.noRegistrado.VentanaResultadosNoRegistrado;

public class ControlResultadosNoRegistrado implements ActionListener {

	private Tienda tienda;
	private VentanaResultadosNoRegistrado vista;

	public ControlResultadosNoRegistrado(Tienda tienda, Producto[] productos) {
		this.tienda = tienda;
		this.vista = new VentanaResultadosNoRegistrado(productos);
		this.vista.setClickListener(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd != null && cmd.startsWith("Ver producto:")) {
			String nombreProducto = cmd.substring("Ver producto:".length());
			this.mostrarProducto(nombreProducto);
		}
	}

	private void mostrarProducto(String nombreProducto) {
		SwingUtilities.invokeLater(() -> {
			try {
				Producto producto = tienda.getAlmacen().getStock(nombreProducto).getProducto();
				new ControlProductoSinRegistrar(tienda, producto);
			} catch (Exception ex) {
				new vistas.common.VentanaMensaje("Producto no encontrado: " + nombreProducto);
			}
		});
	}
}
