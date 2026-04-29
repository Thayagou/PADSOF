package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.noRegistrado.VentanaProductoSinRegistrar;

public class ControlProductoSinRegistrar implements ActionListener {

	private Tienda tienda;
	private VentanaProductoSinRegistrar vista;

	public ControlProductoSinRegistrar(Tienda tienda, Producto producto) {
		this.tienda = tienda;
		this.vista = new VentanaProductoSinRegistrar(producto);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// De momento la vista de detalle de producto no tiene botones con acción.
		// Cuando se añadan (p.ej. "Volver", "Iniciar sesión para comprar"), se
		// gestionan aquí:
		switch (e.getActionCommand()) {
		case "Volver" -> this.volver();
		case "Iniciar sesión" -> SwingUtilities.invokeLater(() -> new ControlLoginRegistro(tienda));
		}
	}

	private void volver() {
		// Vuelve a la pantalla de inicio sin registrar
		SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
	}
}
