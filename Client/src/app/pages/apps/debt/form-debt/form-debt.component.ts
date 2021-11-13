import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Debt } from 'src/app/core/models/debt';
import { DebtService } from 'src/app/core/services/vdg-service/debt.service';
import { NsService } from 'src/app/core/services/vdg-service/ns.service';

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

  @Output()
  debtFormEvent = new EventEmitter<string>();

  submitted: boolean;

  constructor(private formBuilder: FormBuilder, private nsService: NsService, private debtService: DebtService) {
    this.submitted = false;
  }

  debtForm: FormGroup;

  ngOnInit() {

    if (this.debt) {
      this.debtForm = this.formBuilder.group({
        name: [this.debt.name],
        amount: [this.debt.amount],
        currentAmount: [this.debt.currentAmount],
        rate: [this.debt.rate],
        startDate: [this.debt.startDate],
        creditor: [this.debt.creditor],
        endDate: [this.debt.endDate],
        deadlineAmount: [this.debt.deadlineAmount],
        description: [this.debt.description],
        nbrOfDeadline: [this.debt.nbrOfDeadline],
      });
    } else {
      this.debtForm = this.formBuilder.group({
        name: [''],
        amount: [''],
        currentAmount: [''],
        rate: [''],
        startDate: [''],
        creditor: [''],
        endDate: [''],
        deadlineAmount: [''],
        description: [''],
        nbrOfDeadline: [''],
      });

    }

   
  } crudDebt() {
      this.submitted = true;

      if (this.debtForm.invalid) {
        return;
      }

      let debt = new Debt()
      debt.namespace = this.nsService.currentNs()

      if (this.txt.action == 'update') {
        debt = this.debt;
      }

      if (this.debtForm.valid) {
        debt.name = this.debtForm.get('name').value;
        debt.amount = this.debtForm.get('amount').value;
        debt.currentAmount = this.debtForm.get('currentAmount').value;
        debt.rate = this.debtForm.get('rate').value;
        debt.startDate = this.debtForm.get('startDate').value;
        debt.creditor = this.debtForm.get('creditor').value;
        debt.endDate = this.debtForm.get('endDate').value;
        debt.deadlineAmount = this.debtForm.get('deadlineAmount').value;
        debt.description = this.debtForm.get('description').value;
        debt.nbrOfDeadline = this.debtForm.get('nbrOfDeadline').value;

        this.debtService.putDebt(debt).subscribe(() => {
          this.callDebtFormEvent('update')
        })
      }
    } 

    
  callDebtFormEvent(value: string) {
    this.cleanForm()
    this.debtFormEvent.emit(value);
  }

  close() {
    this.callDebtFormEvent('annuler')
  }

  get debtFormCtl() {
    return this.debtForm.controls;
  }

  // Effacer le formulaire
  cleanForm() {
    this.debtForm.patchValue({name:'',
    amount:'',
    currentAmount:'',
    rate:'',
    startDate:'',
    creditor:'',
    endDate:'',
    deadlineAmount:'',
    description:'',
    nbrOfDeadline:'', })
  }
}
