package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasTienda extends JPanel implements VentanaConDisplay<PanelEstadisticasTienda>{
	private static final long serialVersionUID = 1L;
	public static final String CONFIRMAR_CAMBIO_FECHA_ACTION = "Confirmar";

	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";

	
	private String[] ordenes;
	private JSpinner inicio;
	private JSpinner fin;
	private JButton confirmar;
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
		listaStats.removeAll();

		for (PanelEstadisticasTienda panel : listaPaneles) {
			listaStats.add(panel);
		}

		revalidate();
		repaint();
	}

	public YearMonth getInicio() {
		return getMes(inicio);
	}
	
	public YearMonth getFin() {
		return getMes(fin);
	}	
	
	public void setInicio(YearMonth inicio) {
		setMes(this.inicio, inicio);
	}
	
	public void setFin(YearMonth fin) {
		setMes(this.fin, fin);
	}
	
	public static YearMonth getMes(JSpinner spinner) {
		Date date = (Date) spinner.getValue();
	    return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()));
	}
	
	public static void setMes(JSpinner spinner, YearMonth month) {
		Date date = Date.from(month.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
	    spinner.setValue(date);
	}
	
	public String getOpcionSeleccionadaOrden() {
		return ordenes[panelOrdenacion.getOpcionSeleccionada()];
	}
	
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
		confirmar.addActionListener(l);
	}
	
	@Override
	public <K extends PanelEstadisticasTienda> PanelEstadisticasTienda anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaStats.add(panelDisplay);
		return panelDisplay;
	}

}
