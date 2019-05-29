package cpsky.community.service;

import cpsky.community.dto.PaginationDTO;
import cpsky.community.dto.QuestionDto;
import cpsky.community.mapper.QuestionMapper;
import cpsky.community.mapper.UserMapper;
import cpsky.community.model.Question;
import cpsky.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sky
 * @Date: 2019/5/24 14:32
 */
//使用多个Mapper,中间层
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        if (page < 1) {
            page = 1;
        }
        if (page > questionMapper.count()) {
            if(questionMapper.count() % size == 0)
            page = questionMapper.count() / size;
            else {
                page = questionMapper.count() / size + 1;
            }
        }
        Integer offset = size * (page - 1);
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDTO.setQuestions(questionDtoList);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {
        if (page < 1) {
            page = 1;
        }
        if (page > questionMapper.count()) {
            if(questionMapper.count() % size == 0)
                page = questionMapper.count() / size;
            else {
                page = questionMapper.count() / size + 1;
            }
        }
        Integer offset = size * (page - 1);
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Question> questions = questionMapper.listByUserId(userId,offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDTO.setQuestions(questionDtoList);
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }
}

