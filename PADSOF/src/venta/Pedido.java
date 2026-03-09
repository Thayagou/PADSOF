package venta;

import java.time.*;
import java.util.*;

import sistema.AsignadorId;
import usuario.*;

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
	
	public Pedido(ClienteRegistrado cliente, StockExterno...stocks) {
		this.id = AsignadorId.getInstancia().siguienteId();
		this.fechaPago = LocalDateTime.now();
		this.estado = EstadoPedido.PAGADO;
		this.cliente = cliente;
		for(StockExterno s : stocks) {
			itemsPedido.add(new StockExterno(s.getProducto(), s.getUdsEnStock(), s.getPrecioFinal()));
		}
	}
	
	public boolean marcarEnPreparacion(Empleado emp) {
		if(estado != EstadoPedido.PAGADO) return false;
		fechaPreparacion = LocalDateTime.now();
		empPreparacion = emp;
		estado = EstadoPedido.EN_PREPARACION;
		return true;
	}
	
	public boolean marcarListo(Empleado emp) {
		if(estado != EstadoPedido.EN_PREPARACION) return false;
		fechaListo = LocalDateTime.now();
		empListo = emp;
		estado = EstadoPedido.LISTO;
		return true;
	}
	
	public boolean marcarRecogido(Empleado emp) {
		if(estado != EstadoPedido.LISTO) return false;
		fechaRecogida = LocalDateTime.now();
		empRecogida = emp;
		estado = EstadoPedido.RECOGIDO;
		return true;
	}

	public long getId() {
		return id;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public LocalDateTime getFechaPreparacion() {
		return fechaPreparacion;
	}

	public Empleado getEmpPreparacion() {
		return empPreparacion;
	}

	public LocalDateTime getFechaListo() {
		return fechaListo;
	}

	public Empleado getEmpListo() {
		return empListo;
	}

	public LocalDateTime getFechaRecogida() {
		return fechaRecogida;
	}

	public Empleado getEmpRecogida() {
		return empRecogida;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public ClienteRegistrado getCliente() {
		return cliente;
	}

	public StockExterno[] getItemsPedido() {
		return itemsPedido.toArray(new StockExterno[0]);
	}
}
