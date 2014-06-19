package base.grasp;

import java.io.Serializable;

/**
 * @(#)Requerimiento.java
 *
 *
 * @author Erazo Gonzales Daniel
 * @version 1.00 2011/11/14
 */


public class Requerimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tamanio;
	private int cant;
	
    public Requerimiento(int t, int c){
    	tamanio = t;
    	cant = c;
    }
    public Requerimiento(){ 
    	tamanio=0;
    	cant=0;
    }
    
    public int getTam(){
    	return tamanio;
    }
    public int getCant(){
    	return cant;
    }
    public void setTam(int t){
    	tamanio = t;
    }
    public void setCant(int c){
    	cant = c;
    }   
    
	public int getTamanio() {
		return tamanio;
	}
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tamanio;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Requerimiento other = (Requerimiento) obj;
		if (tamanio != other.tamanio)
			return false;
		return true;
	}
    
    
}
