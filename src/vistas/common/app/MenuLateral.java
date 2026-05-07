package vistas.common.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class MenuLateral extends JPanel {

	private static final long serialVersionUID = 1L;

	/** Color del hueco que queda por la indentacion al abrir una seccion */
	private static final Color COLOR_SECCION_BG = ColorPalette.DARK_BLUE.getColor();
	
	/** Color del fondo de la cabecera de cada seccion */
	private static final Color COLOR_TITULO_BG = ColorPalette.LIGHT_PURPLE.getColor();
	
	/** COlor del texto de la cabecera de cada seccion */
	private static final Color COLOR_TEXTO_TITULO = ColorPalette.WHITE.getColor();
	
	/** Color del fondo de la cabecera de cada seccion */
	private static final Color COLOR_BTN_BG = ColorPalette.PURPLE.getColor();
	
	/** COlor del texto de la cabecera de cada seccion */
	private static final Color COLOR_BTN_TITULO = ColorPalette.WHITE.getColor();
	
	private static final Color SEPARATOR_COLOR = ColorPalette.WHITE.getColor();
	
	/**Símbolo que se muestra cuando la sección está abierta */
	private static final String OPEN_SYMBOL = "-";
	
	/**Símbolo que se muestra cuando la sección está cerrada */
	private static final String CLOSED_SYMBOL = "+";
	
	/** Fuente que se usa para el texto de la cabecera */
	private static final Font FUENTE_TITULO = Fonts.BOLD.getFont();
	
	/** Fuente que se usa para el texto de los botones  */
	private static final Font FUENTE_BOTONES = Fonts.TEXT.getFont();
	
	private static final int PADDING_TITULO = TiendaFrame.getInstance().getPixelsWidth(0.01);
	private static final int INDENT_BOTONES = TiendaFrame.getInstance().getPixelsWidth(0.01);
	
	private static final int BTN_HEIGHT = TiendaFrame.getInstance().getPixelsWidth(0.03);

	private final JPanel panelSecciones;
	private int minimumWidth = -1;  /* -1 indica que no se ha establecido */

	public MenuLateral(Map<String, List<JButton>> btnMap, double screenPerc) {
		this(btnMap);
		setMinimumWidth(TiendaFrame.getInstance().getPixelsWidth(screenPerc));
		refresh();
	}
	
	private MenuLateral(Map<String, List<JButton>> btnMap) {
		setLayout(new BorderLayout());
		panelSecciones = new JPanel();
		panelSecciones.setLayout(new BoxLayout(panelSecciones, BoxLayout.Y_AXIS));
		panelSecciones.setBackground(COLOR_SECCION_BG);
		add(panelSecciones, BorderLayout.CENTER);

		for (Map.Entry<String, List<JButton>> entrada : btnMap.entrySet()) {
			addSection(entrada.getKey(), entrada.getValue());
		}
	}

	/* Método para establecer un ancho mínimo en píxeles */
	public void setMinimumWidth(int width) {
		this.minimumWidth = width;
		setMinimumSize(new Dimension(width, 0));
		/* Forzar que el panelSecciones y los botones respeten el ancho */
		panelSecciones.setMinimumSize(new Dimension(width, 0));
		panelSecciones.setPreferredSize(new Dimension(width, panelSecciones.getPreferredSize().height));
		/* Ajustar el ancho máximo para evitar que se estire más de lo deseado (opcional) */
		setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
		/* Propagar el ancho mínimo a los botones de todas las secciones */
		propagarAnchoMinimo(width);
		revalidate();
		repaint();
	}

	private void propagarAnchoMinimo(int width) {
		for (int i = 0; i < panelSecciones.getComponentCount(); i++) {
			if (panelSecciones.getComponent(i) instanceof Seccion) {
				Seccion sec = (Seccion) panelSecciones.getComponent(i);
				sec.setMinimumWidth(width);
			}
		}
	}

	public void addSection(String titulo, List<JButton> botones) {
		Seccion seccion = new Seccion(titulo, botones);
		panelSecciones.add(seccion);
		if (minimumWidth > 0) {
			seccion.setMinimumWidth(minimumWidth);
		}
		panelSecciones.revalidate();
		panelSecciones.repaint();
	}

	public void collapseAll() {
		for (int i = 0; i < panelSecciones.getComponentCount(); i++) {
			if (panelSecciones.getComponent(i) instanceof Seccion) {
				((Seccion) panelSecciones.getComponent(i)).collapse();
			}
		}
	}
	
	public void openAll() {
		for (int i = 0; i < panelSecciones.getComponentCount(); i++) {
			if (panelSecciones.getComponent(i) instanceof Seccion) {
				((Seccion) panelSecciones.getComponent(i)).open();
			}
		}
	}
	
	public void refresh() {
		collapseAll();
		openAll();
	}

	private class Seccion extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JPanel panelBotones;
	    private final JLabel tituloLabel;

	    Seccion(String titulo, List<JButton> botones) {
	        setLayout(new BorderLayout());
	        setBackground(COLOR_SECCION_BG);

	        tituloLabel = new JLabel("  " + titulo + "    "+OPEN_SYMBOL);
	        tituloLabel.setFont(FUENTE_TITULO);
	        tituloLabel.setForeground(COLOR_TEXTO_TITULO);
	        tituloLabel.setOpaque(true);
	        tituloLabel.setBackground(COLOR_TITULO_BG);
	        tituloLabel.setBorder(BorderFactory.createEmptyBorder(PADDING_TITULO, INDENT_BOTONES, PADDING_TITULO, 0));
	        tituloLabel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                toggle();
	            }
	        });

	        panelBotones = new JPanel();
	        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
	        panelBotones.setBackground(COLOR_SECCION_BG);
	        
	        /* Aqui se define el formato de los botones */
	        for (JButton btn : botones) {
	            btn.setAlignmentX(RIGHT_ALIGNMENT);
	            btn.setBackground(COLOR_BTN_BG);
	            btn.setForeground(COLOR_BTN_TITULO);
	            btn.setFont(FUENTE_BOTONES);
	            btn.setHorizontalTextPosition(SwingConstants.LEFT);
	            panelBotones.add(btn);
	        }
	        panelBotones.setVisible(true);

	        add(tituloLabel, BorderLayout.NORTH);
	        add(panelBotones, BorderLayout.CENTER);
	        
	        updateMaxHeight();   /* fuerza a que no se expanda verticalmente */
	    }

	    /* Actualiza la altura máxima a la altura preferida actual */
	    private void updateMaxHeight() {
	        int prefHeight = getPreferredSize().height;
	        setMaximumSize(new Dimension(Integer.MAX_VALUE, prefHeight));
	    }

	    /* Método interno para forzar el ancho mínimo en esta sección */
	    private void setMinimumWidth(int width) {
	        setMinimumSize(new Dimension(width, 0));
	        panelBotones.setMinimumSize(new Dimension(width, 0));
	        
	        /* Forzar que los botones tengan el ancho deseado (mismo ancho para todos) */
	        for (int i = 0; i < panelBotones.getComponentCount(); i++) {
	            JButton btn = (JButton) panelBotones.getComponent(i);
	            btn.setMinimumSize(new Dimension(width - INDENT_BOTONES, BTN_HEIGHT));
	            btn.setMaximumSize(new Dimension(width - INDENT_BOTONES, BTN_HEIGHT));
	            btn.setPreferredSize(new Dimension(width - INDENT_BOTONES, BTN_HEIGHT));
	        }
	        
	        /* (Opcional) ya no forzamos el ancho de los botones porque puede causar problemas */
	        updateMaxHeight();
	        revalidate();
	        
	    }

	    private void toggle() {
	        panelBotones.setVisible(!panelBotones.isVisible());
	        if(!panelBotones.isVisible()) {
	        	setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, SEPARATOR_COLOR));
	        } else {
	        	setBorder(BorderFactory.createEmptyBorder());
	        }
	        
	        String currentText = tituloLabel.getText();
	        if (currentText.contains(OPEN_SYMBOL)) {
	            tituloLabel.setText(currentText.replace(OPEN_SYMBOL, CLOSED_SYMBOL));
	        } else {
	            tituloLabel.setText(currentText.replace(CLOSED_SYMBOL, OPEN_SYMBOL));
	        }
	        updateMaxHeight();   /* actualizar la restricción tras el cambio de visibilidad */
	        revalidate();
	        repaint();
	    }

	    private void collapse() {
	        if (panelBotones.isVisible()) {
	            toggle();
	        }
	    }
	    
	    private void open() {
	    	if (!panelBotones.isVisible()) {
	            toggle();
	        }
	    }
	}
}