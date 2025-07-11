import { Usuario } from './usuario.model';
import { EstadoCuenta } from './estado-cuenta.model';

export interface Cuenta {
  id: number;
  saldo: number;
  fechaCreacion: Date;
  usuario: Usuario;
  estadoCuenta: EstadoCuenta;
}
