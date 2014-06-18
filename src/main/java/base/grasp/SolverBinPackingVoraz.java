package base.grasp;

/**
 * @(#)BinPackingVoraz.java
 *
 *
 * @author 
 * @version 1.00 2011/12/2
 */

import java.util.Scanner;

public class SolverBinPackingVoraz extends SolverBinPacking{
   int tamCont, numCont;
   Requerimiento req[];
   Lista Capacidades;
   Scanner n = new Scanner(System.in);

   public SolverBinPackingVoraz(LecturaDatos datos) {
      super(datos);
      req = new Requerimiento[datos.getCantDistintos()];
      Capacidades = new Lista();
      req = datos.getRequerimientos();
      tamCont = datos.getContenedor();
      resolver();
   }

   public void resolver_base(){
      numCont = BinPackingBFD(tamCont, req, Capacidades);
   }

   public int BinPackingBFD(int L, Requerimiento req[], Lista Capacidades){
      //Ordena el vector de acuerdo al Tamanio del requerimiento
      req = ordenaVector(req);
      int p, j, sobra_i;
      p = 1;
      Capacidades.adiciona(L);
      for(j=0; j<req.length; j++){
         while(req[j].getCant()>0){
            sobra_i = Min(req[j].getTam(), Capacidades);
            if(sobra_i >= 0){
               Capacidades.cambiarCapacidadxPosicion(sobra_i, req[j].getTam());
            }
            else{
               p++;
               Capacidades.adiciona(L);
               Capacidades.cambiarUltimaCapacidad(req[j].getTam());//aca hay errroooooor
            }
            req[j].setCant(req[j].getCant()-1);
         }
      }
      return p;
   }

   public int Min(int capacidad, Lista capac){
      return capac.busqueda(capacidad);	
   }

   public static Requerimiento[] ordenaVector(Requerimiento a[]){
      int i, j;
      for(i=0; i<a.length-1; i++){
         for(j=i+1; j<a.length; j++){
            if(a[i].getTam()<a[j].getTam()){
               Requerimiento aux = a[i];
               a[i] = a[j];
               a[j] = aux;
            }
         }
      }
      return a;
   }


   public int[][] getCortes(){
      return Capacidades.matrizNodos();
   }

   public int getNumeroContenedores(){
      return numCont;
   }

}
