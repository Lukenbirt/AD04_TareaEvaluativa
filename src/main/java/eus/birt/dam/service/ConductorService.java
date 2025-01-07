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

import eus.birt.dam.domain.Conductor;

public class ConductorService extends JDialog implements ActionListener{
	
	// Atributos
	private JTextField nombre;
	private JTextField apellido;
	private JTextField vehiculo;
	private JButton boton1;
	private JButton boton2;
	private Font f = new Font("Book Antiqua", Font.BOLD, 15);
	private Font ff = new Font("Dialog", Font.PLAIN, 13);
	private List <Conductor> conductores;
	private JTable conductorTable;
    private DefaultTableModel tableModel;

	// Constructor
	public ConductorService(Frame ventana, boolean modal) {
		super(ventana, false);
	    setLayout(new BorderLayout());

	    // calcula la resolución de la pantalla actual     
	    int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    
	    // situa la ventana
	    setBounds((ancho / 6) - (this.getWidth() / 5), (alto / 4) - (this.getHeight() / 13), 630, 650);
	    setTitle("CONDUCTORES");
	    
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
		   
		// nombre del conductor
		JLabel nombreConductor = new JLabel("Nombre del conductor:");         
		nombreConductor.setFont(f);
		c.insets = new Insets(7,5,7,5);        // espacio entre filas y columnas
		c.gridx = 1;                           // nº de columna
		c.gridy = 1;                           // nº de fila
		c.anchor = GridBagConstraints.WEST;    // donde se coloca el componente dentro de la celda
		pan.add(nombreConductor, c);     
		nombre = new JTextField(12);
		nombre.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 1;                         
		c.anchor = GridBagConstraints.WEST;
		nombre.setEditable(true);
		nombre.setBackground(new Color(241,246,94));
		pan.add(nombre, c);
		   
		// apellidos del conductor
		JLabel apellidoConductor = new JLabel("Apellidos del conductor:");         
		apellidoConductor.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		pan.add(apellidoConductor, c);     
		apellido = new JTextField(22);
		apellido.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 2;                         
		c.anchor = GridBagConstraints.WEST;
		apellido.setEditable(true);
		apellido.setBackground(new Color(241,246,94));
		pan.add(apellido, c);
		   
		// vehículo
		JLabel vehiculoConductor = new JLabel("Vehículo:");         
		vehiculoConductor.setFont(f);
		c.insets = new Insets(7,5,7,5);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		pan.add(vehiculoConductor, c);     
		vehiculo = new JTextField(22);
		vehiculo.setFont(ff);         
		c.gridx = 2;                          
		c.gridy = 3;                         
		c.anchor = GridBagConstraints.WEST;
		vehiculo.setEditable(true);
		vehiculo.setBackground(new Color(241,246,94));
		pan.add(vehiculo, c);		   
		   
		// botones
		JPanel pan2 = new JPanel();
	    pan2.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
		pan2.setBackground(new Color(188,214,190));
		boton1 = new JButton();
		boton1.setText("Crear Conductor");
		boton1.setFont(f);
		boton1.setBackground(new Color(218,150,33));
		boton1.addActionListener(this);
		pan2.add(boton1);   
		boton2 = new JButton();
		boton2.setText("Listar Conductores");
		boton2.setFont(f);
		boton2.setBackground(new Color(218,150,33));
		boton2.addActionListener(this);
		pan2.add(boton2);
		
		// tabla
	    String[] columnNames = {"Id", "Nombre", "Vehículo"};
	    tableModel = new DefaultTableModel(columnNames, 0);
	    conductorTable = new JTable(tableModel);
	    
	    // crea un renderizador centrado
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    
	    // aplica el renderizador a todas las columnas
	    for (int i = 0; i < conductorTable.getColumnModel().getColumnCount(); i++) {
	        conductorTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

	    // configuración del ancho de las columnas
	    int[] columnWidths = {20, 200, 100};
	    for (int i = 0; i < columnWidths.length; i++) {
	        conductorTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
	    }
	    
	    JScrollPane scrollPane = new JScrollPane(conductorTable);
		   
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
	      
	    // Crea un conductor
	    if (texto.equals("Crear Conductor")) {
	    	  
	    	// crea sessionFactory y session
	    	StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
	    	.configure("hibernate.cfg.xml")
	  		.build();
	  		
	  		Metadata m = new MetadataSources(ssr)
	  		.addAnnotatedClass(Conductor.class)
	  		.getMetadataBuilder()
	  		.build();
	  		
	  		SessionFactory sf = m.getSessionFactoryBuilder().build();
	  		
	  		Session s = sf.openSession();
	  		
	  		try {
	  			// crea un objeto conductor
	  			String nombreConductor = nombre.getText();
	  			String apellidoConductor = apellido.getText();
	  			String nom = nombreConductor + " " + apellidoConductor;
	  			String vehic = vehiculo.getText();
	  			Conductor tempConductor = new Conductor(nom, vehic);
	  			
	  			// comienza la transacción
	  			s.beginTransaction();
	  			
	  			// guarda el objeto Conductor
	  			s.save(tempConductor);
	  			
	  			// hace commit de la transacción
	  			s.getTransaction().commit();
	  			  
	  			// refresh para hacer una select
	  			s.refresh(tempConductor);
	  			conductores = s.createQuery("from Conductor").getResultList();
	  			  
	  			// limpia el modelo actual de la tabla para evitar duplicados
	  			tableModel.setRowCount(0);

	  			// añade cada conductor al modelo de la tabla
	  			for (Conductor cond : conductores) {
	  				Object[] rowData = {cond.getId(), cond.getNombre(), cond.getVehiculo()};
	  				tableModel.addRow(rowData);
	  			}

	  			// actualiza la tabla
	  			conductorTable.repaint();
	  			  
	  			// se dejan en blanco los campos
	  			nombre.setText("");
	  			apellido.setText("");
	  			vehiculo.setText("");
	  			  
	  			// muestra mensaje de confirmación
	  			JOptionPane.showMessageDialog(null, "Conductor creado", "Información", JOptionPane.INFORMATION_MESSAGE);
	  		  
	  		} catch (Exception e) {
	  			// rollback ante alguna excepción
	  			s.getTransaction().rollback();
	  			JOptionPane.showMessageDialog(null, "No se ha podido crear el conductor", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			e.printStackTrace();			
	  		} finally {
	  			s.close();
	  			sf.close();
	  		}
	        
	  		return;
	  	
	  	// Saca un listado de conductores
	    } else if (texto.equals("Listar Conductores")) {
	    	// crea sessionFactory y session
	    	StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
	    	.configure("hibernate.cfg.xml")
	  		.build();
	  		
	  		Metadata m = new MetadataSources(ssr)
	  		.addAnnotatedClass(Conductor.class)
	  		.getMetadataBuilder()
	  		.build();
	  		
	  		SessionFactory sf = m.getSessionFactoryBuilder().build();
	  		
	  		Session s = sf.openSession();
	  		
	  		try {			
	  			// comienza la transacción
	  			s.beginTransaction();
	  			  
	  			// se consulta el listado
	  			conductores = s.createQuery("from Conductor").getResultList();
	  			  
	  			// limpia el modelo actual de la tabla para evitar duplicados
	  			tableModel.setRowCount(0);

	  			// añade cada conductor al modelo de la tabla
	  			for (Conductor cond : conductores) {
	  				Object[] rowData = {cond.getId(), cond.getNombre(), cond.getVehiculo()};
	  				tableModel.addRow(rowData);
	  			}

	  			// actualiza la tabla
	  			conductorTable.repaint();
	  		  
	  		} catch (Exception e) {
	  			// rollback ante alguna excepción
	  			s.getTransaction().rollback();
	  			JOptionPane.showMessageDialog(null, "No se ha podido crear el conductor", "Información", JOptionPane.INFORMATION_MESSAGE);
	  			e.printStackTrace();			
	  		} finally {
	  			s.close();
	  			sf.close();
	  		}
	        
	  		return;	        
    	}
	}	
}
