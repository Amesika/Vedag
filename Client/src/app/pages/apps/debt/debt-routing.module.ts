import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DebtDashbordComponent } from './debt-dashbord/debt-dashbord.component';
import { DebtDetailComponent } from './debt-detail/debt-detail.component';
import { DebtComponent } from './debt/debt.component';

const routes: Routes = [
    {
        path: 'debt',
        component: DebtComponent,
        children: [{
            path: '',
            component: DebtDashbordComponent,
        }, {
            path: ':debtId',
            component: DebtDetailComponent,
        }]
    },



];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ComptableRoutingModule { }
