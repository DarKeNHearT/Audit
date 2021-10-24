package com.varosa.audit.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class JournalPojo {
    private Long id;
    private Date fromDate;
    private Date toDate;
}
