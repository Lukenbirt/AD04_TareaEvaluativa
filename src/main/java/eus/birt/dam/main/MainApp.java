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

public class MainApp extends JFrame implements ActionListener {

	public static void main(String[] args) {           
		MainApp gui = new MainApp();  		
	}

	// ATRIBUTOS
	private JFrame ventana;
    private String[] menus = {"    CONDUCTORES    ", "    VIAJES    ", "    RESERVAS    ",  "    PASAJEROS    "};
    private String[] submenusConductores = {"Crear Conductor"};
    private String[] submenusViajes = {"Crear Viaje", "Buscar Viajes Disponibles", "Listar Viajes"};
    private String[] submenusReservas = {"Crear Reserva", "Cancelar Reserva"};
    private String[] submenusPasajeros = {"Crear Pasajero"};
    private Font f = new Font("Book Antiqua", Font.BOLD, 18);
    private Font ff = new Font("Book Antiqua", Font.BOLD, 16);    
          
    // CONSTRUCTOR
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

   // MÉTODOS
    
   // Crea la barra de menú
   private void añadirComponentes() {      
      JMenuBar barra = new JMenuBar();
      barra.setBackground(new Color(218,150,33));
      barra.setBorder(BorderFactory.createLineBorder(new Color(149,102,19), 3));
      
      for(int i = 0; i < menus.length; i++) {
         JMenu unMenu = new JMenu(menus[i]);
         unMenu.setFont(f);
         barra.add(unMenu);
         
         // submenus conductores
         if(i == 0) {
                  
            for(int j = 0; j < submenusConductores.length; j++) {
            JMenuItem unSubmenu = new JMenuItem(submenusConductores[j]);
            unSubmenu.setFont(ff);
            unMenu.add(unSubmenu);
            unSubmenu.addActionListener(this);
            }
                        
         }
         
         // submenus viajes
         if(i == 1) {
             
             for(int j = 0; j < submenusViajes.length; j++) {
             JMenuItem unSubmenu = new JMenuItem(submenusViajes[j]);
             unSubmenu.setFont(ff);
             unMenu.add(unSubmenu);
             unSubmenu.addActionListener(this);
             }
                         
          }
         
         // submenus reservas
         if(i == 2) {
             
             for(int j = 0; j < submenusReservas.length; j++) {
             JMenuItem unSubmenu = new JMenuItem(submenusReservas[j]);
             unSubmenu.setFont(ff);
             unMenu.add(unSubmenu);
             unSubmenu.addActionListener(this);
             }
                         
          }
         
         // submenus pasajeros
         if(i == 3) {
             
             for(int j = 0; j < submenusPasajeros.length; j++) {
             JMenuItem unSubmenu = new JMenuItem(submenusPasajeros[j]);
             unSubmenu.setFont(ff);
             unMenu.add(unSubmenu);
             unSubmenu.addActionListener(this);
             }
                         
          }
         
      }
            
      ventana.setJMenuBar(barra);
   }
   
   // Método ActionPerformed
   @Override
   public void actionPerformed(ActionEvent event) {
	   JMenuItem botonPulsado = (JMenuItem) event.getSource();
	   String texto = botonPulsado.getText();
      
	   if (texto.equals("Crear Conductor")) {
		   /*ConductorService cs = new ConductorService (ventana, false);
			cs.setVisible(true);
			return;*/
			
      } else if (texto.equals("Crear Viaje")) {
			
      } else if (texto.equals("Buscar Viajes Disponibles")) {
			        
      } else if (texto.equals("Crear Pasajero")) {
			
      } else if (texto.equals("Crear Reserva")) {
			
      } else if (texto.equals("Cancelar Reserva")) {
			
      } else if (texto.equals("Listar Viajes")) {

      }
   }
    
}
   
