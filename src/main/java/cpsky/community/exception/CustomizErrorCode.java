package cpsky.community.exception;

public enum CustomizErrorCode implements ICustomizErrorCode{

    QUESTION_NOT_FOUND("问题不存在");

    private String message;
    CustomizErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
