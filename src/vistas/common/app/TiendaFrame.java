package vistas.common.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.aplicacion.Main;
import vistas.common.assets.VentanaConfirmacion;
import vistas.herramientas.PanelSizes;

/**
 * Frame Singleton central de la aplicación. Sobre este se muestra toda la información de la tienda
 */
public class TiendaFrame extends JFrame {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** CInstancia del TiendaFrame */
	private static TiendaFrame instance;

	/** Contenido que se muestra actualmente por pantalla */
	private Component vistaActual;
	
	/** Barra lateral actual */
	private BarraLateral barraLateral;
	
	/** Barra de tareas actual */
	private BarraTareas barraTareas;
	
	/** Fondo gradiente que se muestra */
	private FondoGradiente fondo;
	
	/** Altura de la pantalla */
	private int height;
	
	/** Anchura de la pantalla */
	private int width;

	/** CardLayout que se usa para reutilizar vistas */
	private final CardLayout cardLayout = new CardLayout();
	
	/** Panel correspondiente al contenido mostrado por pantalla */
	private final JPanel contentPanel = new JPanel(cardLayout);
	
	/** Pila de controladores de pantallas */
	private final Deque<ControladorPantalla> pilaPantallas = new ArrayDeque<>();
	
	/** Controlador de pantalla actual */
	private ControladorPantalla controladorActual = null;

	/**
	 * Instancia un nuevo Objeto TiendaFrame. Se muestra en mitad de la pantalla y tras abrir se toman las medidas de altura y anchura
	 */
	private TiendaFrame() {
		setTitle("Android's Dungeon");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		this.width = screen.width;
		this.height = screen.height;
		setSize(width, height);

		addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            width = getWidth();
	            height = getHeight();
	            revalidate();
	            repaint();
	        }
	    });
		
		fondo = new FondoGradiente();
		fondo.setLayout(new BorderLayout()); // Necesario para que funcione el CENTER
		fondo.setVisible(true);

		// El contentPanel se añade al centro del fondo
		contentPanel.setOpaque(false);
		fondo.add(contentPanel, BorderLayout.CENTER);

		add(fondo);
	}

	/**
	 * Obtiene la única instancia de TiendaFrame.
	 *
	 * @return la única instancia de TiendaFrame
	 */
	public static TiendaFrame getInstance() {
		if (instance == null) {
			instance = new TiendaFrame();
			instance.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			instance.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					Main.guardarTienda();
					instance.dispose();
					System.exit(0);
				}
			});
		}
		instance.setVisible(true);
		return instance;
	}
	
	/**
	 * Método para obtener una confirmación del usuario.
	 *
	 * @param texto Texto que se muestra cuando se pide la confirmación
	 * @return true si el usuario pulsa "Confirmar", false si pulsa "Cancelar"
	 */
	public static boolean getConfirmacionUsuario(String texto) {
		final boolean[] resultado = new boolean[1];

		VentanaConfirmacion ventana = new VentanaConfirmacion(texto);

		ventana.setControlador(e -> {
			switch (e.getActionCommand()) {
			case VentanaConfirmacion.CONFIRM:
				resultado[0] = true;
				ventana.dispose();
				break;

			case VentanaConfirmacion.CANCEL:
				resultado[0] = false;
				ventana.dispose();
				break;
			}
		});
		
		ventana.mostrar();

		return resultado[0];
	}

	/**
	 * Establece BarraTareas.
	 *
	 * @param barraTareas nuevo valor
	 */
	public void setBarraTareas(BarraTareas barraTareas) {
		if (this.barraTareas != null)
			fondo.remove(this.barraTareas);
		fondo.add(barraTareas, BorderLayout.NORTH);
		this.barraTareas = barraTareas;
		revalidate();
		repaint();
	}

	/**
	 * Establece BarraLateral.
	 *
	 * @param barraLateral nuevo valor
	 */
	public void setBarraLateral(BarraLateral barraLateral) {
		if (this.barraLateral != null)
			fondo.remove(this.barraLateral);
		fondo.add(barraLateral, BorderLayout.WEST);
		this.barraLateral = barraLateral;
		revalidate();
		repaint();
	}

	/**
	 * Elimina la barra lateral.
	 */
	public void removeBarraLateral() {
		if (this.barraLateral != null)
			fondo.remove(this.barraLateral);
		revalidate();
		repaint();
	}

	/**
	 * Establece el fondo.
	 *
	 * @param nuevoFondo Nuevo fondo de la tienda
	 */
	public void setFondo(FondoGradiente nuevoFondo) {
		if (this.fondo != null)
			remove(this.fondo);
		nuevoFondo.add(barraLateral, BorderLayout.WEST);
		nuevoFondo.add(barraTareas, BorderLayout.NORTH);
		nuevoFondo.add(vistaActual, BorderLayout.CENTER);
		add(nuevoFondo);
		this.fondo = nuevoFondo;
		revalidate();
		repaint();
	}

	/**
	 * Getter de la vista actual de la aplicación
	 *
	 * @return la vista actual
	 */
	public Component getVistaActual() {
		return vistaActual;
	}

	/**
	 * A partir de un porcentaje entre 0 y 1 obtiene el número de píxeles correspondientes de anchura actual de la aplicación
	 *
	 * @param percentaje Porcentaje de anchura
	 * @return Número de píxeles correspondientes
	 */
	public int getPixelsWidth(double percentage) {
		return (int) (width * percentage);
	}

	/**
	 * A partir de un porcentaje entre 0 y 1 obtiene el número de píxeles correspondientes de altura actual de la aplicación
	 *
	 * @param percentaje Porcentaje de altura
	 * @return Número de píxeles correspondientes
	 */
	public int getPixelsHeight(double percentage) {
		return (int) (height * percentage);
	}

	/**
	 * Píxeles de tamaño de la barra de tareas a partir de un porcentaje establecido
	 *
	 * @return Número de píxeles correspondientes
	 */
	public int toolBarDistFromTop() {
		return (int) (height * PanelSizes.TOOLBAR_HEIGHT);
	}

	/**
	 * Píxeles de tamaño de la barra lateral a partir de un porcentaje establecido
	 *
	 * @return Número de píxeles correspondientes
	 */
	public int optionBarDistFromLeft() {
		return (int) (width * PanelSizes.OPTION_BAR_WIDTH);
	}
	
	/**
	 * Getter de la información que muestra el controlador actual
	 *
	 * @return valor de Info
	 */
	public String getInfo() {
		return controladorActual.getExplicacion();
	}
	
	/**
	 * Navega a una nueva pantalla gestionada por un ControladorPantalla. La vista
	 * del controlador debe ser un JPanel.
	 *
	 * @param nuevoControlador parámetro nuevoControlador
	 */
	public void navegarA(ControladorPantalla nuevoControlador) {
		if (controladorActual != null) {
			controladorActual.ocultar();
			pilaPantallas.push(controladorActual);
		}
		nuevoControlador.mostrar();
		JPanel vista = nuevoControlador.getVista();
		String clave = claveUnica(nuevoControlador);
		if (vista.getClientProperty("_navClave") == null) {
			vista.putClientProperty("_navClave", clave);
			contentPanel.add(vista, clave);
		}
		controladorActual = nuevoControlador;
		cardLayout.show(contentPanel, clave);
		// Actualizar vistaActual por si se usa el método antiguo
		this.vistaActual = vista;
		revalidate();
		repaint();
	}

	/** 
	 * Vuelve a la pantalla anterior, si existe. 
	 */
	public void volverAtras() {
		if (pilaPantallas.isEmpty()) {
			return;
		}
		ControladorPantalla last = controladorActual;
		if (last != null) {
			last.ocultar();
		}
		
		ControladorPantalla prev = null;
		while (!pilaPantallas.isEmpty()) {
			prev = pilaPantallas.pop();
			if (prev.puedeVolver()) {
				break;
			} else {
				prev.destruir();
				prev = null;
			}
		}

		if (prev == null) {
			if (last != null) {
				controladorActual = last;
				String clave = (String) last.getVista().getClientProperty("_navClave");
				if (clave != null)
					cardLayout.show(contentPanel, clave);
				last.mostrar();
				this.vistaActual = last.getVista();
			}
			return;
		}

		controladorActual = prev;
		String clave = (String) prev.getVista().getClientProperty("_navClave");
		if (clave != null) {
			cardLayout.show(contentPanel, clave);
		} else {
			// Si no tiene clave, no se puede mostrar; error grave
			throw new IllegalStateException("Controlador sin clave en CardLayout");
		}
		prev.mostrar();
		this.vistaActual = prev.getVista();

		revalidate();
		repaint();
	}
	
	/**
	 * Reemplaza la pantalla actual por una nueva instancia,
	 * sin modificar el stack de navegacion.
	 *
	 * @param nuevoControlador Controlador de la nueva patnalla que sustituira a la actual
	 */
	public void recargarPantallaActual(ControladorPantalla nuevoControlador) {
	    if (controladorActual == null) {
	        navegarA(nuevoControlador);
	        return;
	    }
	    
	    controladorActual.ocultar();
	    controladorActual.destruir();
	    
	    JPanel nuevaVista = nuevoControlador.getVista();
	    String clave = claveUnica(nuevoControlador);
	    if (nuevaVista.getClientProperty("_navClave") == null) {
	        nuevaVista.putClientProperty("_navClave", clave);
	        contentPanel.add(nuevaVista, clave);
	    }
	    
	    controladorActual = nuevoControlador;
	    cardLayout.show(contentPanel, clave);
	    controladorActual.mostrar();
	    
	    this.vistaActual = nuevaVista;
	    
	    revalidate();
	    repaint();
	}
	
	/**
	 * Recarga el marco
	 */
	public void refresh() {
		JPanel nuevaVista = controladorActual.getVista();
	    String clave = claveUnica(controladorActual);
	    if (nuevaVista.getClientProperty("_navClave") == null) {
	        nuevaVista.putClientProperty("_navClave", clave);
	        contentPanel.add(nuevaVista, clave);
	    }
	    
	    cardLayout.show(contentPanel, clave);
	    
	    this.vistaActual = nuevaVista;
	    
	    revalidate();
	    repaint();
	}

	/**
	 * Vacía la pila de pantallas anteriores y destruye todos los controladores
	 * almacenados en ella. No afecta a la pantalla actual. Útil para liberar
	 * memoria si se sabe que no se volverá atrás.
	 */
	public void vaciarPila() {
		while (!pilaPantallas.isEmpty()) {
			pilaPantallas.pop().destruir();
		}
	}

	/**
	 * Resetea por completo el historial de navegación y establece una nueva
	 * pantalla raíz (por ejemplo, la pantalla de login o el catálogo tras cerrar
	 * sesión).
	 * 
	 * @param nuevaRaiz Controlador de la pantalla que se mostrará como inicio. El
	 *                  historial anterior se eliminará y no se podrá volver atrás.
	 */
	public void resetearNavegacion(ControladorPantalla nuevaRaiz) {
		// Destruir el controlador actual si existe
		if (controladorActual != null) {
			controladorActual.ocultar();
			controladorActual.destruir();
			controladorActual = null;
		}
		// Vaciar y destruir todos los controladores apilados
		while (!pilaPantallas.isEmpty()) {
			pilaPantallas.pop().destruir();
		}
		// Navegar a la nueva raíz (no se apila porque no hay pantalla actual)
		navegarA(nuevaRaiz);
	}

	/**
	 * Para un determinado controlador de pantalla le genera una clave única
	 *
	 * @param c Controlador de pantalla
	 * @return Clave única a partir de un hash
	 */
	private static String claveUnica(ControladorPantalla c) {
		return c.getClass().getName() + "_" + System.identityHashCode(c);
	}
}