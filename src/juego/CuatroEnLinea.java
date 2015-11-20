package juego;

/**
 * Juego Cuatro en L�nea
 * 
 * Reglas:
 * 
 * 		...
 *
 */
public class CuatroEnLinea {

	private Casillero[][] tablero;
	
	private String jugadorRojo;
	
	private String jugadorAmarillo;
	
	private String jugadorActual;
	
	/**
	 * pre : 'filas' y 'columnas' son mayores o iguales a 4.
	 * post: empieza el juego entre el jugador que tiene fichas rojas, identificado como 
	 * 		 'jugadorRojo' y el jugador que tiene fichas amarillas, identificado como
	 * 		 'jugadorAmarillo'. 
	 * 		 Todo el tablero est� vac�o.
	 * 
	 * @param filas : cantidad de filas que tiene el tablero.
	 * @param columnas : cantidad de columnas que tiene el tablero.
	 * @param jugadorRojo : nombre del jugador con fichas rojas.
	 * @param jugadorAmarillo : nombre del jugador con fichas amarillas.
	 */
	public CuatroEnLinea(int filas, int columnas, String jugadorRojo, String jugadorAmarillo) {
		if (filas < 4 || columnas < 4) {
			throw new Error("Se necesitan al menos 4 filas y 4 columnas para jugar.");
		}
		
		tablero = new Casillero[columnas][filas];
		this.jugadorRojo = jugadorRojo;
		this.jugadorAmarillo = jugadorAmarillo;
		
		jugadorActual = this.jugadorRojo;
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				tablero[i][j] = Casillero.VACIO;
			}
		}
	}

	/**
	 * post: devuelve la cantidad m�xima de fichas que se pueden apilar en el tablero.
	 */
	public int contarFilas() {
		return tablero[0].length;
	}

	/**
	 * post: devuelve la cantidad m�xima de fichas que se pueden alinear en el tablero.
	 */
	public int contarColumnas() {
		return tablero.length;
	}

	/**
	 * pre : fila est� en el intervalo [1, contarFilas()],
	 * 		 columnas est� en el intervalo [1, contarColumnas()].
	 * post: indica qu� ocupa el casillero en la posici�n dada por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		return tablero[columna - 1][fila - 1];
	}
	
	/**
	 * pre : el juego no termin�, columna est� en el intervalo [1, contarColumnas()]
	 * 		 y a�n queda un Casillero.VACIO en la columna indicada. 
	 * post: deja caer una ficha en la columna indicada.
	 * 
	 * @param columna
	 */
	public void soltarFicha(int columna) {
		if (columna < 1 || columna > contarColumnas()) {
			throw new Error();
		}
		
		Casillero[] casilleros = tablero[columna - 1];
		
		if (casilleros[casilleros.length -1] != Casillero.VACIO) {
			throw new Error();
		}
		
		int posicion = 0;
		for (int i = 0; i < casilleros.length; i++) {
			if (casilleros[i] != Casillero.VACIO){
				posicion++;
			}
		}
		
		casilleros[posicion] = obtenerCasilleroDelJugadorActual();
		if (! termino()) {
			jugadorActual = obtenerJugadorEnEspera();
		}
	}

	/**
	 * post: indica si el juego termin� porque uno de los jugadores
	 * 		 gan� o no existen casilleros vac�os.
	 */
	public boolean termino() {
		
		if (hayGanador()) {
			return true;
		}
		
		for (int i = 0; i < contarColumnas(); i++) {
			for (int j = 0; j < contarFilas(); j++) {
				if (tablero[i][j] == Casillero.VACIO){
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * post: indica si el juego termin� y tiene un ganador.
	 */
	public boolean hayGanador() {
		for (int i = 0, max = contarColumnas(); i < max; i++) {
			for (int j = 0, maxj = contarFilas(); j < maxj; j++) {
				if (j + 3 < maxj) {
					if (ganoHorizontalmente(i, j)) {
					
						return true;
					}
					
					if (i + 3 < max) {
						if (tablero[i][j] != Casillero.VACIO   &&
							tablero[i][j] == tablero[i + 1][j + 1] &&
							tablero[i][j] == tablero[i + 2][j + 2] &&
							tablero[i][j] == tablero[i + 3][j + 3]) {
						
							return true;
						}
					
						if (tablero[i][j + 3] != Casillero.VACIO       &&
							tablero[i][j + 3] == tablero[i + 1][j + 2] &&
							tablero[i][j + 3] == tablero[i + 2][j + 1] &&
							tablero[i][j + 3] == tablero[i + 3][j]) {
							
							return true;
						}
					}
						
				}
				
				if (i + 3 < max &&
					tablero[i][j] != Casillero.VACIO   &&
					tablero[i][j] == tablero[i + 1][j] &&
					tablero[i][j] == tablero[i + 2][j] &&
					tablero[i][j] == tablero[i + 3][j]) {
					
					return true;
				}
			}
		}
		
		return false;
	}

	private boolean ganoHorizontalmente(int desdeFila, int desdeColumna) {
		return 
			tablero[desdeFila][desdeColumna] != Casillero.VACIO   					 &&
			tablero[desdeFila][desdeColumna] == tablero[desdeFila][desdeColumna + 1] &&
			tablero[desdeFila][desdeColumna] == tablero[desdeFila][desdeColumna + 2] &&
			tablero[desdeFila][desdeColumna] == tablero[desdeFila][desdeColumna + 3];
	}

	/**
	 * pre : el juego termin�.
	 * post: devuelve el nombre del jugador que gan� el juego.
	 */
	public String obtenerGanador() {
		if (! hayGanador()) {
			throw new Error();
		}
		
		return jugadorActual;
	}
	
	private String obtenerJugadorEnEspera() {
		if (jugadorActual == jugadorRojo) {
			return jugadorAmarillo;
		}
		
		return jugadorRojo;
	}

	private Casillero obtenerCasilleroDelJugadorActual() {
		if (jugadorActual == jugadorRojo){
			return Casillero.ROJO;
		}
		
		return Casillero.AMARILLO;
	}
}
