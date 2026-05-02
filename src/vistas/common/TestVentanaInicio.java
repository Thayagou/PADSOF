package vistas.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class TestVentanaInicio extends JPanel implements VentanaConDisplay<PanelDisplay>{

	private static final long serialVersionUID = 1L;

	/** Campo listaPanel. */
	private JPanel listaPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInicioSinRegistrar.
	 *
	 * @param populares parámetro populares
	 */
	public TestVentanaInicio() {
		setOpaque(false);
		setLayout(new BorderLayout());

		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		
		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(PanelFactory.getVentanaConCabecera("      Tests paneles", contenido));
		
		refreshList();
	}
	
	private void refreshList() {
		listaPanel.revalidate();
		listaPanel.repaint();
	}

	/**
	 * Listener disparado al hacer clic en un producto de la lista. ActionCommand:
	 * "Ver producto:<nombreProducto>"
	 *
	 * @param l nuevo valor
	 */
	public void setControlador(ActionListener l) {
		for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}
