package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsMensual;
import modelo.estadistica.StatsUsuario;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;
import vistas.gestor.consultarEstadisticas.PanelProductoEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasCliente;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasProductos;

public class ControlEstadisticasProductos implements ControladorPantalla {
	private Tienda tienda;
	private Gestor gestor;
	private VentanaEstadisticasProductos vista;
	private List<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>> panelesEstadisticas = new ArrayList<>();
	private Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>> orden;
	
	public ControlEstadisticasProductos(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaEstadisticasProductos();
		vista.setControlador(this);
		orden = getComparator(VentanaEstadisticasProductos.ORDENES[0]);
		
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
				double porcentaje = stats.getRecaudacion()/total.getRecaudacion() * 100;
				
				PanelProductoEstadisticas panel = new PanelProductoEstadisticas(p.getNombre(), p.getDescripcion(), imageName, p.getPuntuacionMedia(), p.getPrecio(), stats.getRecaudacion(), stats.getUnidades(), porcentaje, categorias.toArray(new String[0]));
				panelesEstadisticas.add(new ParElementoPanel<>(stats, panel));
				
				vista.anadirDisplay(panel);
			}
			
			TiendaFrame.getInstance().navegarA(this);
		} catch(InvalidArgumentException e) {
			new VentanaMensaje(e.toString());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(VentanaEstadisticasProductos.CAMBIO_ORDEN_ACTION)) {
			orden = getComparator(vista.getOpcionSeleccionadaOrden());
			panelesEstadisticas.sort(orden);
			
			vista.vaciarLista();
			
			for (ParElementoPanel<StatsMensual, PanelProductoEstadisticas> par : panelesEstadisticas) {
				vista.anadirDisplay(par.getPanel());
			}
			
			vista.refrescarLista();	
		}
		
	}
	
	private Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>> getComparator(String criterio) {
		switch(criterio) {
		case VentanaEstadisticasProductos.MAYOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p2.getElem().getRecaudacion(), p1.getElem().getRecaudacion());
	            }
			};
		case VentanaEstadisticasProductos.MENOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p1.getElem().getRecaudacion(), p2.getElem().getRecaudacion());
	            }
			};
		case VentanaEstadisticasProductos.MAS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p2.getElem().getUnidades(), p1.getElem().getUnidades());
	            }
			};
		case VentanaEstadisticasProductos.MENOS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p1.getElem().getUnidades(), p2.getElem().getUnidades());
	            }
			};
		}
		
		return orden;
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	

}
