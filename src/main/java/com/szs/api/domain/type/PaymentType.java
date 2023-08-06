package com.szs.api.domain.type;

public enum PaymentType {

  INSURANCE("보험료"),

  EDUCATION("교육비"),

  DONATION("기부금"),

  MEDICAL("의료비"),

  RETIREMENT("퇴직연금");

  private final String name;

  PaymentType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
