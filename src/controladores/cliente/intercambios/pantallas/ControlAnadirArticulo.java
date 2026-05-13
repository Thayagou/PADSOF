package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controladores.ControlCargaImagen;
import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import vistas.cliente.intercambios.pantallas.VentanaAnadirArticulo;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de añadir artículo de segunda mano.
 */
public class ControlAnadirArticulo implements ActionListener, ControladorPantalla {
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private VentanaAnadirArticulo vista;
	private String[] nombresCategorias; /* Todas las categorías disponibles (para buscar por nombre) */
	private Categoria[] todasCategorias; /* Array de objetos Categoria de la tienda */
	private String fotoSeleccionada;

	/**
	 * Constructor del controlador.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que añade el artículo.
	 */
	public ControlAnadirArticulo(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		fotoSeleccionada = null;

		/* Obtener todas las categorías de la tienda (objetos completos) */
		this.todasCategorias = tienda.getAlmacen().getCategorias();
		this.nombresCategorias = new String[todasCategorias.length];
		for (int i = 0; i < todasCategorias.length; i++) {
			nombresCategorias[i] = todasCategorias[i].getNombre();
		}

		this.vista = new VentanaAnadirArticulo(nombresCategorias);
		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		switch (comando) {
		case VentanaAnadirArticulo.ACTION_CONFIRMAR:
			confirmar();
			break;
		case VentanaAnadirArticulo.ACTION_CANCELAR:
			TiendaFrame.getInstance().volverAtras();
			break;
		case VentanaAnadirArticulo.ACTION_SELECCIONAR_FOTO:
			fotoSeleccionada = ControlCargaImagen.abrir("Articulo");
			vista.actualizarPreview(fotoSeleccionada);
			break;
		}
	}

	/**
	 * Confirma la creación del nuevo artículo.
	 * Valida los campos, procesa las categorías y crea el artículo en la tienda.
	 */
	private void confirmar() {
		/* Validar campos obligatorios */
		String nombre = vista.getNombre();
		String intercambio = vista.getIntercambioBuscado();
		String descripcion = vista.getDescripcion();

		if (nombre.isEmpty() || descripcion.isEmpty()) {
			new VentanaMensaje("Nombre y descripción son obligatorios.");
			return;
		}

		/* Obtener las categorías seleccionadas como objetos Categoria */
		String[] nombresSeleccionados = vista.getCategoriasSeleccionadas();
		Categoria[] categoriasSeleccionadas = new Categoria[nombresSeleccionados.length];
		for (int i = 0; i < nombresSeleccionados.length; i++) {
			for (Categoria cat : todasCategorias) {
				if (cat.getNombre().equals(nombresSeleccionados[i])) {
					categoriasSeleccionadas[i] = cat;
					break;
				}
			}
		}

		/* Crear el artículo en la tienda (aún sin imagen) */
		try {
			tienda.anadirArticulo(nombre, descripcion, cliente.getCartera(), intercambio, fotoSeleccionada,
					categoriasSeleccionadas);

			new VentanaMensaje("Artículo añadido correctamente.");
			TiendaFrame.getInstance().volverAtras(); /* Vuelve a la cartera */
		} catch (Exception ex) {
			new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
		}
	}

	@Override
	public boolean puedeVolver() {
		return false;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana se introducen los datos para añadir un nuevo artículo a la cartera.";
	}
}