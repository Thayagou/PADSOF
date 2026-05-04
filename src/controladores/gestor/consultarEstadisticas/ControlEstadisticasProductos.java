package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modelo.estadistica.StatsMensual;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
import vistas.gestor.consultarEstadisticas.PanelProductoEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasProductos;

public class ControlEstadisticasProductos implements ActionListener {
	private Tienda tienda;
	private Gestor gestor;
	private VentanaEstadisticasProductos vista;
	
	public ControlEstadisticasProductos(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaEstadisticasProductos();
		
		YearMonth inicio = YearMonth.of(2000, 1);
		YearMonth fin = YearMonth.now();
		
		
		try {
			List<Map.Entry<Producto, StatsMensual>> listaProductos = tienda.getHistorial().getProductosMayorRecaudacion(inicio, fin);
			StatsMensual total = tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			
			for (Map.Entry<Producto, StatsMensual> par : listaProductos) {
				Producto p = par.getKey();
				StatsMensual stats = par.getValue();
				ArrayList<String> categorias = new ArrayList<>();
				for(Categoria c : p.getCategorias()) {
					categorias.add(c.getNombre());
				}
				String imageName = p.getImagen() == null ? "producto.png" : p.getImagen();
				
				vista.anadirDisplay(new PanelProductoEstadisticas(p.getNombre(), p.getDescripcion(), imageName, p.getPuntuacionMedia(), p.getPrecio(), stats.getRecaudacion(), stats.getUnidades(), stats.getRecaudacion()/total.getRecaudacion(), categorias.toArray(new String[0])));
			}
			
			TiendaFrame.getInstance().setVistaActual(vista);
		} catch(InvalidArgumentException e) {
			new VentanaMensaje(e.toString());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
