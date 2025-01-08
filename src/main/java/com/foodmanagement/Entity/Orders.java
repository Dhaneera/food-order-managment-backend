package com.foodmanagement.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @Column(name = "ordersId")
    private String Id;
    private String name;
    private String status;
    private Double price;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime orderedAt;

    @ManyToOne
    @JoinColumn(name = "studentId" , referencedColumnName = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "pirivenStudentId" , referencedColumnName = "pirivenStudentId")
    private PirivenStudent pirivenStudent;

    @ManyToOne
    @JoinColumn(name = "staffId" , referencedColumnName = "staffId")
    private Staff staff;

}
