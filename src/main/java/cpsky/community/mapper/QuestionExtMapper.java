package cpsky.community.mapper;

import cpsky.community.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: sky
 * @Date: 2019/5/31 15:49
 */
@Component
public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
}
