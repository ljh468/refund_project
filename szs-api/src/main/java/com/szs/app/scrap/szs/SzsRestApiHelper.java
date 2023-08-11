package com.szs.app.scrap.szs;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.szs.util.JsonUtils.mapToJson;


@Slf4j
public class SzsRestApiHelper {

  private static final String API_SERVER_HOST = "https://codetest.3o3.co.kr";

  private static final String YEAR_END_TAX_SCRAP = "/v2/scrap";

  public enum HttpMethodType { POST, GET, DELETE }

  public String scrap(final Map<String, Object> params) {
    return request(HttpMethodType.POST, YEAR_END_TAX_SCRAP, mapToJson(params));
  }

  //FIXME : 외부 api 연동 모듈 구현 예정
  public String request(HttpMethodType httpMethodType, final String apiPath, final String params) {
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                                                    .writeTimeout(30, TimeUnit.SECONDS)
                                                    .readTimeout(30, TimeUnit.SECONDS)
                                                    .build();

    String requestUrl = API_SERVER_HOST + apiPath;

    if (httpMethodType == null) {
      httpMethodType = HttpMethodType.GET;
    }
    if (params != null && params.length() > 0 && (httpMethodType == HttpMethodType.GET
        || httpMethodType == HttpMethodType.DELETE)) {
      requestUrl += params;
    }

    Request request;
    if (params != null && params.length() > 0 && httpMethodType == HttpMethodType.POST) {
      MediaType JSON = MediaType.get("application/json; charset=utf-8");
      RequestBody body = RequestBody.create(params, JSON);
      request = new Request.Builder().url(requestUrl)
                                     .post(body)
                                     .build();
    } else {
      request = new Request.Builder().url(requestUrl)
                                     .build();
    }

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      return response.body().string();
    } catch (IOException ioException) {
      log.error("IOException: {}", ioException.getMessage());
      return null;
    }
  }

  public Map<String, Object> newScrapByUserRequest(String name, String regNo) {
    Map<String, Object> requestMap = new HashMap<>();
    requestMap.put("name", name);
    requestMap.put("regNo", regNo);
    return requestMap;
  }
}
