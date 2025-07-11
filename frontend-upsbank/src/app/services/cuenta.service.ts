import { Injectable } from '@angular/core';
import { environment } from '../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  private baseUrl = `${environment.apiUrl}/cuenta`;

  constructor(private http: HttpClient) { }
}
