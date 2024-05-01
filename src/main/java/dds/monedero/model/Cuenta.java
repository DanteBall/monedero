package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }


//  de esta manera podemos simplificar la funcion y dejarlo mas declarativo
  public void poner(double cuanto) {
    //validaciones de parametro
    montoNoNegativo(cuanto);

    //validaciones de negocio
    depositoValido();

    //comportamiento
    Movimiento movimiento = new Movimiento(LocalDate.now(), cuanto, true);
    setSaldo(getSaldo()+ movimiento.calcularValor());
    agregarMovimiento(movimiento);
  }

  public void sacar(double cuanto) {
    //validaciones de parametro
    montoNoNegativo(cuanto);
    saldoNoMenor(cuanto);

    //validaciones de negocio
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    extraccionValida(cuanto,limite);

    //comportamiento
    Movimiento movimiento = new Movimiento(LocalDate.now(), cuanto, false);
    setSaldo(getSaldo()+ movimiento.calcularValor());
    agregarMovimiento(movimiento);
  }


  private void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  public void montoNoNegativo(double cuanto){
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void extraccionValida(double cuanto,double limite){
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }
  public void saldoNoMenor(double cuanto){
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  public void depositoValido(){
    if (getMovimientos().stream().filter(Movimiento::esDeposito).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.esDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
