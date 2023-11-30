package com.poly.EasyLearning.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "result")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date = new Date();

    private Boolean active = true;

    @OneToOne(mappedBy = "result", cascade = CascadeType.ALL)
    private Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "result")
    private List<ResultItem> resultItems = new ArrayList<>();
}
