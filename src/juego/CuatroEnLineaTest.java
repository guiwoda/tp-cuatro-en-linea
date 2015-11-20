package juego;

import static org.junit.Assert.*;

import org.junit.Test;

public class CuatroEnLineaTest {
	
	private CuatroEnLinea c = new CuatroEnLinea(4, 4, "El Rojo", "El Amarillo");

	@Test(expected=Error.class)
	public void testNecesitaCuatroOMasFilas() {
		new CuatroEnLinea(2, 5, "José", "Pepe");
	}
	
	@Test(expected=Error.class)
	public void testNecesitaCuatroOMasColumnas() {
		new CuatroEnLinea(5, 3, "José", "Pepe");
	}
	
	@Test
	public void testPuedeDarmeLaCantidadMaximaDeFilas() {
		int filas = 4 + (3 * (int) Math.random());
		c = new CuatroEnLinea(filas, 6, "José", "Pepe");
		
		assertEquals(filas, c.contarFilas());
	}
	
	@Test
	public void testPuedeDarmeLaCantidadMaximaDeColumnas() {
		int columnas = 4 + (3 * (int) (Math.random()+1));
		c = new CuatroEnLinea(6, columnas, "José", "Pepe");
		
		assertEquals(columnas, c.contarColumnas());
	}

	@Test(expected=Error.class)
	public void testNoMeDejaPonerFichasEnColumnasFueraDelRango() {
		c.soltarFicha(7);
	}
	
	@Test(expected=Error.class)
	public void testNoMeDejaPonerFichasEnColumnasIncoherentes() {
		c.soltarFicha(-1);
	}

	@Test
	public void testEmpiezaElRojo() {
		c.soltarFicha(1);
		assertEquals(Casillero.ROJO, c.obtenerCasillero(1, 1));
	}
	
	@Test
	public void testSigueElAmarillo() {
		c.soltarFicha(1);
		c.soltarFicha(2);
		assertEquals(Casillero.AMARILLO, c.obtenerCasillero(1, 2));
	}
	
	@Test
	public void testLasFichasSeApilan() {
		c.soltarFicha(1);
		c.soltarFicha(1);
		c.soltarFicha(1);
		
		assertEquals(Casillero.ROJO, c.obtenerCasillero(1, 1));
		assertEquals(Casillero.AMARILLO, c.obtenerCasillero(2, 1));
		assertEquals(Casillero.ROJO, c.obtenerCasillero(3, 1));
	}

	@Test(expected=Error.class)
	public void testPuedoSoltarUnMaximoDeFichasDadoAlInicio(){
		for (int i = 0; i <= c.contarFilas(); i++) {
			c.soltarFicha(1);
		}
	}
	
	@Test
	public void testAlguienGanoEnHorizontalAbajoDeTodo() {
		assertFalse(c.hayGanador());
		
		c.soltarFicha(1);
		c.soltarFicha(1);
		c.soltarFicha(2);
		c.soltarFicha(2);
		c.soltarFicha(3);
		c.soltarFicha(3);
		c.soltarFicha(4);
		
		assertTrue(c.hayGanador());
	}
	
	@Test
	public void testAlguienGanoEnHorizontalFilaDos() {
		assertFalse(c.hayGanador());
		
		c.soltarFicha(1); // R
		c.soltarFicha(1); // A
		c.soltarFicha(1); // R 
		c.soltarFicha(2); // A
		c.soltarFicha(2); // R
		c.soltarFicha(3); // A
		c.soltarFicha(2); // R
		c.soltarFicha(3); // A
		c.soltarFicha(3); // R
		c.soltarFicha(3); // A
		c.soltarFicha(4); // R
		c.soltarFicha(4); // A
		c.soltarFicha(4); // R
		
		// [R R R R]
		// [A R A A]
		// [R A A R]
		assertTrue(c.hayGanador());
	}
	
	@Test
	public void testAlguienGanoEnVerticalColumnaUno() {
		assertFalse(c.hayGanador());
		
		c.soltarFicha(1); // R
		c.soltarFicha(2); // A
		c.soltarFicha(1); // R 
		c.soltarFicha(2); // A
		c.soltarFicha(1); // R
		c.soltarFicha(2); // A
		c.soltarFicha(1); // R
		
		assertTrue(c.hayGanador());
	}
	
	@Test
	public void testAlguienGanoEnVerticalColumnaTres() {
		c.soltarFicha(3); // R
		c.soltarFicha(2); // A
		c.soltarFicha(3); // R 
		c.soltarFicha(1); // A
		c.soltarFicha(3); // R
		c.soltarFicha(2); // A
		c.soltarFicha(3); // R
		
		assertTrue(c.hayGanador());
	}
	
	@Test
	public void testAlguienGanoEnDiagonalAscendente() {
		c.soltarFicha(1); // R
		c.soltarFicha(2); // A
		c.soltarFicha(2); // R
		c.soltarFicha(3); // A
		c.soltarFicha(4); // R
		c.soltarFicha(3); // A
		c.soltarFicha(3); // R
		c.soltarFicha(4); // A
		c.soltarFicha(4); // R
		c.soltarFicha(3); // A
		c.soltarFicha(4); // R
		
		// [    A R]
		// [    R R]
		// [  R A A]
		// [R A A R]
		
		assertTrue(c.hayGanador());
	}
	
	@Test
	public void testAlguienGanoEnDiagonalDescendente() {
		c.soltarFicha(1); // R
		c.soltarFicha(1); // A
		c.soltarFicha(1); // R
		c.soltarFicha(2); // A
		c.soltarFicha(1); // R
		c.soltarFicha(2); // A
		c.soltarFicha(2); // R
		c.soltarFicha(3); // A
		c.soltarFicha(3); // R
		c.soltarFicha(2); // A
		c.soltarFicha(4); // R
		
		// [R A    ]
		// [R R    ]
		// [R A R  ]
		// [R A A R]
		
		assertTrue(c.hayGanador());
	}
	
	@Test(expected=Error.class)
	public void testSiNadieGanoDebeDarErrorAlConsultarGanador() {
		c.obtenerGanador();
	}
}
