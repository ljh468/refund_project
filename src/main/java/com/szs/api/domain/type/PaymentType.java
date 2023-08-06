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

  public static PaymentType fromName(String name) {
    for (PaymentType type : PaymentType.values()) {
      if (type.name.equalsIgnoreCase(name)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No PaymentType with name " + name + " found");
  }
}
