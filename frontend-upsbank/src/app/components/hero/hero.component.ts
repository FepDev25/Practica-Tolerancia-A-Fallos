import { Component } from '@angular/core';

@Component({
  selector: 'app-hero',
  standalone: true,
  imports: [],
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.css'
})
export class HeroComponent {
  scrollToOperaciones(): void {
    const element = document.getElementById('operaciones');
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  }

}
