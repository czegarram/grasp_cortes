package base.grasp;

import java.util.Scanner;
import java.util.InputMismatchException;

public class AplicacionBinPacking{
   public static void main (String [] args){
      Scanner in = new Scanner(System.in);
      LecturaDatos datos = new LecturaDatos();

      System.out.println("Escriba el nombre del archivo o [nuevo/salir]: ");
      String orden = in.nextLine().trim();
      SolverBinPacking solver;
      while(!orden.equalsIgnoreCase("salir")){
         if(orden.equalsIgnoreCase("nuevo")){
            datos.leer();
            solver = seleccionarSolver(datos, in);
            solver.imprimirResultados();
            if(preguntaSN("Desea guardar este caso? [S/N]: ",in)){
               System.out.print("Nombre del archivo: ");
               orden = in.nextLine().trim();
               if(datos.escribirArchivo(orden)){
                  System.out.println("Guardado");
               }else{
                  System.out.println("Error al guardar el archivo");
               }
            }
         }else{ 
            if(datos.leerArchivo(orden)){
               datos.mostrar();
               solver = seleccionarSolver(datos, in);
               solver.imprimirResultados();
            }else{
               System.out.println("El archivo no existe/no es valido");
            }
         }
         System.out.println("***********************************************");
         System.out.println("Escriba el nombre del archivo o [nuevo/salir]: ");
         orden = in.nextLine().trim();
      }
   }
   public static boolean preguntaSN(String titulo, Scanner in){
      System.out.print(titulo);
      char resp = in.nextLine().charAt(0);
      while(resp != 's' && resp != 'n' && resp != 'S' && resp != 'N'){
         System.out.print("[S/N]: ");
         resp = in.nextLine().charAt(0);
      }
      if(resp == 's' || resp == 'S') return true; else return false;
   }
   public static SolverBinPacking seleccionarSolver(LecturaDatos datos, Scanner in){
      int opcion = 0;
      int noIter = 0;
      double alpha = 0;
      System.out.println("Metodo de solucion:");
      System.out.println("\t1. Voraz (Best Fit Decreasing)");
      System.out.println("\t2. Programacion Dinamica");
      System.out.println("\t3. GRASP");
      System.out.print("Seleccione un metodo [1/2/3]: ");
      do try{
         opcion = in.nextInt();
         if(opcion < 1 || opcion > 3) System.out.print("[1/2/3]: ");
      }catch(InputMismatchException e){
         in.nextLine();
         System.out.print("[1/2/3]: ");
      }while(opcion < 1 || opcion > 3);
      in.nextLine();
      if(opcion == 1){
         //Clase BFD
         System.out.println("Resolviendo...");
         return new SolverBinPackingVoraz(datos);
      }else if(opcion == 2){
         //Clase PDinamica
         System.out.println("Resolviendo...");
         return new SolverBinPackingPDinamica(datos);
      }else{
         //Clase GRASP
         boolean listo;
         do try{
            System.out.print("No. de iteraciones: ");
            noIter = in.nextInt();
            listo = true;
         }catch(InputMismatchException e){
            in.nextLine();
            listo = false;
         }while(!listo);
         in.nextLine();
         do try{
            System.out.print("Coef. de relajacion: ");
            alpha = in.nextDouble();
            listo = true;
         }catch(InputMismatchException e){
            in.nextLine();
            listo = false;
         }while(!listo);
         in.nextLine();
         System.out.println("Resolviendo...");
         return new SolverBinPackingGRASP(datos, alpha, noIter);
      }
   }
}
