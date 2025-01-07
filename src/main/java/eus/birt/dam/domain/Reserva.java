package eus.birt.dam.domain;

import java.time.LocalDate;

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
@Table(name="reserva")
public class Reserva {
	
	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="fechaReserva")
	private LocalDate fechaReserva;
	
	@Column(name="numeroPlazasReservadas")
	private int numeroPlazasReservadas;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="pasajero_id", referencedColumnName="id")	
	private Pasajero pasajero;

	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="viaje_id", referencedColumnName="id")
	private Viaje viaje;

	// Constructores
	public Reserva() {}
	
	public Reserva(LocalDate fechaReserva,int numeroPlazasReservadas, Pasajero pasajero, Viaje viaje) {
		this.fechaReserva = fechaReserva;
		this.numeroPlazasReservadas = numeroPlazasReservadas;
		this.pasajero = pasajero;
		this.viaje = viaje;
	}

	// Métodos Get y Set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	
	public int getNumeroPlazasReservadas() {
		return numeroPlazasReservadas;
	}

	public void setNumeroPlazasReservadas(int numeroPlazasReservadas) {
		this.numeroPlazasReservadas = numeroPlazasReservadas;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}

}
