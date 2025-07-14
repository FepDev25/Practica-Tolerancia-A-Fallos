
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';
import { CuentaService } from '../../services/cuenta.service';
import { TransaccionService } from '../../services/transaccion.service';

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
  checkBalanceForm: FormGroup;
  depositForm: FormGroup;
  withdrawForm: FormGroup;
  transferForm: FormGroup;

  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    public usuarioService: UsuarioService,
    private cuentaService: CuentaService,
    private transaccionService: TransaccionService
  ) {
    this.createAccountForm = this.fb.group({
      nombre: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required]
    });

    this.loginForm = this.fb.group({
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', Validators.required]
    });

    this.checkBalanceForm = this.fb.group({
      cuentaId: ['', Validators.required]
    });

    this.depositForm = this.fb.group({
      cuentaId: ['', Validators.required],
      monto: ['', [Validators.required, Validators.min(0.01)]]
    });

    this.withdrawForm = this.fb.group({
      cuentaId: ['', Validators.required],
      monto: ['', [Validators.required, Validators.min(0.01)]]
    });

    this.transferForm = this.fb.group({
      origenId: ['', Validators.required],
      destinoId: ['', Validators.required],
      monto: ['', [Validators.required, Validators.min(0.01)]]
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
      next: (cuenta) => {
        this.successMessage = `Usuario creado correctamente. Tu número de cuenta es: ${cuenta.id}`;
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

  onConsultarSaldo() {
    if (this.checkBalanceForm.invalid) return;
    const cuentaId = this.checkBalanceForm.value.cuentaId;

    this.cuentaService.consultarSaldo(cuentaId).subscribe({
      next: (resp) => {
        this.successMessage = `Saldo disponible: $${resp.saldo}`;
        this.errorMessage = '';
      },
      error: () => {
        this.errorMessage = 'Cuenta no encontrada';
        this.successMessage = '';
      }
    });
  }

  onDepositar() {
    if (this.depositForm.invalid) return;
  
    const { cuentaId, monto } = this.depositForm.value;
  
    this.transaccionService.depositar(Number(cuentaId), Number(monto)).subscribe({
      next: (resp) => {
        this.successMessage = `Depósito realizado correctamente. Nuevo saldo: $${resp.cuentaDestino?.saldo ?? 'actualizado'}`;
        this.errorMessage = '';
        this.depositForm.reset();
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Error al realizar el depósito';
        this.successMessage = '';
      }
    });
  }
  
  

  onRetirar() {
    if (this.withdrawForm.invalid) return;
  
    const { cuentaId, monto } = this.withdrawForm.value;
  
    this.transaccionService.retirar(Number(cuentaId), Number(monto)).subscribe({
      next: (resp) => {
        this.successMessage = `Retiro realizado correctamente. Nuevo saldo: $${resp.cuentaOrigen?.saldo ?? 'actualizado'}`;
        this.errorMessage = '';
        this.withdrawForm.reset();
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Error al realizar el retiro';
        this.successMessage = '';
      }
    });
  }
  

  onTransferir() {
    if (this.transferForm.invalid) return;
  
    const { origenId, destinoId, monto } = this.transferForm.value;
  
    this.transaccionService.transferir(Number(origenId), Number(destinoId), Number(monto)).subscribe({
      next: (resp) => {
        this.successMessage = `Transferencia realizada correctamente. Nuevo saldo origen: $${resp.cuentaOrigen?.saldo ?? 'actualizado'}`;
        this.errorMessage = '';
        this.transferForm.reset();
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Error al realizar la transferencia';
        this.successMessage = '';
      }
    });
  }
  
}
