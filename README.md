#🏦 Sistema Bancario en Java

📖 Descripción
Sistema bancario interactivo desarrollado en Java que aplica principios de Programación Orientada a Objetos (POO) como encapsulación, responsabilidad única y manejo de excepciones.


#🚀 Características Implementadas
✅ 1. Transferencias entre Cuentas
Transferencia segura de fondos entre diferentes cuentas

Validación de saldo suficiente

Registro automático en historial de transacciones

Confirmación de transferencia exitosa

✅ 2. Historial de Transacciones
Registro completo de todas las operaciones realizadas

Fecha y hora exacta de cada transacción

Tipos de transacción específicos (Depósito, Retiro, Transferencia, Interés)

Consulta del historial por cuenta

✅ 3. Aplicación de Intereses y Cargos
Cálculo automático de intereses sobre el saldo actual

Aplicación de cargos por mantenimiento

Validación de saldo para cargos

Registro en el historial de transacciones

🛠️ Tecnologías
Lenguaje: Java

Paradigma: Programación Orientada a Objetos (POO)

Manejo de Excepciones: Personalizadas

Estructuras de Datos: Listas, Enums

📥 Instalación y Ejecución
Prerrequisitos
Java JDK 8 o superior

Editor de código o IDE (opcional)

Pasos para ejecutar:
Descargar el código:

bash
git clone <url-del-repositorio>
cd <directorio-del-proyecto>
Compilar el programa:

bash
javac BancoInteractivo.java
Ejecutar el sistema:

bash
java BancoInteractivo
🎮 Uso del Sistema
Cuentas Predefinidas (para pruebas):
text
📊 Cuenta: 001 | Titular: Juan Pérez | Saldo: $1000.00
📊 Cuenta: 002 | Titular: María García | Saldo: $500.00
📊 Cuenta: 003 | Titular: Carlos López | Saldo: $1500.00
Menú de Opciones:
💰 Depositar dinero - Agregar fondos a una cuenta

🏧 Retirar dinero - Retirar fondos (con validación de saldo)

🔄 Transferir entre cuentas - Enviar dinero a otra cuenta

📊 Ver saldo - Consultar saldo actual

📋 Ver historial - Revisar todas las transacciones

📈 Aplicar interés - Agregar intereses a una cuenta

👥 Listar cuentas - Ver todas las cuentas existentes

➕ Crear nueva cuenta - Registrar nueva cuenta bancaria

❌ Salir - Terminar el programa

🛡️ Manejo de Errores
El sistema incluye excepciones personalizadas:

SaldoInsuficienteException - Cuando no hay fondos suficientes

CuentaNoEncontradaException - Cuando la cuenta no existe

MontoInvalidoException - Cuando el monto es incorrecto

🏗️ Estructura del Proyecto
text
BancoInteractivo.java
├── Clases Principales
│   ├── CuentaBancaria
│   ├── Transaccion
│   ├── TipoTransaccion (Enum)
│   └── Excepciones Personalizadas
├── Servicios
│   ├── CuentaService
│   ├── TransferenciaService
│   └── InteresService
└── Main con Menú Interactivo
📋 Principios POO Aplicados
✅ Encapsulación: Atributos privados con métodos públicos

✅ Responsabilidad Única: Cada clase tiene una función específica

✅ Manejo de Excepciones: Errores controlados y mensajes claros

✅ Abstracción: Interfaces claras entre componentes

👤 Autor
Desarrollado como proyecto educativo para aplicar principios de POO en Java.

📄 Licencia
Proyecto educativo - Libre uso y modificación.
