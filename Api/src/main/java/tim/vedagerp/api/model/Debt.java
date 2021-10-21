package tim.vedagerp.api.model;
import java.lang.reflect.Constructor;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.DebtDto;


public class Debt {

	private Long id;
	
	private float amount;

	private float currentAmount;
	
	private float dueAmount;

	private float percentage;
	
	private float rate;
	
	private Date startDate;
	
	private String creditor;

    private Account account;

	public Debt(){

	}

	public Debt(DebtDto debtDto){
		this.account = debtDto.getAccount();
		this.amount = debtDto.getAmount();
		this.creditor = debtDto.getCreditor();
		this.dueAmount = debtDto.getDueAmount();
		this.currentAmount = debtDto.getCurrentAmount();
		this.id = debtDto.getId();
		this.rate = debtDto.getRate();
		this.startDate = debtDto.getStartDate();
	}

	public Long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public float getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(float currentAmount) {
		this.currentAmount = currentAmount;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
