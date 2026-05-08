package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelPedidoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import vistas.cliente.venta.pantallas.VentanaCompras;
import vistas.common.app.TiendaFrame;

public class ControlVerCompras implements ControladorPantalla { 
    private Tienda tienda;
    private ClienteRegistrado cliente;
 
    private final VentanaCompras vista;
 
    public ControlVerCompras(Tienda tienda, ClienteRegistrado cliente) {
        this.tienda  = tienda;
        this.cliente = cliente;
 
        this.vista = new VentanaCompras();
        cargarPedidos();
        vista.setControlador(this);
 
        TiendaFrame.getInstance().navegarA(this);
    }
 
 
    private void cargarPedidos() {
        vista.limpiarPedidos();
        for (Pedido pedido : cliente.getPedidos()) {
            new ControlPanelPedidoCliente(tienda, cliente, pedido, vista);
        }
    }
 
 
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
 
    // ── ActionListener ────────────────────────────────────────────────────
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // Acciones globales futuras (ej. filtros, ordenación)
    }

	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran tus pedidos realizados. Para ver los detalles y valorar alguno de los productos, haz clic sobre el pedido.";
	}
}