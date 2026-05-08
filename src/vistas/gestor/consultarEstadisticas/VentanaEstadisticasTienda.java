package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasTienda extends JPanel implements VentanaConDisplay<PanelEstadisticasTienda>{
	private static final long serialVersionUID = 1L;

	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";

	
	private String[] ordenes;
	private static double MAX_HEIGHT_CABECERA = 0.05;
	private JPanel listaStats;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelEstadisticasTienda> listaPaneles = new ArrayList<>();
	
	public VentanaEstadisticasTienda(String[] ordenes, String...columnas) {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		this.ordenes = ordenes;
		
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);
		
		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

		JPanel statsPanel = new JPanel(new GridLayout(1, columnas.length, 20, 0));
		statsPanel.setOpaque(false);

		for (String col: columnas) {
			statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(col, size, ColorPalette.WHITE));
		}
		
		cabecera.add(statsPanel, BorderLayout.EAST);
		
		// Lista 
		listaStats = new JPanel();
		listaStats.setLayout(new BoxLayout(listaStats, BoxLayout.Y_AXIS));
		listaStats.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaStats);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ordenes);
		panelOrdenacion.setActionCommand(CAMBIO_ORDEN_ACTION);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void vaciarLista() {
		listaPaneles.clear();
	}

	public void refrescarLista() {
		listaStats.removeAll();

		for (PanelEstadisticasTienda panel : listaPaneles) {
			listaStats.add(panel);
		}

		revalidate();
		repaint();
	}

	public String getOpcionSeleccionadaOrden() {
		return ordenes[panelOrdenacion.getOpcionSeleccionada()];
	}
	
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}
	
	@Override
	public <K extends PanelEstadisticasTienda> PanelEstadisticasTienda anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaStats.add(panelDisplay);
		return panelDisplay;
	}

}
