package br.com.henrique.cliente.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CPF, String> {

  @Override
  public void initialize(CPF constraintAnnotation) {
  }

  @Override
  public boolean isValid(String cpf, ConstraintValidatorContext context) {
    if (cpf == null || cpf.trim().isEmpty()) {
      return true;
    }

    cpf = cpf.replaceAll("[^\\d]", "");

    if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (10 - i);
    }
    int remainder = sum % 11;
    int digit1 = remainder < 2 ? 0 : 11 - remainder;
    if (digit1 != Integer.parseInt(String.valueOf(cpf.charAt(9)))) {
      return false;
    }

    sum = 0;
    for (int i = 0; i < 10; i++) {
      sum += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (11 - i);
    }
    remainder = sum % 11;
    int digit2 = remainder < 2 ? 0 : 11 - remainder;
    return digit2 == Integer.parseInt(String.valueOf(cpf.charAt(10)));
  }
}
