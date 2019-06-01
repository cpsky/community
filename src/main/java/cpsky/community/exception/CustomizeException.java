package cpsky.community.exception;

/**
 * @Author: sky
 * @Date: 2019/5/30 19:31
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
