package vistas.gestor.configurarSistema;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarParametrosSistema extends JPanel implements VentanaConDisplay<PanelParametroSistema> {
	private static final long serialVersionUID = 1L;
	
	private JPanel listaParametros = new JPanel();
	
	public VentanaGestionarParametrosSistema() {
		setLayout(new BorderLayout());
		listaParametros.setLayout(new BoxLayout(listaParametros, BoxLayout.Y_AXIS));
		listaParametros.setBackground(ColorPalette.CARD_LIGHT.getColor());
		// listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaParametros);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		add(PanelFactory.getVentanaConCabecera("      Parametros del sistema", contenido), BorderLayout.CENTER);
	}
	
	
	@Override
	public <K extends PanelParametroSistema> PanelParametroSistema anadirDisplay(K panelDisplay) {
		listaParametros.add(panelDisplay);
		return panelDisplay;
	}

}
