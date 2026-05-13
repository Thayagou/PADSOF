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

import controladores.TiendaFrame;
import vistas.common.displays.PanelCategoria;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Esta clase representa un panel para modificar o borrar una categoría
 */
public class PanelCategoriaGestionarCategoria extends PanelCategoria {
	private static final long serialVersionUID = 1L;
	/** Nombre de la acción asociada a modificar */
	public static final String MODIFICAR_ACTION = "Modificar";
	/** Nombre de la acción asociada a borrar */
	public static final String BORRAR_ACTION = "Borrar";
	/** Nombre de la acción asociada a confirmar */
	public static final String CONFIRMAR_ACTION = "Confirmar";
	/** Nombre de la acción asociada a confirmar */
	public static final String CANCELAR_ACTION = "Cancelar";
	/** Ancho en pixeles de los botones */
	public static final int BTN_WIDTH = TiendaFrame.getInstance().getPixelsWidth(0.1);
	/** Alto en pixeles de los botones */
	public static final int BTN_HEIGHT = TiendaFrame.getInstance().getPixelsWidth(0.03);
	/** Ancho en pixeles del espacio para introducir el nombre de la categoria */
	public static final int FIELD_WIDTH = TiendaFrame.getInstance().getPixelsWidth(0.62);
	/** Botón de modificar */
	private JButton modButton;
	/** Botón de borrar */
	private JButton borrarButton;
	/** Botón de confirmar */
	private JButton confirmarMod;

	/** Campo de nombre de la categoría */
	private JTextField nombreCategoria;
	/** Indica si el panel está extendido */
	private boolean expanded = false;
	/** Panel extendido */
	private JPanel expandedPanel;
	/** Dimensión original del panel */
	private Dimension originalMaxSize;

	/**
	 * Constructor del panel de gestionar categorías
	 * @param nombreCategoria Nombre de la categoría
	 */
	public PanelCategoriaGestionarCategoria(String nombreCategoria) {
		super(nombreCategoria, "");

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		modButton = ButtonFactory.newRoundedButton(MODIFICAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		modButton.setMaximumSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
		ButtonFactory.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		borrarButton = ButtonFactory.newRoundedButton(BORRAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		borrarButton.setMaximumSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
		ButtonFactory.paintButton(borrarButton, ColorPalette.CARD_DARK, ColorPalette.DARK_GREY);
		ButtonFactory.addHoverColorChange(borrarButton);
		
		confirmarMod = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		confirmarMod.setMaximumSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
		confirmarMod.setMinimumSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
		ButtonFactory.paintButton(confirmarMod, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarMod, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
		eastPanel.add(Box.createHorizontalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createHorizontalStrut(gapSize));
		eastPanel.add(borrarButton);
		eastPanel.add(Box.createHorizontalStrut(gapSize));

		add(eastPanel, BorderLayout.EAST);
		
		super.getClickArea().addActionListener(e -> toggleExpand());
		modButton.addActionListener(e -> toggleExpand());
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
		setModificarCancelar();
		expanded = !expanded;
	}

	/**
	 * Expande el panel con la opción de modificar la categoría
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
			textField.setMaximumSize(new Dimension(FIELD_WIDTH, Short.MAX_VALUE));
			textField.setOpaque(false);
			textField.add(label, BorderLayout.NORTH);
			textField.add(nombreCategoria, BorderLayout.CENTER);
			
			expandedPanel.add(textField);
			int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
			expandedPanel.add(Box.createHorizontalStrut(gapSize));
			expandedPanel.add(confirmarMod);

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
	 * Contrae el panel de gestionar categorías
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
	
	private void setModificarCancelar() {
		modButton.setText(expanded ? MODIFICAR_ACTION : CANCELAR_ACTION);
	}

	/**
	 * Asigna un controlador a los botones del panel
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		modButton.addActionListener(c);
		borrarButton.addActionListener(c);
		confirmarMod.addActionListener(c);
	}

	/**
	 * Devuelve el nuevo nombre de la categoría
	 * @return Nombre para la categoría
	 */
	public String getNombreCategoria() {
		return nombreCategoria.getText();
	}

}
