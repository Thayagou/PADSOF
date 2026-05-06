package vistas.herramientas;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
 
import javax.swing.BorderFactory;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controladores.ButtonAdapter;
import vistas.common.InvisibleCheckBox;

public class ButtonFactory {
	private static String IMAGE_PATH = "resources/gui/";

	public ButtonFactory() {

	}

	private static String getHTMLCenteredLabel(String label) {
		return "<html><center>" + label + "</center></html>";
	}

	private static String getHTMLLabel(String label) {
		return "<html><div style='text-align:left;'>" + label + "</div></html>";
	}

	public static ImageIcon loadImageIconScaled(String imageName, int h, int w) {
		ImageIcon original = loadImageIcon(imageName);
		if (h <= 0 || w <= 0)
			return original;
		Image img = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	public static ImageIcon loadImageInBounds(String imageName, int maxH, int maxW) {
		ImageIcon original = loadImageIcon(imageName);
		if (maxH <= 0 || maxW <= 0)
			return original;

		int imgW = original.getIconWidth();
		int imgH = original.getIconHeight();

		// Solo escalar si supera los límites
		if (imgH > maxH || imgW > maxW || (imgH < maxH && imgW < maxW)) {
			double scaleH = (double) maxH / imgH;
			double scaleW = (double) maxW / imgW;
			double scale = Math.min(scaleH, scaleW); // mantener proporción

			int newW = (int) (imgW * scale);
			int newH = (int) (imgH * scale);

			Image scaled = original.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
			original = new ImageIcon(scaled);
		}

		return original;
	}

	public static ImageIcon loadImageIcon(String imageName) {
		return new ImageIcon(IMAGE_PATH + imageName);
	}

	private static void setDefault(JButton button) {
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setFont(Fonts.TEXT.getFont());
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		paintButton(button, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);

		addMouseMecanics(button, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
	}

	public static void paintButton(JButton button, ColorPalette background, ColorPalette foreground) {
		button.setBackground(background.getColor());
		button.setForeground(foreground.getColor());
	}

	private static void iconoDinamico(JButton button, ImageIcon original, double percIcono) {
		button.addComponentListener(new ButtonAdapter(button, percIcono, original));
	}

	public static JButton newButton(String label) {
		JButton button = new JButton(getHTMLCenteredLabel(label));
		button.setActionCommand(label);

		return button;
	}

	public static JButton newRoundedButton(String label, int height, int width, double roundness) {
		JButton button = new RoundedButton(getHTMLLabel(label), roundness);

		button.setActionCommand(label);
		button.setPreferredSize(new Dimension(width, height));

		setDefault(button);

		return button;
	}

	public static JButton newRoundedIconButton(String label, int height, int width, double roundness,
			String imageName) {
		ImageIcon icon = loadImageIconScaled(imageName, height, width);
		JButton button = new RoundedButton(getHTMLLabel(label), roundness);

		button.setIcon(icon);
		button.setPreferredSize(new Dimension(width, height));
		button.setActionCommand(label);

		setDefault(button);
		iconoDinamico(button, icon, 0.6);

		return button;
	}

	public static JButton newButton(String label, int height, int width) {
		JButton button = newButton(label);
		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);
		setDefault(button);

		return button;
	}

	public static JButton newButtonLeft(String label, int height, int width) {
		JButton button = new JButton(getHTMLLabel(label));
		button.setActionCommand(label);
		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);
		setDefault(button);

		return button;
	}

	public static JButton newIconButton(String imageName, int height, int width) {
		ImageIcon icon = loadImageIconScaled(imageName, height, width);
		JButton button = new JButton(icon);

		// iconoDinamico(button, icon, 0.6);

		setDefault(button);

		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);

		return button;
	}

	public static JButton newIconButton(String label, int height, int width, String imageName) {

		ImageIcon icon = loadImageIconScaled(imageName, height, width);
		JButton button = newButton(label);
		button.setIcon(icon);
		// JButton button = this.newIconButton(imageName, height, width);
		button.setText(getHTMLCenteredLabel(label));
		setDefault(button);
		iconoDinamico(button, icon, 0.6);

		return button;
	}

	public static void addMouseMecanics(JButton btn, ColorPalette defaultC, ColorPalette pressedC) {
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBackground(pressedC.getColor());
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBackground(defaultC.getColor());
			}
		});

	}

	public static JLabel newLabel(String text, Fonts font) {
		JLabel label = new JLabel(getHTMLCenteredLabel(text));
		label.setFont(font.getFont());
		label.setHorizontalTextPosition(SwingConstants.LEFT);

		return label;
	}

	public static JLabel newLeftAlignedLabel(String text, Fonts font) {
		JLabel label = new JLabel(getHTMLLabel(text));
		label.setFont(font.getFont());
		label.setHorizontalTextPosition(SwingConstants.LEFT);

		return label;
	}

	public static JTextField newTextField(String text, Fonts font) {
		JTextField field = new JTextField(text);
		field.setFont(font.getFont());
		field.setForeground(ColorPalette.GREY.getColor());

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(text)) {
					field.setText("");
					field.setForeground(ColorPalette.BLACK.getColor());
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().isEmpty()) {
					field.setText(text);
					field.setForeground(ColorPalette.GREY.getColor());
				}
			}
		});

		return field;
	}
	
	public static JTextArea newTextArea(String text, Fonts font) {
		JTextArea field = new JTextArea(text);
		field.setFont(font.getFont());
		field.setForeground(ColorPalette.GREY.getColor());

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(text)) {
					field.setText("");
					field.setForeground(ColorPalette.BLACK.getColor());
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().isEmpty()) {
					field.setText(text);
					field.setForeground(ColorPalette.GREY.getColor());
				}
			}
		});

		return field;
	}

	public static JSpinner spinnerFecha(Fonts font) {
		SpinnerDateModel modelo = new SpinnerDateModel();
		JSpinner spinner = new JSpinner(modelo);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy HH:mm");
		spinner.setEditor(editor);
		spinner.setFont(font.getFont());
		return spinner;
	}
	
	public static JSpinner spinnerLocalDate(Fonts font) {
		JSpinner spinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
	    spinner.setEditor(editor);
	    spinner.setFont(font.getFont());
	    return spinner;
	}

	public static <T> JComboBox<T> newComboBox(Fonts font, @SuppressWarnings("unchecked") T... elementos) {
		JComboBox<T> comboBox = new JComboBox<T>(elementos);
		comboBox.setFont(font.getFont());

		return comboBox;
	}

	/**
	 * Crea una check box que no se controla a sí misma, sino que depende de
	 * toggleSelection
	 * 
	 * @param label
	 * @param selected
	 * @param unselected
	 * @return
	 */
	public static InvisibleCheckBox newInvisibleCheckBox(String labelSelected, String labelUnselected,
			ColorPalette selected, ColorPalette unselected) {
		return new InvisibleCheckBox(labelSelected, labelUnselected, selected, unselected);
	}

	public static JSpinner spinnerEntero(Fonts font, int height, int width) {
		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setPreferredSize(new Dimension(width, height));
		spinner.setFont(font.getFont());
		return spinner;
	}

	/**
     * Añade un tooltip visualmente personalizado al botón {@code btn} que
     * aparece tras {@code nSecs} segundos de hover y desaparece al salir.
     *
     * El estilo (fondo, borde, fuente) se define aquí una sola vez y es
     * independiente del UIManager global, por lo que puede diferir del
     * tooltip por defecto de la aplicación.
     *
     * Uso:
     *   ButtonFactory.addHoverInfo(miBoton, "Volver a la pantalla anterior", 1.0);
     *
     * @param btn   Botón al que se añade el tooltip.
     * @param txt   Texto que mostrará el tooltip.
     * @param nSecs Segundos de espera antes de que aparezca (puede ser decimal, ej. 0.5).
     */
    public static void addHoverInfo(JButton btn, String txt, double nSecs) {

        // ── Construir el panel del tooltip ────────────────────────────────
        // Usamos un JPanel personalizado en lugar de setToolTipText() para
        // tener control total sobre el aspecto visual.
        JToolTip tooltip = new JToolTip() {
            private static final long serialVersionUID = 1L;

            {
                // Fondo y borde iguales al UIManager de TiendaFrame,
                // pero definidos aquí para no depender del orden de inicialización.
                setBackground(ColorPalette.CARD_LIGHT.getColor());
                setForeground(ColorPalette.DARK_GREY.getColor());
                setFont(Fonts.TEXT.getFont());
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor(), 1),
                        BorderFactory.createEmptyBorder(4, 8, 4, 8)
                ));
                setOpaque(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                // Fondo redondeado para que combine con RoundedButton
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tooltip.setTipText(txt);

        // ── Ventana ligera que contiene el tooltip ────────────────────────
        // Popup en vez de JWindow para que no robe el foco y no aparezca
        // en la barra de tareas del sistema operativo.
        Popup[] popupHolder = { null }; // array para poder modificarlo desde el lambda

        // ── Timer por botón ───────────────────────────────────────────────
        int delayMs = (int)(nSecs * 1000);
        javax.swing.Timer timer = new javax.swing.Timer(delayMs, null);
        timer.setRepeats(false); // dispara una sola vez por hover

        timer.addActionListener(ev -> {
            // Calcular posición: justo debajo del botón
            Point loc = btn.getLocationOnScreen();
            int x = loc.x;
            int y = loc.y + btn.getHeight() + 2;

            // Ajustar tamaño del tooltip al texto
            tooltip.setSize(tooltip.getPreferredSize());

            PopupFactory factory = PopupFactory.getSharedInstance();
            popupHolder[0] = factory.getPopup(btn, tooltip, x, y);
            popupHolder[0].show();
        });

        // ── MouseListener ─────────────────────────────────────────────────
        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                timer.restart(); // reinicia el contador cada vez que se entra
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                timer.stop();
                if (popupHolder[0] != null) {
                    popupHolder[0].hide();
                    popupHolder[0] = null;
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Ocultar también al hacer clic para no dejar el tooltip flotando
                timer.stop();
                if (popupHolder[0] != null) {
                    popupHolder[0].hide();
                    popupHolder[0] = null;
                }
            }
        });

        // Desactivar el tooltip nativo de Swing para este botón
        // (evita que aparezcan dos tooltips a la vez si alguien llama también a setToolTipText)
        btn.setToolTipText(null);
    }
}
