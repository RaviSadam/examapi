package com.ravi.examapi.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ravi.examapi.Dto.ValidationRequest;
import com.ravi.examapi.Dto.ValidationResult;
import com.ravi.examapi.ErrorHandlingAndMessages.Message;
import com.ravi.examapi.ErrorHandlingAndMessages.NotFound;
import com.ravi.examapi.Model.Question;
import com.ravi.examapi.Service.ExamService;



@Controller
@ResponseBody
public class MainController {

    private final ExamService examService;
    
    @Autowired
    public MainController(ExamService examService){
        this.examService=examService;
    }

    //home url
    @GetMapping("/")
    public ResponseEntity<List<Question>> home(){
        
        return ResponseEntity.ok().body(this.examService.getQuestions(null,"All","category",false));
    }

    @GetMapping("/get-question/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable("id") long id){

        return ResponseEntity.ok().body(this.examService.getQuestion(id));
    }

    @GetMapping("/get-questions")
    public ResponseEntity<List<Question>> getQuestions(
        @RequestParam(value="diffculty",required = false) String diffculty,
        @RequestParam(value="category",required = false,defaultValue = "All") String category,
        @RequestParam(value="sortBy",required = false,defaultValue ="category") String sort,
        @RequestParam(value="correctOption",required = false) boolean correctOption 
        ){
        return ResponseEntity.ok().body(this.examService.getQuestions(diffculty,category,sort,correctOption));
    }

    @GetMapping("/get-categories")
    public ResponseEntity<Set<String>> getCategories(){
        return ResponseEntity.ok().body(this.examService.getCategories());
    }

    @PostMapping("/validate-questions")
    public ResponseEntity<ValidationResult> validateQuestions(@RequestBody List<ValidationRequest> userResponse){
        return ResponseEntity.ok().body(this.examService.validateQuestions(userResponse));
    }

    @PostMapping("/add-questions")
    //adding questions to DB
    public ResponseEntity<Message> addQuestion(@RequestBody List<Question> questions){
        this.examService.addQuestion(questions);
        return ResponseEntity.ok().body(new Message("Questions added",this.getTime()));
    }

    @PutMapping("/update-question/{id}")
    public  ResponseEntity<Message> updateQuestion(@RequestBody Question question,@PathVariable("id") long id){
        this.examService.updateQuestion(id,question);
        return ResponseEntity.ok().body(new Message("Question updated",this.getTime()));
    }

    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<Message> deleteQuestion(@PathVariable("id") long id){
        this.examService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Message("Question Deleted",this.getTime()));
    }

    //generic mapping
    @RequestMapping("/**")
    public String unknownMethod(){
        throw new NotFound("Requested path not founded","/**");
    }

    private Date getTime(){
        return new Date(System.currentTimeMillis());
    }
}
