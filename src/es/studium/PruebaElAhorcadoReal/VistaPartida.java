package es.studium.PruebaElAhorcadoReal;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;

public class VistaPartida extends Frame
{
	private static final long serialVersionUID = 1L;
	// IMAGENES
	Toolkit herramientas;
	Image letras, soporte, fondoPizarra, boton, error1, error2, error3, error4, error5, error6;
	//MENU
	MenuBar mnBar = new MenuBar();
	Menu menuAyuda = new Menu("Ayuda");
	MenuItem mniAyuda = new MenuItem("Ayuda");

	Dialog dlgHasGanado = new Dialog(this,"¡Ganaste!",true);
	Label lblMensajeHasGanado = new Label("¡Has Ganado!");

	Dialog dlgHasPerdido = new Dialog(this,"¡Perdiste!",true);
	Label lblMensajeHasPerdido = new Label("¡Has Perdido!");

	String palabraSecreta = "";
	int cuentaLetraPalabra = 0;
	int errores = 0;

	public VistaPartida(String p)
	{
		this.palabraSecreta=p;
		herramientas = getToolkit();
		letras = herramientas.getImage("./recursos/letras3.png");
		soporte = herramientas.getImage("./recursos/soporte.png");
		fondoPizarra =  herramientas.getImage("./recursos/fondoPizarra.jpg");
		boton =  herramientas.getImage("./recursos/boton.png");
		error1 = herramientas.getImage("./recursos/error1.png");
		error2 = herramientas.getImage("./recursos/error2.png");
		error3 = herramientas.getImage("./recursos/error3.png");
		error4 = herramientas.getImage("./recursos/error4.png");
		error5 = herramientas.getImage("./recursos/error5.png");
		error6 = herramientas.getImage("./recursos/error6.png");
		mnBar.add(menuAyuda);
		menuAyuda.add(mniAyuda);
		setMenuBar(mnBar);
		this.setTitle("El Ahorcado");
		this.setSize(600,450);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(false);
	}

	public void paint(Graphics g)
	{
		// IMAGEN FONDO
		g.drawImage(fondoPizarra, 1, 5, this);
		// IMAGEN SOPORTE AHORCADO
		g.drawImage(soporte, -350, -100, this);
		// IMAGEN BOTÓN RESOLVER
		g.drawImage(boton, 470, 250, this);
		// DIBUJA TEXTO PALABRA OCULTA
		Font fuente = new Font("Chiller", Font.BOLD, 30);
		g.setFont(fuente);
		g.setColor(Color.WHITE);
		g.drawString("PALABRA OCULTA ", 250, 90);
		// DIBUJA TEXTO DE CONTAR LETRAS
		Font font = new Font("Calibri", Font.BOLD, 14);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("La palabra contiene "+cuentaLetraPalabra+ " letras:", 275, 120);
		// DIBUJA TEXTO DE LA PALABRA SECRETA
		Font font2 = new Font("Calibri", Font.PLAIN, 30);
		g.setFont(font2);
		g.setColor(Color.RED);
		g.drawString(palabraSecreta, 325, 170);
		// IMAGEN PANEL LETRAS
		g.drawImage(letras, 255, 180, this);
		switch(errores)
		{
		case 1:
			g.drawImage(error1, -350, -100, this);
			break;
		case 2:
			g.drawImage(error2, -350, -100, this);
			break;
		case 3:
			g.drawImage(error3,-350, -100, this);
			break;
		case 4:
			g.drawImage(error4, -350, -100, this);
			break;
		case 5:
			g.drawImage(error5, -350, -100, this);
			break;
		case 6:
			g.drawImage(error6, -350, -100, this);
			break;
		}
	}

	public void actualizarPalabraSecreta(String p)
	{
		palabraSecreta = p;
		repaint();
	}

	public void cuentaLetrasPalabra(String p)
	{
		cuentaLetraPalabra = p.length();
		repaint();
	}

	public void actualizarSoporte(int p)
	{
		errores = p;
		repaint();
	}
}