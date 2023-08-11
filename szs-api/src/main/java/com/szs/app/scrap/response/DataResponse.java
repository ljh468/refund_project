package com.szs.app.scrap.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {

  private JsonListResponse jsonList;

  private String appVer;

  private String errMsg;

  private String company;

  private String svcCd;

  private String hostNm;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime workerResDt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime workerReqDt;

}
