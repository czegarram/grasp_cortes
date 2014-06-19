package base.grasp;

public abstract class SolverBinPacking{
   protected LecturaDatos datos;
   private long tiempoEjecucion;

   public SolverBinPacking(){
	   
   }
   
   
   public SolverBinPacking(LecturaDatos datos){
      this.datos = datos;
   }
   
   /**
    * Resuelve el problema de bin packing.
    */
   protected abstract void resolver_base();
   /**
    * Resuelve el problema de bin packing y mide el tiempo de ejecucion.
    */
   public void resolver(){
      long inicio = System.currentTimeMillis();
      resolver_base();
      tiempoEjecucion = System.currentTimeMillis() - inicio;
   }
   /**
    * Devuelve la matriz con la descripcion de los cortes de cada contenedor.
    * Es una matriz irregular, es decir:
    *    int cortes[][] = new int[no.contenedores]
    *    por cada i en cortes... cortes[i] = new int[no. cortes en ese contenedor]
    * Si se meten varios del mismo tipo en el contenedor se escribe repetido
    */
   public abstract int[][] getCortes();
   /**
    * Devuelve el numero de contenedores
    */
   public abstract int getNumeroContenedores();

   public long getTiempoEjecucion(){
      return tiempoEjecucion;
   }
   /**
    * Devuelve los residuos (cant no usada) en cada contenedor.
    * Si ya tienen preparada el vector de residuos de modo que es mas facil que el
    * metodo de abajo (lo digo por ti daniel (?)) pueden reemplazar este codigo.
    * Recuerden, los contenedores siguen en el mismo orden que en getCortes()
    */
   public int[] getResiduosIndividuales(){
      int cortes[][] = getCortes();
      int residuos[] = new int[cortes.length];
      int suma;
      for(int i=0; i<cortes.length; i++){
         suma = 0;
         for(int j=0; j<cortes[i].length; j++) suma += cortes[i][j];
         residuos[i] = datos.getContenedor() - suma;
      }
      return residuos;
   }
   /**
    * Devuelve la cant no usada total.
    */
   public int getResiduoTotal(){
      int residuos[] = getResiduosIndividuales();
      int suma = 0;
      for(int i=0; i<residuos.length; i++) suma += residuos[i];
      return suma;
   }
   public int getResiduoReal(){
      int residuos[] = getResiduosIndividuales();
      int iMax = 0;
      int suma = 0;
      for(int i=1; i<residuos.length; i++){
         if(residuos[i] > residuos[iMax]){
            suma += residuos[iMax];
            iMax = i;
         }else{
            suma += residuos[i];
         }
      }
      return suma;
   }
   public String imprimirResultados(){
      System.out.println("Tiempo ejecucion: " + getTiempoEjecucion() + " milisegundos");
      System.out.println("Numero de contenedores: " + getNumeroContenedores());
      System.out.println("Cortes por contenedor: ");
      int cortes[][] = getCortes();
      for(int i=0; i<cortes.length; i++){
         System.out.print("  " + (i+1) + ". [");
         for(int j=0; j<cortes[i].length; j++){
            if(j != 0) System.out.print(", ");
            System.out.print(cortes[i][j]);
         }
         System.out.println("]");
      }
      System.out.println("Residuos (porcentaje de uso):");
      int residuos[] = getResiduosIndividuales();
      for(int i=0; i<residuos.length; i++){
         System.out.printf("  %d. %d (%.2f%%)\n", i+1, residuos[i], 100*(1-(double)residuos[i]/datos.getContenedor()));
      }
      int residuoTotal = getResiduoTotal();
      System.out.println("Residuo total: " + residuoTotal);
      int residuoReal = getResiduoReal();
      System.out.printf("Residuo real: %d (%.2f%% de un contenedor)\n", residuoReal, 100*(double)residuoReal/datos.getContenedor());
      System.out.printf("Porcentaje de uso real: %.2f%%\n", 100*(1-(double)residuoReal/(getNumeroContenedores()*datos.getContenedor())));
      return "";
   }
}
