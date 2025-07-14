import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { HeroComponent } from './components/hero/hero.component';
import { NuestrosServiciosComponent } from './components/nuestros-servicios/nuestros-servicios.component';
import { OperacionesComponent } from './components/operaciones/operaciones.component';
import { HistorialTransaccionesComponent } from './components/historial-transacciones/historial-transacciones.component';
import { ContactoComponent } from './components/contacto/contacto.component';
import { FooterComponent } from './components/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeroComponent, HeaderComponent, NuestrosServiciosComponent, OperacionesComponent, HistorialTransaccionesComponent, ContactoComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend-upsbank';
}
