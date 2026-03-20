package logica;
import java.io.File;
import java.util.Scanner;
//import java.util.ArrayList;


public class Main 
{
	public static void main(String[] args) 
	{
		// Vicente Renato Galaz Palacios, 21.831.202-0, Ingeniería en Tecnologías de Información.
		
		//programa();
		archivo();
		
	}

	public static void programa()
	{}
	
	
	// Lectura de Archivos "Registros" y "Usuarios"
	public static void archivo()
	{
		try 
		{
			// Se abren registros y usuarios
			File registros = new File("Registros.txt");
			File usuarios = new File("Usuarios.txt");
			
			// Lectura de "Registros"
			Scanner lector = new Scanner(registros);
			while (lector.hasNextLine())
			{
				String lineaR = lector.nextLine();
				String[] partes = lineaR.split(";");
			}
			lector.close();
			
			// Lectura de "Usuarios"
			int indice = 0;
			String[][] usercon = new String[3][2];
			Scanner lector1 = new Scanner(usuarios);
			while (lector1.hasNextLine())
			{
				String lineaU = lector1.nextLine();
				String[] partes = lineaU.split(";");
				String usuario = partes[0];
				String contraseña = partes[1];
				usercon[indice][0] = usuario;
				usercon[indice][1] = contraseña;
				indice += 1;
			}
			lector1.close();
			
		} 
		
		catch (Exception e) 
		{
			System.out.println("Error al leer archivo.");
		} 

		
	}
	
}
