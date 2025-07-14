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
    const now = new Date().toISOString(); // genera fecha actual en formato ISO
    return this.http.post(`${this.baseUrl}`, {
      cuentaOrigenId: cuentaId,
      cuentaDestinoId: cuentaId, // depósito va hacia sí mismo
      monto: monto,
      tipoTransaccionId: 1,
      fecha: now
    });
  }

  retirar(cuentaId: number, monto: number): Observable<any> {
    const now = new Date().toISOString();
    return this.http.post(`${this.baseUrl}`, {
      cuentaOrigenId: cuentaId,
      cuentaDestinoId: cuentaId,
      monto: monto,
      tipoTransaccionId: 2,
      fecha: now
    });
  }

  transferir(origenId: number, destinoId: number, monto: number): Observable<any> {
    const now = new Date().toISOString();
    return this.http.post(`${this.baseUrl}`, {
      cuentaOrigenId: origenId,
      cuentaDestinoId: destinoId,
      monto: monto,
      tipoTransaccionId: 3,
      fecha: now
    });
  }

  getTransacciones(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
}
