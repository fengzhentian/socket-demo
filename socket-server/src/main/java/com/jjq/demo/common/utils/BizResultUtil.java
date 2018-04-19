package com.jjq.demo.common.utils;

import com.jjq.demo.common.vo.BizResultVO;
import com.jjq.demo.constants.ErrorType;

public class BizResultUtil {

    public static <T> BizResultVO<T> success(T data) {
        BizResultVO<T> result = new BizResultVO<T>();
        result.setCode(ErrorType.BIZ_SUCCESS.getCode());
        result.setMessage(ErrorType.BIZ_SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

}
