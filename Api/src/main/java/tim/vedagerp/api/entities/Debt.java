package tim.vedagerp.api.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "debt")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

	@ManyToOne
	@JoinColumn(name = "namespace_id", foreignKey = @ForeignKey(name = "FK_DEBT_NS"))
	private NameSpace namespace;

}
