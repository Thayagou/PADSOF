package venta.pedidos;

import java.time.*;
import java.util.*;

import sistema.AsignadorId;
import usuario.*;
import venta.productos.StockExterno;

public class Pedido {
	private final long id;
	private final LocalDateTime fechaPago;
	private LocalDateTime fechaPreparacion;
	private Empleado empPreparacion;
	private LocalDateTime fechaListo;
	private Empleado empListo;
	private LocalDateTime fechaRecogida;
	private Empleado empRecogida;
	private EstadoPedido estado;
	private final ClienteRegistrado cliente;
	private final Set<StockExterno> itemsPedido = new HashSet<StockExterno>();
	private final double precioTotal;
	
	/**
	 * Creador de la clase Pedido
	 * @param cliente Cliente que realiza el pedido
	 * @param stocks Array de los stocks externos de los productos del pedido
	 */
	public Pedido(ClienteRegistrado cliente, StockExterno...stocks) throws IllegalArgumentException {
		if(cliente == null || stocks == null) throw new IllegalArgumentException();
		for(StockExterno st : stocks) {
			if(st == null) throw new IllegalArgumentException("StockExterno null entre los items del pedido");
		}
		
		this.id = AsignadorId.getInstancia().siguienteId();
		this.fechaPago = LocalDateTime.now();
		this.estado = EstadoPedido.PAGADO;
		this.cliente = cliente;
		
		double precioTotal = 0;
		for(StockExterno s : stocks) {
			itemsPedido.add(new StockExterno(s.getProducto(), s.getUdsEnStock(), s.getPrecioUnitarioFinal()));
			precioTotal += s.getPrecioTotal();
		}
		this.precioTotal = precioTotal;
	}
	
	/**
	 * Getter del precio total del pedido
	 * @return Precio total del pedido
	 */
	public double getPrecioTotal() {
		return precioTotal;
	}

	/**
	 * Getter de la id del pedido
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter de la fecha de pago del pedido
	 * @return Fecha y hora de pago del pedido
	 */
	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	/**
	 * Getter de la fecha de inicio de preparacion del pedido
	 * @return Fecha y hora de inicio de preparacion del pedido
	 */
	public LocalDateTime getFechaPreparacion() {
		return fechaPreparacion;
	}

	/**
	 * Getter del empleado que marcó el pedido como En_Preparacion
	 * @return Empleado que marcó el pedido como En_Preparacion
	 */
	public Empleado getEmpPreparacion() {
		return empPreparacion;
	}

	/**
	 * Getter de la fecha en que quedó listo el pedido
	 * @return Fecha y hora en que quedó listo el pedido
	 */
	public LocalDateTime getFechaListo() {
		return fechaListo;
	}

	/**
	 * Getter del empleado que marcó el pedido como Listo
	 * @return Empleado que marcó el pedido como Listo
	 */
	public Empleado getEmpListo() {
		return empListo;
	}

	/**
	 * Getter de la fecha de recogida del pedido
	 * @return Fecha y hora de recogida del pedido
	 */
	public LocalDateTime getFechaRecogida() {
		return fechaRecogida;
	}

	/**
	 * Getter del empleado que marcó el pedido como recogido
	 * @return Empleado que marcó el pedido como recogido
	 */
	public Empleado getEmpRecogida() {
		return empRecogida;
	}

	/**
	 * Getter del estado del pedido
	 * @return Estado del pedido
	 */
	public EstadoPedido getEstado() {
		return estado;
	}
	
	/**
	 * Avanza el estado del pedido
	 * @param emp Empleado que actualiza el estado del pedido
	 */
	public void nextEstadoPedido(Empleado emp) throws IllegalArgumentException {
		if(emp == null) throw new IllegalArgumentException();
		
		if(estado.ordinal() == EstadoPedido.values().length - 1) return;
		
		estado = estado.getSiguienteEstado();
		switch(estado) {
		case EN_PREPARACION:
			empPreparacion = emp;
			fechaPreparacion = LocalDateTime.now();
			return;
		case LISTO:
			empListo = emp;
			fechaListo = LocalDateTime.now();
			return;
		case RECOGIDO:
			empRecogida = emp;
			fechaRecogida = LocalDateTime.now();
			return;
		default:
			break;
		}
	}

	/**
	 * Getter del cliente que hizo el pedido
	 * @return Cliente registrado que hizo el pedido
	 */
	public ClienteRegistrado getCliente() {
		return cliente;
	}

	/**
	 * Getter de los items del pedido
	 * @return Array de los stocks externos de los productos del pedido con sus unidades
	 */
	public StockExterno[] getItemsPedido() {
		return itemsPedido.toArray(new StockExterno[0]);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("Pedido id=%d\n  Items:\n", id));
		for(StockExterno st : itemsPedido) {
			s.append("  " + st.toString() + "\n");
		}
		s.append("\n  Precio total: "+precioTotal);
		s.append("\n  Fecha de pedido: "+fechaPago);
		return s.toString();
	}
}
