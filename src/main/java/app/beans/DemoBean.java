package app.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import base.scope.SpringViewScoped;
import base.grasp.Requerimiento;
import base.grasp.SolverBinPackingGRASP;


@Component
@SpringViewScoped
public class DemoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Requerimiento> requerimientos;
	private Requerimiento requerimiento;
	private int step=0;
	private BigDecimal factor;
	private int tamanioContenedor;
	private String resultado;
	private int iteraciones;
	
	public DemoBean(){
		initFields();
	}

	private void initFields() {
		requerimientos = new ArrayList<Requerimiento>();
		requerimiento = new Requerimiento();
		factor = new BigDecimal(0);
		step=0;
		tamanioContenedor=0;
		resultado="";
		iteraciones=0;
	}
	
	@PostConstruct
	public void init(){

	}
	
	public String reinit() {
		requerimiento = new Requerimiento();         
        return null;
    }
	
	public void resolver(){
		SolverBinPackingGRASP grasp = new SolverBinPackingGRASP(requerimientos,factor.doubleValue(),iteraciones,tamanioContenedor);
		resultado = grasp.imprimirResultados();
		step=1;
	}
	
	public void irAlPrimerStep(){
		resultado="";
		step=0;		
	}
	
	public void limpiarFormulario(){
		initFields();
	}

	public List<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}

	public int getTamanioContenedor() {
		return tamanioContenedor;
	}

	public void setTamanioContenedor(int tamanioContenedor) {
		this.tamanioContenedor = tamanioContenedor;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public int getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
	}	
	

}
