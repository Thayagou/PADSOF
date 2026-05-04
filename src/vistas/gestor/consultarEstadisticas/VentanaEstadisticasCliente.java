package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelMultiopcion;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasCliente extends JPanel implements VentanaConDisplay<PanelClienteEstadisticas>{
	private static final long serialVersionUID = 1L;
	public static String MAYOR_RECAUDACION = "Mayor recaudación";
	public static String MENOR_RECAUDACION = "Menor recaudación";
	public static String MAS_UNIDADES = "Más productos comprados";
	public static String MENOS_UNIDADES = "Menos productos comprados";
	public static String MAS_ARTICULOS = "Más artículos intercambiados";
	public static String MENOS_ARTICULOS = "Menos artículos intercambiados";
	
	
	public static String[] ORDENES = {MAYOR_RECAUDACION, MENOR_RECAUDACION, 
			MAS_UNIDADES, MENOS_UNIDADES, MAS_ARTICULOS, MENOS_ARTICULOS};
	
	private JPanel listaClientes;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelClienteEstadisticas> listaPaneles = new ArrayList<>();
	
	public VentanaEstadisticasCliente() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// Lista 
		listaClientes = new JPanel();
		listaClientes.setLayout(new BoxLayout(listaClientes, BoxLayout.Y_AXIS));
		listaClientes.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaClientes);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scroll);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void refrescarLista() {
		listaClientes.removeAll();
		
		for (PanelClienteEstadisticas panel: listaPaneles) {
			listaClientes.add(panel);
		}
		
		revalidate();
		repaint();
	}
	
	public List<PanelClienteEstadisticas> getListaPaneles() {
		return listaPaneles;
	}
	
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}
	
	@Override
	public <K extends PanelClienteEstadisticas> PanelClienteEstadisticas anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaClientes.add(panelDisplay);
		return panelDisplay;
	}

}
