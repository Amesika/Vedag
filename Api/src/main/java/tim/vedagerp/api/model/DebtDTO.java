package tim.vedagerp.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import tim.vedagerp.api.entities.Account;

@Setter
@Getter
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
	

	
}
