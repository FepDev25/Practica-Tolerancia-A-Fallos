import { Injectable } from '@angular/core';
import { environment } from '../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransaccionService {

  private baseUrl = `${environment.apiUrl}/transaccion`;

  constructor(private http: HttpClient) { }
  depositar(cuentaId: number, monto: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/depositar`, {
      cuentaId,
      monto
    });
  }

  retirar(cuentaId: number, monto: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/retirar`, {
      cuentaId,
      monto
    });
  }

  transferir(origenId: number, destinoId: number, monto: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/transferir`, {
      origenId,
      destinoId,
      monto
    });
  }
  getTransacciones(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
  
}
