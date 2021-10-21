import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DebtComponent } from './debt/debt.component';

const routes: Routes = [
    {
        path: 'debt',
        component: DebtComponent,
    }

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ComptableRoutingModule { }
