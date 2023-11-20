package com.ravi.examapi.Model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    @Id
    @SequenceGenerator(
        name="question_id_generator",
        sequenceName = "question_id_generator",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "question_id_generator"
    )
    @Column(name="id")
    @JsonProperty("id")
    private long questionId;

    @Column(name="question")
    @JsonProperty("question")
    private String question;

    @ElementCollection
    @CollectionTable(name="questions_options",joinColumns = @JoinColumn(name="id"))
    @Column(name="options")
    Set<String> options=new HashSet<>();

    @Column(name="correct_answer",length = 255)
    @JsonProperty("correct_answer")
    private String correctOption;

    @Column(columnDefinition = "INT DEFAULT 0")
    private long likes;

    @Column(length = 70)
    private String category;

    @Column(length = 30)
    private String diffculty;

    @Column(length = 30)
    private String type;
}
