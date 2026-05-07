package vistas.empleado.valorarArticulos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaValoracionIndividual extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField estimacion;
	private JComboBox<String> estado;
	private JButton btnValorar;

	private static int MAX_DESC = 120;

	public VentanaValoracionIndividual(String nombreUser, String imagenUser, String fecha, String nombreArt,
			String imagenArt, String[] categorias, String desc, String[] tiposEstado) {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// Panel IZQUIERDO: info del artículo
		JPanel dcha = new JPanel(new GridBagLayout());
		dcha.setOpaque(false);
		dcha.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 24));
		GridBagConstraints gbcD = new GridBagConstraints();
		gbcD.insets = new Insets(6, 0, 6, 0);
		gbcD.anchor = GridBagConstraints.WEST;
		gbcD.fill = GridBagConstraints.HORIZONTAL;
		gbcD.gridx = 0;
		gbcD.weightx = 1.0;

		//Información del artículo
		JLabel fechaSol = ButtonFactory.newLabel(fecha, Fonts.SUBTITLE);
		gbcD.gridy = 0;
		dcha.add(fechaSol, gbcD);

		JLabel nombreArtic = ButtonFactory.newLabel(nombreArt, Fonts.TITLE);
		gbcD.gridy = 1;
		dcha.add(nombreArtic, gbcD);

		JLabel imageArtic = new JLabel(
				ButtonFactory.loadImageIconScaled(imagenArt, TiendaFrame.getInstance().getPixelsHeight(0.4), TiendaFrame.getInstance().getPixelsWidth(0.3)));
		imageArtic.setHorizontalAlignment(SwingConstants.CENTER);
		gbcD.gridy = 2;
		gbcD.fill = GridBagConstraints.NONE;
		gbcD.anchor = GridBagConstraints.CENTER;
		dcha.add(imageArtic, gbcD);

		String cats = String.join(", ", categorias);
		if (!cats.isEmpty()) {
			if (cats.length() > MAX_DESC)
				cats = cats.substring(0, MAX_DESC) + "...";
			JLabel categoriasLabel = ButtonFactory.newLabel(cats, Fonts.BOLD);
			categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
			gbcD.gridy = 3;
			gbcD.fill = GridBagConstraints.HORIZONTAL;
			gbcD.anchor = GridBagConstraints.WEST;
			dcha.add(categoriasLabel, gbcD);
		}

		if (desc.length() > MAX_DESC)
			desc = desc.substring(0, MAX_DESC) + "...";
		JLabel descArt = ButtonFactory.newLabel(desc, Fonts.BOLD);
		descArt.setForeground(ColorPalette.DARK_GREY.getColor());
		gbcD.gridy = 4;
		dcha.add(descArt, gbcD);

		// Información de usuario
		JPanel izqdaWrapper = new JPanel(new BorderLayout());
		izqdaWrapper.setOpaque(false);
		izqdaWrapper.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 16));
		
		JPanel izqda = new JPanel(new GridBagLayout());
		izqda.setOpaque(false);
		izqda.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 16));
		GridBagConstraints gbcI = new GridBagConstraints();
		gbcI.insets = new Insets(6, 0, 6, 0);
		gbcI.fill = GridBagConstraints.HORIZONTAL;
		gbcI.anchor = GridBagConstraints.NORTHWEST;
		gbcI.gridx = 0;
		gbcI.weightx = 1.0;

		// Bloque de usuario
		JLabel labelSolicitante = ButtonFactory.newLabel("Solicitante", Fonts.SUBTITLE);
		labelSolicitante.setForeground(ColorPalette.PURPLE.getColor());
		gbcI.gridy = 0;
		izqda.add(labelSolicitante, gbcI);

		JLabel imageUser = new JLabel(
				ButtonFactory.loadImageIconScaled(imagenUser, TiendaFrame.getInstance().getPixelsHeight(0.12), TiendaFrame.getInstance().getPixelsHeight(0.12)));
		JLabel labelNombreUser = ButtonFactory.newLabel(nombreUser, Fonts.TITLE3);

		JPanel userPanel = new JPanel();
		userPanel.setOpaque(false);
		userPanel.add(imageUser);
		userPanel.add(labelNombreUser);
		gbcI.gridy = 1;
		izqda.add(userPanel, gbcI);

		// Separador
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setForeground(ColorPalette.PURPLE.getColor());
		gbcI.gridy = 2;
		gbcI.insets = new Insets(10, 0, 10, 0);
		izqda.add(sep, gbcI);

		// Bloque izquierda
		JLabel labelValoracion = ButtonFactory.newLabel("Valoración", Fonts.SUBTITLE);
		labelValoracion.setForeground(ColorPalette.PURPLE.getColor());
		gbcI.gridy = 3;
		gbcI.insets = new Insets(6, 0, 6, 0);
		izqda.add(labelValoracion, gbcI);

		// Elegir estimación
		JLabel labelEstimacion = ButtonFactory.newLabel("Estimación de precio:", Fonts.TITLE3);
		gbcI.gridy = 4;
		izqda.add(labelEstimacion, gbcI);
		estimacion = ButtonFactory.newTextField(" 0.0 €", Fonts.TITLE3);
		gbcI.gridy = 5;
		izqda.add(estimacion, gbcI);

		// Elegir estado
		JLabel labelEstado = ButtonFactory.newLabel("Estado:", Fonts.TITLE3);
		gbcI.gridy = 6;
		izqda.add(labelEstado, gbcI);
		estado = ButtonFactory.newComboBox(Fonts.TITLE3, tiposEstado);
		gbcI.gridy = 7;
		izqda.add(estado, gbcI);
		izqdaWrapper.add(izqda, BorderLayout.NORTH);

		// Botón valorar
		btnValorar = ButtonFactory.newRoundedButton("Valorar", TiendaFrame.getInstance().getPixelsHeight(0.1), TiendaFrame.getInstance().getPixelsWidth(0.05), 0.5f);
		btnValorar.setActionCommand("Valorar");
		btnValorar.setFont(Fonts.BOLD.getFont());
		ButtonFactory.paintButton(btnValorar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnValorar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		gbcI.gridy = 9;
		gbcI.weighty = 0;
		gbcI.fill = GridBagConstraints.HORIZONTAL;
		gbcI.anchor = GridBagConstraints.SOUTH;
		gbcI.insets = new Insets(10, 0, 10, 0);
		izqdaWrapper.add(btnValorar, BorderLayout.SOUTH);

		// Separador vertical entre paneles
		JSeparator separadorVertical = new JSeparator(SwingConstants.VERTICAL);
		separadorVertical.setForeground(ColorPalette.PURPLE.getColor());
		separadorVertical.setPreferredSize(new Dimension(2, 0));

		// Panel contenedor
		JPanel contenido = new JPanel(new GridBagLayout());
		contenido.setOpaque(false);
		GridBagConstraints gbcC = new GridBagConstraints();

		gbcC.gridy = 0;
		gbcC.fill = GridBagConstraints.BOTH;
		gbcC.weighty = 1.0;

		gbcC.gridx = 2;
		gbcC.weightx = 0.5;
		contenido.add(dcha, gbcC);

		gbcC.gridx = 1;
		gbcC.weightx = 0.0;
		gbcC.fill = GridBagConstraints.VERTICAL;
		contenido.add(separadorVertical, gbcC);

		gbcC.gridx = 0;
		gbcC.weightx = 0.5;
		gbcC.weighty = 1.0;
		gbcC.fill = GridBagConstraints.BOTH;
		contenido.add(izqdaWrapper, gbcC);

		add(PanelFactory.getVentanaConCabecera("Producto a valorar", contenido), BorderLayout.CENTER);
	}

	public void setControlador(ActionListener c) {
		estimacion.addActionListener(c);
		estado.addActionListener(c);
		btnValorar.addActionListener(c);
	}

	public String getEstimacion() {
		return estimacion.getText();
	}

	public String getEstadoFisico() {
		return (String) estado.getSelectedItem();
	}
}
