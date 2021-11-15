import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import * as moment from 'moment';
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
export class DebtGraphComponent implements OnInit, OnChanges {


  revenueAreaChart: ChartType;

  @Input()
  debt: Debt;

  maxNbr = 33;

  constructor(private debtService: DebtService, private nsService: NsService) { }

  ngOnInit() {

    const { debt } = history.state

  }

  /**
   * fetches the dashboard value
   */
  private _fetchData() {

    this.revenueAreaChart = revenueAreaChart;
    let cat = []
    if (this.debt !== undefined) {

      for (let i = 0; i < this.maxNbr; i++) {
        if (this.debt.startDate) {
          var futureMonth = moment(this.debt.startDate, "YYYY-MM-DD").add(i, 'M').format("MM/YYYY");
          //cat.push(futureMonth);
          cat.push(i);
        }
      }
      this.revenueAreaChart.xaxis.categories = cat;

      this.revenueAreaChart.series = [];

      //for (let j = 0; j < 3; j++) {
      let serie = new Serie();
      let serieNbr = 7;
      serie.name = this.debt.name;

      for (let i = 0; i < this.maxNbr; i++) {

        if (i < this.debt.nbrOfDeadline) {
          serie.data.push(this.debt.deadlineAmount);
        } else {
          serie.data.push(0);
        }
      }
      this.revenueAreaChart.series.push(serie);
    }
    //}

  }

  ngOnChanges(changes: SimpleChanges): void {
    this.debt = new Debt();
    this.debt = changes.debt.currentValue;
    this._fetchData();
  }


}
