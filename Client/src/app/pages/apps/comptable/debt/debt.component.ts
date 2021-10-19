import { Component, OnInit } from '@angular/core';
import { AccountSolde } from 'src/app/core/models/account_solde';
import { FyService } from 'src/app/core/services/vdg-service/fy.service';
import { JournalRowService } from 'src/app/core/services/vdg-service/jr.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';

@Component({
  selector: 'app-debt',
  templateUrl: './debt.component.html',
  styleUrls: ['./debt.component.scss']
})
export class DebtComponent implements OnInit {

  constructor(private nsService: NsService, private fyService: FyService, private jrService: JournalRowService) { }

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

}
