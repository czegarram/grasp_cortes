package grasp_demo;

import static org.junit.Assert.*;

import org.junit.Test;

import base.grasp.Residuo;

public class ResiduoUnitTest {

	@Test
	public void esLaprimeraIteracion() {
		Residuo r = new Residuo();
		r.setIteracion(1);
		assertTrue(r.esLaPrimeraIteracion());
	}
	
	@Test
	public void esLaCuartaIteracion() {
		Residuo r = new Residuo();
		r.setIteracion(4);
		assertFalse(r.esLaPrimeraIteracion());
	}
		
	@Test
	public void porcentajeEsMenorA100(){
		Residuo r = new Residuo();
		r.setPorcentaje(10.0);
		assertTrue(r.porcentajeEsMenorA100());
	}
	
	@Test
	public void porcentajeEs100(){
		Residuo r = new Residuo();
		r.setPorcentaje(100.0);
		assertFalse(r.porcentajeEsMenorA100());
	}
	
	@Test
	public void porcentajeEsMayorA100(){
		Residuo r = new Residuo();
		r.setPorcentaje(140.0);
		assertFalse(r.porcentajeEsMenorA100());
	}
}
