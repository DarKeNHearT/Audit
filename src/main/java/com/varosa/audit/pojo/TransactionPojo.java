package com.varosa.audit.pojo;


import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPojo {
    private Long id;
    private Date createdDate;
    private long amount;
    private boolean status;
    private Long debitAccountId;
    private Long creditAccountId;
    private String description;
    private Long userId;


}
