import java.util.*;
import java.time.LocalDateTime;

// Excepciones personalizadas
class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String mensaje) { super(mensaje); }
}

class CuentaNoEncontradaException extends RuntimeException {
    public CuentaNoEncontradaException(String mensaje) { super(mensaje); }
}

class MontoInvalidoException extends RuntimeException {
    public MontoInvalidoException(String mensaje) { super(mensaje); }
}

// Enums para tipos de transacción
enum TipoTransaccion {
    DEPOSITO, RETIRO, TRANSFERENCIA_ENVIADA, TRANSFERENCIA_RECIBIDA, APLICACION_INTERES
}

// Clase Transacción
class Transaccion {
    private TipoTransaccion tipo;
    private double monto;
    private LocalDateTime fecha;
    private String descripcion;

    public Transaccion(TipoTransaccion tipo, double monto, String descripcion) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
    }

    public TipoTransaccion getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return String.format("[%s] %s - $%.2f - %s", fecha, tipo, monto, descripcion);
    }
}

// Clase principal Cuenta Bancaria
class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private List<Transaccion> historialTransacciones;

    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.historialTransacciones = new ArrayList<>();
        registrarTransaccion(TipoTransaccion.DEPOSITO, saldoInicial, "Depósito inicial");
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public List<Transaccion> getHistorialTransacciones() { return new ArrayList<>(historialTransacciones); }

    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        this.saldo += monto;
        registrarTransaccion(TipoTransaccion.DEPOSITO, monto, "Depósito realizado");
        System.out.println("✅ Depósito exitoso: $" + monto);
    }

    public void retirar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        if (monto > saldo) throw new SaldoInsuficienteException("Saldo insuficiente. Tienes: $" + saldo);
        this.saldo -= monto;
        registrarTransaccion(TipoTransaccion.RETIRO, monto, "Retiro realizado");
        System.out.println("✅ Retiro exitoso: $" + monto);
    }

    public void aplicarInteres(double monto, String descripcion) {
        this.saldo += monto;
        registrarTransaccion(TipoTransaccion.APLICACION_INTERES, monto, descripcion);
    }

    public void registrarTransaccion(TipoTransaccion tipo, double monto, String descripcion) {
        historialTransacciones.add(new Transaccion(tipo, monto, descripcion));
    }

    @Override
    public String toString() {
        return String.format("Cuenta: %s | Titular: %s | Saldo: $%.2f", numeroCuenta, titular, saldo);
    }
}

// Servicio para manejar cuentas
class CuentaService {
    private List<CuentaBancaria> cuentas = new ArrayList<>();

    public void agregarCuenta(CuentaBancaria cuenta) { cuentas.add(cuenta); }

    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) return cuenta;
        }
        throw new CuentaNoEncontradaException("Cuenta no encontrada: " + numeroCuenta);
    }

    public List<Transaccion> obtenerHistorial(String numeroCuenta) {
        return buscarCuenta(numeroCuenta).getHistorialTransacciones();
    }

    public List<CuentaBancaria> getCuentas() { return new ArrayList<>(cuentas); }
    
    public boolean existeCuenta(String numeroCuenta) {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) return true;
        }
        return false;
    }
}

// Servicio para transferencias
class TransferenciaService {
    private CuentaService cuentaService;

    public TransferenciaService(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    public void transferir(String cuentaOrigen, String cuentaDestino, double monto) {
        if (monto <= 0) throw new MontoInvalidoException("El monto debe ser positivo");

        CuentaBancaria origen = cuentaService.buscarCuenta(cuentaOrigen);
        CuentaBancaria destino = cuentaService.buscarCuenta(cuentaDestino);

        if (origen.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo actual: $" + origen.getSaldo());
        }

        // Realizar transferencia
        origen.retirar(monto);
        destino.depositar(monto);

        // Registrar transacciones específicas
        origen.registrarTransaccion(TipoTransaccion.TRANSFERENCIA_ENVIADA, monto, "Transferencia a: " + cuentaDestino);
        destino.registrarTransaccion(TipoTransaccion.TRANSFERENCIA_RECIBIDA, monto, "Transferencia de: " + cuentaOrigen);

        System.out.println("✅ Transferencia exitosa: $" + monto + " de " + cuentaOrigen + " a " + cuentaDestino);
    }
}

// Servicio para intereses
class InteresService {
    public void aplicarInteresMensual(CuentaBancaria cuenta, double tasaInteres) {
        if (tasaInteres <= 0) throw new IllegalArgumentException("La tasa debe ser positiva");
        
        double interes = cuenta.getSaldo() * (tasaInteres / 100);
        cuenta.aplicarInteres(interes, String.format("Interés mensual (%.2f%%)", tasaInteres));
        
        System.out.printf("✅ Interés de $%.2f aplicado a %s%n", interes, cuenta.getNumeroCuenta());
    }

    public void aplicarCargoPorMantenimiento(CuentaBancaria cuenta, double cargo) {
        if (cargo <= 0) throw new IllegalArgumentException("El cargo debe ser positivo");

        if (cuenta.getSaldo() >= cargo) {
            cuenta.retirar(cargo);
            System.out.printf("⚠️  Cargo de $%.2f aplicado a %s%n", cargo, cuenta.getNumeroCuenta());
        } else {
            System.out.printf("❌ No se pudo aplicar cargo. Saldo insuficiente en %s%n", cuenta.getNumeroCuenta());
        }
    }
}

// CLASE PRINCIPAL CON MENÚ INTERACTIVO
public class BancoInteractivo {
    private static Scanner scanner = new Scanner(System.in);
    private static CuentaService cuentaService = new CuentaService();
    private static TransferenciaService transferenciaService = new TransferenciaService(cuentaService);
    private static InteresService interesService = new InteresService();

    public static void main(String[] args) {
        // Crear algunas cuentas de ejemplo
        inicializarCuentas();
        
        System.out.println("🏦 BIENVENIDO AL SISTEMA BANCARIO INTERACTIVO 🏦");
        
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    depositar();
                    break;
                case 2:
                    retirar();
                    break;
                case 3:
                    transferir();
                    break;
                case 4:
                    verSaldo();
                    break;
                case 5:
                    verHistorial();
                    break;
                case 6:
                    aplicarInteres();
                    break;
                case 7:
                    listarCuentas();
                    break;
                case 8:
                    crearCuenta();
                    break;
                case 0:
                    salir = true;
                    System.out.println("👋 ¡Gracias por usar nuestro sistema bancario!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intenta de nuevo.");
            }
            
            if (!salir) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private static void inicializarCuentas() {
        cuentaService.agregarCuenta(new CuentaBancaria("001", "Juan Pérez", 1000.0));
        cuentaService.agregarCuenta(new CuentaBancaria("002", "María García", 500.0));
        cuentaService.agregarCuenta(new CuentaBancaria("003", "Carlos López", 1500.0));
    }
    
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📋 MENÚ PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. 💰 Depositar dinero");
        System.out.println("2. 🏧 Retirar dinero");
        System.out.println("3. 🔄 Transferir entre cuentas");
        System.out.println("4. 📊 Ver saldo de cuenta");
        System.out.println("5. 📋 Ver historial de transacciones");
        System.out.println("6. 📈 Aplicar interés a cuenta");
        System.out.println("7. 👥 Listar todas las cuentas");
        System.out.println("8. ➕ Crear nueva cuenta");
        System.out.println("0. ❌ Salir del sistema");
        System.out.println("=".repeat(50));
        System.out.print("Selecciona una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void depositar() {
        System.out.println("\n💰 DEPOSITAR DINERO");
        System.out.print("Número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        
        if (!cuentaService.existeCuenta(numeroCuenta)) {
            System.out.println("❌ La cuenta no existe.");
            return;
        }
        
        System.out.print("Monto a depositar: $");
        try {
            double monto = Double.parseDouble(scanner.nextLine());
            CuentaBancaria cuenta = cuentaService.buscarCuenta(numeroCuenta);
            cuenta.depositar(monto);
        } catch (NumberFormatException e) {
            System.out.println("❌ Monto inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void retirar() {
        System.out.println("\n🏧 RETIRAR DINERO");
        System.out.print("Número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        
        if (!cuentaService.existeCuenta(numeroCuenta)) {
            System.out.println("❌ La cuenta no existe.");
            return;
        }
        
        System.out.print("Monto a retirar: $");
        try {
            double monto = Double.parseDouble(scanner.nextLine());
            CuentaBancaria cuenta = cuentaService.buscarCuenta(numeroCuenta);
            cuenta.retirar(monto);
        } catch (NumberFormatException e) {
            System.out.println("❌ Monto inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void transferir() {
        System.out.println("\n🔄 TRANSFERIR ENTRE CUENTAS");
        System.out.print("Cuenta de origen: ");
        String cuentaOrigen = scanner.nextLine();
        System.out.print("Cuenta de destino: ");
        String cuentaDestino = scanner.nextLine();
        
        if (!cuentaService.existeCuenta(cuentaOrigen) || !cuentaService.existeCuenta(cuentaDestino)) {
            System.out.println("❌ Una de las cuentas no existe.");
            return;
        }
        
        System.out.print("Monto a transferir: $");
        try {
            double monto = Double.parseDouble(scanner.nextLine());
            transferenciaService.transferir(cuentaOrigen, cuentaDestino, monto);
        } catch (NumberFormatException e) {
            System.out.println("❌ Monto inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void verSaldo() {
        System.out.println("\n📊 VER SALDO");
        System.out.print("Número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        
        try {
            CuentaBancaria cuenta = cuentaService.buscarCuenta(numeroCuenta);
            System.out.println("💵 Saldo actual: $" + cuenta.getSaldo());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void verHistorial() {
        System.out.println("\n📋 HISTORIAL DE TRANSACCIONES");
        System.out.print("Número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        
        try {
            List<Transaccion> historial = cuentaService.obtenerHistorial(numeroCuenta);
            if (historial.isEmpty()) {
                System.out.println("No hay transacciones registradas.");
            } else {
                System.out.println("📜 Transacciones:");
                for (Transaccion transaccion : historial) {
                    System.out.println("  " + transaccion);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void aplicarInteres() {
        System.out.println("\n📈 APLICAR INTERÉS");
        System.out.print("Número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        
        if (!cuentaService.existeCuenta(numeroCuenta)) {
            System.out.println("❌ La cuenta no existe.");
            return;
        }
        
        System.out.print("Tasa de interés (%): ");
        try {
            double tasa = Double.parseDouble(scanner.nextLine());
            CuentaBancaria cuenta = cuentaService.buscarCuenta(numeroCuenta);
            interesService.aplicarInteresMensual(cuenta, tasa);
        } catch (NumberFormatException e) {
            System.out.println("❌ Tasa inválida.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void listarCuentas() {
        System.out.println("\n👥 LISTA DE CUENTAS");
        List<CuentaBancaria> cuentas = cuentaService.getCuentas();
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
        } else {
            for (CuentaBancaria cuenta : cuentas) {
                System.out.println("  " + cuenta);
            }
        }
    }
    
    private static void crearCuenta() {
        System.out.println("\n➕ CREAR NUEVA CUENTA");
        System.out.print("Número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        
        if (cuentaService.existeCuenta(numeroCuenta)) {
            System.out.println("❌ Ya existe una cuenta con ese número.");
            return;
        }
        
        System.out.print("Nombre del titular: ");
        String titular = scanner.nextLine();
        
        System.out.print("Saldo inicial: $");
        try {
            double saldoInicial = Double.parseDouble(scanner.nextLine());
            CuentaBancaria nuevaCuenta = new CuentaBancaria(numeroCuenta, titular, saldoInicial);
            cuentaService.agregarCuenta(nuevaCuenta);
            System.out.println("✅ Cuenta creada exitosamente!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Saldo inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
