package com.damaya.proyinv.cliente;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jvnet.substance.SubstanceLookAndFeel;
import com.damaya.proyinv.constantes.ConstantesAutenticacion;
import com.damaya.proyinv.constantes.ConstantesPantallaPrincipal;
import com.damaya.proyinv.gui.InterfazGraficaMonitoreoInstrumentosElectronicos;

/**
 * The Class MonitorVisualGotaAplicacion.
 */
public class MonitorVisualInstrumentosElectronicos extends JWindow implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The logger. */
	private static Logger logPrincipal = Logger.getLogger("principal");
	private static JProgressBar barraProgreso = new JProgressBar();
	private static MonitorVisualInstrumentosElectronicos monitorVisualInstrumentosElectronicosAplicacion;
	private int contador;
	private Timer temporizadorPrincipal;
	
	/**
	 * Instantiates a new monitor visual instrumentos electronicos aplicacion.
	 */
	public MonitorVisualInstrumentosElectronicos() {
		super();
		BasicConfigurator.configure();
		Container container = getContentPane();
		container.setLayout(null);
		JPanel panelMonitorVisual = new JPanel();
		panelMonitorVisual.setBorder(new javax.swing.border.EtchedBorder());
		panelMonitorVisual.setBackground(new Color(255, 255, 255));
		panelMonitorVisual.setBounds(10, 10, 348, 150);
		panelMonitorVisual.setLayout(null);
		container.add(panelMonitorVisual);
		JLabel label = new JLabel("Monitor Visual de Instrumentos Electrónicos.");
		label.setFont(new Font("Verdana", Font.BOLD, 14));
		label.setBounds(10, 11, 315, 30);
		panelMonitorVisual.add(label);
		barraProgreso.setMaximum(50);
		barraProgreso.setBounds(55, 180, 250, 15);
		container.add(barraProgreso);
		abrirBarraProgreso();
		setSize(370, 215);
		setLocationRelativeTo(null);
		setVisible(true);
	};
	
	/**
	 * The main method.
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		lanzarPanelAutenticacion();
	}
	
	public static void lanzarPanelAutenticacion() {
		JPanel panelAutenticacion = new JPanel(new GridLayout(0, 1));
		JTextField campoNombreUsuario = new JTextField(15);
		panelAutenticacion.add(new JLabel("Usuario:"));
		panelAutenticacion.add(campoNombreUsuario);
		JPasswordField campoPassword = new JPasswordField(15);
		panelAutenticacion.add(new JLabel("Contraseña:"));
		panelAutenticacion.add(campoPassword);
		int resultado = JOptionPane.showConfirmDialog(null, panelAutenticacion, "Autenticación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (resultado == JOptionPane.OK_OPTION) {
			String nombreUsuario = campoNombreUsuario.getText();
			char[] caracteresPassword = campoPassword.getPassword();
			String clave = new String(caracteresPassword);
			if (nombreUsuario.equals(ConstantesAutenticacion.USUARIO_AUTENTICACION) && clave.equals(ConstantesAutenticacion.CLAVE_AUTENTICACION)) {
				JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
				monitorVisualInstrumentosElectronicosAplicacion = new MonitorVisualInstrumentosElectronicos();
			}
			else {
				JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña no válidos.", "Error Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
			}
			for (int i = 0; i < caracteresPassword.length; i++) {
				caracteresPassword[i] = 0;
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "El inicio de sesión ha sido cancelado.");
		}
	}
	
	public void abrirBarraProgreso() {
		ActionListener oyenteBarraProgreso = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				contador++;
				barraProgreso.setValue(contador);
				if (contador == 50) {
					cargarInterfazGraficaMonitorVisual();
					monitorVisualInstrumentosElectronicosAplicacion.setVisible(false);
					temporizadorPrincipal.stop();
				}
			}
			
			public void cargarInterfazGraficaMonitorVisual() {
				EventQueue.invokeLater(new Runnable() {
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							Dimension d = new Dimension();
							ImageIcon imagenProyecto = new ImageIcon("imagenes/proyecto.png");
							File log4jfile = new File("conf/logMonitorVisual.properties");
							PropertyConfigurator.configure(log4jfile.toString());
							logPrincipal.info(ConstantesPantallaPrincipal.TRAZA_NIVEL_INFORMACION + " " + ConstantesPantallaPrincipal.MENSAJE_INICIO_PANTALLA);
							InterfazGraficaMonitoreoInstrumentosElectronicos interfazMonitoreoGota = new InterfazGraficaMonitoreoInstrumentosElectronicos();
							Set<AWTKeyStroke> teclas = new HashSet<AWTKeyStroke>();
							teclas.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
							teclas.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
							interfazMonitoreoGota.getContentPane().setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclas);
							interfazMonitoreoGota.setVisible(true);
							interfazMonitoreoGota.setResizable(false);
							interfazMonitoreoGota.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
							interfazMonitoreoGota.setIconImage(imagenProyecto.getImage());
							interfazMonitoreoGota.setDefaultLookAndFeelDecorated(true);
							interfazMonitoreoGota.setLocation((int) ((d.getWidth() / 2) + 290), 50);
							interfazMonitoreoGota.setExtendedState(JFrame.ICONIFIED);
							interfazMonitoreoGota.setExtendedState(interfazMonitoreoGota.getExtendedState() | JFrame.ICONIFIED);
							interfazMonitoreoGota.setExtendedState(JFrame.NORMAL);
							interfazMonitoreoGota.setExtendedState(interfazMonitoreoGota.getExtendedState() & ~JFrame.ICONIFIED);
							SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");
							SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.SubstanceAquaTheme");
							SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
						}
						catch (Exception e) {
							logPrincipal.error(e);
						}
					}
				});
			}
		};
		temporizadorPrincipal = new Timer(50, oyenteBarraProgreso);
		temporizadorPrincipal.start();
	}
}