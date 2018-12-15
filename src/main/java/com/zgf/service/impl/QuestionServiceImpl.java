package com.zgf.service.impl;

import com.zgf.mapper.QuestionMapper;
import com.zgf.model.Question;
import com.zgf.model.QuestionExample;
import com.zgf.service.QuestionService;
import com.zgf.session.SessionUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zgf
 * Date 2018/12/8 15:39
 * Description
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void createQuestion(Question question) {
        question.setGmtModified(System.currentTimeMillis());
        question.setGmtCreate(question.getGmtModified());
        question.setUserId(SessionUtil.getUser().getId());
        questionMapper.insertSelective(question);
    }

    @Override
    public List<Question> list(Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andStatusEqualTo(new Byte("1"));
        questionExample.setOrderByClause("gmt_create desc, comment_count desc, like_count desc, view_count desc");
        return questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(page * size, size));
    }
}
