package com.murphy.backend.common;

/**
 * return back response
 * @author murphy
 * @param <T>
 *     data type    数据类型
 *     message      消息
 *     code         状态码
 *     data         数据
 * @return BaseResponse<T>
 *
 */
public class ResultUtils {

    /**
     * success
     * @param data
     * @param <T>
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "success", "成功");
    }

    public static <T> BaseResponse<T> successWithoutData() {
        return new BaseResponse<>(200, "success");
    }

    /**
     * error
     * @param errorCode
     * @param <T>
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    public static <T> BaseResponse<T> error(int errorCode, String message, String description) {
        return new BaseResponse<>(errorCode, null, message, description);
    }
}
