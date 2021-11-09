package tim.vedagerp.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.NameSpace;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DebtDTO {

	private Long id;

	/**Nom de la dêtte */
	private String name;

	/** Description de la dêttes */
	private String description;
	
	/** Montant Totale de la dêtte */
	private float amount;

	/** Montant Actuelle de la dêtte */
	private float currentAmount;

	/** Taux du près */
	private float rate;

	/** Date de début */
	private String startDate;

	/** Créancier */
	private String creditor;

	/** Compte comptable associer */
	private Account account;

	/** Montant Totale à payer */
	private float dueAmount;

	/** Montant de l'écheance de la dêtte */
	private float deadlineAmount;

	/** Pourcentage de progression de la dêtte */
	private float percentage;

	/** Nombre des échénances */
	private int nbrOfDeadline;

	/** Liste des écheances */
	private List<String> listOfDeadlines ;

	/** Date de fin */
	private String endDate;

	/** Définition de l'espace de travail */
	private NameSpace namespace;
	
}
