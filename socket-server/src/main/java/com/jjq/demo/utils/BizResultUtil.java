package com.jjq.demo.utils;

import com.jjq.demo.constants.ErrorType;
import com.jjq.demo.web.vo.BizResultVO;

public class BizResultUtil {

    public static <T> BizResultVO<T> success(T data) {
        BizResultVO<T> result = new BizResultVO<T>();
        result.setCode(ErrorType.BIZ_SUCCESS.getCode());
        result.setMessage(ErrorType.BIZ_SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

}
