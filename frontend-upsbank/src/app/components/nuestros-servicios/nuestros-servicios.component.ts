import { Component } from '@angular/core';

@Component({
  selector: 'app-nuestros-servicios',
  standalone: true,
  imports: [],
  templateUrl: './nuestros-servicios.component.html',
  styleUrl: './nuestros-servicios.component.css'
})
export class NuestrosServiciosComponent {
  irAOperaciones(): void {
    const target = document.getElementById('operaciones');
    if (target) {
      target.scrollIntoView({ behavior: 'smooth' });
    }
  }

}
