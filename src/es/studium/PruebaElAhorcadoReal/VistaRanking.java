package es.studium.PruebaElAhorcadoReal;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;

public class VistaRanking extends Frame
{
	private static final long serialVersionUID = 1L;
	TextArea listadoRanking = new TextArea(5, 30);
	Button btnAtras = new Button("Atrás");

	// HERRAMIENTA IMAGEN
	Image ranking;
	Toolkit herramienta;

	public VistaRanking()
	{
		herramienta = getToolkit();
		ranking = herramienta.getImage("./recursos/ranking.png");
		setTitle("Top 10 Jugadores");
		setLayout(new FlowLayout());
		listadoRanking.setEditable(false);
		add(listadoRanking);
		add(btnAtras);
		setSize(300,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
	public void paint(Graphics g)
	{
		//Dibujar la imagen
		g.drawImage(ranking,0,0,this);
	}
}