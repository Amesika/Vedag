import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { Subscription } from 'rxjs/internal/Subscription';
import { Debt } from 'src/app/core/models/debt';
import { MaxInfoDTO } from 'src/app/core/models/MaxInfo';
import { Serie } from 'src/app/core/models/serie';
import { DebtService } from 'src/app/core/services/vdg-service/debt.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';
import { revenueAreaChart } from './data';
import { ChartType } from './debt-graph.model';

@Component({
  selector: 'app-debt-graph',
  templateUrl: './debt-graph.component.html',
  styleUrls: ['./debt-graph.component.scss']
})
export class DebtGraphComponent implements OnInit {


  revenueAreaChart: ChartType;

  debts: Debt[] = [];

  maxInfoDTO: MaxInfoDTO = new MaxInfoDTO;

  show = false;

  subscription: Subscription;
  subscription2: Subscription;

  constructor(private debtService: DebtService, private routeParams: ActivatedRoute, private nsService: NsService) {


  }

  ngOnInit() {

    let debtId = this.routeParams.snapshot.params["debtId"]
    console.log(debtId)
    this.subscription2 = this.debtService.getMaxDeadlineNbr(this.nsService.currentNs().id).subscribe((maxInfoDTO: MaxInfoDTO) => {
      this.maxInfoDTO = maxInfoDTO;

      if (debtId == undefined) {
        this.subscription = this.debtService.getDebt(this.nsService.currentNs().id).subscribe((debts) => {
          debts.forEach(debt => {
            this.setDebts(this.debts, debt);
          });
          this._fetchData();
        })
      } else {
        this.subscription = this.debtService.getOneDebt(debtId).subscribe((debt) => {
          this.setDebts(this.debts, debt);
          this._fetchData();
        })
      }
    })
  }

  private _fetchData() {

    this.revenueAreaChart = revenueAreaChart;
    let cat = []
    for (let i = 0; i < this.maxInfoDTO.nbrmax; i++) {
      let u = moment(this.maxInfoDTO.startDate, "DD-MM-YYYY").add(i, 'M').format("MM/YYYY");
      cat.push(u);
    }
    this.revenueAreaChart.xaxis.categories = cat;
    this.getSeries()
  }

  getSerie(debt): Serie {
    let serie = new Serie();
    if (debt !== undefined) {
      serie.name = debt.name;

      for (let i = 0; i < this.maxInfoDTO.nbrmax; i++) {
        if (i < debt.nbrOfDeadline - 1) {
          serie.data.push(debt.deadlineAmount);
        } else if (i < debt.nbrOfDeadline) {
          let newAmount = (debt.currentAmount * -1) - (debt.nbrOfDeadline - 1) * debt.deadlineAmount
          serie.data.push(Math.round((newAmount + Number.EPSILON) * 100) / 100);
        } else {
          serie.data.push(0);
        }
      }
    }
    return serie;
  }

  getSeries() {
    let series = [];

    this.debts.forEach(debt => {

      this.revenueAreaChart.series = [];
      series.push(this.getSerie(debt));
      this.show = true;
      //}
    });
    this.revenueAreaChart.series = series;

  }

  setDebts(debts: Debt[], debt: Debt) {
    let newdebt: Debt = new Debt;
    newdebt.setProperies(debt);
    this.debts.push(newdebt);
  }

}
