package com.zgf.service;

import com.zgf.model.Question;

import java.util.List;

/**
 * Created by zgf
 * Date 2018/12/8 15:39
 * Description
 */
public interface QuestionService {
    void createQuestion(Question question);

    List<Question> list(Integer page, Integer size);
}
