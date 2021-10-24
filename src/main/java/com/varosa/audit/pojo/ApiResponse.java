package com.varosa.audit.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//@Data is a convenient shortcut annotation that bundles the features of @ToString,
// @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor together
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
@Scope("prototype")
public class ApiResponse implements Serializable {
    private int status;
    private String message;
    private Object data;
}
