package controladores.noRegistrado;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.*;
import vistas.common.*;
import vistas.noRegistrado.*;

public class ControlInicioSinRegistrar implements ActionListener {

	private Tienda tienda;
	//private VentanaInicioSinRegistrar vista;
	private TestVentanaInicio vista;
	
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

		//this.vista = new VentanaInicioSinRegistrar();
		this.vista = new TestVentanaInicio();
		//this.vista.setClickListener(this);
		for(Producto p : populares) {
			//new ControlPanelProductoNoRegistrado(tienda, p, vista);
			new ControladorTest(tienda, p, vista);
		}
		tiendaFrame.setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*String cmd = e.getActionCommand();
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
		}*/
	}
}