package es.studium.PruebaElAhorcadoReal;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;

public class VistaJugador extends Frame
{
	private static final long serialVersionUID = 1L;
	// Nueva Partida
	Label lblNombreJugador = new Label("Introduce tu nombre:");
	TextField txtNombreJugador = new TextField(25);
	Button btnJugar = new Button("Jugar");
	Button btnAtras = new Button("Atrás");
	
	Dialog dlgError = new Dialog(this,"Error",true);
	Label lblError = new Label("Introduce nombre del jugador");
	// HERRAMIENTA IMAGEN
	Image imagenFondo;
	Toolkit herramienta;

	public VistaJugador()
	{
		herramienta = getToolkit();
		imagenFondo = herramienta.getImage("./recursos/ahorcado1.jpg");
		setLayout(new FlowLayout());
		setTitle("El Ahorcado");
		add(lblNombreJugador);
		add(txtNombreJugador);
		add(btnJugar);
		add(btnAtras);
		setSize(250, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
		
		dlgError.setLayout(new FlowLayout());
		dlgError.add(lblError);
		dlgError.setSize(220,100);
		dlgError.setResizable(false);
		dlgError.setLocationRelativeTo(null);
		dlgError.setVisible(false);
	}
	public void paint(Graphics g)
	{
		//Dibujar la imagen
		g.drawImage(imagenFondo,20,50,this);
	}
}
