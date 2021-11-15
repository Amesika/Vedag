import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AccountSolde } from 'src/app/core/models/account_solde';
import { Debt } from 'src/app/core/models/debt';
import { DebtService } from 'src/app/core/services/vdg-service/debt.service';
import { FyService } from 'src/app/core/services/vdg-service/fy.service';
import { JournalRowService } from 'src/app/core/services/vdg-service/jr.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';

@Component({
  selector: 'app-debt',
  templateUrl: './debt.component.html',
  styleUrls: ['./debt.component.scss']
})
export class DebtComponent implements OnInit,OnChanges {

  debt:Debt;

  constructor(private  debtService: DebtService) { }

  ngOnInit() {
    this.debt = this.debtService.currentDebt;

    const {debt} = history.state
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.debt = this.debtService.currentDebt;
  }


}
