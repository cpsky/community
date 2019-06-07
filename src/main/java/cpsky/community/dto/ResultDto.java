package cpsky.community.dto;

import cpsky.community.exception.CustomizErrorCode;
import cpsky.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author: sky
 * @Date: 2019/6/3 16:54
 */
@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDto errof(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errof(CustomizeException ex) {
        return errof(ex.getCode(), ex.getMessage());
    }
    public static ResultDto errof(CustomizErrorCode errorCode) {
        return errof(errorCode.getCode(), errorCode.getMessage());
    }
    public static ResultDto okOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }
    public static <T> ResultDto okOf(T t) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }
}
