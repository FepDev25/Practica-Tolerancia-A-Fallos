import { Injectable } from '@angular/core';
import { environment } from '../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransaccionService {

  private baseUrl = `${environment.apiUrl}/transaccion`;

  constructor(private http: HttpClient) { }
}
