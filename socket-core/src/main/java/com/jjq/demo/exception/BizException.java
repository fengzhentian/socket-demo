package com.jjq.demo.exception;

import com.jjq.demo.constants.ErrorCode;

/**
 * 业务异常
 * 
 * @author jingjq
 * @version 2017-11-29 10:15:31
 */
public class BizException extends AppException {

    private static final long serialVersionUID = 1L;

    /**
     * 无参构造函数
     */
    public BizException() {
        super();
    }

    public BizException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BizException(ErrorCode errorCode, String... errMsg) {
        super(errorCode, errMsg);
    }

    public BizException(ErrorCode errorCode, String errMsg,
            Boolean isTransfer) {
        super(errorCode, errMsg, isTransfer);
    }

    public BizException(ErrorCode errCode, Throwable cause, String... errMsg) {
        super(errCode, cause, errMsg);
    }

}
