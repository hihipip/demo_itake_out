package com.boss.demo.handler;

import com.boss.demo.tools.CodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GlobalException extends RuntimeException{
    private CodeMsg codeMsg;
    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
