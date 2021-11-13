import { Injectable } from '@angular/core';
import { DecimalPipe } from '@angular/common';

import { Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { TableState } from 'src/app/shared/widgets/table-advanced/table-advanced.model';
import { Ns } from 'src/app/pages/apps/ns/models/ns';
import { CookieService } from './cookie.service';
import { environment } from 'src/environments/environment';
import { Debt } from '../../models/debt';


@Injectable({
    providedIn: 'root'
})

export class DebtService {


    href = environment.base_url;
    apiUrl = '/api/v1/debt'

    constructor(protected _http: HttpClient) {
    }

    //Récuperer une dêtte
    getOneDebt(id: number) {
        const requestUrl = `${this.href}${this.apiUrl}/${id}`;
        return this._http.get<any>(requestUrl)
    }

    //Récuperer une dêtte
    getDebt(nsId: number) {
        const requestUrl = `${this.href}${this.apiUrl}?nsId=${nsId}`;
        return this._http.get<any>(requestUrl)
    }

    // Mise à jour des soldes
    reloadDebt(nsId:number): Observable<any> {
        const requestUrl = `${this.href}${this.apiUrl}/reload?nsId=${nsId}`
        console.log(requestUrl);
        return this._http.get<any>(requestUrl);
    }

    // Modification d'une dette
    putDebt(debt: Debt) : Observable<Debt> {
        const requestUrl = `${this.href}${this.apiUrl}`
        return this._http.put<Debt>(requestUrl,debt);
    }
  

}
