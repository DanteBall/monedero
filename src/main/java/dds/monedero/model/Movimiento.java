package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private double monto;
  private boolean esDeposito;

  public Movimiento(LocalDate fecha, double monto, boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  //  los metodos fueDepositado() y fue extraido() no se utilizan,
//  ademas no tienen sentido, todos los movimientos se extraen o depositan al momento,
//  de su creacion. Tendria sentido si el sistema permitiera planificar movimientos a futuro, pero
//  no es el caso y podria englobarse en un solo metodo
  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }
  public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

//  los metodos isDeposito() y is Extraccion() son innecesarios,
//  se puede usar la variable esDeposito directamente
  public boolean isDeposito() {
    return esDeposito;
  }

  public boolean isExtraccion() {
    return !esDeposito;
  }

//  no es responsabilidad del movimiento agregarse en la cuenta
//  y setear atributos internos de la cuenta
  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto, esDeposito);
  }

//  en el sistema actualmente solo se realizan sumas y restas, pero si el sistema creciera
//  y se agregan nuevos movimientos o se complejizan los procesos quiza seria mejor separar
//  los depositos y extracciones en clases distintas
  public double calcularValor(Cuenta cuenta) {
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto();
    }
  }
}
