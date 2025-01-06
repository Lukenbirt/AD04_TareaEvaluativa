package eus.birt.dam.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="viaje")
public class Viaje {

	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	@Column(name="ciudadDestino")
	private String ciudadDestino;
		
	@Column(name="ciudadOrigen")
	private String ciudadOrigen;

	@Column(name = "fechaHora")
	private LocalDateTime fechaHora;
	
	@Column(name="plazasDisponibles")
	private int plazasDisponibles;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="conductor_id", referencedColumnName="id")
	private Conductor conductor;

	// Constructores
	public Viaje() {}
	
	public Viaje(int id, String ciudadDestino, String ciudadOrigen, LocalDateTime fechaHora, int plazasDisponibles, Conductor conductor) {
		this.id = id;
		this.ciudadDestino = ciudadDestino;
		this.ciudadOrigen = ciudadOrigen;
		this.fechaHora = fechaHora;
		this.plazasDisponibles = plazasDisponibles;
		this.conductor = conductor;
	}

	// Métodos Get y Set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

}
