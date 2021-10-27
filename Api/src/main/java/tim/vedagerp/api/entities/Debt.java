package tim.vedagerp.api.entities;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "debts")
@Setter
@Getter
public class Debt {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column()
	private String name;

	@Column()
	private String description;

	@Column()
	private float amount;

	@Column()
	private float currentAmount;

	@Column()
	private float rate;

	@Column()
	private Date startDate;

	@Column()
	private String creditor;

	@OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

	
}
