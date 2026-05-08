package vistas.empleado.gestionarProductos.gestionarCategorias;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Esta clase representa el panel que permite crear una nueva categoría
 */
public class PanelCrearCategoria extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	/** Nombre de la acción de crear una categoría */
	public final static String CREAR_ACTION = "Crear nueva categoría...";
	/** Nombre de la acción confirmar */
	public final static String CONFIRMAR_ACTION = "Confirmar";
	/** Botón de confirmar */
	private JButton confirmarButton;
	
	/** Campo con nombre de la categoría */
	private JTextField nombreCategoria;
	/** Indica si el panel está expandido */
	private boolean expanded = false;
	/** Panel expandido */
	private JPanel expandedPanel;
	/** Dimensión original del panel */
	private Dimension originalMaxSize;

	/**
	 * Constructor de un panel para crear una categoría
	 */
	public PanelCrearCategoria() {
		super(0.08, 0.06, CREAR_ACTION);
		
		JLabel label = new JLabel(CREAR_ACTION);
		label.setFont(Fonts.TITLE3.getFont());
		add(label, BorderLayout.CENTER);
		
		confirmarButton = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		ButtonFactory.paintButton(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		super.getClickArea().addActionListener(e -> toggleExpand());
	}
	
	/**
	 * Modifica el estado de expansión del panel
	 */
	public void toggleExpand() {
		if (expanded) {
			collapsePanel();
		} else {
			expandPanel();
		}
		expanded = !expanded;
	}

	/**
	 * Expande el panel
	 */
	private void expandPanel() {
		if (expandedPanel == null) {
			expandedPanel = new JPanel();
			expandedPanel.setOpaque(false);
			expandedPanel.setLayout(new BoxLayout(expandedPanel, BoxLayout.X_AXIS));
			
			nombreCategoria = ButtonFactory.newTextField("Nombre", Fonts.TEXT);
			JLabel label = new JLabel("Introduzca el nuevo nombre");
			label.setFont(Fonts.TEXT.getFont());
			JPanel textField = new JPanel(new BorderLayout());
			textField.setOpaque(false);
			textField.add(label, BorderLayout.NORTH);
			textField.add(nombreCategoria, BorderLayout.CENTER);
			
			expandedPanel.add(textField);
			int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
			expandedPanel.add(Box.createHorizontalStrut(gapSize));
			expandedPanel.add(confirmarButton);

			expandedPanel.setVisible(false);
			add(expandedPanel, BorderLayout.SOUTH);
		}

		expandedPanel.setVisible(true);
		
		if (originalMaxSize == null) {
	        originalMaxSize = getMaximumSize();
	    }
		int expandedHeight = originalMaxSize.height * 2;
	    setMaximumSize(new Dimension(originalMaxSize.width, expandedHeight));

	    revalidate();
	    repaint();
	}

	/**
	 * Reduce el tamaño del panel
	 */
	private void collapsePanel() {
		if (expandedPanel != null) {
	        expandedPanel.setVisible(false);
	    }

		if (originalMaxSize != null && getParent() != null) {
	        setMaximumSize(originalMaxSize);
	    }

	    revalidate();
	    repaint();
	}
	
	/**
	 * Asigna un controlador a los componentes del panel
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		confirmarButton.addActionListener(c);
	}
	
	/**
	 * Devuelve el nombre de la nueva categoría
	 * @return El nombre de la categoría
	 */
	public String getNombreCategoria() {
		return nombreCategoria.getText();
	}

}
