import { FORMERR } from "dns";
import * as moment from "moment";
import { Ns } from "src/app/pages/apps/ns/models/ns";
import { Account } from "./account";

const MOMENT_DATE_FORMER = "DD-MM-YYYY";



export class Debt {


	id: number;

	/**Nom de la dêtte */
	name: string;

	/** Description de la dêttes */
	description: string;

	/** Montant Totale de la dêtte */
	amount: number;

	/** Montant Actuelle de la dêtte */
	currentAmount: number;

	/** Taux du près */
	rate: number;

	/** Date de début */
	startDate: string;

	/** Créancier */
	creditor: string;

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

	/** Nombre des échénances max */
	maxOfDeadline: number;

	/** Liste des écheances */
	listOfDeadlines: string[];

	/** Date de fin */
	endDate: string;


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
		this.maxOfDeadline = debt.maxOfDeadline;
		this.name = debt.name;

		let namespace = new Ns()
		namespace.setProperies(debt.namespace);
		this.namespace = namespace; 
	}
}