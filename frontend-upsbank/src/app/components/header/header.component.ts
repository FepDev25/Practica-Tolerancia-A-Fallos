import { Component, OnInit } from '@angular/core';
import {UsuarioService} from '../../services/usuario.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
  mobileMenuVisible = false;

  constructor(public usuarioService: UsuarioService) {}

  ngOnInit(): void {}

  toggleMobileMenu() {
    this.mobileMenuVisible = !this.mobileMenuVisible;
  }

  logout() {
    this.usuarioService.logout();
    window.location.href = '/'; // recarga o puedes usar routing
  }

  isLoggedIn(): boolean {
    return this.usuarioService.isLoggedIn();
  }

}
