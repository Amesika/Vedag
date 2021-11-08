import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComptableRoutingModule } from './debt-routing.module';
import { UIModule } from 'src/app/shared/ui/ui.module';
import { WidgetModule } from 'src/app/shared/widgets/widget.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { FlatpickrModule } from 'angularx-flatpickr';
import { DebtComponent } from './debt/debt.component';
import { DebtDashbordComponent } from './debt-dashbord/debt-dashbord.component';
import { DebtDetailComponent } from './debt-detail/debt-detail.component';

@NgModule({
  declarations: [
    DebtComponent,
    DebtDashbordComponent,
    DebtDetailComponent
  ],
  exports:[DebtComponent,DebtDashbordComponent],
  imports: [
    CommonModule,
    ComptableRoutingModule,
    UIModule,
    WidgetModule,
    FormsModule,
    NgSelectModule,
    ReactiveFormsModule,
    FlatpickrModule.forRoot(),
  ]
})
export class DetteModule { }
