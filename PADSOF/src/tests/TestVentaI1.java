package tests;

import venta.productos.*;
import usuario.*;

import java.time.*;
import java.util.*;

import javax.swing.ImageIcon;

import venta.descuentos.*;
import venta.pedidos.*;

public class TestVentaI1 {
/*
	public static void main(String[] args) {
		System.out.println("===============TEST DE VENTAS===============");
		
		Categoria cAventura = new Categoria("Aventura");
		Categoria cSuperHeroes = new Categoria("SuperHeroes");
		Categoria cInfantil = new Categoria("Infantil");
		Categoria cEducativo = new Categoria("Educativo");
		ImageIcon imagen = null;
		Producto pComic = new Comic("SpiderMan", "Comic de el hombre araña", 9.99, imagen, LocalDate.now(), "Stan Lee", 67, "Anaya", cAventura, cSuperHeroes);
		Producto pJuego = new Juego("Aprende a contar", "Juego para aprender a contar", 15.99, imagen, 1, "3-6 años", TipoJuego.TABLERO, cInfantil, cEducativo);
		Producto pFigura = new Figura("Hulk", "Figura de accion de hulk", 150.99, imagen, "16x17cm", "Duplo", "plastico", cInfantil, cSuperHeroes);
		Stock[] stocksPack = {new Stock(pComic, 1), new Stock(pFigura, 1)};
		Producto pPack = new Pack(stocksPack, "MarvelPack", "Comic de spiderman y figura de hulk.", 155.99, imagen, cSuperHeroes);
		
		pComic.anadirResena(new Resena(3, "Muy aburrido", null));
		pComic.anadirResena(new Resena(4, "Bastante bueno", null));
		pJuego.anadirResena(new Resena(5, "Estuve una semana intentando aprender a contar y no me sirvió. Le pongo 1 estrella.", null));
		pJuego.anadirResena(new Resena(4, "El cuatro es mi numero favorito", null));
		
		cInfantil.anadirDescuento(new DescuentoDinero(3, LocalDateTime.now(), LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 19.99));
		
		boolean resp = pJuego.anadirDescuento(new DescuentoPorcentaje(1, LocalDateTime.now(), LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 50));
		System.out.println("Se impide añadir descuento si ya tiene por categoria: "+(!resp));
		
		boolean repetido = pComic.anadirCategorias(cAventura);
		System.out.println("Se impide añadir categoria repetida: " + (!repetido));
		
		pPack.anadirDescuento(new DescuentoRegalo(1, LocalDateTime.now(), LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, pComic));
		
		System.out.println(pComic.toString());
		System.out.println(pJuego.toString());
		System.out.println(pFigura.toString());
		System.out.println(pPack.toString());
		
		System.out.println("\n-----------Eliminar y restaurar producto-------------\n");

		pFigura.eliminar();
		System.out.println("Figura eliminada: " + pFigura.isEliminado());

		pFigura.restaurar();
		System.out.println("Figura restaurada: " + !pFigura.isEliminado());
		
		System.out.println("\n-----------Cambiamos las categorias y comprobamos si cambian los descuentos-------------\n");
		
		pJuego.quitarCategorias(cInfantil);
		System.out.println(pJuego);
		pComic.anadirCategorias(cInfantil);
		System.out.println(pComic);
		
		System.out.println("\n----------Simulamos un cliente comprando los productos------------------\n");
		
		ClienteRegistrado cliente = new ClienteRegistrado("usrName", "pass");
		
		//Añadimos un cierto número de veces cada producto al carrito
		for(int i=0; i<3; i++) {
			cliente.getCarrito().anadirProducto(pComic);
		}
		for(int i=0; i<5; i++) {
			cliente.getCarrito().anadirProducto(pJuego);
		}
		for(int i=0; i<1; i++) {
			cliente.getCarrito().anadirProducto(pFigura);
		}
		for(int i=0; i<2; i++) {
			cliente.getCarrito().anadirProducto(pPack);
		}
		
		Pedido pedidoCliente = cliente.carritoAPedido();
		System.out.println("Pedido hecho por el cliente: " + pedidoCliente);


	}
*/
}
