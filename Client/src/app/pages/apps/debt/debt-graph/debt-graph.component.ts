import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { Subscription } from 'rxjs/internal/Subscription';
import { Debt } from 'src/app/core/models/debt';
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

  @Input()
  debt: Debt;

  maxNbr = 0;

  show = false;

  subscription: Subscription;
  subscription2: Subscription;

  constructor(private debtService: DebtService, private routeParams: ActivatedRoute, private nsService: NsService) {
    

  }

  ngOnInit() {

    let debtId = this.routeParams.snapshot.params["debtId"]
    this.subscription2 = this.debtService.getMaxDeadlineNbr(this.nsService.currentNs().id).subscribe((nbr)=>{
      this.maxNbr = nbr;
      this.subscription = this.debtService.getOneDebt(debtId).subscribe((debt) => {
      this.debt.setProperies(debt);
      this._fetchData();
    })
    })
    
    
    
  }

  /**
   * fetches the dashboard value
   */
  private _fetchData() {

    this.revenueAreaChart = revenueAreaChart;
    let cat = []
    if (this.debt !== undefined) {
      for (let i = 0; i < this.maxNbr; i++) {
        let u = moment(this.debt.startDate, "DD-MM-YYYY").add(i, 'M').format("MM/YYYY");
        cat.push(u);
      }

      this.revenueAreaChart.xaxis.categories = cat;
      this.revenueAreaChart.series = [];

      let serie = new Serie();
      serie.name = this.debt.name;

      for (let i = 0; i < this.maxNbr; i++) {

        if (i < this.debt.nbrOfDeadline - 1) {
          serie.data.push(this.debt.deadlineAmount);
        } else if (i < this.debt.nbrOfDeadline) {
          let newAmount = (this.debt.currentAmount*-1) - (this.debt.nbrOfDeadline - 1)*this.debt.deadlineAmount
          serie.data.push(Math.round((newAmount+ Number.EPSILON) * 100) / 100);
        } else {
          serie.data.push(0);
        }
      }
      this.revenueAreaChart.series.push(serie);
      this.show = true;
    }
    //}

  }

}
