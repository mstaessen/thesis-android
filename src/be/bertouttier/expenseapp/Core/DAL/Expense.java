package be.bertouttier.expenseapp.Core.DAL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

@Table(name="Expenses")
public class Expense {
	
	// Fields
	@PrimaryKey(autoIncrement = true)
    @Column(name="id")
	private long id;
	@Column(name="date")
	private Date date; // OK
	@Column(name="projectcode")
	private String projectCode;
	@Column(name="amount")
	private Float amount; // OK
	@Column(name="remarks")
	private String remarks;
	@Column(name="evidence")
	private String evidence; //OK
	@Column(name="currency")
	private String currency; // OK
	@Column(name="expensetypeid")
	private int expenseTypeId;
	@Column(name="expenselocationid")
	private int expenseLocationId;

	public Expense ()
	{
		
	}
	
	public Expense (Date date, String projectCode, float amount, String remarks, String evidence, String currency, int expenseTypeId, int expenseLocationId)
	{
		this.setDate(date);
		this.setProjectCode(projectCode);
		this.setAmount(amount);
		this.setRemarks(remarks);
		this.setEvidence(evidence);
		this.setCurrency(currency);
		this.setExpenseTypeId(expenseTypeId);
		this.setExpenseLocationId(expenseLocationId);
	}
	
	// Getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public int getExpenseLocationId() {
		return expenseLocationId;
	}

	public void setExpenseLocationId(int expenseLocationId) {
		this.expenseLocationId = expenseLocationId;
	}
	
	// Methods
	public String getExpenseType()
	{
		Map<Integer,String> types = new HashMap<Integer, String>(){{
		    put(1, "Hotel");
		    put(2, "Lunch");
		    put(3, "Diner");
		    put(4, "Ticket");
		    put(5, "Restaurant");
		    put(6, "Other...");
		}};
		return types.get(this.expenseTypeId);
	}

	@Override
	public String toString() 
	{
		return "Expense: " + date.toString() + " " + projectCode + " " + String.valueOf(amount) + " " + remarks + " " + evidence + " " + currency + " " + String.valueOf(expenseTypeId) + " " + String.valueOf(expenseLocationId);
	}
	

}
