package com.ravi.examapi.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ravi.examapi.Dto.CategoryProjection;
import com.ravi.examapi.Model.Question;

import jakarta.transaction.Transactional;

public interface QuestionsRepo extends JpaRepository<Question,Long>{

    @Query(value="SELECT DISTINCT category FROM Questions",nativeQuery = true)
    public Set<CategoryProjection> getCategories();

    @Query(value="SELECT * FROM questions ORDER BY RAND(),:sort DESC LIMIT 20",nativeQuery = true)
    public List<Question> findAll(String sort);

    @Query(value="SELECT * FROM questions where diffculty=:diffculty ORDER BY RAND(),:sort DESC LIMIT 20",nativeQuery = true)
    public List<Question> findByDiffculty(@Param("diffculty") String diffculty,String sort);

    @Query(value="SELECT * FROM questions WHERE category=:category ORDER BY RAND(),:sort DESC LIMIT 20",nativeQuery = true)
    public List<Question> findByCategory(@Param("category") String category,String sort);

    @Query(value="SELECT * FROM questions WHERE categort=:category AND diffculty=:diffculty ORDER BY RAND(),:sort DESC LIMIT 20",nativeQuery = true)
    public List<Question> findByCategoryAndDiffculty(@Param("category") String category,@Param("diffculty") String diffculty,String sort);

    @Query(value="SELECT * FROM questions WHERE id IN :questionIds",nativeQuery = true)
    public List<Question> findAllByQuestionId(@Param("questionIds") Set<Long> set);

    public Question findByQuestionId(long questionId);

    @Transactional
    public long deleteByQuestionId(long id);
}
