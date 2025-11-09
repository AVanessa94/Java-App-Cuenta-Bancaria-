# 🏦 **Sistema Bancario en Java**

***

## 📖 **Descripción**  
Sistema bancario interactivo desarrollado en Java que aplica principios de Programación Orientada a Objetos (POO) como encapsulación, responsabilidad única y manejo de excepciones.

***

## 🚀 **Características Implementadas**

### **✅ 1. Transferencias entre Cuentas**  
- **Transferencia segura** de fondos entre diferentes cuentas  
- **Validación de saldo** suficiente  
- **Registro automático** en historial de transacciones  
- **Confirmación** de transferencia exitosa  

***

### **✅ 2. Historial de Transacciones**  
- **Registro completo** de todas las operaciones realizadas  
- **Fecha y hora exacta** de cada transacción  
- **Tipos de transacción específicos** (Depósito, Retiro, Transferencia, Interés)  
- **Consulta del historial** por cuenta  

***

### **✅ 3. Aplicación de Intereses y Cargos**  
- **Cálculo automático** de intereses sobre el saldo actual  
- **Aplicación de cargos** por mantenimiento  
- **Validación de saldo** para cargos  
- **Registro** en el historial de transacciones  

***
  
## 🛠️ **Tecnologías**  
- **Lenguaje:** Java  
- **Paradigma:** Programación Orientada a Objetos (POO)  
- **Manejo de Excepciones:** Personalizadas  
- **Estructuras de Datos:** Listas, Enums  

***

## 📥 **Instalación y Ejecución**

### **Prerrequisitos**  
- **Java JDK 8** o superior  


## ▶️ **Pasos para ejecutar**

**1️⃣ Compilar el programa**
```bash
javac BancoInteractivo.java
```

**2️⃣ Ejecutar el sistema**
```bash
java BancoInteractivo
```

---

## 💻 **Uso del Sistema**

Al iniciar, se cargan cuentas predefinidas:

- **Cuenta:** 001 | **Titular:** Juan Pérez  
- **Cuenta:** 002 | **Titular:** María García  
- **Cuenta:** 003 | **Titular:** Carlos López  

---

## 🧭 **Menú de Opciones**

- Depositar dinero  
- Retirar dinero  
- Transferir entre cuentas  
- Ver saldo  
- Ver historial  
- Aplicar interés  
- Listar cuentas  
- Crear nueva cuenta  
- Salir  

---

## 🚨 **Manejo de Errores**

- **SaldoInsuficienteException** → Cuando no hay fondos suficientes  
- **CuentaNoEncontradaException** → Cuando la cuenta no existe  
- **MontoInvalidoException** → Cuando el monto es incorrecto  

---

## 🧩 **Estructura del Proyecto**

```
BancoInteractivo/
├── Main.java
├── modelos/
│   ├── CuentaBancaria.java
│   ├── Transaccion.java
│   ├── TipoTransaccion.java
├── excepciones/
│   ├── SaldoInsuficienteException.java
│   ├── CuentaNoEncontradaException.java
│   ├── MontoInvalidoException.java
├── servicios/
│   ├── CuentaService.java
│   ├── TransferenciaService.java
│   ├── InteresService.java
```

---

## 🧠 **Principios POO Aplicados**

- **Encapsulación:** Atributos privados con métodos públicos.  
- **Responsabilidad Única:** Cada clase cumple una función específica.  
- **Abstracción:** Interfaces claras entre componentes.  
- **Manejo de Excepciones:** Errores controlados y mensajes claros.  
- **Abierto/Cerrado:** Fácil de extender sin modificar el código base.  
