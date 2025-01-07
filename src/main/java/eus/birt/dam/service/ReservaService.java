package eus.birt.dam.service;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import eus.birt.dam.domain.Conductor;
import eus.birt.dam.domain.Pasajero;
import eus.birt.dam.domain.Reserva;
import eus.birt.dam.domain.Viaje;

public class ReservaService extends JDialog implements ActionListener{
	
	// Atributos
	private JTextField fechaReserva;
	private JTextField numeroPlazas;
	private JTextField pasajero;
	private JTextField viaje;
	private JButton boton1;
	private JButton boton2;
	private JButton boton3;
	private Font f = new Font("Book Antiqua", Font.BOLD, 15);
	private Font ff = new Font("Dialog", Font.PLAIN, 13);
	private List <Reserva> reservas;
	private JTable reservaTable;
    private DefaultTableModel tableModel;

	// Constructor
	public ReservaService(Frame ventana, boolean modal) {
		super(ventana, false);
	    setLayout(new BorderLayout());

	    // calcula la resolución de la pantalla actual     
	    int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    
	    // situa la ventana
	    setBounds((ancho / 3) - (this.getWidth() / 5), (alto / 6) - (this.getHeight() / 13), 630, 680);
	    setTitle("RESERVAS");
	    
	    // crea la interfaz
	    añadirComponentes();
	}

	// Métodos
	
	// Añade los componentes a la interfaz
    private void añadirComponentes() {
    	JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		pan.setBackground(new Color(188,214,190));
		// para colocar y dar tamaño a cada celda de la rejilla
		GridBagConstraints c = new GridBagConstraints();
		   
		// fecha de reserva
		JLabel fec = new JLabel("Fecha (YYYY-MM-DD):");       
		fec.setFont(f);
		c.insets = new Insets(7,5,7,5);        // espacio entre filas y columnas
		c.gridx = 1;                           // nº de columna
		c.gridy = 1;                           // nº de fila
		c.anchor = GridBagConstraints.WEST;    // donde se coloca el componente dentro de la celda
		pan.add(fec, c);     
		fechaReserva = new JTextField(15);
		fechaReserva.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 1;                         
		c.anchor = GridBagConstraints.WEST;
		fechaReserva.setEditable(true);
		fechaReserva.setBackground(new Color(241,246,94));
		pan.add(fechaReserva, c);
		
		// plazas reservadas
		JLabel npr = new JLabel("Número de plazas reservadas:");         
		npr.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		pan.add(npr, c);     
		numeroPlazas = new JTextField(10);
		numeroPlazas.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 2;                         
		c.anchor = GridBagConstraints.WEST;
		numeroPlazas.setEditable(true);
		numeroPlazas.setBackground(new Color(241,246,94));
		pan.add(numeroPlazas, c);

		// pasajero
		JLabel pasa = new JLabel("Código del pasajero:");         
		pasa.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		pan.add(pasa, c);     
		pasajero = new JTextField(10);
		pasajero.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 3;                         
		c.anchor = GridBagConstraints.WEST;
		pasajero.setEditable(true);
		pasajero.setBackground(new Color(241,246,94));
		pan.add(pasajero, c);
		
		// viaje
		JLabel vi = new JLabel("Código del viaje:");         
		vi.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		pan.add(vi, c);     
		viaje = new JTextField(10);
		viaje.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 4;                         
		c.anchor = GridBagConstraints.WEST;
		viaje.setEditable(true);
		viaje.setBackground(new Color(241,246,94));
		pan.add(viaje, c);		
		
		// botones
		JPanel pan2 = new JPanel();
	    pan2.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
		pan2.setBackground(new Color(188,214,190));
		boton1 = new JButton();
		boton1.setText("Crear Reserva");
		boton1.setFont(f);
		boton1.setBackground(new Color(218,150,33));
		boton1.addActionListener(this);
		pan2.add(boton1);   
		boton2 = new JButton();
		boton2.setText("Cancelar Reserva");
		boton2.setFont(f);
		boton2.setBackground(new Color(218,150,33));
		boton2.addActionListener(this);
		pan2.add(boton2); 
		boton3 = new JButton();
		boton3.setText("Listar Reservas");
		boton3.setFont(f);
		boton3.setBackground(new Color(218,150,33));
		boton3.addActionListener(this);
		pan2.add(boton3);
		
		// tabla
	    String[] columnNames = {"Id", "Fecha", "Plazas", "Pasajero", "Viaje"};
	    tableModel = new DefaultTableModel(columnNames, 0);
	    reservaTable = new JTable(tableModel);
	    
	    // crea un renderizador centrado
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    
	    // aplica el renderizador a todas las columnas
	    for (int i = 0; i < reservaTable.getColumnModel().getColumnCount(); i++) {
	        reservaTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

	    // configuración del ancho de las columnas
	    int[] columnWidths = {20, 60, 20, 200, 150};
	    for (int i = 0; i < columnWidths.length; i++) {
	        reservaTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
	    }
	    
	    JScrollPane scrollPane = new JScrollPane(reservaTable);
		   
		// añado los paneles al Layout
		add(pan, BorderLayout.NORTH);
		add(pan2, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.SOUTH);
	}

	// Método ActionPerformed
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton botonPulsado = (JButton) event.getSource();
	    String texto = botonPulsado.getText();
	      
	    // crea un viaje
	    if (texto.equals("Crear Reserva")) {
	    	
	    	// crea sessionFactory y session
	    	StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
	    	.configure("hibernate.cfg.xml")
	  		.build();
	  		
	  		Metadata m = new MetadataSources(ssr)
	  		.addAnnotatedClass(Viaje.class)
	  		.addAnnotatedClass(Conductor.class)
	  		.addAnnotatedClass(Pasajero.class)
	  		.addAnnotatedClass(Reserva.class)
	  		.getMetadataBuilder()
	  		.build();
	  		
	  		SessionFactory sf = m.getSessionFactoryBuilder().build();
	  		
	  		Session s = sf.openSession();
	  		
	  		try {
	  			String fec = fechaReserva.getText();
	  			String pla = numeroPlazas.getText();
	  			String pas = pasajero.getText();
	  			String via = viaje.getText();
	  			
	  			 // convertimos el string a LocalDate
	  	        LocalDate fecha = LocalDate.parse(fec);
	  	        
	  	        // convertimos a int las plazas reservadas, y los id de pasajero y viaje
	  	        int plazasRes = Integer.parseInt(pla);
	  	        int id_pa = Integer.parseInt(pas);
	  			int id_vi = Integer.parseInt(via);

	  			// comienza la transacción
	  			s.beginTransaction();
	  			
	  	        // buscamos los objetos pasajero y viaje con los id aportados por el usuario
	  			Pasajero pasaje = s.get(Pasajero.class, id_pa);
	  			Viaje viaj = s.get(Viaje.class, id_vi);
	  	        
	  	        // creamos el objeto Reserva
	  	        Reserva tempReserva = new Reserva(fecha, plazasRes, pasaje, viaj);
	  	        
	  	        // comprobar que hay plazas disponibles en el viaje seleccionado
	  	        int num = viaj.getPlazasDisponibles();
	  	        int calculo = num - plazasRes;
	  	        
	  	        // si hay plazas disponibles
	  	        if (calculo > -1) {
	  				// guarda el objeto Reserva
	  				s.save(tempReserva);
	  				
	  				// actualiza las plazas disponibles en el viaje
	  				viaj.setPlazasDisponibles(calculo);
	  				
	  				// hace commit de la transacción
	  				s.getTransaction().commit();
	  			  
	  				// refresh para hacer una select
	  				s.refresh(tempReserva);
	  				reservas = s.createQuery("from Reserva").getResultList();
	  			  
	  				// limpia el modelo actual de la tabla para evitar duplicados
	  				tableModel.setRowCount(0);

	  				// añade cada reserva al modelo de la tabla
	  				for (Reserva res : reservas) {
	  					Object[] rowData = {res.getId(), res.getFechaReserva(), res.getNumeroPlazasReservadas(), res.getPasajero().getNombre(), res.getViaje().getCiudadDestino()};
	  					tableModel.addRow(rowData);
	  				}

	  				// actualiza la tabla
	  				reservaTable.repaint();
	  			  
	  				// se dejan en blanco los campos
	  				fechaReserva.setText("");
	  				numeroPlazas.setText("");
	  				pasajero.setText("");
	  				viaje.setText("");
	  			  
	  				// muestra mensaje de confirmación
	  				JOptionPane.showMessageDialog(null, "Reserva creada", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			
	  	        } else {
	  			// si no hay plazas disponibles
	  			JOptionPane.showMessageDialog(null, "No se puede crear la reserva.\nViaje sin plazas disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);	
	  	        }
	  	        
	  		} catch (Exception e) {
	  			// rollback ante alguna excepción
	  			s.getTransaction().rollback();
	  			JOptionPane.showMessageDialog(null, "No se ha podido crear la reserva", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			e.printStackTrace();			
	  		} finally {
	  			s.close();
	  			sf.close();
	  		}
	        
	  		return;

	    // saca un listado con todos los viajes
	    } else if (texto.equals("Cancelar Reserva")) {
	    	
    	// saca un listado con todos los viajes
    	} else if (texto.equals("Listar Reservas")) {
    		
	    	// crea sessionFactory y session
	    	StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
	    	.configure("hibernate.cfg.xml")
	  		.build();
	  		
	  		Metadata m = new MetadataSources(ssr)
	  		.addAnnotatedClass(Viaje.class)
	  		.addAnnotatedClass(Pasajero.class)
	  		.addAnnotatedClass(Reserva.class)
	  		.addAnnotatedClass(Conductor.class)
	  		.getMetadataBuilder()
	  		.build();
	  		
	  		SessionFactory sf = m.getSessionFactoryBuilder().build();
	  		
	  		Session s = sf.openSession();
	  		
	  		try {
	  			// comienza la transacción
	  			s.beginTransaction();
	  			
	  			// se consulta el listado
	  			reservas = s.createQuery("from Reserva").getResultList();
	  			
	  			// limpia el modelo actual de la tabla para evitar duplicados
	  			tableModel.setRowCount(0);

	  			// añade cada reserva al modelo de la tabla
	  			for (Reserva res : reservas) {
	  				Object[] rowData = {res.getId(), res.getFechaReserva(), res.getNumeroPlazasReservadas(), res.getPasajero().getNombre(), res.getViaje().getCiudadDestino()};
	  				tableModel.addRow(rowData);
	  			}

	  			// actualiza la tabla
	  			reservaTable.repaint();
	  		  
	  		} catch (Exception e) {
	  			// rollback ante alguna excepción
	  			s.getTransaction().rollback();
	  			JOptionPane.showMessageDialog(null, "No se ha podido crear la reserva", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			e.printStackTrace();			
	  		} finally {
	  			s.close();
	  			sf.close();
	  		}
	        
	  		return;   
    	}
	}	
}
