package logica;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main 
{
	// Vicente Renato Galaz Palacios, 21.831.202-0, Ingeniería en Tecnologías de Información.
	static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		
		//programa();
		try { menu(); } 
		catch (FileNotFoundException e) { System.out.println("Archivo no encontrado"); }
		catch (Exception e) {System.out.println("Ocurrió un error: " + e.getMessage());}
	}

	public static void menu() throws FileNotFoundException
	{	
		/*
		 * Aquí comienza el menú interactivo, usamos do while ya que es lo más práctico al hacer un menú,
		 * siempre nos aseguramos que el usuario entre al menos 1 vez al menú, además, para evitar que 
		 * el usuario se equivoque en la selección de opciones, usamos en la variable opciones el método
		 * .trim(), aunque el usuario haga " 3" en vez de "3", la opcion eliminará el espacio.
		 */
		
		boolean ejecucion = true;
		boolean ejecucion1 = true;
		String[][] usuarios = archivo_usuarios();
		String[][] log = archivo_registros();
		String opcion;
		
		System.out.println(usuarios[0][0]);
		do
		{
			System.out.println("---------- MENÚ ----------");
			System.out.println("1) Menú de Usuarios");
			System.out.println("2) Menú de Análisis");
			System.out.println("3) Salir");
			System.out.print("--> ");
			opcion = teclado.nextLine().trim();
			
			if (opcion.equals("1"))
			{
				
				boolean acceso = false;
				int i;
				System.out.println();
				System.out.print("Usuario: ");
				String user = teclado.nextLine().trim();
				System.out.print("Contraseña: ");
				String password = teclado.nextLine().trim();	
				System.out.println();

		// En esta parte comprobamos usuario y contraseña para ver si permitimos el acceso o no
				for (i = 0; i < usuarios.length; i++)
				{
					if (user.equals(usuarios[i][0]))
					{
						if (password.equals(usuarios[i][1]))
						{
							acceso = true;
							System.out.println("Acceso correcto!");
							System.out.println();
						}
					}
				}
				
				if (acceso)
				{
					do
					{
						System.out.println("Bienvenido " + user + "!");
						System.out.println();
						System.out.println("¿Qué deseas realizar? ");
						System.out.println("1) Registrar actividad. ");
						System.out.println("2) Modificar actividad. ");
						System.out.println("3) Eliminar actividad. ");
						System.out.println("4) Cambiar contraseña. ");
						System.out.println("5) Cerrar Sesión. ");
						System.out.print("--> ");
						String opcion_1 = teclado.nextLine();
						System.out.println();
						if (opcion_1.equals("1"))
						{
							
						}
						else if (opcion_1.equals("2"))
						{
							
						}
						else if (opcion_1.equals("3"))
						{
							
						}
						else if (opcion_1.equals("4"))
						{
							System.out.print("Ingrese nueva contraseña: ");
							String nueva_contraseña = teclado.nextLine();
							System.out.println();
							for (int n = 0; n < usuarios.length; n++)
							{
								if (usuarios[n][0] != null && usuarios[n][0].equals(user))
							    {
							        usuarios[n][1] = nueva_contraseña;
							    }
							}
							
						   /* 
							* Aquí invocamos a la funcion para guardar la contraseña, reescribiendo 
							* todo a partir de la matriz
							*/
							
							guardar_usuarios(usuarios);
							System.out.println("Contraseña actualizada!");
							System.out.println();
							
						}
						else if (opcion_1.equals("5"))
						{
							ejecucion1 = false;
						}
					} while (ejecucion1 == true);
					
					
				}
				
				
			}
			else if (opcion.equals("3"))
			{
				System.out.println("");
				System.out.println("Ejecución finalizada.");
				ejecucion = false;
			}
			
		} while (ejecucion == true);
	}
	
	
	// Lectura de Archivos "Registros" y "Usuarios"
	public static String[][] archivo_usuarios() throws FileNotFoundException
	{
		// Primer archivo
		File archivo_usuarios = new File("Usuarios.txt");
		Scanner lector = new Scanner(archivo_usuarios);
		String[][] usuarios = new String[3][2];
		int i = 0;
		/*
		 * En esta parte leemos el archivo txt "Usuarios" y lo guardamos en una matriz 3 x 2
		 * de esa forma cuando querramos acceder a los usuarios simplemente comparamos el usuario
		 * y contraseña a usuarios.
		 */
		while (lector.hasNextLine())
		{
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			usuarios[i][0] = partes[0];
			usuarios[i][1] = partes[1];
			i++;
		}
		lector.close();
		return usuarios;
	}
	
	public static String[][] archivo_registros() throws FileNotFoundException
	{
		// Segundo archivo
		String[][] log = new String[300][4];
		File archivo_registros = new File("Registros.txt");
		Scanner lector = new Scanner(archivo_registros);
		int i = 0;
		/*
		 * En esta parte leemos el archivo txt "Registros" y lo guardamos en la matriz "log", el
		 * cual tiene 300 filas, ya que no se podrá tener más de 300 actividades.
		 */
		while (lector.hasNextLine())
		{
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			log[i][0]  = partes[0];
			log[i][1]  = partes[1];
			log[i][2]  = partes[2];
			log[i][3]  = partes[3];
			i++;
		}
		lector.close();
		return log;
	}
	
	/*
	 * Esta función sirve para reescribir todo el archivo, mucho más práctico al momento de actualizar datos
	 * en el txt, en vez de cambiar una linea en específico, cambiamos todo el archivo tomando como referecia la
	 * matriz que ya leyó el archivo anteriormente, asi que no se perderían datos, además el guardado ocurre después
	 * de preguntarle al usuario la nueva contraseña por lo que la matriz que toma de argumento .
	 */
	
	public static void guardar_usuarios(String[][] usuarios) throws FileNotFoundException
	{
		PrintWriter escritor = new PrintWriter(new File("Usuarios.txt"));
		for (int i = 0; i < usuarios.length; i++)
		{
			// Este if sirve para que no se reescriban datos de más en el txt.
			if (usuarios[i][0] != null)
			{
				escritor.println(usuarios[i][0] + ";" + usuarios[i][1]);
			}
		}
		escritor.close();
	}
	
	/*
	 * Aquí más de lo mismo, en vez de cambiar usuario, cambiamos el log completo, tomamos como argumento la
	 * matriz que contiene el log y lo reescribimos completamente.
	 */
	
	public static void guardar_log(String[][] log) throws FileNotFoundException
	{
		PrintWriter escritor = new PrintWriter(new File("Registros.txt"));
		for (int i = 0; i < log.length; i++)
		{
			// Este if también sirve para no escribir de más en los txt.
			if (log[i][0] == null)
			{
				escritor.println(
		                log[i][0] + ";" +
		                log[i][1] + ";" +
		                log[i][2] + ";" +
		                log[i][3]
		            );
			}
		}
		escritor.close();
	}

}