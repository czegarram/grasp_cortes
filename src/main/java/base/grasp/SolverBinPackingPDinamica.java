package base.grasp;

import java.util.*;

public class SolverBinPackingPDinamica extends SolverBinPacking{
   private int N, B;
   private int w[], v[];
   private SolverBISSP bissp;
   private LinkedList<int[]> cortes;

   public SolverBinPackingPDinamica(LecturaDatos datos){
      super(datos);
      this.N = datos.getCantDistintos();
      this.w = datos.getTamanio(); this.v = datos.getDemanda();
      this.B = datos.getContenedor();
      bissp = new SolverBISSP(N, B);
      cortes = new LinkedList<int[]>();
      resolver();
   }

   /*
    * Metodo de resolucion
    * */
   protected void resolver_base(){
      ordenarObjetos();
      int vAct[] = Arrays.copyOf(v, N), v2[];
      int k[];
      LinkedList<int[]> posiblesK = new LinkedList<int[]>();
      int p, t;

      while(sumaTotalActual(vAct) > B){
         //System.err.println("Demanda actual: "+Arrays.toString(vAct));
         v2 = Arrays.copyOf(vAct, N);
         while(!vectorVacio(v2)){
            //System.err.println("   Demanda:" + Arrays.toString(v2));
            k = bissp.resolverCon(w, v2);
            //System.err.println("   K:" + Arrays.toString(k));
            posiblesK.add(k);
            p = hallarP(k, v2);
            //System.err.println("   p: " + p);
            v2[p] = k[p] - 1;
         }
         k = seleccionarMayor(posiblesK);
         //System.err.println("Elegido: "+Arrays.toString(k));
         t = hallarT(k, vAct);
         //System.err.println("t: "+t);
         for(int i=0; i<N; i++) vAct[i] -= k[i]*t;
         for(int i=0; i<t; i++) cortes.add(k);
         posiblesK.clear();
      }
      cortes.add(vAct);
   }

   /*
    * Metodos auxiliares para la resolucion
    * */
   private int hallarP(int k[], int v[]){
      double rateMax = 0, rate;
      int p = 0;
      for(int i=0; i<N; i++){
         if(k[i] != 0 && v[i] != 0){
            rate = (double)k[i]/v[i];
            if(rate > rateMax){
               rateMax = rate;
               p = i;
            }
         }
      }
      return p;
   }
   private int hallarT(int k[], int v[]){
      int cMin = Integer.MAX_VALUE, c;
      for(int i=0; i<N; i++){
         if(k[i] != 0 && v[i] != 0){
            c = v[i]/k[i];
            if(c < cMin) cMin = c;
         }
      }
      return cMin;
   }
   private boolean vectorVacio(int v[]){
      for(int i=0;i<v.length; i++){
         if(v[i]!=0) return false;
      }
      return true;
   }
   private int[] seleccionarMayor(LinkedList<int[]> ks){
      ListIterator<int[]> iter = ks.listIterator(0);
      int kMax[] = iter.next(), k[];
      boolean fin;
      while(iter.hasNext()){
         fin = false;
         k = iter.next();
         for(int i=0; i<N && !fin; i++){
            if(k[i] > kMax[i]){
               kMax = k;
               fin = true;
            }else if(k[i] < kMax[i]){
               fin = true;
            }
         }
      }
      return kMax;
   }
   private void ordenarObjetos(){
      for(int i=1; i<N; i++){
         int k = i;
         int wi = w[k];
         int vi = v[k];
         while(k > 0 && wi > w[k-1]){
            w[k] = w[k-1];
            v[k] = v[k-1];
            k--;
         }
         w[k] = wi;
         v[k] = vi;
      }
   }
   private int sumaTotalActual(int vact[]){
      int suma = w[0]*vact[0];
      for(int i=1; i<N; i++) suma += w[i]*vact[i];
      return suma;
   }

   /*
    * Obtencion de datos
    * */
   public int getNumeroContenedores(){
      return cortes.size();
   }
   public int[][] getCortes(){
      int matCortes[][] = new int[cortes.size()][];
      ListIterator<int[]> iter = cortes.listIterator(0);
      int cont=0, pieza;
      int demanda[], totalElementos;
      while(iter.hasNext()){
         demanda = iter.next();
         totalElementos = 0;
         for(int j=0; j<demanda.length; j++) totalElementos += demanda[j];
         matCortes[cont] = new int[totalElementos];
         pieza = 0;
         for(int j=0; j<demanda.length; j++){
            for(int k=0; k<demanda[j]; k++) matCortes[cont][pieza++] = w[j];
         }
         cont++;
      }
      return matCortes;
   }

   public void imprimirCortes(){
      ListIterator<int[]> iter = cortes.listIterator(0);
      while(iter.hasNext()){
         System.out.println(Arrays.toString(iter.next()));
      }
   }

   public static void main (String [] args){
      LecturaDatos datos = new LecturaDatos();
      datos.leer();
      System.out.println("Resolviendo...");
      SolverBinPackingPDinamica solver = new SolverBinPackingPDinamica(datos);
      solver.imprimirResultados();
   }
}
