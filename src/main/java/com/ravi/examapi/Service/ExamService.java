package com.ravi.examapi.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ravi.examapi.Dto.CategoryProjection;
import com.ravi.examapi.Dto.QuestionDto;
import com.ravi.examapi.Dto.ValidationRequest;
import com.ravi.examapi.Dto.ValidationResult;
import com.ravi.examapi.ErrorHandlingAndMessages.QuestionNotFound;
import com.ravi.examapi.Model.Question;
import com.ravi.examapi.Repository.QuestionsRepo;


@Service
public class ExamService {

    private final QuestionsRepo questionsRepo;
    
    @Autowired
    public ExamService(QuestionsRepo questionsRepo){
        this.questionsRepo=questionsRepo;
    }

    //gives the questions
    @Cacheable(cacheNames = "questions",key="#category")
    public List<Question> getQuestions(String diffculty, String category, String sort,boolean correctOption) {
        List<Question> result=new ArrayList<>();
        if(diffculty==null && (category==null || category.equals("All"))){
            result=questionsRepo.findAll(sort);
        }
        else if(diffculty==null){
            result=questionsRepo.findByCategory(category,sort);
        }
        else if(category==null){
            result=questionsRepo.findByDiffculty(diffculty,sort);
        }
        else{
            result=questionsRepo.findByCategoryAndDiffculty(category,diffculty,sort);
        }
        if(correctOption){
            for(Question question:result){
                question.getOptions().add(question.getCorrectOption());
                question.setCorrectOption(null);
            }
        }
        return result;  
    }

    //return all avaliable question categories
    public Set<String> getCategories(){
        Set<String> categories=new HashSet<>();
        for(CategoryProjection projection:questionsRepo.getCategories()){
            categories.add(projection.getCategory());
        }
        return categories;
    }


    //validates the user options
    public ValidationResult validateQuestions(List<ValidationRequest> userResponse){
        HashMap<Long,String> map=new HashMap<>();
        for(ValidationRequest request:userResponse){
            map.put(request.getId(),request.getSelectedOption());
        }
        return this.convertToValidationResult(this.questionsRepo.findAllByQuestionId(map.keySet()),userResponse,map);
    }

    //validates the user options
    private ValidationResult convertToValidationResult(List<Question> data,List<ValidationRequest> userResponse,HashMap<Long,String> map){
        ValidationResult validationResult=new ValidationResult();
        List<QuestionDto> questionDtos=new ArrayList<>();
        long score=0;
        for(Question question:data){
            QuestionDto questionDto=new QuestionDto();
            questionDto.setCorrectOption(question.getCorrectOption());
            questionDto.setId(question.getQuestionId());
            question.getOptions().add(question.getCorrectOption());
            questionDto.setOptions(question.getOptions());
            questionDto.setSelectedOption(map.get(question.getQuestionId()));
            questionDtos.add(questionDto);
            questionDto.setQuestion(question.getQuestion());
            if(questionDto.getSelectedOption().equals(question.getCorrectOption()))
                score++;
        }
        validationResult.setDetails(questionDtos);
        validationResult.setScore(score);
        return validationResult;
    }

    //adding question to DB
    public void addQuestion(List<Question> questions){
        this.questionsRepo.saveAll(questions);
    }

    @CachePut(cacheNames = "questions",key="#id")
    public void updateQuestion(long id,Question updateQuestion){
        Question question=this.questionsRepo.findByQuestionId(id);
        if(question==null)
            throw new QuestionNotFound("Question not fond with id:"+id);
        question.setCategory(updateQuestion.getCategory());
        question.setCorrectOption(updateQuestion.getCorrectOption());
        question.setDiffculty(updateQuestion.getDiffculty());
        question.setOptions(updateQuestion.getOptions());
        question.setQuestion(updateQuestion.getQuestion());
        questionsRepo.save(question);
    }

    //returns the question with id
    @Cacheable(cacheNames = "questions",key="#id")
    public Question getQuestion(long id){
        System.out.println("fetched from DB");
        Question question=this.questionsRepo.findByQuestionId(id);
        if(question==null)
            throw new QuestionNotFound("Question not fond with id:"+id);
        return question;
    }

    @CacheEvict(cacheNames="questions",key="#id")
    public void deleteQuestion(long id){
        this.getQuestion(id);
        this.questionsRepo.deleteByQuestionId(id);
    }

}
