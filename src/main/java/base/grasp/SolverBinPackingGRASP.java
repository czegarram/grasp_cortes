package base.grasp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @(#)GRASP.java
 *
 *
 * @author Erazo Gonzales Daniel
 * @version 1.00 2011/11/14
 */
import java.util.Random;

public class SolverBinPackingGRASP extends SolverBinPacking {
	private final int ARRAY_SIZE = 6000;
	private double alfa;
	private int nIter;

	private int n;
	private int E[];
	private int mmin;
	private int L;
	private int minB[][];
	private int minR[];

	private int auxReq[];
	private int B[][];
	private int Res[];
	private int rcl[];
	private int m;
	private int nReq;
	private int num_rcl;
	
	private Resultado resultado;

	public SolverBinPackingGRASP(LecturaDatos datos, double a, int ni) {
		super(datos);
		minB = new int[ARRAY_SIZE][ARRAY_SIZE];
		minR = new int[ARRAY_SIZE];
		alfa = a;
		nIter = ni;
		E = datos.getObjetos();
		n = datos.getCantTotal();
		L = datos.getContenedor();

		System.out.println("Objetos: " + Arrays.toString(E));
		System.out.println("Cantidad Total: " + n);
		System.out.println("Contenedor: " + L);

		resolver();

	}

	/**
	 * Mtodo GRASP
	 * 
	 * @param reqs
	 *            : requerimientos
	 * @param a
	 *            : coeficiente de relacion
	 * @param ni
	 *            : numeros de iteraciones
	 * @param tamanioContenedor
	 *            : tama�o del contenedor
	 */
	public SolverBinPackingGRASP(List<Requerimiento> reqs, double a, int ni,
			int tamanioContenedor) {
		minB = new int[ARRAY_SIZE][ARRAY_SIZE];
		minR = new int[ARRAY_SIZE];
		alfa = a;
		nIter = ni;
		L = tamanioContenedor;
		n = 0;
		for (Requerimiento reg : reqs) {
			n += reg.getCant();
		}
		E = new int[n];
		int count = 0;
		for (int i = 0; i < reqs.size(); i++) {
			Requerimiento temp = reqs.get(i);
			for (int k = 0; k < temp.getCant(); k++) {
				E[count] = temp.getTamanio();
				count++;
			}

		}

		resolver();
	}

	protected void resolver_base() {
		GRASP_Algoritmo(nIter);
	}

	public void GRASP_Algoritmo(int nIter) {
		int conta = 0;

		B = new int[ARRAY_SIZE][ARRAY_SIZE];
		Res = new int[n];// vector de residuos
		Ordenar_QuickSort(E, 0, n - 1);
		while (conta < nIter) {
			GRASP_Construccion();
			Registrar_Mejor_Solucion(conta);

			conta++;
		}
	}

	public void Registrar_Mejor_Solucion(int conta) {
		int i, j = 0;

		if (m < mmin || conta == 0) {
			for (i = 0; i < m; i++) {
				for (j = 0; j < ARRAY_SIZE; j++) {
					if (B[i][j] != 0) {
						minB[i][j] = B[i][j];
						minR[i] = Res[i];
					}
				}
			}
			mmin = m;
		}
	}

	public void GRASP_Construccion() {
		int k, j;
		int b, d, resultado;
		Random rand = new Random();
		boolean flag;

		auxReq = new int[n];
		for (int i = 0; i < n; i++) {
			auxReq[i] = E[i];
		}
		nReq = n;
		m = 0;
		rcl = new int[0];
		Res[0] = L;
		while (nReq > 0) {
			num_rcl = 0;
			b = auxReq[0];
			d = auxReq[nReq - 1];
			llenar_RCL(b, d);
			num_rcl = rand.nextInt(num_rcl);
			resultado = rcl[num_rcl];
			eliminar(auxReq, nReq, num_rcl);
			nReq--;
			flag = false;
			k = 0;
			while (k < m && !flag) {
				if (Res[k] >= resultado) {
					flag = true;// hay espacio en el contenedor
					break;
				}
				k++;
			}
			if (!flag) {// Si no hay espacio en el contenedor
				m++;
				B[k][0] = resultado;
				Res[k] = L - resultado;
			} else {
				j = 0;
				while (B[k][j] > 0) {
					j++;
				}
				B[k][j] = resultado;
				Res[k] = Res[k] - resultado;
			}
		}
	}

	public void llenar_RCL(int b, int d) {
		int i;

		for (i = 0; i < nReq; i++) {
			if ((double) auxReq[i] >= (b - alfa * (b - d))) {
				rcl = redimensionar(rcl, num_rcl);
				rcl[num_rcl] = auxReq[i];
				num_rcl++;
			} else
				i = nReq - 1;
		}
	}

	public int[] redimensionar(int vect[], int dim) {
		int aux[] = new int[dim + 1];
		int i;

		for (i = 0; i < dim; i++) {
			aux[i] = vect[i];
		}
		vect = aux;
		return vect;
	}

	public void eliminar(int vect[], int dim, int pos) {
		int i;
		for (i = pos; i < dim - 1; i++) {
			vect[i] = vect[i + 1];
		}
		dim--;
	}

	public void Ordenar_QuickSort(int V[], int izq, int der) {
		int i, j;
		int x, aux;

		i = izq;
		j = der;
		x = V[(izq + der) / 2];
		do {
			while ((V[i] > x) && (j <= der))
				i++;
			while ((x > V[j]) && (j > izq))
				j--;
			if (i <= j) {
				aux = V[i];
				V[i] = V[j];
				V[j] = aux;
				i++;
				j--;
			}
		} while (i <= j);
		if (izq < j)
			Ordenar_QuickSort(V, izq, j);
		if (i < der)
			Ordenar_QuickSort(V, i, der);
	}

	public int[][] getCortes() {
		int cortes[][] = new int[mmin][];
		int cantObjetos;
		for (int i = 0; i < mmin; i++) {
			cantObjetos = 0;
			for (int j = 0; j < ARRAY_SIZE && minB[i][j] != 0; j++)
				cantObjetos++;
			cortes[i] = new int[cantObjetos];
			for (int j = 0; j < cantObjetos; j++)
				cortes[i][j] = minB[i][j];
		}
		return cortes;
	}

	public int getNumeroContenedores() {
		return mmin;
	}

	public int[] getResiduosIndividuales() {
		int R[] = new int[mmin];
		for (int i = 0; i < mmin; i++)
			R[i] = minR[i];
		return R;
	}

	@Override
	public String imprimirResultados() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);

		System.out.println("Tiempo ejecucion: " + getTiempoEjecucion()
				+ " milisegundos");
		System.out
				.println("Numero de contenedores: " + getNumeroContenedores());
		System.out.println("Cortes por contenedor: ");
		int cortes[][] = getCortes();
		for (int i = 0; i < cortes.length; i++) {
			System.out.print("  " + (i + 1) + ". [");
			for (int j = 0; j < cortes[i].length; j++) {
				if (j != 0)
					System.out.print(", ");
				System.out.print(cortes[i][j]);
			}
			System.out.println("]");
		}
		System.out.println("Residuos (porcentaje de uso):");
		int residuos[] = getResiduosIndividuales();
		for (int i = 0; i < residuos.length; i++) {
			System.out.printf("  %d. %d (%.2f%%)\n", i + 1, residuos[i],
					100 * (1 - (double) residuos[i] / L));
		}
		int residuoTotal = getResiduoTotal();
		System.out.println("Residuo total: " + residuoTotal);
		int residuoReal = getResiduoReal();
		System.out.printf("Residuo real: %d (%.2f%% de un contenedor)\n",
				residuoReal, 100 * (double) residuoReal / L);
		System.out
				.printf("Porcentaje de uso real: %.2f%%\n",
						100 * (1 - (double) residuoReal
								/ (getNumeroContenedores() * L)));
		System.out.flush();
		System.setOut(old);
		// Show what happened
		return baos.toString();
	}
	
	
	public void generarResultado(){
		resultado = new Resultado();
		

		resultado.setTiempoEjecucion(getTiempoEjecucion());
		resultado.setNumeroContenedores(getNumeroContenedores());

		
		Map<Integer,List<Integer>> map = new HashMap <Integer,List<Integer>>();
		int cortes[][] = getCortes();
		for (int i = 0; i < cortes.length; i++) {
			List<Integer> list = new ArrayList<Integer>();

			for (int j = 0; j < cortes[i].length; j++) {

				list.add(cortes[i][j]);
			}

			map.put(i+1, list);
		}
		resultado.setCortes(map);
		
		Map<Integer,Residuo> residuos_map = new HashMap<Integer,Residuo>();

		int residuos[] = getResiduosIndividuales();
		for (int i = 0; i < residuos.length; i++) {
			double nroIteracion = i + 1;
			double valor = residuos[i];
			double porcentaje = 100 * (1 - (double) residuos[i] / L);

			Residuo r = new Residuo();
			r.setPorcentaje(porcentaje);
			r.setValor(valor);
			residuos_map.put((int)nroIteracion, r);
		}
		resultado.setResiduos(residuos_map);
		
		int residuoTotal = getResiduoTotal();

		resultado.setResiduoTotal(residuoTotal);
		
		int residuoReal = getResiduoReal();
		double porcentajeResiduoReal = 100 * (double) residuoReal / L;

		Residuo r = new Residuo();
		r.setValor(residuoReal);
		r.setPorcentaje(porcentajeResiduoReal);
		
		resultado.setResiduoReal(r);		
		
		double porcentajeUsoReal = 100 * (1 - (double) residuoReal
				/ (getNumeroContenedores() * L));

		
		resultado.setPorcentajeUsoReal(porcentajeUsoReal);
		resultado.setTamMax(L);

	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	
}
