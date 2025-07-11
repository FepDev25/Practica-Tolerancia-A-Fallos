import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-operaciones',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './operaciones.component.html',
  styleUrl: './operaciones.component.css'
})
export class OperacionesComponent {

  activeTab: string = 'create-account';

  createAccountForm: FormGroup;
  loginForm: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, public usuarioService: UsuarioService) {
    this.createAccountForm = this.fb.group({
      nombre: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required]
    });

    this.loginForm = this.fb.group({
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required]
    });
  }

  changeTab(tab: string) {
    this.activeTab = tab;
    this.successMessage = '';
    this.errorMessage = '';
  }

  onCreateAccount() {
    if (this.createAccountForm.invalid) {
      this.errorMessage = 'Por favor complete todos los campos correctamente';
      return;
    }

    this.usuarioService.registrarUsuarioConCuenta(this.createAccountForm.value).subscribe({
      next: () => {
        this.successMessage = 'Usuario creado correctamente';
        this.errorMessage = '';
        this.createAccountForm.reset();
      },
      error: () => {
        this.errorMessage = 'Error al crear usuario';
        this.successMessage = '';
      }
    });
  }

  onLogin() {
    if (this.loginForm.invalid) {
      this.errorMessage = 'Por favor complete todos los campos correctamente';
      return;
    }

    this.usuarioService.login(this.loginForm.value).subscribe({
      next: () => {
        this.successMessage = 'Login exitoso';
        this.errorMessage = '';
        this.loginForm.reset();
        this.activeTab = 'check-balance';
      },
      error: () => {
        this.errorMessage = 'Error en el login';
        this.successMessage = '';
      }
    });
  }

  onLogout() {
    this.usuarioService.logout();
    this.activeTab = 'create-account';
  }
}
