import { Account } from "./account";

export class Debt{
 
    id:number;
	amount:number;
    currentAmount:number;
    dueAmount:number;
    rate:number;
    startDate:Date;
    creditor:String;
    account:Account;

    constructor(){}

    setProperies(debt: any) {
      this.id = debt.id ;
      this.amount = debt.amount ;
      this.currentAmount = debt.currentAmount ;
      this.dueAmount = debt.dueAmount ;
      this.rate = debt.rate ;
      this.startDate = debt.startDate ;
      this.creditor = debt.creditor ;
      this.account = debt.account ;
    }
}