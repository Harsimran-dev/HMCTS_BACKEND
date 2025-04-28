package com.hmctsbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import com.hmctsbackend.enums.Status;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Due Date is required")
    private LocalDateTime dueDateTime;
}
