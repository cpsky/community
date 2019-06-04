package cpsky.community.exception;

import cpsky.community.model.Comment;

public enum CustomizErrorCode implements ICustomizErrorCode{

    QUESTION_NOT_FOUND(2001,"问题不存在"),
    TARGET_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录"),
    SYS_ERROR(2004,"服务器异常"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在");

    private Integer code;
    private String message;
    CustomizErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
