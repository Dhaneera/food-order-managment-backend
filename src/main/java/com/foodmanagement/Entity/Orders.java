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
    private String id;
    private String name;
    private String status;
    private Double price;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private LocalDateTime orderedAt;

}
