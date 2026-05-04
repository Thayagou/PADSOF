package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.estadistica.StatsMensual;
import modelo.estadistica.StatsProducto;
import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelCategoria;
import vistas.common.PanelCategoriaSeleccion;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.gestor.PanelProductoEstadisticas;

public class ControlPanelEstadisticaProducto implements ActionListener {
	private Producto producto;
	private StatsMensual stats;
	private Tienda tienda;
	private PanelProductoEstadisticas panel;
	
	public ControlPanelEstadisticaProducto(Tienda tienda, Producto producto, StatsMensual stats, VentanaConDisplay<? super PanelProducto> vista) {
		this.tienda = tienda;
		this.producto = producto;
		this.stats = stats;
		
		String imageName = producto.getImagen() == null ? "producto.png" : producto.getImagen();
		
		panel = new PanelProductoEstadisticas(producto.getNombre(), producto.getDescripcion(), imageName, producto.getPuntuacionMedia(), producto.getPrecio(), stats.getRecaudacion(), stats.getUnidades(), stats.getRecaudacion()/total.getRecaudacion(), categorias.toArray(new String[0]))
		panel = new PanelProductoEstadisticas(producto.getNombre(), producto.getDescripcion(), imageName, producto.getPuntuacionMedia(), producto.getPrecio(), stats.getRecaudacion(), stats.getUnidades(), .getDescripcion());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaSeleccion.INCLUIR_ACTION:
			panel.toggleCheckBox();
			break;
		}
	}

}
