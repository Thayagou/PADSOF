package tests.iteracion1;

import venta.productos.*;

import java.time.*;
import java.util.*;

import javax.swing.ImageIcon;

import venta.descuentos.*;
import venta.pedidos.*;

public class TestVentaI1 {

	public static void main(String[] args) {
		Categoria cAventura = new Categoria("Aventura");
		Categoria cSuperHeroes = new Categoria("SuperHeroes");
		Categoria cInfantil = new Categoria("Infantil");
		Categoria cEducativo = new Categoria("Educativo");
		ImageIcon imagen = null;
		Producto p1 = new Comic("SpiderMan", "Comic de el hombre araña", 9.99, imagen, LocalDate.now(), "Stan Lee", 67, "Anaya", cAventura, cSuperHeroes);
		Producto p2 = new Juego("Aprende a contar", "Juego para aprender a contar", 15.99, imagen, 1, "3-6 años", TipoJuego.TABLERO, cInfantil, cEducativo);
		Producto p3 = new Figura("Hulk", "Figura de accion de hulk", 150.99, imagen, "16x17cm", "Duplo", "plastico", cInfantil, cSuperHeroes);
		Stock[] stocksPack = {new Stock(p1, 1), new Stock(p3, 1)};
		Producto p4 = new Pack(stocksPack, "MarvelPack", "Comic de spiderman y figura de hulk.", 155.99, imagen, cSuperHeroes);
		
		p1.anadirResena(new Resena(3, "Muy aburrido", null));
		p1.anadirResena(new Resena(4, "Bastante bueno", null));
		p2.anadirResena(new Resena(5, "Estuve una semana intentando aprender a contar y no me sirvió. Le pongo 1 estrella.", null));
		p2.anadirResena(new Resena(4, "El cuatro es mi numero favorito", null));
		
		cInfantil.anadirDescuento(new DescuentoDinero(3, LocalDateTime.now(), LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 19.99));
		boolean resp = p2.anadirDescuento(new DescuentoPorcentaje(1, LocalDateTime.now(), LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 50));
		System.out.println(resp);
		p4.anadirDescuento(new DescuentoRegalo(1, LocalDateTime.now(), LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, p1));
		
		System.out.println(p1.toString());
		System.out.println(p2.toString());
		System.out.println(p3.toString());
		System.out.println(p4.toString());
		
		System.out.println("\n-----------Cambiamos las categorias y comprobamos si cambian los descuentos-------------\n");
		
		p2.quitarCategorias(cInfantil);
		System.out.println(p2);
		p1.anadirCategorias(cInfantil);
		System.out.println(p1);
		
		System.out.println("\n----------Calculamos los descuentos y creamos un pedido------------------\n");
		
		StockExterno item1 = new StockExterno(p1, 3);
		StockExterno item2 = new StockExterno(p2, 5);
		StockExterno item3 = new StockExterno(p3, 1);
		StockExterno item4 = new StockExterno(p4, 1);
		
		item1.setPrecioFinal(item1.getProducto().getDescuento().getPrecioDescontado(item1.getUdsEnStock(), 0, item1.getProducto().getPrecio()));
		item3.setPrecioFinal(item3.getProducto().getDescuento().getPrecioDescontado(item3.getUdsEnStock(), 0, item3.getProducto().getPrecio()));
		item4.setPrecioFinal(item4.getProducto().getDescuento().getPrecioDescontado(item4.getUdsEnStock(), 0, item4.getProducto().getPrecio()));
		
		ArrayList<Producto> regalos = new ArrayList<Producto>();
		regalos.add(item1.getProducto().getDescuento().getRegalo(item1.getUdsEnStock(), 0));
		regalos.add(item3.getProducto().getDescuento().getRegalo(item3.getUdsEnStock(), 0));
		regalos.add(item4.getProducto().getDescuento().getRegalo(item4.getUdsEnStock(), 0));
		
		Pedido pedido = new Pedido(null, item1, item2, item3, item4);
		
		System.out.println(pedido);
		System.out.println(regalos);


	}

}
