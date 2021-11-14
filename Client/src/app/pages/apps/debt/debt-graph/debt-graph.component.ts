import { Component, OnInit } from '@angular/core';
import { revenueAreaChart } from './data';
import { ChartType } from './debt-graph.model';

@Component({
  selector: 'app-debt-graph',
  templateUrl: './debt-graph.component.html',
  styleUrls: ['./debt-graph.component.scss']
})
export class DebtGraphComponent implements OnInit {

  revenueAreaChart: ChartType;


  ngOnInit() {

    /**
     * Fetches the data
     */
    this._fetchData();
  }

  /**
   * fetches the dashboard value
   */
  private _fetchData() {
    this.revenueAreaChart = revenueAreaChart;
  }

}
