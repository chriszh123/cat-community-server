package com.zgf.controller;

import com.zgf.dto.ResultDTO;
import com.zgf.error.CommonErrorCode;
import com.zgf.model.Question;
import com.zgf.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zgf
 * Date 2018/12/8 16:02
 * Description
 */
@RestController
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "api/question", method = RequestMethod.POST)
    public ResultDTO post(@RequestBody Question question) {
        try {
            questionService.createQuestion(question);
            return ResultDTO.ok(null);
        } catch (Exception e) {
            log.error("QuestionController post error, question : {}", question, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }

    @RequestMapping(value = "api/question/list", method = RequestMethod.GET)
    public ResultDTO list(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        try {
            List<Question> questions = questionService.list(page, size);
            return ResultDTO.ok(questions);
        } catch (Exception e) {
            log.error("QuestionController post error", e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }
}
