package SimplifyPay.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"user\"") 
public class UserEntity {
    
    @Id
    private UUID id;

    private String name;

    private String document;

    @Email
    private String email;

    @NotNull
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletEntity wallet;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "created_at")
    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "updated_at")
    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
