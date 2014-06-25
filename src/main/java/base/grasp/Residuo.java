package base.grasp;

public class Residuo {
	private double iteracion;
	private double valor;
	private double porcentaje;
	private int nuevoAtributo;
	
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
	
	public int getNuevoAtributo() {
		return nuevoAtributo;
	}
	public void setNuevoAtributo(int nuevoAtributo) {
		this.nuevoAtributo = nuevoAtributo;
	}
	public boolean esLaPrimeraIteracion(){
		Double d = new Double(iteracion);
		int val = d.intValue();
		if(val==1)
			return true;
		else
			return false;
	}
	
	public boolean porcentajeEsMenorA100(){
		if(porcentaje<100.0d)
			return true;
		else
			return false;		
	}
	
	@Override
	public String toString() {
		return "Residuo [valor=" + valor + ", porcentaje=" + porcentaje + "]";
	}	
	
}
