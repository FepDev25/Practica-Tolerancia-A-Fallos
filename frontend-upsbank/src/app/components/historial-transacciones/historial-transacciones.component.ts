import { Component, OnInit } from '@angular/core';
import { TransaccionService } from '../../services/transaccion.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-historial-transacciones',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historial-transacciones.component.html',
  styleUrl: './historial-transacciones.component.css'
})
export class HistorialTransaccionesComponent implements OnInit {
  transacciones: any[] = [];

  constructor(private transaccionService: TransaccionService) {}

  ngOnInit(): void {
    this.cargarTransacciones();
  }

  cargarTransacciones() {
    this.transaccionService.getTransacciones().subscribe({
      next: (data) => {
        this.transacciones = data;
      },
      error: () => {
        this.transacciones = [];
      }
    });
  }

}
