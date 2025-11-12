package com.cdad.shareloc.dao;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Entity(name = "User")
@Table(name = "users")
public class User {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "accumulatedpoints")
	private Long accumulated_points;
	

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
	@JoinTable(name = "user_colocation",
    joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = { @JoinColumn(name = "colocation_id") })
    @JsonBackReference
	private Set<Colocation> colocations = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
	@JoinTable(name = "user_service",
	joinColumns = { @JoinColumn(name = "user_id_created") },
	inverseJoinColumns = { @JoinColumn(name = "service_id") })
	private Set<ServiceShareloc> services = new HashSet<>();
	
	
	public User(long id, String email, String password, String firstname, String lastname, Long accumulated_points) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.accumulated_points = accumulated_points;
		
	}

	public User() 
	{}

	//GETTERS
	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public Long getAccumulated_points() {
		return accumulated_points;
	}

	public Set<Colocation> getColocations() {
		return colocations;
	}
	
	public Set<ServiceShareloc> getServices() {
		return services;
	}
	
	
	
	
	//SETTERS
	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setColocations(Set<Colocation> colocations) {
		this.colocations = colocations;
	}
	
	public void setServices(Set<ServiceShareloc> services) {
		this.services = services;
	}
	
	public void setAccumulated_points(Long accumulated_points) {
		this.accumulated_points = accumulated_points;
	}

	public void addColocation(Colocation colocation)
	{
		this.colocations.add(colocation);
		colocation.getUsers().add(this);
	}
	
	public void addService(ServiceShareloc service)
	{
		this.services.add(service);
		service.getUsers().add(this);
	}
	
		
	public void removeColocation(Long colocationId)
	{
		Colocation colocation = this.colocations.stream().filter(c -> c.getId() == colocationId).findFirst().orElse(null);
		
		if(colocation != null)
		{
			this.colocations.remove(colocation);
			colocation.getUsers().remove(this);
		}
	}
	
	
	public void removeService(long serviceId)
	{
		ServiceShareloc service = this.services.stream().filter(s -> s.getId() == serviceId).findFirst().orElse(null);
		
		if(service != null)
		{
			this.services.remove(service);
			service.getUsers().remove(this);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(accumulated_points, colocations, email, firstname, id, lastname, password, services);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(accumulated_points, other.accumulated_points)
				&& Objects.equals(colocations, other.colocations) && Objects.equals(email, other.email)
				&& Objects.equals(firstname, other.firstname) && id == other.id
				&& Objects.equals(lastname, other.lastname) && Objects.equals(password, other.password)
				&& Objects.equals(services, other.services);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
