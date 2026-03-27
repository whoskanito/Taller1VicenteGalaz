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
		try { menu(); } 
		catch (FileNotFoundException e) { System.out.println("Archivo no encontrado"); }
		catch (Exception e) {System.out.println("Ocurrió un error: " + e.getMessage());}
	}

	public static void menu() throws FileNotFoundException
	{	
		/*
		 * Aquí comienza el menú interactivo, usamos do while ya que es lo más práctico al hacer un menú,
		 * siempre nos aseguramos que el usuario entre al menos 1 vez al menú, además, para evitar que 
		 * el usuario se equivoque en la selección de opciones, usamos en la variable opciones (y en varias
		 * variables de selección) el método .trim(), aunque el usuario haga " 3" en vez de "3", la opcion 
		 * eliminará el espacio.
		 */
		
		boolean ejecucion = true;
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
				/*
				 * En esta parte iniciamos el menú de usuario con sus respectivas opciones
				 */
				
				if (acceso)
				{
					boolean ejecucion1 = true;
					System.out.println("Bienvenido " + user + "!");
					do
					{
			/*
			 * 1. Aquí es necesario explicar el funcionamiento de lo que se le muestra al usuario 
			 * y los índices del log, al usuario se le muestra una posición falsa del índice de su registro
			 * (1,2,3,4...) en cambio el registro de por ejemplo, Martin, seria realmente 0,3,6...
			 * Para eso creamos la lista pocisiones, esta la usaremos para guardar el índice real más adelante.
			 * 
			 * 2. Usaremos en todo momento el boolean de ejecucion_log_user para los menú del 1 al 4, ya que 
			 * como está aquí arriba su inicialización siempre que el usuario quiera volver, volverá al menú
			 * de usuario y no se deslogueará su cuenta.
			 */
						int[] posiciones = new int[log.length];
						boolean ejecucion_log_user = true;
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
							
			/*
			 * Gracias a que hice la opción 2 de modificar actividad primero, hacer la 1 se me hizo súper
			 * fácil, aquí simplemente recorremos el log hasta encontrar un espacio vacío, al decirle
			 * que si encuentra un null (independiente si es usuario, fecha, hora o actividad) rellene
			 * con datos nuevos, hacemos un break instantaneamente ya que, de lo contrario, todas las lineas
			 * a partir de esa, se llenarán de copias, lo cual no queremos, solo queremos que 1 sola linea
			 * se actualice, asi que luego invocamos a guardar_log y listo, se añade la nueva actividad, además
			 * tenemos un boolean que avisa si se modificó o no alguna linea, si no lo hace significa que no hay
			 * ningún vacío, es decir que el registro está lleno, y no se permitirá hacer cambios.														 						
			 */							
							do
							{	
								System.out.println("0) Regresar al menu de usuario");
								System.out.print("Ingresa fecha de actividad: ");
								String na_fecha = teclado.nextLine();
								System.out.println();
								if (na_fecha.equals("0"))
								{
									ejecucion_log_user = false;
								} 
								else
								{
									System.out.println("0) Regresar al menu de usuario");
									System.out.print("Ingresa hora de actividad: ");
									String na_hora = teclado.nextLine();
									System.out.println();
									if (na_hora.equals("0"))
									{
										ejecucion_log_user = false;
									}
									else
									{
										System.out.println("0) Regresar al menu de usuario");
										System.out.print("¿Qué actividad quieres registrar?: ");
										String na_actividad = teclado.nextLine();
										System.out.println();
										if (na_actividad.equals("0"))
										{
											ejecucion_log_user = false;
										}
										else
										{
											boolean se_guardo = false;
											for (int q = 0; q < log.length; q++)
											{
												if (log[q][0] == null)
												{
													log[q][0] = user;
													log[q][1] = na_fecha;
													log[q][2] = na_hora;
													log[q][3] = na_actividad;
													se_guardo = true;
													ejecucion_log_user = false;
													break;
												}
											}												
											if (!se_guardo)
											{
												System.out.println("El registro está lleno, elimina actividades antes de añadir");
											}
											else if (se_guardo)
											{ 
												guardar_log(log);
												System.out.println("Registro guardado!");
											}
										}
									}
								}																																					
							} while (ejecucion_log_user == true);
						}
						else if (opcion_1.equals("2"))
						{
							do
							{			
								int count = 0;
								System.out.println("¿Qué actividad quieres modificar?");
								System.out.println();
								System.out.println("0) Regresar al menu de usuario");
								for (int l = 0; l < log.length; l++)
								{
									if (log[l][0] != null && log[l][0].equals(user))
									{
										System.out.println((count + 1) + ") " +
										log[l][0] + ", " +
										log[l][1] + ", " +
										log[l][2] + ", " +
										log[l][3]
												);
										posiciones[count] = l; //Esto guarda el índice real :p
										count++;
									}
								}
								System.out.print("--> ");
								int log_seleccionado = teclado.nextInt();
								teclado.nextLine();
								if (log_seleccionado == 0)
								{
									ejecucion_log_user = false;
								}
								else if (log_seleccionado > 0 && log_seleccionado <= 300)
								{
									boolean ejecucion_m_user1 = true;
									do
									{										
										System.out.println("¿Que es lo que quieres modificar? ");
										System.out.println("1) Fecha");
										System.out.println("2) Duración");
										System.out.println("3) Tipo de actividad");
										System.out.println("4) Regresar al menu de usuario");
										System.out.print("--> ");
										String opcion_2 = teclado.nextLine();
										System.out.println();
					/*
					 * Aquí creamos la variable índice real, como el arreglo posiciones tiene el largo
					 * del log original, podemos manipular ciertos índices en específico, el contador llegará
					 * a un máximo del log del usuario, y arriba le decimos a posiciones que en el índice de 
					 * contador sea igual al original, y de esa forma indice real puede tomar su valor como
					 * lo hacemos más abajo, de esa forma si Martín quiere cambiar su registro numero 67 en 
					 * realidad estaría cambíando el 199. (La explicación está acá porque fue la primera opción en la que
					 * tuve que cranear esta solución de los índices xd).
					 * 
					 * meow :3
					 */
										int indiceReal = posiciones[log_seleccionado - 1];
										if (opcion_2.equals("1"))
										{	
											System.out.println("0) Regresar");
											System.out.print("Ingresa nueva fecha: ");
											String nueva_fecha = teclado.nextLine().trim();
											System.out.println();
											if (nueva_fecha.equals("0"))
											{
												ejecucion_m_user1 = false;
												ejecucion_log_user = false;											
											}
											else
											{
												log[indiceReal][1] = nueva_fecha;		
												guardar_log(log);
												System.out.println("Registro actualizado!");
												System.out.println();
												ejecucion_m_user1 = false;
												ejecucion_log_user = false;
											} 
										} 											
										else if (opcion_2.equals("2"))
										{
											System.out.println("0) Regresar");
											System.out.print("Ingresa duración actualizada: ");
											String nuevo_log = teclado.nextLine().trim();
											System.out.println();
											if (nuevo_log.equals("0"))
											{
												ejecucion_m_user1 = false;
												ejecucion_log_user = false;
												
											}
											else
											{
												log[indiceReal][2] = nuevo_log;		
												guardar_log(log);
												System.out.println("Registro actualizado!");
												System.out.println();
												ejecucion_m_user1 = false;
												ejecucion_log_user = false;
											}
											
										}
										else if (opcion_2.equals("3"))
										{
											System.out.println("0) Regresar");								
											System.out.print("Ingrese nuevo tipo de actividad: ");
											String nuevo_log = teclado.nextLine().trim();
											System.out.println();											
											if (nuevo_log.equals("0"))
											{
												ejecucion_m_user1 = false;
												ejecucion_log_user = false;
												
											}
											else
											{
												log[indiceReal][3] = nuevo_log;		
												guardar_log(log);
												System.out.println("Registro actualizado!");
												System.out.println();
												ejecucion_m_user1 = false;
												ejecucion_log_user = false;
											} 
										}
										else if (opcion_2.equals("4"))
										{
											ejecucion_m_user1 = false;
											ejecucion_log_user = false;
										}	
									} while (ejecucion_m_user1);
								}
							} while (ejecucion_log_user);
						}
						else if (opcion_1.equals("3"))
						{							
				/*
				 * Bueno y aquí también usamos la lógica del 2 pero en vez de modificar, eliminamos, le damos los 
				 * índices falsos al usuario y usamos exactamente las mismas varibles puesto que las creamos dentro
				 * de los ciclos asi que cada vez que el usuario quiera borrar o modificar el indice real siempre se 
				 * creará al momento de apretar la opción, no antes, asi no guarda información.
				 */
							
							do
							{
								int count1 = 0;
								System.out.println("¿Qué actividad quieres eliminar?");
								System.out.println();
								System.out.println("0) Regresar al menu de usuario");
								for (int a = 0; a < log.length; a++)
								{
									if (log[a][0] != null && log[a][0].equals(user))
									{
										System.out.println((count1 + 1) + ") " +
										log[a][0] + ", " +
										log[a][1] + ", " +
										log[a][2] + ", " +
										log[a][3]
												);
										posiciones[count1] = a; //Esto guarda el índice real :p
										count1++;
									}
								}
								System.out.print("--> ");
								int log_seleccionado = teclado.nextInt();
								teclado.nextLine();								
								if (log_seleccionado == 0)
								{
									ejecucion_log_user = false;
								}
								else if (log_seleccionado > 0 && log_seleccionado <= 300)
								{
									int indiceReal = posiciones[log_seleccionado - 1];
									log[indiceReal][0] = null;
									log[indiceReal][1] = null;
									log[indiceReal][2] = null;
									log[indiceReal][3] = null;
									guardar_log(log);
									System.out.println("Registro eliminado :(");
									System.out.println();
									ejecucion_log_user = false;
								}
							} while (ejecucion_log_user);
								
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
				else
				{
					System.out.println("Datos inválidos ");
					System.out.println();
				}
			}
			else if (opcion.equals("2"))
			{
				
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
			if (log[i][0] != null)
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