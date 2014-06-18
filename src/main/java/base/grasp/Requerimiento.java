package base.grasp;

/**
 * @(#)Requerimiento.java
 *
 *
 * @author Erazo Gonzales Daniel
 * @version 1.00 2011/11/14
 */


public class Requerimiento {
	private int tamanio;
	private int cant;
	
    public Requerimiento(int t, int c){
    	tamanio = t;
    	cant = c;
    }
    public Requerimiento(){ 
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
}
