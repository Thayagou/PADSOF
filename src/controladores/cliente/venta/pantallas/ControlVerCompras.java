package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.cliente.venta.ControlPanelPedidoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import vistas.cliente.venta.pantallas.VentanaCompras;

/**
 * Controlador de la ventana de historial de compras del cliente.
 */
public class ControlVerCompras implements ControladorPantalla { 
    
    /** Campo tienda. Referencia al modelo de la tienda. */
    private Tienda tienda;
    
    /** Campo cliente. Cliente registrado que visualiza sus compras. */
    private ClienteRegistrado cliente;
 
    /** Campo vista. Ventana de compras asociada a este controlador. */
    private final VentanaCompras vista;
 
    /**
     * Instancia un nuevo Objeto ControlVerCompras.
     *
     * @param tienda Referencia al modelo de la tienda.
     * @param cliente Cliente registrado que visualiza sus compras.
     */
    public ControlVerCompras(Tienda tienda, ClienteRegistrado cliente) {
        this.tienda  = tienda;
        this.cliente = cliente;
 
        this.vista = new VentanaCompras();
        cargarPedidos();
        vista.setControlador(this);
 
        TiendaFrame.getInstance().navegarA(this);
    }
 
    /**
     * cargarPedidos.
     * Carga los pedidos del cliente en la ventana de compras.
     */
    private void cargarPedidos() {
        vista.limpiarPedidos();
        
        Pedido[] pedidos = cliente.getPedidos();
        for (int i = pedidos.length -1; i >= 0 ; i--) {
            new ControlPanelPedidoCliente(tienda, cliente, pedidos[i], vista);
        }
    }
 
    /**
     * Obtiene Vista.
     *
     * @return valor de Vista, el panel de la ventana de compras.
     */
    @Override
    public JPanel getVista() {
        return vista;
    }
 
    /**
     * Al volver a esta pantalla refrescamos los pedidos por si han cambiado.
     */
    @Override
    public void mostrar() {
        cargarPedidos();
    }
 
    /**
     * actionPerformed.
     *
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Sin acciones para esta ventana */
    }

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, instrucciones para ver detalles de pedidos.
	 */
	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran tus pedidos realizados. Para ver los detalles y valorar alguno de los productos, haz clic sobre el pedido.";
	}
}