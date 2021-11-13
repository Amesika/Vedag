import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Debt } from 'src/app/core/models/debt';
import { DebtService } from 'src/app/core/services/vdg-service/debt.service';

@Component({
  selector: 'app-debt-detail',
  templateUrl: './debt-detail.component.html',
  styleUrls: ['./debt-detail.component.scss']
})
export class DebtDetailComponent implements OnInit {

  constructor(private router: Router,private routeParams: ActivatedRoute, private debtService: DebtService) { }

  debtId: number;
  debt:Debt = new Debt();

  ngOnInit() {

    this.getInputData();
    this.getDebt();
  }

  // Input dans l'url
  getInputData() {
    this.debtId = this.routeParams.snapshot.params["debtId"]
  }

  // Récupération de la dêtte
  getDebt() {
    this.debtService.getOneDebt(this.debtId).subscribe((debt) => {
     
      if(!debt["text"]){
        this.debt.setProperies(debt);
        this.debt.currentAmount = this.debt.currentAmount*-1;
      }
    })
  }

  // Mise à jour de la dêtte
  updateDebt(){
    this.router.navigate(['debt','update']);
  }

}
