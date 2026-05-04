package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Desktop.Action;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelMultiopcion;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasProductos extends JPanel implements VentanaConDisplay<PanelProducto>{
	private static final long serialVersionUID = 1L;
	public static String MAYOR_RECAUDACION = "Mayor recaudación";
	public static String MENOR_RECAUDACION = "Menor recaudación";
	public static String MAS_UNIDADES = "Más unidades vendidas";
	public static String MENOS_UNIDADES = "Menos unidades vendidas";
	
	public static String[] ORDENES = {MAYOR_RECAUDACION, MENOR_RECAUDACION, MAS_UNIDADES, MENOS_UNIDADES};
	
	private JPanel listaProductos;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelProducto> listaPaneles = new ArrayList<>();
	
	public VentanaEstadisticasProductos() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// Lista 
		listaProductos = new JPanel();
		listaProductos.setLayout(new BoxLayout(listaProductos, BoxLayout.Y_AXIS));
		listaProductos.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaProductos);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scroll);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void refrescarLista() {
		listaProductos.removeAll();
		
		for (PanelProducto panel: listaPaneles) {
			listaProductos.add(panel);
		}
		
		revalidate();
		repaint();
	}
	
	public List<PanelProducto> getListaPaneles() {
		return listaPaneles;
	}
	
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}
	
	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaProductos.add(panelDisplay);
		return panelDisplay;
	}

}
