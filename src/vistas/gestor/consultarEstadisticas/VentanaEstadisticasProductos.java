package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.UIManager;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasProductos extends JPanel implements VentanaConDisplay<PanelProducto> {
	private static final long serialVersionUID = 1L;
	public static final String CONFIRMAR_CAMBIO_FECHA_ACTION = "Confirmar";
	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";
	public static final String MAYOR_RECAUDACION = "Mayor recaudación";
	public static final String MENOR_RECAUDACION = "Menor recaudación";
	public static final String MAS_UNIDADES = "Más unidades vendidas";
	public static final String MENOS_UNIDADES = "Menos unidades vendidas";

	public static String[] ORDENES = { MAYOR_RECAUDACION, MENOR_RECAUDACION, MAS_UNIDADES, MENOS_UNIDADES };
	private static double MAX_HEIGHT_CABECERA = 0.05;
	private JSpinner inicio;
	private JSpinner fin;
	private JButton confirmar;
	private JPanel listaProductos;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelProducto> listaPaneles = new ArrayList<>();

	public VentanaEstadisticasProductos() {
		setOpaque(false);
		setLayout(new BorderLayout(30, 0));

		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);

		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());
		//cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);

		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Total recaudado", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Unidades vendidas", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Porcentaje de recaudación", size, ColorPalette.WHITE));

		// Lista
		listaProductos = new JPanel();
		listaProductos.setLayout(new BoxLayout(listaProductos, BoxLayout.Y_AXIS));
		listaProductos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaProductos);
		//JPanel panelScroll = new JPanel();
		//panelScroll.add(scroll, BorderLayout.CENTER);
		JPanel wrapper = new JPanel();
		wrapper.setOpaque(false);
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
		wrapper.add(statsPanel);
		wrapper.add(Box.createHorizontalStrut(3*UIManager.getInt("ScrollBar.width")));
		
		cabecera.add(wrapper, BorderLayout.EAST);
		JPanel contenido = new JPanel(new BorderLayout());
		
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);
		panelOrdenacion.setActionCommand(CAMBIO_ORDEN_ACTION);
		
		// Obtiene el panel de cabecera norte del PanelMultiopcion y le añade los spinners para elegir mes de inicio y fin 
		BorderLayout layout = (BorderLayout) panelOrdenacion.getLayout();
		JPanel norte = (JPanel) layout.getLayoutComponent(panelOrdenacion, BorderLayout.NORTH);
		
		// Crea los spinners de inicio y fin y los añade a la cabecera
		JLabel labelInicio = ButtonFactory.newLeftAlignedLabel("Inicio", Fonts.TITLE3);
		labelInicio.setForeground(ColorPalette.WHITE.getColor());
		inicio = ButtonFactory.spinnerFechaYearMonth(Fonts.TEXT);
		
		JLabel labelFin = ButtonFactory.newLeftAlignedLabel("Fin", Fonts.TITLE3);
		labelFin.setForeground(ColorPalette.WHITE.getColor());
		fin = ButtonFactory.spinnerFechaYearMonth(Fonts.TEXT);
		
		confirmar = ButtonFactory.newRoundedButton(CONFIRMAR_CAMBIO_FECHA_ACTION, maxHeight, TiendaFrame.getInstance().getPixelsWidth(0.08), maxHeight);
		
		
		int gap = TiendaFrame.getInstance().getPixelsWidth(0.07);
		norte.add(Box.createHorizontalStrut(gap));
		norte.add(labelInicio);
		norte.add(inicio);
		norte.add(Box.createHorizontalStrut(gap));
		norte.add(labelFin);
		norte.add(fin);
		norte.add(Box.createHorizontalStrut(gap));
		norte.add(confirmar);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void vaciarLista() {
		listaPaneles.clear();
	}

	public void refrescarLista() {
		listaProductos.removeAll();

		for (PanelProducto panel : listaPaneles) {
			listaProductos.add(panel);
		}

		revalidate();
		repaint();
	}

	public String getOpcionSeleccionadaOrden() {
		return ORDENES[panelOrdenacion.getOpcionSeleccionada()];
	}
	
	public YearMonth getInicio() {
		return VentanaEstadisticasTienda.getMes(inicio);
	}
	
	public YearMonth getFin() {
		return VentanaEstadisticasTienda.getMes(fin);
	}	
	
	public void setInicio(YearMonth inicio) {
		VentanaEstadisticasTienda.setMes(this.inicio, inicio);
	}
	
	public void setFin(YearMonth fin) {
		VentanaEstadisticasTienda.setMes(this.fin, fin);
	}

	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
		confirmar.addActionListener(l);
	}

	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaProductos.add(panelDisplay);
		return panelDisplay;
	}

}
