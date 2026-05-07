package controladores.noRegistrado;

import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.venta.productos.*;
import vistas.common.*;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.noRegistrado.*;

public class ControlInicioSinRegistrar implements ActionListener, ControladorPantalla {

	private Tienda tienda;
	private VentanaInicioSinRegistrar vista;
	//private TestVentanaInicio vista;
	
	public ControlInicioSinRegistrar(Tienda tienda) {
		this.tienda = tienda;
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

		// Barra de tareas superior
		ControlBarraTareasNoRegistrado ctrlBarraTareas = new ControlBarraTareasNoRegistrado(tienda);
		BarraTareasNoRegistrado barraTareas = new BarraTareasNoRegistrado();
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);

		//Barra lateral vacía
		tiendaFrame.removeBarraLateral();
		
		// Obtener productos populares del modelo y construir la vista
		Producto[] populares = tienda.getAlmacen().getProductosCoincidentes("");

		this.vista = new VentanaInicioSinRegistrar();
		for(Producto p : populares) {
			new ControlPanelProductoNoRegistrado(tienda, p, vista);
		}
		tiendaFrame.resetearNavegacion(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd != null && cmd.startsWith("Ver producto:")) {
			String nombreProducto = cmd.substring("Ver producto:".length());
			SwingUtilities.invokeLater(() -> {
				try {
					Producto producto = tienda.getAlmacen().getStock(nombreProducto).getProducto();
					new ControlProductoSinRegistrar(tienda, producto);
				} catch (Exception ex) {
					new VentanaMensaje("Producto no encontrado: " + nombreProducto);
				}
			});
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}