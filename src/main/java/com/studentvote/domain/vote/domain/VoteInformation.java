package com.studentvote.domain.vote.domain;

import com.studentvote.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;

    private String dateTime;

    private String description;

    private String guideImageUrl;


    public VoteInformation(String name, String dateTime, String description, String guideImageUrl) {
        this.name = name;
        this.dateTime = dateTime;
        this.description = description;
        this.guideImageUrl = guideImageUrl;
    }

    public static VoteInformation of(String name, String dateTime, String description, String guideImageUrl) {
        return new VoteInformation(name, dateTime, description, guideImageUrl);
    }
}
