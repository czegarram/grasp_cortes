package base.grasp;

import java.util.Scanner;
import java.io.*;

public class LecturaDatos{
   private int N, B;
   private int w[], v[];

   public LecturaDatos(){
      N = B = 0;
      w = v = null;
   }

   public boolean leerArchivo(String nombre){
      File archivo = new File(nombre);
      if(archivo.exists()) try{
         BufferedReader reader = new BufferedReader(new FileReader(archivo));
         if(!reader.readLine().equals("UNMSMEAPISW")){
            reader.close();
            return false;
         }
         B = Integer.parseInt(reader.readLine());
         N = Integer.parseInt(reader.readLine());
         w = new int[N];
         v = new int[N];
         for(int i=0; i<N; i++){
            w[i] = Integer.parseInt(reader.readLine());
            v[i] = Integer.parseInt(reader.readLine());
         }
         reader.close();
         return true;
      }catch(Exception e){
         e.printStackTrace();
         return false;
      }else return false;
   }
   public boolean escribirArchivo(String nombre){
      File archivo = new File(nombre);
      try{
         BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
         writer.write("UNMSMEAPISW\n");
         writer.write(B + "\n");
         writer.write(N + "\n");
         for(int i=0; i<N; i++){
            writer.write(w[i]+"\n");
            writer.write(v[i]+"\n");
         }
         writer.close();
         return true;
      }catch(Exception e){
         e.printStackTrace();
         return false;
      }
   }

   public void leer(){
      @SuppressWarnings("resource")
	Scanner in = new Scanner(System.in);
      System.out.print("Tamanio contenedor: ");
      B = in.nextInt();
      System.out.print("No. objetos distintos: ");
      N = in.nextInt();
      w = new int[N];
      v = new int[N];
      for(int i=0; i<N; i++){
         System.out.println("Objeto " + (i+1));
         System.out.print("   Tamanio: ");
         w[i] = in.nextInt();
         System.out.print("   Demanda: ");
         v[i] = in.nextInt();
      }
   }
   public void mostrar(){
      System.out.println("Tamanio contenedor: " + B);
      System.out.println("No. objetos distintos: " + N);
      for(int i=0; i<N; i++){
         System.out.println("Objeto " + (i+1));
         System.out.println("   Tamanio: " + w[i]);
         System.out.println("   Demanda: " + v[i]);
      }
   }

   public int getCantDistintos(){
      return N;
   }
   public int getCantTotal(){
      int suma = 0;
      for(int i=0;i<v.length;i++) suma += v[i];
      return suma;
   }
   public int getContenedor(){
      return B;
   }

   public int[] getTamanio(){
      return w;
   }
   public int[] getDemanda(){
      return v;
   }
   public int[] getObjetos(){
      int o[] = new int[getCantTotal()];
      int k = 0;
      for(int i=0; i<w.length; i++){
         for(int j=0; j<v[i]; j++){
            o[k] = w[i];
            k++;
         }
      }
      return o;
   }

   public Requerimiento[] getRequerimientos(){
      Requerimiento rq[] = new Requerimiento[N];
      for(int i=0; i<N; i++) rq[i] = new Requerimiento(w[i], v[i]);
      return rq;
   }
}
