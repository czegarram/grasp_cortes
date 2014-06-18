package base.grasp;

/**
 * @(#)Lista.java
 *
 *
 * @author 
 * @version 1.00 2011/11/13
 */


public class Lista {
   Nodo cab, fin;

   public Lista() {
      cab = null;
   }

   public void cambiarUltimaCapacidad(int newcapac){
      fin.valor = fin.valor - newcapac;
      fin.insertarDato(newcapac);
   }

   public void cambiarCapacidadxPosicion(int pos, int newcapac)
   {
      Nodo p = cab;
      int cont = 0;
      while(p != null && cont<pos){
         p = p.sgte;
         cont++;
      }
      p.valor = p.valor - newcapac;
      p.insertarDato(newcapac);//Esta es la lista que registrara cada paquete x CAJA
   }

   public boolean listaVacia(){
      return cab==null;
   }
   public int tamanio(){
      Nodo p = cab;
      int t=0;
      while(p != null){
         t++;
         p = p.sgte;
      }
      return t;
   }

   /*
      public void adiciona(int dato){
      Nodo p = new Nodo(dato);
      if(listaVacia()) cab = p;
      else{
      p.sgte = cab;
      cab = p;
      }
      }
      */

   public void adiciona(int dato){
      Nodo p = new Nodo(dato);
      if(listaVacia()){ cab = p; fin = p;}
      else{
         fin.sgte = p;
         fin = p;
      }
   }

   public int busqueda(int cap){
      Nodo p = cab;
      int min=-1, minimo=-1, pos=0;
      while(p != null){
         if(minimo == -1){
            if(p.valor-cap>=0){
               minimo = p.valor-cap;
               min = pos;
            }
         }
         else{
            if(p.valor-cap>=0){
               if((p.valor-cap)<minimo){
                  minimo = p.valor-cap;
                  min = pos;
               }
            }
         }
         p = p.sgte; pos++;
      }	
      return min;
   }

   public void recorridoNodos(){
      Nodo p = cab;
      int i=0;
      while(p != null){
         System.out.print("\nCaja "+(i+1)+" : ");
         p.mostrarListadepaquetes();
         p = p.sgte; i++;
      }
   }
   //Version que devuelve una vector de vectores
   public int[][] matrizNodos(){
      int matriz[][] = new int[tamanio()][];
      Nodo p = cab;
      int i=0;
      while(p != null){
         matriz[i] = p.mostrarVectorDePaquetes();
         p = p.sgte;
         i++;
      }
      return matriz;
   }

   public void mostrarLista() {
      Nodo p = cab;
      System.out.print("{");
      while(p != null){	
         System.out.print(p.valor+" ");
         p = p.sgte;
      }
      System.out.print("}");
   }
   //Version que devuelve un vector
   public int[] vectorLista(){
      int vector[] = new int[tamanio()];
      Nodo p = cab;
      int i = 0;
      while(p != null){	
         vector[i] = p.valor;
         p = p.sgte;
         i++;
      }
      return vector;
   }
}
