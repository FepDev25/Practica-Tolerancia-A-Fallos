import { Injectable } from '@angular/core';
import { environment } from '../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  private baseUrl = `${environment.apiUrl}/cuenta`;

  constructor(private http: HttpClient) { }
  consultarSaldo(idCuenta: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${idCuenta}`);
  }
}
