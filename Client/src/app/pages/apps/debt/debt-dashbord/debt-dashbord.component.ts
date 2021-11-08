import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountSolde } from 'src/app/core/models/account_solde';
import { FyService } from 'src/app/core/services/vdg-service/fy.service';
import { JournalRowService } from 'src/app/core/services/vdg-service/jr.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';

@Component({
  selector: 'app-debt-dashbord',
  templateUrl: './debt-dashbord.component.html',
  styleUrls: ['./debt-dashbord.component.scss']
})
export class DebtDashbordComponent implements OnInit {

  constructor(private router: Router,private nsService: NsService, private fyService: FyService, private jrService: JournalRowService) { }

  debts: AccountSolde[] = [];

  total:number=0;

  ngOnInit() {
    this._fetchData();
  }

  private _fetchData() {

    this.jrService.getDebt(this.nsService.currentNs().id, this.fyService.currentFy().id)
    .subscribe((accountSoldes) => {
      this.debts = accountSoldes;
      this.total = 0;
      accountSoldes.forEach(items => {
        this.total += items.solde;
      });
    });

   
  }

  getDebtDetails(debt){
    console.log(debt)
    let debtId =  3208
    this.router.navigate(['/', 'debt', debtId]);
  }

}
