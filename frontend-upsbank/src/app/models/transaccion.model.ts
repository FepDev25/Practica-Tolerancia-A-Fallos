import { Cuenta } from './cuenta.model';
import { TipoTransaccion } from './tipo-transaccion.model';

export interface Transaccion {
  id: number;
  cuentaOrigen: Cuenta;
  cuentaDestino: Cuenta;
  monto: number;
  tipoTransaccion: TipoTransaccion;
  fecha: Date;
}
