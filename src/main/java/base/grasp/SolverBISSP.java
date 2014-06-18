package base.grasp;

public class SolverBISSP{
   private int matriz[][][];
   private int objSuficientes;
   private int B;

   public SolverBISSP(int NObjD, int Bin){
      matriz = new int[NObjD][Bin+1][2];
      B = Bin;
      objSuficientes = -1;
   }

   public int[] resolverCon(int w[], int v[]){
      if(w.length != matriz.length || v.length != matriz.length) return null;
      objSuficientes = -1;
      calcularMatriz(w, v);
      return hallarVector(w);
   }

   private void calcularMatriz(int w[], int v[]){
      int suma[] = new int[matriz.length];
      
      suma[0] = w[0]*v[0];
      for(int i=1; i<suma.length; i++)
         suma[i] = suma[i-1] + w[i]*v[i];

      for(int y=0; y<=B; y++){
         if(y != 0 && y%w[0]==0 && y/w[0] <= v[0]){
            matriz[0][y][0] = 0;
            matriz[0][y][1] = y/w[0];
         }else{
            matriz[0][y][0] = matriz[0][y][1] = -1;
         }
      }
      if(matriz[0][B][0] != -1){
         objSuficientes = 0;
         return;
      }

      for(int k=1; k<matriz.length; k++){
         for(int y=0; y<=B; y++){
            if(matriz[k-1][y][0] != -1 || y < w[k] || y > suma[k]){
               matriz[k][y][0] = matriz[k-1][y][0];
               matriz[k][y][1] = matriz[k-1][y][1];
            }else if(matriz[k][y-w[k]][0] == k && matriz[k][y-w[k]][1] < v[k]){
               matriz[k][y][0] = k;
               matriz[k][y][1] = matriz[k][y-w[k]][1] + 1;
            }else if(v[k] > 0 && (y == w[k] || (matriz[k][y-w[k]][0] != -1 && matriz[k][y-w[k]][0] != k))){
               matriz[k][y][0] = k;
               matriz[k][y][1] = 1;
            }else{
               matriz[k][y][0] = matriz[k][y][1] = -1;
            }
         }
         if(matriz[k][B][0] != -1){
            objSuficientes = k;
            return;
         }
      }
      if(objSuficientes == -1) objSuficientes = matriz.length - 1;
   }

   private int[] hallarVector(int w[]){
      if(objSuficientes == -1) return null;
      int vect[] = new int[matriz.length];
      for(int i=0; i<vect.length; i++) vect[i] = 0;

      int bmax = B;
      while(matriz[objSuficientes][bmax][0] == -1) bmax--;

      int obj, cant;
      do{
         obj = matriz[objSuficientes][bmax][0];
         cant = matriz[objSuficientes][bmax][1];
         vect[obj] += cant;
         bmax -= cant*w[obj];
      }while(bmax > 0);

      return vect;
   }

 }
