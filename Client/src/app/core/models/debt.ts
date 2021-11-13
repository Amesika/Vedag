import { FORMERR } from "dns";
import * as moment from "moment";
import { Ns } from "src/app/pages/apps/ns/models/ns";
import { Account } from "./account";

const MOMENT_DATE_FORMER = "MM/DD/YYYY h:mm:ss";

export class Debt {


	id: number;

	/**Nom de la dêtte */
	name: String;

	/** Description de la dêttes */
	description: String;

	/** Montant Totale de la dêtte */
	amount: number;

	/** Montant Actuelle de la dêtte */
	currentAmount: number;

	/** Taux du près */
	rate: number;

	/** Date de début */
	startDate: String;

	/** Créancier */
	creditor: String;

	/** Compte comptable associer */
	account: Account;

	/** Montant Totale à payer */
	dueAmount: number;

	/** Montant de l'écheance de la dêtte */
	deadlineAmount: number;

	/** Pourcentage de progression de la dêtte */
	percentage: number;

	/** Nombre des échénances */
	nbrOfDeadline: number;

	/** Liste des écheances */
	listOfDeadlines: String[];

	/** Date de fin */
	endDate: String;


	/** Définition de l'espace de travail */
	namespace: Ns;

	constructor() { }

	setProperies(debt: any) {
		this.id = debt.id;
		this.amount = debt.amount;
		this.currentAmount = debt.currentAmount;
		this.dueAmount = debt.dueAmount;
		this.rate = debt.rate;
		this.startDate = moment(debt.startDate).format(MOMENT_DATE_FORMER);
		this.creditor = debt.creditor;
		this.account = debt.account;
		this.endDate = moment(debt.endDate).format(MOMENT_DATE_FORMER);
		this.deadlineAmount = debt.deadlineAmount;
		this.description = debt.description;
		this.nbrOfDeadline = debt.nbrOfDeadline;
		this.name = debt.name;

		let namespace = new Ns()
		namespace.setProperies(debt.namespace);
		this.namespace = namespace; 

	}
}