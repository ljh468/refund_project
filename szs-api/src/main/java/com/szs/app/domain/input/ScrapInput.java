package com.szs.app.domain.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class ScrapInput {

  @NotNull
  private String name;

  @NotNull
  private String regNo;

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("name", name)
        .append("regNo", regNo)
        .toString();
  }
}
