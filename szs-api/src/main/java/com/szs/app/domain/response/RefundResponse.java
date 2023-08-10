package com.szs.app.domain.response;

import com.szs.app.domain.entity.Refund;
import lombok.*;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {

  private Long id;

  private Long determinedTax;

  private Long retirePensionCredit;

  private boolean isCompleted;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static RefundResponse from(Refund refund) {
    if (isNull(refund)) {
      return null;
    }
    return RefundResponse.builder()
                         .id(refund.getId())
                         .determinedTax(refund.getDeterminedTax())
                         .retirePensionCredit(refund.getRetirePensionCredit())
                         .isCompleted(refund.isCompleted())
                         .isDeleted(refund.isDeleted())
                         .createdAt(refund.getCreatedAt())
                         .updatedAt(refund.getUpdatedAt())
                         .build();
  }

}
