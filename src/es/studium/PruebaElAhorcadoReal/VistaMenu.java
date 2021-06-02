package es.studium.PruebaElAhorcadoReal;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;

public class VistaMenu extends Frame
{
	private static final long serialVersionUID = 1L;
	Label lblElAhorcado = new Label("EL AHORCADO");
	Button btnNuevaPartida = new Button("Nueva Partida");
	Button btnRanking = new Button("Ranking");
	Button btnAyuda = new Button("Ayuda");
	Button btnSalir = new Button("Salir");
	
	// HERRAMIENTA IMAGEN
	Image imagen;
	Toolkit herramienta;

	public VistaMenu()
	{
		herramienta = getToolkit();
		imagen = herramienta.getImage("./recursos/ahorcado1.jpg");
		setLayout(new FlowLayout());
		//lblElAhorcado.setFont(new Font("Texto", 1, 15)); // Tipo de fuente
		//lblElAhorcado.setForeground(Color.pink); // Color
		setTitle("El Ahorcado");
		lblElAhorcado.setForeground(Color.RED);
		lblElAhorcado.setFont(new Font("Arial", Font.BOLD, 22));
		add(lblElAhorcado);
		btnNuevaPartida.setForeground(Color.RED);
		btnNuevaPartida.setFont(new Font("Arial", Font.PLAIN, 12));
		add(btnNuevaPartida);
		btnRanking.setForeground(Color.RED);
		btnRanking.setFont(new Font("Arial", Font.PLAIN, 12));
		add(btnRanking);
		btnAyuda.setForeground(Color.RED);
		btnAyuda.setFont(new Font("Arial", Font.PLAIN, 12));
		add(btnAyuda);
		btnSalir.setForeground(Color.RED);
		btnSalir.setFont(new Font("Arial", Font.PLAIN, 12));
		add(btnSalir);
		setSize(200, 170);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);		
	}
	public void paint(Graphics g)
	{
		//Dibujar la imagen
		g.drawImage(imagen,20,50,this);
	}
}
