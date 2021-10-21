package com.example.demo.pojo;


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
    private String debitAccountName;
    private String creditAccountName;
    private String description;
    private String email;


}
