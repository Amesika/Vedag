package tim.vedagerp.api.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ForeignKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(
name = "accounts",
uniqueConstraints=
@UniqueConstraint(columnNames={
	"label", "number","namespace_id"
}))
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(unique=true)
	private String label;

	@Column()
	private String labelBilan;
	
	@Column(unique=true)
	private	String	number;
	
	@ManyToOne()
    @JoinColumn(name="category_id",foreignKey=@ForeignKey(name="FK_ACCOUNT_CATEGORY"))
	private	Category category;
	
	@OneToMany(mappedBy = "debit")
    private List<JournalRow> journalDebit;
	
	@OneToMany(mappedBy = "credit")
    private List<JournalRow> journalCredit;
	
	@ManyToOne
    @JoinColumn(name="namespace_id",foreignKey=@ForeignKey(name="FK_ACCOUNT_NS"))
	private NameSpace namespace;
	
	@ManyToOne()
    @JoinColumn(name="account_id",foreignKey=@ForeignKey(name="FK_ACCOUNT_ACCOUNT"))
	private	Account account;
}
