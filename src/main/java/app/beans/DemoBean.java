package app.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.springframework.stereotype.Component;

import base.scope.SpringViewScoped;
import base.grasp.Requerimiento;
import base.grasp.Residuo;
import base.grasp.Resultado;
import base.grasp.SolverBinPackingGRASP;


@Component
@SpringViewScoped
public class DemoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Requerimiento> requerimientos;
	private List<Residuo> residuos;
	private Requerimiento requerimiento;
	private int step=0;
	private BigDecimal factor;
	private int tamanioContenedor;
	private String resultado;
	private int iteraciones;
	private Resultado data;
	
	private HorizontalBarChartModel horizontalContenedoresBarModel;
	
	public DemoBean(){
		initFields();
	}

	private void initFields() {
		requerimientos = new ArrayList<Requerimiento>();
		residuos = new ArrayList<Residuo>();
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
	
	private void createContenedoresGraphic() {
		horizontalContenedoresBarModel = new HorizontalBarChartModel();
		horizontalContenedoresBarModel.setStacked(true);
		horizontalContenedoresBarModel.setBarMargin(40);
		
		
		Axis xAxis = horizontalContenedoresBarModel.getAxis(AxisType.X);
		xAxis.setLabel("Espacio del Contenedor");
		xAxis.setMin(0);
		
		
		xAxis.setMax(data.getTamMax());
		int max=-5;
		for(Integer iteracion: data.getCortes().keySet() ){
			if(iteracion>max)
				max=iteracion;
		}
		
		
		List<ChartSeries> espacios = new ArrayList<ChartSeries>();
		Map<Integer,Boolean> chartCreados = new HashMap<Integer,Boolean>();
		for(Integer iteracion: data.getCortes().keySet() ){
			List<Integer> registros = data.getCortes().get(iteracion);
			int i=0;

			for(Integer basija:registros){					
				if(chartCreados.containsKey(i)){
					ChartSeries cs= espacios.get(i);
					cs.set("Contenedor"+iteracion, basija);
					
				}else{
					ChartSeries cs = new ChartSeries();
					cs.setLabel("Espacio"+(espacios.size()+1));
					
					for(int k=1;k<=max;k++){
						cs.set("Contenedor"+k, 0);
					}
					
					cs.set("Contenedor"+iteracion, basija);
					espacios.add(cs);
					chartCreados.put(i, true);
				}				
				i++;
			}
		}

		for(ChartSeries chart:espacios){
			horizontalContenedoresBarModel.addSeries(chart);
		}
	}
	
	public String reinit() {
		requerimiento = new Requerimiento();         
        return null;
    }
	
	public void resolver(){
		if(requerimientos.size()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Aviso", "No ha ingresado requerimientos");
			FacesContext.getCurrentInstance().addMessage(null,fm);
			return;
		}
		
		if(tamanioContenedor==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Aviso", "El tamaño del contenedor no puede ser 0.");
			FacesContext.getCurrentInstance().addMessage(null,fm);
			return;
		}
		
		if(iteraciones==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Aviso", "El numero de iteraciones no puede ser 0.");
			FacesContext.getCurrentInstance().addMessage(null,fm);
			return;
		}
		
		SolverBinPackingGRASP grasp = new SolverBinPackingGRASP(requerimientos,factor.doubleValue(),iteraciones,tamanioContenedor);
		resultado = grasp.imprimirResultados();
		grasp.generarResultado();
		data = grasp.getResultado();
		createContenedoresGraphic();
		residuos = new ArrayList<Residuo>();
		for(Integer i :data.getResiduos().keySet()){
			data.getResiduos().get(i).setIteracion(i);
			residuos.add(data.getResiduos().get(i));
		}
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

	public HorizontalBarChartModel getHorizontalContenedoresBarModel() {
		return horizontalContenedoresBarModel;
	}

	public void setHorizontalContenedoresBarModel(
			HorizontalBarChartModel horizontalContenedoresBarModel) {
		this.horizontalContenedoresBarModel = horizontalContenedoresBarModel;
	}

	public Resultado getData() {
		return data;
	}

	public void setData(Resultado data) {
		this.data = data;
	}

	public List<Residuo> getResiduos() {
		return residuos;
	}

	public void setResiduos(List<Residuo> residuos) {
		this.residuos = residuos;
	}		
	
}
