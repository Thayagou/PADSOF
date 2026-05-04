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

import vistas.common.PanelCategoria;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoriaGestionarCategoria extends PanelCategoria {

	private static final long serialVersionUID = 1L;
	public static final String MODIFICAR_ACTION = "Modificar";
	public static final String BORRAR_ACTION = "Borrar";
	public static final String CONFIRMAR_ACTION = "Confirmar";
	private JButton modButton;
	private JButton borrarButton;
	private JButton confirmarMod;

	private JTextField nombreCategoria;
	private boolean expanded = false;
	private JPanel expandedPanel;
	private Dimension originalMaxSize;

	public PanelCategoriaGestionarCategoria(String nombreCategoria) {
		super(nombreCategoria, "");

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		modButton = ButtonFactory.newRoundedButton(MODIFICAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H),
				maxCompHeight, 0.75f);
		ButtonFactory.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		borrarButton = ButtonFactory.newRoundedButton(BORRAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H),
				maxCompHeight, 0.75f);
		ButtonFactory.paintButton(borrarButton, ColorPalette.RED, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(borrarButton, ColorPalette.RED, ColorPalette.LIGHT_RED);
		
		confirmarMod = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
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

	public void toggleExpand() {
		if (expanded) {
			collapsePanel();
		} else {
			expandPanel();
		}
		expanded = !expanded;
	}

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

	public void setControlador(ActionListener c) {
		super.setControlador(c);
		modButton.addActionListener(c);
		borrarButton.addActionListener(c);
		confirmarMod.addActionListener(c);
	}

	public String getNombreCategoria() {
		return nombreCategoria.getText();
	}

}
