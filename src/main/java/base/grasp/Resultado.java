package base.grasp;

import java.util.List;
import java.util.Map;

public class Resultado {
	private long tiempoEjecucion;
	private int numeroContenedores;
	private Map<Integer,List<Integer>> cortes;
	private Map<Integer,Residuo> residuos;
	private int residuoTotal;
	private Residuo residuoReal;
	private double porcentajeUsoReal;
	private int tamMax;
	
	public Resultado(){
		
	}

	public Resultado(long tiempoEjecucion, int numeroContenedores,
			Map<Integer, List<Integer>> cortes, Map<Integer, Residuo> residuos,
			int residuoTotal, Residuo residuoReal, double porcentajeUsoReal) {
		super();
		this.tiempoEjecucion = tiempoEjecucion;
		this.numeroContenedores = numeroContenedores;
		this.cortes = cortes;
		this.residuos = residuos;
		this.residuoTotal = residuoTotal;
		this.residuoReal = residuoReal;
		this.porcentajeUsoReal = porcentajeUsoReal;
	}
	
	public long getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(long tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public int getNumeroContenedores() {
		return numeroContenedores;
	}

	public void setNumeroContenedores(int numeroContenedores) {
		this.numeroContenedores = numeroContenedores;
	}

	public Map<Integer, List<Integer>> getCortes() {
		return cortes;
	}

	public void setCortes(Map<Integer, List<Integer>> cortes) {
		this.cortes = cortes;
	}

	public Map<Integer, Residuo> getResiduos() {
		return residuos;
	}

	public void setResiduos(Map<Integer, Residuo> residuos) {
		this.residuos = residuos;
	}

	public int getResiduoTotal() {
		return residuoTotal;
	}

	public void setResiduoTotal(int residuoTotal) {
		this.residuoTotal = residuoTotal;
	}

	public Residuo getResiduoReal() {
		return residuoReal;
	}

	public void setResiduoReal(Residuo residuoReal) {
		this.residuoReal = residuoReal;
	}

	public double getPorcentajeUsoReal() {
		return porcentajeUsoReal;
	}

	public void setPorcentajeUsoReal(double porcentajeUsoReal) {
		this.porcentajeUsoReal = porcentajeUsoReal;
	}

	public int getTamMax() {
		return tamMax;
	}

	public void setTamMax(int tamMax) {
		this.tamMax = tamMax;
	}

	@Override
	public String toString() {
		return "Resultado [tiempoEjecucion=" + tiempoEjecucion
				+ ", numeroContenedores=" + numeroContenedores + ", cortes="
				+ cortes + ", residuos=" + residuos + ", residuoTotal="
				+ residuoTotal + ", residuoReal=" + residuoReal
				+ ", porcentajeUsoReal=" + porcentajeUsoReal + "]";
	}
	
	
}
