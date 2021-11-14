import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { Debt } from 'src/app/core/models/debt';
import { DebtService } from 'src/app/core/services/vdg-service/debt.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';
const MOMENT_FR_FORMER = "DD-MM-YYYY";
const MOMENT_DATE_FORMER = "YYYY-MM-DD";

@Component({
  selector: 'app-form-debt',
  templateUrl: './form-debt.component.html',
  styleUrls: ['./form-debt.component.scss']
})
export class FormDebtComponent implements OnInit {

  @Input()
  debt: Debt;
  @Input()
  txt: any = {};

  // Range
  rangMin = 0;
  rangMax = 0;

  @Output()
  debtFormEvent = new EventEmitter<string>();

  submitted: boolean;

  constructor(private router: Router, private formBuilder: FormBuilder, private nsService: NsService, private debtService: DebtService) {
    this.submitted = false;
  }

  debtForm: FormGroup;

  ngOnInit() {

    const {debt} = history.state

    if(debt == undefined){
      this.router.navigate(['/', 'debt']);
    }
    this.debt = debt;
    console.log(this.debt);
    console.log(this.debt.startDate);
    console.log([moment(this.debt.startDate,MOMENT_FR_FORMER).format(MOMENT_DATE_FORMER)]);
    console.log([moment(this.debt.startDate)]);
    console.log([moment(this.debt.startDate,MOMENT_FR_FORMER)]);

    if (this.debt) {
      this.debtForm = this.formBuilder.group({
        name: [this.debt.name],
        amount: [this.debt.amount],
        currentAmount: [this.debt.currentAmount*-1],
        rate: [this.debt.rate],
        startDate: [moment(this.debt.startDate,MOMENT_FR_FORMER).format(MOMENT_DATE_FORMER)],
        creditor: [this.debt.creditor],
        endDate:  [moment(this.debt.endDate,MOMENT_FR_FORMER).format(MOMENT_DATE_FORMER)],
        deadlineAmount: [this.debt.deadlineAmount],
        description: [this.debt.description],
        nbrOfDeadline: [this.debt.nbrOfDeadline],
      });
    } else {
      this.debtForm = this.formBuilder.group({
        name: [''],
        amount: [0],
        currentAmount: [100],
        rate: [3],
        startDate: [''],
        creditor: [''],
        endDate: [''],
        deadlineAmount: [10],
        description: [''],
        nbrOfDeadline: [0],
      });

    }
    this.rangMax = this.debtForm.controls['currentAmount'].value;
    this.handleRange();
    this.correction();
    console.log(this.debtForm.controls['startDate'].value)

  } 
  
  crudDebt() {
    this.submitted = true;

    if (this.debtForm.invalid) {
      return;
    }

    let debt = new Debt()
    debt.namespace = this.nsService.currentNs()

    debt = this.debt;
    
    if (this.debtForm.valid) {
      debt.name = this.debtForm.get('name').value;
      debt.amount = this.debtForm.get('amount').value;
      debt.dueAmount = this.debtForm.get('amount').value;
      debt.currentAmount = this.debtForm.get('currentAmount').value*-1;
      debt.rate = this.debtForm.get('rate').value;
      debt.startDate = this.debtForm.get('startDate').value;
      debt.creditor = this.debtForm.get('creditor').value;
      debt.endDate = this.debtForm.get('endDate').value;
      debt.deadlineAmount = this.debtForm.get('deadlineAmount').value;
      debt.description = this.debtForm.get('description').value;
      debt.nbrOfDeadline = this.debtForm.get('nbrOfDeadline').value;

      console.log(debt)

      this.debtService.putDebt(debt).subscribe(() => {
        console.log(debt)
        this.callDebtFormEvent('update')
      })
    }
  }

  correction(){
    console.log(this.debtForm.controls['nbrOfDeadline'].value)
    if (this.debtForm.controls['nbrOfDeadline'].value === Infinity) {
      this.debtForm.patchValue({
        nbrOfDeadline: 1
      })
      this.handleNbrOfDeadline(event);
    }
  }


  callDebtFormEvent(value: string) {
    this.cleanForm()
    this.debtFormEvent.emit(value);
    this.router.navigate(['/', 'debt', this.debt.id]);
  }

  close() {
    this.callDebtFormEvent('annuler')
    this.router.navigate(['/', 'debt', this.debt.id]);
  }

  get debtFormCtl() {
    return this.debtForm.controls;
  }

  // Effacer le formulaire
  cleanForm() {
    this.debtForm.patchValue({
      name: '',
      amount: 0,
      currentAmount: 100,
      rate: 3,
      startDate: '',
      creditor: '',
      endDate: '',
      deadlineAmount: 10,
      description: '',
      nbrOfDeadline: 0,
    })
  }

  // Range change 
  handleRange() {
    console.log("handleRange")
    // Calcule du nombre d'écheance
    let nbr = 0;

    if (this.debtForm.controls['deadlineAmount'].value !== 0) {
      nbr = Math.floor(this.debtForm.controls['currentAmount'].value / this.debtForm.controls['deadlineAmount'].value);
      let divRest = (this.debtForm.controls['currentAmount'].value % this.debtForm.controls['deadlineAmount'].value);
      if (divRest !== 0) {
        nbr += 1;
      }
    }
    nbr = Math.abs(nbr);
    if (this.debtForm.controls['nbrOfDeadline'].value !== nbr) {
      this.debtForm.patchValue({
        nbrOfDeadline: nbr
      })
    }

  }

  // Changement du nombre d'écheance
  handleNbrOfDeadline(event) {

    event.preventDefault();
    let range = 0;
    console.log("handleNbrOfDeadline")
    if (this.debtForm.controls['nbrOfDeadline'].value !== 0) {
      range = Math.floor(this.debtForm.controls['currentAmount'].value / this.debtForm.controls['nbrOfDeadline'].value);
    }

   

    this.debtForm.patchValue({
      deadlineAmount: range
    })
  }

}
