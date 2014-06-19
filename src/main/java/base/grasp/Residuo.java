package base.grasp;

public class Residuo {
	private double iteracion;
	private double valor;
	private double porcentaje;
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}		
	public double getIteracion() {
		return iteracion;
	}
	public void setIteracion(double iteracion) {
		this.iteracion = iteracion;
	}
	
	@Override
	public String toString() {
		return "Residuo [valor=" + valor + ", porcentaje=" + porcentaje + "]";
	}	
	
}
