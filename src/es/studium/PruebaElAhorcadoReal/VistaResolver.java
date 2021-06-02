package es.studium.PruebaElAhorcadoReal;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;

public class VistaResolver extends Frame
{
	private static final long serialVersionUID = 1L;
	Label lblResolver = new Label("Introduce la palabra:");
	TextField txtResolver = new TextField(25);
	Button btnResolver = new Button("Resolver");
	Button btnAtras = new Button("Atrás");

	// HERRAMIENTA IMAGEN
	Image imagenFondo;
	Toolkit herramienta;
	
	public VistaResolver()
	{
		herramienta = getToolkit();
		imagenFondo = herramienta.getImage("./recursos/ahorcado1.jpg");
		setLayout(new FlowLayout());
		setTitle("El Ahorcado");
		add(lblResolver);
		add(txtResolver);
		add(btnResolver);
		add(btnAtras);
		setSize(300, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
	}
	public void paint(Graphics g)
	{
		//Dibujar la imagen
		g.drawImage(imagenFondo,20,50,this);
	}
}