import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountSolde } from 'src/app/core/models/account_solde';
import { Debt } from 'src/app/core/models/debt';
import { DebtService } from 'src/app/core/services/vdg-service/debt.service';
import { FyService } from 'src/app/core/services/vdg-service/fy.service';
import { JournalRowService } from 'src/app/core/services/vdg-service/jr.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';

@Component({
  selector: 'app-debt-dashbord',
  templateUrl: './debt-dashbord.component.html',
  styleUrls: ['./debt-dashbord.component.scss']
})
export class DebtDashbordComponent implements OnInit {

  constructor(private router: Router, private nsService: NsService, private debtService: DebtService) { }

  debts: AccountSolde[] = [];

  total: number = 0;

  ngOnInit() {
    this._fetchData();
  }

  private _fetchData() {

    this.debtService.getDebt(this.nsService.currentNs().id)
      .subscribe((debts) => {
        this.debts = debts.map((debt) => {
          let newDebt = new Debt;
          newDebt.setProperies(debt);
          this.total += newDebt.currentAmount;
          return newDebt
        })
      });


  }

  getDebtDetails(debt:Debt) {
    console.log(debt)
    let debtId = debt.id
    this.router.navigate(['/', 'debt', debtId]);
  }

  reloadDebt() {
    this.debtService.reloadDebt(this.nsService.currentNs().id).subscribe((data) => {
      console.log(data);
    });
  }

}
