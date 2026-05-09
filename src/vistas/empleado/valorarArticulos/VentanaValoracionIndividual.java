package vistas.empleado.valorarArticulos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

/**
 * Esta clase representa una ventana donde especificar una valoración de un artículo
 */
public class VentanaValoracionIndividual extends JPanel {
	private static final long serialVersionUID = 1L;
	/** Campo de la estimación asociada al artículo */
	private JTextField estimacion;
	/** Seleccionador del estado del artículo */
	private JComboBox<String> estado;
	/** Botón de valorar */
	private JButton btnValorar;
	/** Carácteres máximos para la descripción */
	private static int MAX_DESC = 120;

	/**
	 * Constructor de la ventana de crear una valoración
	 * @param nombreUser Nombre el usuario propietario
	 * @param imagenUser Imagen del usuario
	 * @param fecha Fecha de solicitud de la valoración
	 * @param nombreArt Nombre del artículo
	 * @param imagenArt Imagen del artículo
	 * @param categorias Categorías del artículo
	 * @param desc En qué está interesado el propietario
	 * @param tiposEstado Tipos de estados posibles para el artículo
	 */
	public VentanaValoracionIndividual(String nombreUser, String imagenUser, String fecha, String nombreArt,
			String imagenArt, String[] categorias, String desc, String[] tiposEstado) {
		setLayout(new BorderLayout(0, 0));
		setBackground(ColorPalette.CARD_LIGHT.getColor());

		JPanel dcha = new JPanel();
		dcha.setLayout(new BoxLayout(dcha, BoxLayout.Y_AXIS));
		dcha.setOpaque(false);
		dcha.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 24));

		JLabel fechaSol = ButtonFactory.newLabel(fecha, Fonts.SUBTITLE);
		fechaSol.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel nombreArtic = ButtonFactory.newLabel(nombreArt, Fonts.TITLE);
		nombreArtic.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel imageArtic = new JLabel(
		    ButtonFactory.loadImageIconScaled(imagenArt,
		        TiendaFrame.getInstance().getPixelsHeight(0.4),
		        TiendaFrame.getInstance().getPixelsWidth(0.3)));
		imageArtic.setHorizontalAlignment(SwingConstants.LEFT);
		imageArtic.setAlignmentX(Component.LEFT_ALIGNMENT);

		dcha.add(fechaSol);
		dcha.add(Box.createVerticalStrut(6));
		dcha.add(nombreArtic);
		dcha.add(Box.createVerticalStrut(6));
		dcha.add(imageArtic);
		dcha.add(Box.createVerticalStrut(6));

		String cats = String.join(", ", categorias);
		if (!cats.isEmpty()) {
		    if (cats.length() > MAX_DESC) cats = cats.substring(0, MAX_DESC) + "...";
		    JLabel categoriasLabel = ButtonFactory.newLabel(cats, Fonts.BOLD);
		    categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
		    categoriasLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		    dcha.add(categoriasLabel);
		    dcha.add(Box.createVerticalStrut(6));
		}

		if (desc.length() > MAX_DESC) desc = desc.substring(0, MAX_DESC) + "...";
		JLabel descArt = ButtonFactory.newLabel(desc, Fonts.BOLD);
		descArt.setForeground(ColorPalette.DARK_GREY.getColor());
		descArt.setAlignmentX(Component.LEFT_ALIGNMENT);
		dcha.add(descArt);


		JPanel izqda = new JPanel();
		izqda.setLayout(new BoxLayout(izqda, BoxLayout.Y_AXIS));
		izqda.setOpaque(false);

		JLabel labelSolicitante = ButtonFactory.newLabel("Solicitante", Fonts.SUBTITLE);
		labelSolicitante.setForeground(ColorPalette.PURPLE.getColor());
		labelSolicitante.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel imageUser = new JLabel(
		    ButtonFactory.loadImageIconScaled(imagenUser,
		        TiendaFrame.getInstance().getPixelsHeight(0.12),
		        TiendaFrame.getInstance().getPixelsHeight(0.12)));
		JLabel labelNombreUser = ButtonFactory.newLabel(nombreUser, Fonts.TITLE3);

		JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		userPanel.setOpaque(false);
		userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		userPanel.add(imageUser);
		userPanel.add(labelNombreUser);

		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setForeground(ColorPalette.PURPLE.getColor());
		sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		sep.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel labelValoracion = ButtonFactory.newLabel("Valoración", Fonts.SUBTITLE);
		labelValoracion.setForeground(ColorPalette.PURPLE.getColor());
		labelValoracion.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel labelEstimacion = ButtonFactory.newLabel("Estimación de precio:", Fonts.TITLE3);
		labelEstimacion.setAlignmentX(Component.LEFT_ALIGNMENT);
		estimacion = ButtonFactory.newTextField(" 0.0 €", Fonts.TITLE3);
		estimacion.setAlignmentX(Component.LEFT_ALIGNMENT);
		estimacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, estimacion.getPreferredSize().height));

		JLabel labelEstado = ButtonFactory.newLabel("Estado:", Fonts.TITLE3);
		labelEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
		estado = ButtonFactory.newComboBox(Fonts.TITLE3, tiposEstado);
		estado.setAlignmentX(Component.LEFT_ALIGNMENT);
		estado.setMaximumSize(new Dimension(Integer.MAX_VALUE, estado.getPreferredSize().height));

		izqda.add(labelSolicitante);
		izqda.add(Box.createVerticalStrut(6));
		izqda.add(userPanel);
		izqda.add(Box.createVerticalStrut(10));
		izqda.add(sep);
		izqda.add(Box.createVerticalStrut(10));
		izqda.add(labelValoracion);
		izqda.add(Box.createVerticalStrut(6));
		izqda.add(labelEstimacion);
		izqda.add(Box.createVerticalStrut(6));
		izqda.add(estimacion);
		izqda.add(Box.createVerticalStrut(6));
		izqda.add(labelEstado);
		izqda.add(Box.createVerticalStrut(6));
		izqda.add(estado);


		btnValorar = ButtonFactory.newRoundedButton("Valorar",
		    TiendaFrame.getInstance().getPixelsHeight(0.1),
		    TiendaFrame.getInstance().getPixelsWidth(0.05), 0.5f);
		btnValorar.setActionCommand("Valorar");
		btnValorar.setFont(Fonts.BOLD.getFont());
		ButtonFactory.paintButton(btnValorar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnValorar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);


		JPanel izqdaWrapper = new JPanel(new BorderLayout());
		izqdaWrapper.setOpaque(false);
		izqdaWrapper.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 16));
		izqdaWrapper.add(izqda, BorderLayout.NORTH);
		izqdaWrapper.add(btnValorar, BorderLayout.SOUTH);


		JSeparator separadorVertical = new JSeparator(SwingConstants.VERTICAL);
		separadorVertical.setForeground(ColorPalette.PURPLE.getColor());
		separadorVertical.setMaximumSize(new Dimension(2, Integer.MAX_VALUE));
		separadorVertical.setAlignmentY(Component.TOP_ALIGNMENT);


		// CONTENEDOR PRINCIPAL
		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.X_AXIS));
		contenido.setOpaque(false);

		izqdaWrapper.setAlignmentY(Component.TOP_ALIGNMENT);
		dcha.setAlignmentY(Component.TOP_ALIGNMENT);

		contenido.add(izqdaWrapper);
		contenido.add(separadorVertical);
		contenido.add(dcha);

		// Dividir 50/50 al redimensionar
		contenido.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        int mitad = (contenido.getWidth() - 2) / 2;
		        int alto = contenido.getHeight();
		        izqdaWrapper.setPreferredSize(new Dimension(mitad, alto));
		        dcha.setPreferredSize(new Dimension(mitad, alto));
		        contenido.revalidate();
		    }
		});

		add(PanelFactory.getVentanaConCabecera("Producto a valorar", contenido), BorderLayout.CENTER);
	}

	/**
	 * Asigna un controlador a los componentes de la ventana
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		estimacion.addActionListener(c);
		estado.addActionListener(c);
		btnValorar.addActionListener(c);
	}

	/**
	 * Devuelve la estimación elegida
	 * @return Estimación para el artículo
	 */
	public String getEstimacion() {
		return estimacion.getText();
	}

	/**
	 * Devuelve el estado físico seleccionado
	 * @return Estado físico seleccionado
	 */
	public String getEstadoFisico() {
		return (String) estado.getSelectedItem();
	}
}
