package com.varosa.audit.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserPojo {
    private Long id;
    private String password;
    private boolean status;
    private String email;
    private Long roleId;
    private Long branchId;
}
