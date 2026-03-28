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
		String opcion;	
		do
		{
			String[][] usuarios = archivo_usuarios();
			String[][] log = archivo_registros();
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
								if (na_fecha.equals("0") || na_fecha.isEmpty())
								{
									ejecucion_log_user = false;
								} 
								else
								{
									System.out.println("0) Regresar al menu de usuario");
									System.out.print("Ingresa hora de actividad: ");
									String na_hora = teclado.nextLine();
									System.out.println();
									if (na_hora.equals("0") || na_hora.isEmpty())
									{
										ejecucion_log_user = false;
									}
									else
									{
										System.out.println("0) Regresar al menu de usuario");
										System.out.print("¿Qué actividad quieres registrar?: ");
										String na_actividad = teclado.nextLine();
										System.out.println();
										if (na_actividad.equals("0") || na_actividad.isEmpty())
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
								System.out.println();
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
				/*
				 * Aquí empezamos el menú de análisis, no se necesita que el usuario ingrese credenciales ni 
				 * modificar los logs asi que, las funciones de guardado no las usaremos puesto que siempre 
				 * que los usuarios entren a este menú, todo estaría actualizado, solo necesitamos cargar los 
				 * datos y almacenarlos en matrices, por cierto, como en esta parte solo revisamos mas no
				 * cambiamos, todos los proceso de cálculo los haremos en funciones, cosa que cuando vayamos
				 * a los condicionales solo pongamos print resultado en vez de hacer el proceso en cada if, asi
				 * se ve un poco más limpio.
				 */
				// String[] unicos = unicos(log); Lección aprendida, mirar el git de nuevo antes de hacer nada...
				String[] moda = actividadMasRealizada(log);
				String[][] moda_usuario = actividadDeModa_usuario(log);
				String[] flojo = elFlojo(log);
				boolean menu_analisis = true;
				System.out.println("Bienvenido al menú de análisis c:");
				System.out.println();
				do
				{					
					System.out.println("¿Que deseas estadística deseas revisar?");
					System.out.println();
					System.out.println("1) Actividad más realizada");
					System.out.println("2) Actividad más realizada por cada usuario");
					System.out.println("3) Usuario con mayor procastinacion");
					System.out.println("4) Ver todas las actividades");
					System.out.println("5) Volver al menú principal.");
					System.out.print("--> ");
					String option = teclado.nextLine();
					System.out.println();
					if (option.equals("1"))
					{
						System.out.println("Actividad más realizada en general:");
						System.out.println();
						System.out.println(moda[0] + " ha sido la actividad más realizada con un total de " + moda[1] + " horas invertidas.");
						System.out.println();
					}
					else if (option.equals("2"))
					{
						System.out.println("Actividad más realizada por usuario registrado: ");
						System.out.println();
						for (int u = 0; u < moda_usuario.length;u++) //ya ni se cuanta variables he iniciado en los for xd
						{
							System.out.println(moda_usuario[u][0] + " -> " + moda_usuario[u][1] + " -> con " + moda_usuario[u][2] + " horas registradas");
							
						}
						System.out.println();
					}
					else if (option.equals("3"))
					{
						System.out.print("Usuario con más procrastinación: ");
						System.out.println();
						System.out.println(flojo[0] + " es el usuario más procrastinador con " + flojo[1] + " horas perdidas");
					
					}
					else if (option.equals("4"))
					{
						System.out.println("Todas las actividades registradas");
		// a "todas las actividades" pense que se refería a las actividades únicas, hice algo na que ver antes XD
						System.out.println();
						for (int z = 0; z < log.length; z++)
						{
							if (log[z][0] != null)
							{
								System.out.println((z + 1) + ") " +
										log[z][0] + " el " +
										log[z][1] + " hizo " +
										log[z][2] + " hora(s) de " +
										log[z][3]
												);
							}
						}
						System.out.println();
					}
					else if (option.equals("5"))
					{									
						menu_analisis = false;
						System.out.println();
					}
				} while (menu_analisis);		
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
	
	
	/*
	 * En esta funcion usamos una lógica parecida que la de únicos, pero en vez de contabilizar 1 por 
	 * tipo, le sumamos 1 a los que se repitan, y aparte contamos las horas invetidas.
	 */
	public static String[] actividadMasRealizada(String[][] log)
	{
		String[] actividades = new String[300];
	    int[] horas = new int[300];
	    int cant = 0;
	    
	    for (int i = 0; i < log.length; i++)
	    {
	        if (log[i][0] != null)
	        {
	            String actividad = log[i][3];
	            int h = Integer.parseInt(log[i][2]); //con el parseInt convertimos el string en entero :p
	            boolean existe = false;

	            for (int j = 0; j < cant; j++)
	            {
	                if (actividades[j] != null && actividades[j].equals(actividad))
	                {
	                    horas[j] = horas[j] + h; //hacemos un acumulador pa cada actividad
	                    existe = true;
	                }
	            }
	            if (existe == false)
	            {
	                actividades[cant] = actividad;
	                horas[cant] = h;
	                cant++;
	            }
	        }
	    }
	    int max = 0;
	    String mejor = "";
	    for (int i = 0; i < cant; i++)
	    {
	        if (horas[i] > max)
	        {
	            max = horas[i];
	            mejor = actividades[i];
	        }
	    }
	    
		String[] resultado = new String[2];
		resultado[0] = mejor;
	    resultado[1] = String.valueOf(max);
		return resultado;
	}
	
	/*
	 * Bueno, decidí hacer dos funciones por separado porque asi podria tener dos variables para cada cosa, me 
	 * explico, si tuviera una para buscar la mas realizada solo me devolvería la matriz, pero de esa forma tendría
	 * que usar un cuarto indice para ver la mas realizada pero, sentía que quedaría bien desordenado dentro de la funcion
	 * y no se entenedría nada, asi que uso una para suarios y otra para la mas realizada en general
	 */
	
	public static String[][] actividadDeModa_usuario(String[][] log)
	{
		String[] usuarios = new String[300];
	    int cantidad_usuarios = 0;
	    
	    for (int i = 0; i < log.length; i++)
	    {
	        if (log[i][0] != null)
	        {
	            String user = log[i][0];
	            boolean existe = false;
	            for (int j = 0; j < cantidad_usuarios; j++)
	            {
	                if (usuarios[j] != null && usuarios[j].equals(user))
	                {
	                    existe = true;
	                }
	            }
	            if (existe == false)
	            {
	                usuarios[cantidad_usuarios] = user;
	                cantidad_usuarios++;
	            }
	        }
	    }
		String[][] resultado = new String[cantidad_usuarios][3];
		for (int u = 0; u < cantidad_usuarios; u++)
	    {
	        String user = usuarios[u];
	        String[] actividades = new String[300];
	        int[] horas = new int[300];
	        int cant = 0;
	        //estoy cansado jefe:(
	        for (int i = 0; i < log.length; i++)
	        {
	            if (log[i][0] != null && log[i][0].equals(user))
	            {
	                String act = log[i][3];
	                int h = Integer.parseInt(log[i][2]);
	                boolean existe = false;

	                for (int j = 0; j < cant; j++)
	                {
	                    if (actividades[j] != null && actividades[j].equals(act))
	                    {
	                        horas[j] = horas[j] + h;
	                        existe = true;
	                    }
	                }
	                if (existe == false)
	                {
	                    actividades[cant] = act;
	                    horas[cant] = h;
	                    cant++;
	                }
	            }
	        }
	        int max = 0;
	        String mejor = "";

	        for (int i = 0; i < cant; i++)
	        {
	            if (horas[i] > max)
	            {
	                max = horas[i];
	                mejor = actividades[i];
	            }
	        }
	        resultado[u][0] = user;
	        resultado[u][1] = mejor;
	        resultado[u][2] = String.valueOf(max);
	    }
		return resultado; 
		/*
		 * fun fact, no me funcionaba la funcion al principio y estuve como 1 hora viendo por qué, reinicie como
		 * 20 veces la consola y nada, despues de intentar cosa sin resultado, probe una vez mas y ahi si se solucionó 
		 * un bug de que si agregaba una actividad luego la funcion no la tomaba y se quedaba con el log original, iwkms
		 */
	}

	/*
	 * Esta funcion es un poco más sencilla porque solo hay que sumar las horas pero tiene que ser 
	 * por usuario asi que debemos usar 2 arreglos nuevamente, en una guardariamos los usuarios y en
	 * la otra las horas, no podemos usar una matriz porque necesitamos comparar y seria un poco mas latoso
	 * asi que 2 listas no más, con un ciclo for leemos log y agregamos a cada usuario sus horas correspondientes, mas
	 * adelante hacemos la típica comparación de mayor menor y vemos quien perdió más tiempo, luego retornamos
	 * la lista y podemos usarla en el menú.
	 */
	
	public static String[] elFlojo(String[][] log) 
	{
		String[] usuarios = new String[300];
	    int[] horas = new int[300];
	    int cant = 0;
	    
	    for (int i = 0; i < log.length; i++)
	    {
	        if (log[i][0] != null)
	        {
	            String user = log[i][0];
	            int h = Integer.parseInt(log[i][2]);
	            boolean existe = false;
	            for (int j = 0; j < cant; j++)
	            {
	                if (usuarios[j] != null && usuarios[j].equals(user))
	                {
	                    horas[j] = horas[j] + h;
	                    existe = true;
	                }
	            }
	            if (existe == false)
	            {
	                usuarios[cant] = user;
	                horas[cant] = h;
	                cant++;
	            }
	        }
	    }
	    int max = 0;
	    String peor = "";
	    
	    // Aqui comparamos al mas procrastinador 
	    for (int i = 0; i < cant; i++)
	    {
	        if (horas[i] > max)
	        {
	            max = horas[i];
	            peor = usuarios[i];
	        }
	    }
	    String[] resultado = new String[2];
	    resultado[0] = peor;
	    resultado[1] = String.valueOf(max);
	    
	    return resultado;
	}
	
}