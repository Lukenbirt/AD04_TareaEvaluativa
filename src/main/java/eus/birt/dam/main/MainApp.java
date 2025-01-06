package eus.birt.dam.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import eus.birt.dam.service.ConductorService;
import eus.birt.dam.service.ViajeService;

public class MainApp extends JFrame implements ActionListener {

	public static void main(String[] args) {           
		MainApp gui = new MainApp();  		
	}

	// Atributos
	private JFrame ventana;
    private String[] menus = {"    CONDUCTORES    ", "    VIAJES    ", "    RESERVAS    ",  "    PASAJEROS    "};
    private String[] submenus = {"Conductores", "Viajes", "Reservas",  "Pasajeros"};
    private Font f = new Font("Book Antiqua", Font.BOLD, 18);
    private Font ff = new Font("Book Antiqua", Font.BOLD, 16);    
          
    // Constructor
    public MainApp() {
    	this.ventana = new JFrame();
    	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	ventana.setTitle("AGENCIA DE VIAJES");
    	ventana.setLayout(new BorderLayout());
    	añadirComponentes();
    	ventana.pack();
    	ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);   // inicia la ventana a pantalla completa
    	ventana.getContentPane().setBackground(new Color(79,152,115));
    	ventana.setVisible(true);
    }

    // Métodos
    
    // crea la barra de menú
    private void añadirComponentes() {      
    	JMenuBar barra = new JMenuBar();
    	barra.setBackground(new Color(218,150,33));
    	barra.setBorder(BorderFactory.createLineBorder(new Color(149,102,19), 3));
      
    	for(int i = 0; i < menus.length; i++) {
    		JMenu unMenu = new JMenu(menus[i]);
    		unMenu.setFont(f);
    		barra.add(unMenu);
    		JMenuItem unSubmenu = new JMenuItem(submenus[i]);
    		unSubmenu.setFont(ff);
    		unMenu.add(unSubmenu);
    		unSubmenu.addActionListener(this);
    	}
            
    	ventana.setJMenuBar(barra);
    }
   
    // Método ActionPerformed
    @Override
    public void actionPerformed(ActionEvent event) {
    	JMenuItem botonPulsado = (JMenuItem) event.getSource();
    	String texto = botonPulsado.getText();
      
    	// abre la ventana de conductores
    	if (texto.equals("Conductores")) {
    		ConductorService cs = new ConductorService (ventana, false);
    		cs.setVisible(true);
    		return;
		
    	// abre la ventana de viajes
    	} else if (texto.equals("Viajes")) {
    		ViajeService vs = new ViajeService (ventana, false);
    		vs.setVisible(true);
    		return;
    	
    	// abre la ventana de reservas
    	} else if (texto.equals("Reservas")) {
		
    	// abre la ventana de pasajeros
    	} else if (texto.equals("Pasajeros")) {
			
    	}
    }    
}
   
