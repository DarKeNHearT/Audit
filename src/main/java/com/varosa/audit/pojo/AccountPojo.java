package com.varosa.audit.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class AccountPojo {
    private Long id;
    private String name;
    private boolean status;
    private Long parentId;
    private String all_parent;

}
