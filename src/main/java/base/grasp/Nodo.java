package base.grasp;

/**
 * @(#)Nodo.java
 *
 *
 * @author 
 * @version 1.00 2011/11/13
 */


public class Nodo {
	int valor;
	Nodo sgte;
	Lista paquetes;

    public Nodo(int val) {
    	valor = val;
    	sgte = null;
    	paquetes = new Lista();
    }
    
    public void setValor(int v){
    	valor = v;
    }
    
    public void insertarDato(int dato){
    	paquetes.adiciona(dato);
    }
    
    public void mostrarListadepaquetes()
    {
    	paquetes.mostrarLista();
    }
    //Version que devuelve un vector
    public int[] mostrarVectorDePaquetes()
    {
    	return paquetes.vectorLista();
    }
}
