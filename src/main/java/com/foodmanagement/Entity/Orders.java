package com.foodmanagement.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @Column(name = "ordersId")
    private UUID id;
    private String name;
    private String status;
    private Double price;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime orderedAt;

}
