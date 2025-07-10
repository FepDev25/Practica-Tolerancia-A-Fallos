
# Documentación de la API de Upsbank

## Tabla de Contenidos
1.  [Controlador de Cuentas](#controlador-de-cuentas)
2.  [Controlador de Transacciones](#controlador-de-transacciones)
3.  [Controlador de Usuarios](#controlador-de-usuarios)
4.  [Manejo de Errores](#manejo-de-errores)

---

## Controlador de Cuentas

**Ruta base:** `/api/cuenta`

### `GET /`

*   **Descripción:** Obtiene una lista de todas las cuentas.
*   **Respuestas:**
    *   `200 OK`: Devuelve un array de objetos de cuenta.

### `GET /{id}`

*   **Descripción:** Obtiene una cuenta por su ID.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID de la cuenta.
*   **Respuestas:**
    *   `200 OK`: Devuelve el objeto de la cuenta.
    *   `404 Not Found`: Si la cuenta no existe.

### `GET /estadoCuenta`

*   **Descripción:** Obtiene todos los posibles estados de cuenta.
*   **Respuestas:**
    *   `200 OK`: Devuelve un array de objetos de estado de cuenta.

### `GET /estadoCuenta/{id}`

*   **Descripción:** Obtiene un estado de cuenta por su ID.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID del estado de la cuenta.
*   **Respuestas:**
    *   `200 OK`: Devuelve el objeto de estado de la cuenta.
    *   `404 Not Found`: Si el estado de la cuenta no existe.

### `POST /`

*   **Descripción:** Crea una nueva cuenta.
*   **Cuerpo de la Solicitud:** `CuentaDAO`
    ```json
    {
      "saldo": 1000.00,
      "fechaCreacion": "2025-07-10T10:00:00",
      "usuarioId": 1,
      "estadoCuentaId": 1
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve la cuenta recién creada.
    *   `400 Bad Request`: Si hay errores de validación.

### `PUT /{id}`

*   **Descripción:** Actualiza una cuenta existente.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID de la cuenta a actualizar.
*   **Cuerpo de la Solicitud:** `CuentaDAO`
    ```json
    {
      "saldo": 1500.00,
      "fechaCreacion": "2025-07-10T10:00:00",
      "usuarioId": 1,
      "estadoCuentaId": 1
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve la cuenta actualizada.
    *   `400 Bad Request`: Si hay errores de validación.
    *   `404 Not Found`: Si la cuenta no existe.

### `DELETE /{id}`

*   **Descripción:** Elimina una cuenta.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID de la cuenta a eliminar.
*   **Respuestas:**
    *   `200 OK`: Devuelve el ID de la cuenta eliminada.
    *   `404 Not Found`: Si la cuenta no existe.

---

## Controlador de Transacciones

**Ruta base:** `/api/transaccion`

### `GET /`

*   **Descripción:** Obtiene una lista de todas las transacciones.
*   **Respuestas:**
    *   `200 OK`: Devuelve un array de objetos de transacción.

### `GET /{id}`

*   **Descripción:** Obtiene una transacción por su ID.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID de la transacción.
*   **Respuestas:**
    *   `200 OK`: Devuelve el objeto de la transacción.
    *   `404 Not Found`: Si la transacción no existe.

### `GET /tipoTransaccion`

*   **Descripción:** Obtiene todos los tipos de transacciones.
*   **Respuestas:**
    *   `200 OK`: Devuelve un array de objetos de tipo de transacción.

### `GET /tipoTransaccion/{id}`

*   **Descripción:** Obtiene un tipo de transacción por su ID.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID del tipo de transacción.
*   **Respuestas:**
    *   `200 OK`: Devuelve el objeto de tipo de transacción.
    *   `404 Not Found`: Si el tipo de transacción no existe.

### `POST /`

*   **Descripción:** Crea una nueva transacción.
*   **Cuerpo de la Solicitud:** `TransaccionDAO`
    ```json
    {
      "cuentaOrigenId": 1,
      "cuentaDestinoId": 2,
      "monto": 100.00,
      "tipoTransaccionId": 1,
      "fecha": "2025-07-10T12:00:00"
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve la transacción recién creada.
    *   `400 Bad Request`: Si hay errores de validación.

### `PUT /{id}`

*   **Descripción:** Actualiza una transacción existente.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID de la transacción a actualizar.
*   **Cuerpo de la Solicitud:** `TransaccionDAO`
    ```json
    {
      "cuentaOrigenId": 1,
      "cuentaDestinoId": 2,
      "monto": 150.00,
      "tipoTransaccionId": 1,
      "fecha": "2025-07-10T12:30:00"
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve la transacción actualizada.
    *   `400 Bad Request`: Si hay errores de validación.
    *   `404 Not Found`: Si la transacción no existe.

### `DELETE /{id}`

*   **Descripción:** Elimina una transacción.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID de la transacción a eliminar.
*   **Respuestas:**
    *   `200 OK`: Devuelve el ID de la transacción eliminada.
    *   `404 Not Found`: Si la transacción no existe.

---

## Controlador de Usuarios

**Ruta base:** `/api/usuarios`

### `GET /`

*   **Descripción:** Obtiene una lista de todos los usuarios.
*   **Respuestas:**
    *   `200 OK`: Devuelve un array de objetos de usuario.

### `GET /{id}`

*   **Descripción:** Obtiene un usuario por su ID.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID del usuario.
*   **Respuestas:**
    *   `200 OK`: Devuelve el objeto del usuario.
    *   `404 Not Found`: Si el usuario no existe.

### `POST /`

*   **Descripción:** Crea un nuevo usuario.
*   **Cuerpo de la Solicitud:** `Usuario`
    ```json
    {
      "nombre": "Felipe",
      "correo": "felipe@example.com",
      "contrasena": "password123"
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve el usuario recién creado.
    *   `400 Bad Request`: Si hay errores de validación.

### `PUT /{id}`

*   **Descripción:** Actualiza un usuario existente.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID del usuario a actualizar.
*   **Cuerpo de la Solicitud:** `Usuario`
    ```json
    {
      "nombre": "Felipe Pérez",
      "correo": "felipe.perez@example.com",
      "contrasena": "newpassword123"
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve el usuario actualizado.
    *   `400 Bad Request`: Si hay errores de validación.
    *   `404 Not Found`: Si el usuario no existe.

### `DELETE /{id}`

*   **Descripción:** Elimina un usuario.
*   **Parámetros de Ruta:**
    *   `id` (Long): El ID del usuario a eliminar.
*   **Respuestas:**
    *   `200 OK`: Devuelve el ID del usuario eliminado.
    *   `404 Not Found`: Si el usuario no existe.

### `POST /login`

*   **Descripción:** Autentica a un usuario.
*   **Cuerpo de la Solicitud:** `LoginRequest`
    ```json
    {
      "correo": "felipe@example.com",
      "contrasena": "password123"
    }
    ```
*   **Respuestas:**
    *   `200 OK`: Devuelve un mensaje de bienvenida.
    *   `401 Unauthorized`: Si las credenciales son incorrectas.

---

## Manejo de Errores

La API devuelve respuestas de error estandarizadas en el siguiente formato:

```json
{
  "message": "Mensaje detallado de la excepción",
  "error": "Descripción legible por humanos del error",
  "status": <código de estado HTTP>,
  "date": "<fecha y hora>"
}
```

### Errores Comunes

*   **`400 Bad Request`**
    *   `Fondos insuficientes`: El saldo de la cuenta no es suficiente para la transacción.
    *   `La cuenta no está activa`: La cuenta de origen o de destino no está activa.
    *   `El formato de la fecha es incorrecto. Ej: 2025-12-31T00:00:00`: El formato de fecha proporcionado no es válido.
*   **`404 Not Found`**
    *   `El usuario no existe`: El `usuarioId` proporcionado no corresponde a ningún usuario.
    *   `El estado de la cuenta no existe`: El `estadoCuentaId` proporcionado no es válido.
    *   `La cuenta no existe`: El `cuentaId` proporcionado no corresponde a ninguna cuenta.
    *   `El tipo de transacción no existe`: El `tipoTransaccionId` proporcionado no es válido.
