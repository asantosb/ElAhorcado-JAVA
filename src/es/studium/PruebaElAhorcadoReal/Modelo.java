package es.studium.PruebaElAhorcadoReal;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Modelo
{
	// SONIDOS
	// APLICAMOS SONIDO AL PULSAR BOTONES DEL MENÚ
	public void Sonido()
	{
		File sf = new File("./recursos/click2.wav");
		AudioFileFormat aff;
		AudioInputStream ais;
		try
		{
			aff = AudioSystem.getAudioFileFormat(sf);
			ais = AudioSystem.getAudioInputStream(sf);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);
			ol.loop(0);
			Thread.sleep(500);
			ol.close();
		}
		catch(UnsupportedAudioFileException ee)
		{
			System.out.println(ee.getMessage());
		}
		catch(IOException ea)
		{
			System.out.println(ea.getMessage());
		}
		catch(LineUnavailableException LUE)
		{
			System.out.println(LUE.getMessage());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	// BASE DE DATOS
	// Método conectar BD
	public Connection conectar()
	{
		Connection c = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/elahorcado?serverTimezone=UTC";
		String login = "root";
		String password = "GrupoSsb91";
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD clientes
			c = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return (c);
	}

	// Método desconectar BD
	public void cerrar(Connection conexion)
	{
		try
		{
			if(conexion!=null)
			{
				conexion.close();
			}
		}
		catch (SQLException error)
		{
			System.out.println("Error 3-"+error.getMessage());
		}
	}

	public void crearRankingNuevo(Connection conexion, String nombre, int puntos)
	{
		Statement statement = null;
		String sentencia="INSERT INTO jugadores VALUES (null, '" + nombre + "','" + puntos + "')";
		try
		{
			// Crear sentencia
			statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println(sentencia);
		}
		catch (SQLException e)
		{
			System.out.println("Error 4-"+e.getMessage());
		}
	}
	// RANKING
	public String consultaRanking(Connection conexion)
	{
		String datos = "";
		Statement statement = null;
		ResultSet rs = null;
		String sentencia = "SELECT * FROM jugadores ORDER BY puntosJugador DESC LIMIT 10;";
		try
		{
			//Crear una sentencia
			statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos = datos + rs.getInt("idJugador") + "\t" + "\t";
				datos = datos + rs.getString("nombreJugador")+ "\t";
				datos = datos + rs.getString("puntosJugador") + "\n";
			}
		}
		catch (SQLException error)
		{
			System.out.println("Error 4-"+error.getMessage());
		}
		return (datos);
	}

	// GENERA PALABRA RANDOM
	public String getPalabraSecreta()
	{
		Random rnd = new Random();
		// SACO PALABRA ALEATORIA
		String palabras[]= {"GATO","TIGRE", "SERPIENTE","FANTASMA","TERMO","SAPO","ACTOR","ROPA","ORDENADOR","CALAVERA"};
		int palabrasAleatorias = rnd.nextInt(palabras.length);
		return palabras[palabrasAleatorias];
	}

	// GENERA LOS * DE LA PALABRA
	public String getPalabraGuionesSecreta(String palabra)
	{
		String palabraGuiones = new String ();

		for(int i=0; i<palabra.length();i++)
		{
			palabraGuiones = palabraGuiones + "*";
		}
		return palabraGuiones;
	}

	// CUENTA LAS LETRAS DE LA PALABRA
	public int getContarLetrasPalabras(String palabra)
	{
		int contarLetras = palabra.length();
		return (contarLetras);
	}

	// COMPROBAR SI 
	boolean hasGanado(String guiones) 
	{
		if(guiones.contains("*"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// ARCHIVO AYUDA
	public void Ayuda()
	{
		try
		{
			Runtime.getRuntime().exec("hh.exe ayuda.chm");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
