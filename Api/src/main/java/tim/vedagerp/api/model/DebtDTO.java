package tim.vedagerp.api.model;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.NameSpace;
import tim.vedagerp.api.helper.DateFormer;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DebtDTO {

	private Long id;

	/** Nom de la dêtte */
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
	private List<String> listOfDeadlines;

	/** Date de fin */
	private String endDate;

	/** Définition de l'espace de travail */
	private NameSpace namespace;

	public void updateProperties() throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormer.DATE_FORMAT);
		//SimpleDateFormat simpleDateFormatv2 = new SimpleDateFormat(DateFormer.DATE_FORMAT_V2);

		// Calcule de nombre d'échéance
		int nbr = 0;

		if (this.deadlineAmount != 0) {
			nbr = (int) (this.currentAmount / this.deadlineAmount);
			float divRest = (this.currentAmount % this.deadlineAmount);
			nbr = Math.abs(nbr);
			if (divRest != 0) {
				nbr =  nbr + 1;
			}
		}
		this.nbrOfDeadline = nbr;

		// Pourcentage de progression
		this.percentage = this.currentAmount*100/this.amount;
		this.percentage = Math.abs((float) (Math.round(this.percentage*100.0)/100.0));

		// Date de fin

		Date endDate =  simpleDateFormat.parse(this.startDate);
		endDate = DateFormer.addMonthToDate(endDate,this.nbrOfDeadline);
		
		this.endDate =simpleDateFormat.format(endDate);
	}

}
