package com.szs.app.global.scrap.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseData {

  private String status;

  private DataResponse data;

  private Map<String, String> errors;

}
