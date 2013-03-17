package be.bertouttier.expenseapp.Core.DAL;

import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Column;

@Table(name="Employee")
public class Employee {
	
	@PrimaryKey(autoIncrement = true)
    @Column(name="userId")
	private long userId;
	@Column(name="id")
	private int id;
	@Column(name="email")
	private String email;
	@Column(name="employeeNumber")
	private int employeeNumber;
	@Column(name="firstName")
	private String firstName;
	@Column(name="lastName")
	private String lastName;
	@Column(name="password")
	private String password;
	@Column(name="unitId")
	private int unitId;
	@Column(name="token")
	private String token;

	// getters/setters
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	// toString method
	@Override
	public String toString() 
	{
		return "Employee: " + token + " " + id + " " + email + " " + employeeNumber + " " + firstName + " " + lastName + " " + password + " " + unitId;
	}
    
   
}