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

import eus.birt.dam.domain.Pasajero;

public class PasajeroService extends JDialog implements ActionListener{
	
	// Atributos
	private JTextField nombre;
	private JTextField apellido;
	private JTextField email;
	private JButton boton1;
	private JButton boton2;
	private Font f = new Font("Book Antiqua", Font.BOLD, 15);
	private Font ff = new Font("Dialog", Font.PLAIN, 13);
	private List <Pasajero> pasajeros;
	private JTable pasajeroTable;
    private DefaultTableModel tableModel;

	// Constructor
	public PasajeroService(Frame ventana, boolean modal) {
		super(ventana, false);
	    setLayout(new BorderLayout());

	    // calcula la resolución de la pantalla actual     
	    int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    
	    // situa la ventana
	    setBounds((ancho / 5) - (this.getWidth() / 5), (alto / 7) - (this.getHeight() / 13), 1030, 648);
	    setTitle("CREAR PASAJERO");
	    
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
		   
		// nombre del pasajero
		JLabel nombrePasajero = new JLabel("Nombre del pasajero:");         
		nombrePasajero.setFont(f);
		c.insets = new Insets(7,5,7,5);        // espacio entre filas y columnas
		c.gridx = 1;                           // nº de columna
		c.gridy = 1;                           // nº de fila
		c.anchor = GridBagConstraints.WEST;    // donde se coloca el componente dentro de la celda
		pan.add(nombrePasajero, c);     
		nombre = new JTextField(15);
		nombre.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 1;                         
		c.anchor = GridBagConstraints.WEST;
		nombre.setEditable(true);
		nombre.setBackground(new Color(241,246,94));
		pan.add(nombre, c);
		   
		// apellidos del pasajero
		JLabel apellidoPasajero = new JLabel("Apellidos del pasajero:");         
		apellidoPasajero.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		pan.add(apellidoPasajero, c);     
		apellido = new JTextField(40);
		apellido.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 2;                         
		c.anchor = GridBagConstraints.WEST;
		apellido.setEditable(true);
		apellido.setBackground(new Color(241,246,94));
		pan.add(apellido, c);
		   
		// email
		JLabel emailPasajero = new JLabel("E-mail:");         
		emailPasajero.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		pan.add(emailPasajero, c);     
		email = new JTextField(40);
		email.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 3;                         
		c.anchor = GridBagConstraints.WEST;
		email.setEditable(true);
		email.setBackground(new Color(241,246,94));
		pan.add(email, c);		   
		   
		// botones
		JPanel pan2 = new JPanel();
	    pan2.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
		pan2.setBackground(new Color(188,214,190));
		boton1 = new JButton();
		boton1.setText("Crear Pasajero");
		boton1.setFont(f);
		boton1.setBackground(new Color(218,150,33));
		boton1.addActionListener(this);
		pan2.add(boton1);   
		boton2 = new JButton();
		boton2.setText("Listar Pasajeros");
		boton2.setFont(f);
		boton2.setBackground(new Color(218,150,33));
		boton2.addActionListener(this);
		pan2.add(boton2);
		
		// tabla
	    String[] columnNames = {"Id", "Nombre", "E-mail"};
	    tableModel = new DefaultTableModel(columnNames, 0);
	    pasajeroTable = new JTable(tableModel);
	    // crea un renderizador centrado
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    // aplica el renderizador a todas las columnas
	    for (int i = 0; i < pasajeroTable.getColumnModel().getColumnCount(); i++) {
	        pasajeroTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }
	    JScrollPane scrollPane = new JScrollPane(pasajeroTable);
		   
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
	      
	    // Crea un pasajero
	    if (texto.equals("Crear Pasajero")) {
	    	  
	    	// crea sessionFactory y session
	    	StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
	    	.configure("hibernate.cfg.xml")
	  		.build();
	  		
	  		Metadata m = new MetadataSources(ssr)
	  		.addAnnotatedClass(Pasajero.class)
	  		.getMetadataBuilder()
	  		.build();
	  		
	  		SessionFactory sf = m.getSessionFactoryBuilder().build();
	  		
	  		Session s = sf.openSession();
	  		
	  		try {
	  			// crea un objeto conductor
	  			String nombrePasajero = nombre.getText();
	  			String apellidoPasajero = apellido.getText();
	  			String nom = nombrePasajero + " " + apellidoPasajero;
	  			String em = email.getText();
	  			Pasajero tempPasajero = new Pasajero(nom, em);
	  			
	  			// comienza la transacción
	  			s.beginTransaction();
	  			
	  			// guarda el objeto Pasajero
	  			s.save(tempPasajero);
	  			
	  			// hace commit de la transacción
	  			s.getTransaction().commit();
	  			  
	  			// refresh para hacer una select
	  			s.refresh(tempPasajero);
	  			pasajeros = s.createQuery("from Pasajero").getResultList();
	  			  
	  			// limpia el modelo actual de la tabla para evitar duplicados
	  			tableModel.setRowCount(0);

	  			// añade cada pasajero al modelo de la tabla
	  			for (Pasajero pas : pasajeros) {
	  				Object[] rowData = {pas.getId(), pas.getNombre(), pas.getEmail()};
	  				tableModel.addRow(rowData);
	  			}

	  			// actualiza la tabla
	  			pasajeroTable.repaint();
	  			  
	  			// se dejan en blanco los campos
	  			nombre.setText("");
	  			apellido.setText("");
	  			email.setText("");
	  			  
	  			// muestra mensaje de confirmación
	  			JOptionPane.showMessageDialog(null, "Pasajero creado", "Información", JOptionPane.INFORMATION_MESSAGE);
	  		  
	  		} catch (Exception e) {
	  			// rollback ante alguna excepción
	  			s.getTransaction().rollback();
	  			JOptionPane.showMessageDialog(null, "No se ha podido crear el pasajero", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			e.printStackTrace();			
	  		} finally {
	  			s.close();
	  			sf.close();
	  		}
	        
	  		return;
	  	
	  	// Saca un listado de pasajeros
	    } else if (texto.equals("Listar Pasajeros")) {
	    	// crea sessionFactory y session
	    	StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
	    	.configure("hibernate.cfg.xml")
	  		.build();
	  		
	  		Metadata m = new MetadataSources(ssr)
	  		.addAnnotatedClass(Pasajero.class)
	  		.getMetadataBuilder()
	  		.build();
	  		
	  		SessionFactory sf = m.getSessionFactoryBuilder().build();
	  		
	  		Session s = sf.openSession();
	  		
	  		try {			
	  			// comienza la transacción
	  			s.beginTransaction();
	  			  
	  			// se consulta el listado
	  			pasajeros = s.createQuery("from Pasajero").getResultList();
	  			  
	  			// limpia el modelo actual de la tabla para evitar duplicados
	  			tableModel.setRowCount(0);

	  			// añade cada pasajero al modelo de la tabla
	  			for (Pasajero pas : pasajeros) {
	  				Object[] rowData = {pas.getId(), pas.getNombre(), pas.getEmail()};
	  				tableModel.addRow(rowData);
	  			}

	  			// actualiza la tabla
	  			pasajeroTable.repaint();
	  		  
	  		} catch (Exception e) {
	  			// rollback ante alguna excepción
	  			s.getTransaction().rollback();
	  			JOptionPane.showMessageDialog(null, "No se ha podido crear el pasajero", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			e.printStackTrace();			
	  		} finally {
	  			s.close();
	  			sf.close();
	  		}
	        
	  		return;	        
    	}
	}	
}
