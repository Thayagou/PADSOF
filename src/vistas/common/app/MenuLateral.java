package vistas.common.app;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import vistas.herramientas.*;

/**
 * Panel de menu lateral con secciones desplegables. Cada seccion tiene un
 * cabecera clicable que muestra u oculta sus botones internos de forma
 * independiente.
 */
public class MenuLateral extends JPanel {

	private static final long serialVersionUID = 1L;

	/* ── Macros de layout (porcentajes de pantalla) ── */
	private static final double HEADER_H_MACRO = 45.0 / 1080.0; /* Altura de cada cabecera de seccion */
	private static final double INDENT_MACRO = 20.0 / 1920.0; /* Sangria de los botones internos */
	private static final double BTN_H_MACRO = 40.0 / 1080.0; /* Altura de cada boton interno */
	private static final double SECTION_GAP_MACRO = 4.0 / 1080.0; /* Espacio vertical entre secciones */
	private static final double SCROLL_WIDTH_MACRO = 0.15; /* Ancho del menu lateral */

	/* ── Indicadores visuales de estado ── */
	private static final String ICONO_CERRADO = "  \u25BA  "; /* Flecha derecha */
	private static final String ICONO_ABIERTO = "  \u25BC  "; /* Flecha abajo */

	/* ── Panel interno que contiene todas las secciones ── */
	private final JPanel contenedor;

	/**
	 * Construye el menu lateral a partir de un mapa ordenado de secciones.
	 *
	 * @param btnMap Mapa cuya clave es el titulo de la seccion y el valor es la
	 *               lista de botones que contiene.
	 */
	public MenuLateral(Map<String, List<JButton>> btnMap) {
		TiendaFrame t = TiendaFrame.getInstance();
		int menuW = t.getPixelsWidth(SCROLL_WIDTH_MACRO);

		setLayout(new BorderLayout());
		setOpaque(false);
		setPreferredSize(new Dimension(menuW, 0));

		contenedor = new JPanel();
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		contenedor.setOpaque(false);

		/* ── Añadir secciones del mapa en orden ── */
		for (Map.Entry<String, List<JButton>> entry : btnMap.entrySet()) {
			añadirSeccion(entry.getKey(), entry.getValue());
		}

		/* ── Empujar secciones hacia arriba ── */
		contenedor.add(Box.createVerticalGlue());

		/* ── Scroll que envuelve el contenedor ── */
		JScrollPane scroll = PanelFactory.getScroll(contenedor);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);

		add(scroll, BorderLayout.CENTER);
	}

	/*
	 * ════════════════════════════════════════════════════════════════ API publica
	 * ════════════════════════════════════════════════════════════════
	 */

	/**
	 * Añade una nueva seccion al menu de forma dinamica tras la construccion.
	 *
	 * @param titulo Titulo de la seccion.
	 * @param btns   Lista de botones que contiene la seccion.
	 */
	public void addSection(String titulo, List<JButton> btns) {
		/* ── Retirar el glue antes de insertar para mantener el orden ── */
		int last = contenedor.getComponentCount() - 1;
		if (last >= 0)
			contenedor.remove(last);

		añadirSeccion(titulo, btns);

		contenedor.add(Box.createVerticalGlue());
		contenedor.revalidate();
		contenedor.repaint();
	}

	/*
	 * ════════════════════════════════════════════════════════════════ Construccion
	 * interna de secciones
	 * ════════════════════════════════════════════════════════════════
	 */

	/**
	 * Construye y registra una seccion con su cabecera y su subpanel de botones.
	 *
	 * @param titulo Titulo de la seccion.
	 * @param btns   Botones internos de la seccion.
	 */
	private void añadirSeccion(String titulo, List<JButton> btns) {
		TiendaFrame t = TiendaFrame.getInstance();
		int headerH = t.getPixelsHeight(HEADER_H_MACRO);
		int indent = t.getPixelsWidth(INDENT_MACRO);
		int btnH = t.getPixelsHeight(BTN_H_MACRO);
		int sectionGap = t.getPixelsHeight(SECTION_GAP_MACRO);
		int menuW = t.getPixelsWidth(SCROLL_WIDTH_MACRO);

		/* ── Subpanel de botones (inicialmente oculto) ── */
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		subPanel.setOpaque(false);
		subPanel.setVisible(false); /* Cerrado por defecto */

		for (JButton btn : btns) {
			btn.setAlignmentX(LEFT_ALIGNMENT);
			btn.setMaximumSize(new Dimension(menuW - indent, btnH));
			btn.setMinimumSize(new Dimension(0, btnH));
			btn.setPreferredSize(new Dimension(menuW - indent, btnH));
			subPanel.add(btn);
		}

		/* ── Cabecera de la seccion ── */
		JButton cabecera = new JButton(ICONO_CERRADO + titulo);
		cabecera.setActionCommand(titulo);
		cabecera.setFont(Fonts.BOLD.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorderPainted(false);
		cabecera.setFocusPainted(false);
		cabecera.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cabecera.setHorizontalAlignment(SwingConstants.LEFT);
		cabecera.setMaximumSize(new Dimension(menuW, headerH));
		cabecera.setMinimumSize(new Dimension(0, headerH));
		cabecera.setPreferredSize(new Dimension(menuW, headerH));
		cabecera.setAlignmentX(LEFT_ALIGNMENT);

		ButtonFactory.addMouseMecanics(cabecera, ColorPalette.DARK_BLUE, ColorPalette.BG_BLUE);

		/* ── Toggle al hacer clic en la cabecera ── */
		cabecera.addActionListener(new ToggleListener(cabecera, subPanel, titulo));

		/* ── Panel de seccion que agrupa cabecera + subpanel ── */
		JPanel seccion = new JPanel();
		seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));
		seccion.setOpaque(false);
		seccion.setAlignmentX(LEFT_ALIGNMENT);
		seccion.add(cabecera);

		/* ── Subpanel con sangria ── */
		JPanel indentado = new JPanel(new BorderLayout());
		indentado.setOpaque(false);
		indentado.setBorder(BorderFactory.createEmptyBorder(0, indent, 0, 0));
		indentado.add(subPanel, BorderLayout.CENTER);
		seccion.add(indentado);

		contenedor.add(seccion);
		contenedor.add(Box.createVerticalStrut(sectionGap));
	}

	/*
	 * ════════════════════════════════════════════════════════════════ Listener
	 * interno de toggle con animacion
	 * ════════════════════════════════════════════════════════════════
	 */

	/**
	 * Listener que muestra u oculta el subpanel de una seccion al hacer clic en su
	 * cabecera, con una animacion suave por pasos.
	 */
	private class ToggleListener implements ActionListener {

		/* ── Duracion y pasos de la animacion ── */
		private static final int TIMER_DELAY_MS = 15; /* Milisegundos entre pasos de animacion */
		private static final int PASOS_ANIMACION = 8; /* Numero de pasos totales */

		private final JButton cabecera;
		private final JPanel subPanel;
		private final String titulo;

		private javax.swing.Timer animTimer;
		private int pasoActual;
		private boolean expandiendo;
		private int alturaObjetivo;
		private int alturaInicial;

		public ToggleListener(JButton cabecera, JPanel subPanel, String titulo) {
			this.cabecera = cabecera;
			this.subPanel = subPanel;
			this.titulo = titulo;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (animTimer != null && animTimer.isRunning())
				animTimer.stop();

			expandiendo = !subPanel.isVisible();
			alturaInicial = expandiendo ? 0 : subPanel.getPreferredSize().height;
			alturaObjetivo = expandiendo ? calcularAlturaTotal() : 0;
			pasoActual = 0;

			/* ── Preparar visibilidad antes de animar ── */
			if (expandiendo) {
				subPanel.setVisible(true);
				cabecera.setText(ICONO_ABIERTO + titulo);
			}

			animTimer = new javax.swing.Timer(TIMER_DELAY_MS, ev -> animarPaso());
			animTimer.start();
		}

		/**
		 * Ejecuta un paso de la animacion ajustando la altura maxima del subpanel.
		 */
		private void animarPaso() {
			pasoActual++;
			double progreso = (double) pasoActual / PASOS_ANIMACION;

			/* ── Suavizado cuadratico (ease-in-out) ── */
			double factor = progreso < 0.5 ? 2 * progreso * progreso : 1 - Math.pow(-2 * progreso + 2, 2) / 2;

			int alturaActual = (int) (alturaInicial + (alturaObjetivo - alturaInicial) * factor);

			subPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, alturaActual));
			subPanel.setPreferredSize(new Dimension(subPanel.getWidth(), alturaActual));
			contenedor.revalidate();
			contenedor.repaint();

			if (pasoActual >= PASOS_ANIMACION) {
				animTimer.stop();

				/* ── Estado final tras animacion ── */
				if (!expandiendo) {
					subPanel.setVisible(false);
					subPanel.setMaximumSize(null);
					subPanel.setPreferredSize(null);
					cabecera.setText(ICONO_CERRADO + titulo);
				} else {
					subPanel.setMaximumSize(null);
					subPanel.setPreferredSize(null);
				}
				contenedor.revalidate();
				contenedor.repaint();
			}
		}

		/**
		 * Calcula la altura total que ocuparan los botones del subpanel.
		 *
		 * @return Altura en pixeles del subpanel expandido.
		 */
		private int calcularAlturaTotal() {
			int total = 0;
			for (Component c : subPanel.getComponents()) {
				total += c.getPreferredSize().height;
			}
			return total;
		}
	}
}