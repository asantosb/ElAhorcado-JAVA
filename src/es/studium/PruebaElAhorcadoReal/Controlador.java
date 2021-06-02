package es.studium.PruebaElAhorcadoReal;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;


public class Controlador implements WindowListener, ActionListener, MouseListener
{
	VistaMenu vistaMenu;
	VistaJugador vistaJugador;
	VistaPartida vistaPartida;
	VistaResolver vistaResolver;
	VistaRanking vistaRanking;
	Modelo modelo;

	String palabraSecreta;
	String palabraSecretaGuiones;
	int cuentaLetraPalabra = 0;
	int letrasPulsadas[]= new int [26];
	int intentos = 0;
	int puntuacion = 0;
	String auxPalabraGuiones = new String();
	Connection conexion = null;
	public Controlador(VistaMenu vM , Modelo m)
	{
		this.vistaMenu = vM;
		vistaJugador = new VistaJugador();
		vistaPartida = new VistaPartida(palabraSecreta);
		vistaResolver = new VistaResolver();
		vistaRanking = new VistaRanking();
		this.modelo = m;
		// ACTIVA LOS LISTENER
		this.vistaMenu.addWindowListener(this);
		this.vistaMenu.btnNuevaPartida.addActionListener(this);
		this.vistaMenu.btnRanking.addActionListener(this);
		this.vistaMenu.btnAyuda.addActionListener(this);
		this.vistaMenu.btnSalir.addActionListener(this);
		this.vistaJugador.addWindowListener(this);
		this.vistaJugador.dlgError.addWindowListener(this);
		this.vistaJugador.btnJugar.addActionListener(this);
		this.vistaJugador.btnAtras.addActionListener(this);
		this.vistaPartida.addWindowListener(this);
		this.vistaPartida.dlgHasGanado.addWindowListener(this);
		this.vistaPartida.dlgHasPerdido.addWindowListener(this);
		this.vistaPartida.addMouseListener(this);
		this.vistaPartida.mniAyuda.addActionListener(this);
		this.vistaResolver.addWindowListener(this);
		this.vistaResolver.btnResolver.addActionListener(this);
		this.vistaResolver.btnAtras.addActionListener(this);
		this.vistaRanking.addWindowListener(this);
		this.vistaRanking.btnAtras.addActionListener(this);
	}

	public void actionPerformed(ActionEvent botonPulsado)
	{
		if(this.vistaMenu.btnSalir.equals(botonPulsado.getSource()))
		{
			this.modelo.Sonido();
			System.exit(0);
		}
		else if(this.vistaMenu.btnNuevaPartida.equals(botonPulsado.getSource()))
		{
			this.modelo.Sonido();
			this.vistaJugador.setVisible(true);
		}
		else if(this.vistaJugador.btnJugar.equals(botonPulsado.getSource()))
		{
			this.modelo.Sonido();
			String texto=this.vistaJugador.txtNombreJugador.getText();
			texto=texto.replaceAll(" ", "");
			if(texto.length()==0){
				this.vistaJugador.dlgError.setVisible(true);   
			}
			else
			{
				// Guardamos en palabraSecreta la función para generar la palabra secreta
				palabraSecreta = this.modelo.getPalabraSecreta();
				// Guardamos en cuentaLetrasPalabra la función para contar las letras
				cuentaLetraPalabra = this.modelo.getContarLetrasPalabras(palabraSecreta);
				// Guardamos en palabraSecretaGuiones la funcion de crear los * de la palabra metiendole palabraSecreta
				palabraSecretaGuiones = this.modelo.getPalabraGuionesSecreta(palabraSecreta);
				new VistaPartida(palabraSecretaGuiones);
				this.vistaPartida.setVisible(true);
				// Métemos palabraSecreta y palabraSecretaGuiones en los métodos de la vista para el repaint
				this.vistaPartida.cuentaLetrasPalabra(palabraSecreta);
				this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			}
		}
		else if(this.vistaJugador.btnAtras.equals(botonPulsado.getSource())) 
		{
			this.modelo.Sonido();
			this.vistaJugador.txtNombreJugador.selectAll();
			this.vistaJugador.txtNombreJugador.setText("");
			this.vistaJugador.setVisible(false);
		}
		else if(this.vistaResolver.btnResolver.equals(botonPulsado.getSource()))
		{
			if(this.vistaResolver.txtResolver.getText().equals(palabraSecreta))
			{
				puntuacion++;
				this.vistaPartida.dlgHasGanado.setLayout(new FlowLayout());
				this.vistaPartida.dlgHasGanado.add(this.vistaPartida.lblMensajeHasGanado);
				Connection conexion = this.modelo.conectar();
				this.modelo.crearRankingNuevo(conexion, this.vistaJugador.txtNombreJugador.getText(), puntuacion);
				this.modelo.cerrar(conexion);
				this.vistaPartida.dlgHasGanado.setSize(220,100);
				this.vistaPartida.dlgHasGanado.setBackground(Color.GREEN);
				this.vistaPartida.lblMensajeHasGanado.setForeground(Color.WHITE);
				this.vistaPartida.lblMensajeHasGanado.setFont(new Font("Consolas", Font.BOLD, 20));
				this.vistaPartida.lblMensajeHasGanado.setText("¡¡Has ganado "+this.vistaJugador.txtNombreJugador.getText()+"!!");
				this.vistaPartida.dlgHasGanado.setResizable(false);
				this.vistaPartida.dlgHasGanado.setLocationRelativeTo(null);
				this.vistaPartida.dlgHasGanado.setVisible(true);
			}
			else
			{
				puntuacion--;
				this.vistaPartida.dlgHasPerdido.setLayout(new FlowLayout());
				this.vistaPartida.dlgHasPerdido.add(this.vistaPartida.lblMensajeHasPerdido);
				Connection conexion = this.modelo.conectar();
				this.modelo.crearRankingNuevo(conexion, this.vistaJugador.txtNombreJugador.getText(), puntuacion);
				this.modelo.cerrar(conexion);
				this.vistaPartida.dlgHasPerdido.setSize(220,100);
				this.vistaPartida.dlgHasPerdido.setBackground(Color.RED);
				this.vistaPartida.lblMensajeHasPerdido.setForeground(Color.YELLOW);
				this.vistaPartida.lblMensajeHasPerdido.setFont(new Font("Consolas", Font.BOLD, 25));
				this.vistaPartida.dlgHasPerdido.setResizable(false);
				this.vistaPartida.dlgHasPerdido.setLocationRelativeTo(null);
				this.vistaPartida.dlgHasPerdido.setVisible(true);
			}
		}
		else if(this.vistaResolver.btnAtras.equals(botonPulsado.getSource()))
		{
			this.vistaResolver.setVisible(false);
		}
		else if(this.vistaMenu.btnRanking.equals(botonPulsado.getSource()))
		{
			this.modelo.Sonido();
			this.vistaRanking.setVisible(true);
			// Conectar BD
			conexion = this.modelo.conectar();
			// Realizar consulta, y sacar información
			String informacion = "";
			this.vistaRanking.listadoRanking.selectAll();
			this.vistaRanking.listadoRanking.setText("");
			this.vistaRanking.listadoRanking.append("Id Jugador\tJugador\tPuntos\n");
			informacion = this.modelo.consultaRanking(conexion);
			// Rellenaremos TextArea
			this.vistaRanking.listadoRanking.append(informacion);
			// Cerrar la conexión
			this.modelo.cerrar(conexion);
		}
		else if(this.vistaRanking.btnAtras.equals(botonPulsado.getSource())) 
		{
			this.vistaRanking.setVisible(false);
		}
		else if(this.vistaMenu.btnAyuda.equals(botonPulsado.getSource()))
		{
			this.modelo.Sonido();
			this.modelo.Ayuda();
		}
		else if(this.vistaPartida.mniAyuda.equals(botonPulsado.getSource())) 
		{
			this.modelo.Ayuda();
		}
	}

	public void windowClosing(WindowEvent botonPulsado)
	{
		if(this.vistaJugador.dlgError.isActive())
		{
			this.vistaJugador.dlgError.setVisible(false);
		}
		else
		{
			System.exit(0);
		}
	}

	public void mouseClicked(MouseEvent evento)
	{
		int x = evento.getX();
		int y = evento.getY();
		boolean hasAcertado = false;
		boolean juegoTerminado = false;
		if((x>=277)&&(x<=292)&&(y>=200)&&(y<=222)&&(letrasPulsadas[0]==0))
		{	
			auxPalabraGuiones = "";
			char letraA = 'A';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraA)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraA;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[0]=1;
		}
		else if((x>=310)&&(x<=323)&&(y>=200)&&(y<=223)&&(letrasPulsadas[1]==0))
		{
			auxPalabraGuiones = "";
			char letraB = 'B';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraB)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraB;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[1]=1;
		}
		else if((x>=342)&&(x<=358)&&(y>=200)&&(y<=223)&&(letrasPulsadas[2]==0))
		{
			auxPalabraGuiones = "";
			char letraC = 'C';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraC)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraC;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[2]=1;
		}
		else if((x>=374)&&(x<=389)&&(y>=200)&&(y<=223)&&(letrasPulsadas[3]==0))
		{
			auxPalabraGuiones = "";
			char letraD = 'D';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraD)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraD;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[3]=1;
		}
		else if((x>=382)&&(x<=423)&&(y>=200)&&(y<=223)&&(letrasPulsadas[4]==0))
		{
			auxPalabraGuiones = "";
			char letraE = 'E';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraE)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraE;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[4]=1;
		}
		else if((x>=265)&&(x<=279)&&(y>=233)&&(y<=259)&&(letrasPulsadas[5]==0))
		{
			auxPalabraGuiones = "";
			char letraF = 'F';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraF)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraF;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[5]=1;
		}
		else if((x>=275)&&(x<=315)&&(y>=233)&&(y<=259)&&(letrasPulsadas[6]==0))
		{
			auxPalabraGuiones = "";
			char letraG = 'G';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraG)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraG;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[6]=1;
		}
		else if((x>=330)&&(x<=346)&&(y>=233)&&(y<=259)&&(letrasPulsadas[7]==0))
		{
			auxPalabraGuiones = "";
			char letraH = 'H';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraH)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraH;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[7]=1;
		}
		else if((x>=367)&&(x<=376)&&(y>=233)&&(y<=259)&&(letrasPulsadas[8]==0))
		{
			auxPalabraGuiones = "";
			char letraI = 'I';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraI)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraI;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[8]=1;
		}
		else if((x>=392)&&(x<=403)&&(y>=233)&&(y<=259)&&(letrasPulsadas[9]==0))
		{
			auxPalabraGuiones = "";
			char letraJ = 'J';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraJ)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraJ;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[9]=1;
		}
		else if((x>=392)&&(x<=404)&&(y>=232)&&(y<=259)&&(letrasPulsadas[10]==0))
		{
			auxPalabraGuiones = "";
			char letraK = 'K';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraK)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraK;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[10]=1;
		}
		else if((x>=274)&&(x<=286)&&(y>=236)&&(y<=295)&&(letrasPulsadas[11]==0))
		{
			auxPalabraGuiones = "";
			char letraL = 'L';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraL)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraL;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[11]=1;
		}
		else if((x>=297)&&(x<=322)&&(y>=236)&&(y<=295)&&(letrasPulsadas[12]==0))
		{
			auxPalabraGuiones = "";
			char letraM = 'M';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraM)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraM;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[12]=1;
		}
		else if((x>=337)&&(x<=359)&&(y>=236)&&(y<=295)&&(letrasPulsadas[13]==0))
		{
			auxPalabraGuiones = "";
			char letraN = 'N';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraN)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraN;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[13]=1;
		}
		else if((x>=375)&&(x<=396)&&(y>=236)&&(y<=295)&&(letrasPulsadas[14]==0))
		{
			auxPalabraGuiones = "";
			char letraO = 'O';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraO)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraO;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[14]=1;
		}
		else if((x>=418)&&(x<=437)&&(y>=270)&&(y<=294)&&(letrasPulsadas[15]==0))
		{
			auxPalabraGuiones = "";
			char letraP = 'P';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraP)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraP;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[15]=1;
		}
		else if((x>=266)&&(x<=292)&&(y>=304)&&(y<=330)&&(letrasPulsadas[16]==0))
		{
			auxPalabraGuiones = "";
			char letraQ = 'Q';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraQ)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraQ;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[16]=1;
		}
		else if((x>=305)&&(x<=325)&&(y>=304)&&(y<=330)&&(letrasPulsadas[17]==0))
		{
			auxPalabraGuiones = "";
			char letraR = 'R';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraR)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraR;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[17]=1;
		}
		else if((x>=338)&&(x<=359)&&(y>=304)&&(y<=330)&&(letrasPulsadas[18]==0))
		{
			auxPalabraGuiones = "";
			char letraS = 'S';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraS)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraS;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[18]=1;
		}
		else if((x>=373)&&(x<=397)&&(y>=303)&&(y<=330)&&(letrasPulsadas[19]==0))
		{
			auxPalabraGuiones = "";
			char letraT = 'T';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraT)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraT;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[19]=1;
		}
		else if((x>=410)&&(x<=433)&&(y>=303)&&(y<=330)&&(letrasPulsadas[20]==0))
		{
			auxPalabraGuiones = "";
			char letraU = 'U';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraU)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraU;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[20]=1;
		}
		else if((x>=258)&&(x<=284)&&(y>=338)&&(y<=365)&&(letrasPulsadas[21]==0))
		{
			auxPalabraGuiones = "";
			char letraV = 'V';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraV)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraV;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[21]=1;
		}
		else if((x>=301)&&(x<=327)&&(y>=338)&&(y<=365)&&(letrasPulsadas[22]==0))
		{
			auxPalabraGuiones = "";
			char letraW = 'W';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraW)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraW;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[22]=1;
		}
		else if((x>=342)&&(x<=367)&&(y>=338)&&(y<=365)&&(letrasPulsadas[23]==0))
		{
			auxPalabraGuiones = "";
			char letraX = 'X';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraX)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraX;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[23]=1;
		}
		else if((x>=355)&&(x<=399)&&(y>=338)&&(y<=365)&&(letrasPulsadas[24]==0))
		{
			auxPalabraGuiones = "";
			char letraY = 'Y';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraY)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraY;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[24]=1;
		}
		else if((x>=415)&&(x<=440)&&(y>=338)&&(y<=365)&&(letrasPulsadas[25]==0))
		{
			auxPalabraGuiones = "";
			char letraZ = 'Z';
			for(int i=0;i<palabraSecreta.length();i++)
			{
				if(palabraSecreta.charAt(i) == letraZ)
				{
					auxPalabraGuiones = auxPalabraGuiones + letraZ;
					hasAcertado = true;
					System.out.println("Has acertado");
					puntuacion++;
				}
				else if(palabraSecretaGuiones.charAt(i) != '*')
				{
					auxPalabraGuiones = auxPalabraGuiones + palabraSecretaGuiones.charAt(i);
				}
				else
				{
					auxPalabraGuiones = auxPalabraGuiones + "*";
				}
			}
			palabraSecretaGuiones = auxPalabraGuiones;
			this.vistaPartida.actualizarPalabraSecreta(palabraSecretaGuiones);
			letrasPulsadas[25]=1;
		}
		if((x>=482)&&(x<=555)&&(y>=258)&&(y<=286))
		{
			hasAcertado = true;
			this.vistaResolver.setVisible(true);
		}
		if(!hasAcertado)
		{
			System.out.println("No has acertado ninguna letra");
			intentos++;
			puntuacion--;
			this.vistaPartida.actualizarSoporte(intentos);
			if(intentos==6)
			{
				puntuacion--;
				conexion = this.modelo.conectar();
				this.modelo.crearRankingNuevo(conexion, this.vistaJugador.txtNombreJugador.getText(), puntuacion);
				this.modelo.cerrar(conexion);
				juegoTerminado = true;
				this.vistaPartida.dlgHasPerdido.setLayout(new FlowLayout());
				this.vistaPartida.dlgHasPerdido.add(this.vistaPartida.lblMensajeHasPerdido);
				this.vistaPartida.dlgHasPerdido.setSize(300,100);
				this.vistaPartida.dlgHasPerdido.setBackground(Color.RED);
				this.vistaPartida.lblMensajeHasPerdido.setForeground(Color.YELLOW);
				this.vistaPartida.lblMensajeHasPerdido.setFont(new Font("Consolas", Font.BOLD, 25));
				this.vistaPartida.dlgHasPerdido.setResizable(false);
				this.vistaPartida.dlgHasPerdido.setLocationRelativeTo(null);
				this.vistaPartida.dlgHasPerdido.setVisible(true);
			}
		}
		else
		{
			boolean juegoGanado = this.modelo.hasGanado(palabraSecretaGuiones);
			if(juegoGanado)
			{
				puntuacion++;
				Connection conexion = this.modelo.conectar();
				this.modelo.crearRankingNuevo(conexion, this.vistaJugador.txtNombreJugador.getText(), puntuacion);
				this.modelo.cerrar(conexion);
				juegoTerminado = true;
				this.vistaPartida.dlgHasGanado.setLayout(new FlowLayout());
				this.vistaPartida.dlgHasGanado.add(this.vistaPartida.lblMensajeHasGanado);
				this.vistaPartida.dlgHasGanado.setSize(350,100);
				this.vistaPartida.dlgHasGanado.setBackground(Color.GREEN);
				this.vistaPartida.lblMensajeHasGanado.setForeground(Color.WHITE);
				this.vistaPartida.lblMensajeHasGanado.setFont(new Font("Consolas", Font.BOLD, 20));
				this.vistaPartida.lblMensajeHasGanado.setText("¡Has ganado "+this.vistaJugador.txtNombreJugador.getText()+"!");
				this.vistaPartida.dlgHasGanado.setResizable(false);
				this.vistaPartida.dlgHasGanado.setLocationRelativeTo(null);
				this.vistaPartida.dlgHasGanado.setVisible(true);
			}
		}
		// Ver coordenadas
		// System.out.println("x:"+x+", y:"+y);
	}


	public void mouseEntered(MouseEvent arg0)
	{}
	public void mouseExited(MouseEvent arg0)
	{}
	public void mousePressed(MouseEvent arg0)
	{}
	public void mouseReleased(MouseEvent arg0)
	{}
	public void windowActivated(WindowEvent arg0)
	{}
	public void windowClosed(WindowEvent arg0)
	{}
	public void windowDeactivated(WindowEvent arg0)
	{}
	public void windowDeiconified(WindowEvent arg0)
	{}
	public void windowIconified(WindowEvent arg0)
	{}
	public void windowOpened(WindowEvent arg0)
	{}
}