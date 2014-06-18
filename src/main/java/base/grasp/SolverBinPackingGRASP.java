package base.grasp;

/**
 * @(#)GRASP.java
 *
 *
 * @author Erazo Gonzales Daniel
 * @version 1.00 2011/11/14
 */
import java.util.Random;

public class SolverBinPackingGRASP extends SolverBinPacking{
   private final int ARRAY_SIZE = 500;
   private double alfa;
   private int nIter;

   private int n;
   private int E[];
   private int mmin;
   private int L;
   private int minB[][];
   private int minR[];

   private int auxReq[];
   private int B[][];
   private int Res[];
   private int rcl[];
   private int m;
   private int nReq;
   private int num_rcl;

   public SolverBinPackingGRASP(LecturaDatos datos, double a, int ni) {
      super(datos);
      minB = new int[ARRAY_SIZE][ARRAY_SIZE];
      minR = new int[ARRAY_SIZE]; 
      alfa = a;
      nIter = ni;
      E = datos.getObjetos();
      n = datos.getCantTotal();
      L = datos.getContenedor();
      resolver();
   }

   protected void resolver_base(){
      GRASP_Algoritmo(nIter);
   }

   public void GRASP_Algoritmo(int nIter){
      int conta = 0; 

      B = new int[ARRAY_SIZE][ARRAY_SIZE];
      Res = new int[n];//vector de residuos
      Ordenar_QuickSort(E, 0, n-1);
      while(conta<nIter){
         GRASP_Construccion();
         Registrar_Mejor_Solucion(conta);
         conta++;
      }
   }

   public void Registrar_Mejor_Solucion(int conta){
      int i, j = 0;

      if(m<mmin || conta==0){
         for(i=0;i<m;i++){
            for(j=0;j<ARRAY_SIZE;j++){
               if(B[i][j]!=0){
                  minB[i][j] = B[i][j];
                  minR[i] = Res[i];	
               }
            }
         }
         mmin = m;
      }
   }

   public void GRASP_Construccion(){
      int k, j;
      int b , d, resultado;
      Random rand = new Random();
      boolean flag;

      auxReq = new int[n];
      for(int i=0;i<n;i++){
         auxReq[i] = E[i];
      }
      nReq = n;
      m = 0; 
      rcl = new int[0];
      Res[0] = L;
      while(nReq>0){
         num_rcl = 0;
         b = auxReq[0];
         d = auxReq[nReq-1];
         llenar_RCL(b, d);
         num_rcl = rand.nextInt(num_rcl);
         resultado = rcl[num_rcl];
         eliminar(auxReq, nReq, num_rcl);
         nReq--; 
         flag =  false;
         k = 0;
         while(k<m && !flag){
            if(Res[k]>=resultado){
               flag = true;//hay espacio en el contenedor
               break;
            } 
            k++;
         }
         if(!flag){//Si no hay espacio en el contenedor
            m++;
            B[k][0] = resultado;
            Res[k] = L - resultado;
         }
         else{
            j = 0;
            while(B[k][j]>0){
               j++;
            }
            B[k][j] = resultado;
            Res[k] = Res[k] - resultado;
         }	
      }	
   }

   public void llenar_RCL(int b, int d){
      int i;

      for(i=0;i<nReq;i++){
         if((double)auxReq[i]>=(b-alfa*(b-d))){
            rcl = redimensionar(rcl,num_rcl);
            rcl[num_rcl] = auxReq[i];
            num_rcl++;
         }
         else i = nReq-1;
      }
   }

   public int[] redimensionar(int vect[], int dim){
      int aux[] = new int[dim+1];
      int i;

      for(i=0;i<dim;i++){
         aux[i] = vect[i];
      }
      vect = aux;
      return vect;
   }

   public void eliminar(int vect[], int dim, int pos){
      int i;
      for(i=pos;i<dim-1;i++){
         vect[i] = vect[i+1];
      }
      dim--;
   }

   public void Ordenar_QuickSort(int V[], int izq, int der){
      int i, j; 
      int x, aux;

      i = izq;
      j = der;
      x = V[(izq+der)/2];
      do{
         while((V[i]>x) && (j<=der)) i++;
         while((x>V[j]) && (j>izq)) j--;
         if(i<=j){
            aux = V[i];
            V[i] = V[j];
            V[j] = aux;
            i++;
            j--;
         }
      }while(i<=j);
      if(izq<j) Ordenar_QuickSort(V,izq,j);
      if(i<der) Ordenar_QuickSort(V,i,der);
   }

   public int[][] getCortes(){
      int cortes[][] = new int[mmin][];
      int cantObjetos;
      for(int i=0; i<mmin; i++){
         cantObjetos = 0;
         for(int j=0; j<ARRAY_SIZE && minB[i][j] != 0; j++) cantObjetos++;
         cortes[i] = new int[cantObjetos];
         for(int j=0; j<cantObjetos; j++) cortes[i][j] = minB[i][j];
      }
      return cortes;
   }
   public int getNumeroContenedores(){
      return mmin;
   }
   public int[] getResiduosIndividuales(){
      int R[] = new int[mmin];
      for(int i=0; i<mmin; i++) R[i] = minR[i];
      return R;
   }
}
