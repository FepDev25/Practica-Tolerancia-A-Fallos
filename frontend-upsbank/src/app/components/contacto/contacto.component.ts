import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './contacto.component.html',
  styleUrl: './contacto.component.css'
})
export class ContactoComponent {
  contactoForm: FormGroup;
  enviado = false;
  mensajeExito = '';
  mensajeError = '';

  constructor(private fb: FormBuilder) {
    this.contactoForm = this.fb.group({
      nombre: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      mensaje: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.contactoForm.invalid) {
      this.mensajeError = 'Por favor completa todos los campos correctamente.';
      this.mensajeExito = '';
      return;
    }



// Simulación de envío exitoso
this.mensajeExito = '¡Tu mensaje ha sido enviado correctamente!';
this.mensajeError = '';
this.contactoForm.reset();
}

}
