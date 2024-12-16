package SimplifyPay.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name="transaction")
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payer_wallet_id", nullable = false)
    private WalletEntity payerWallet;

    @ManyToOne
    @JoinColumn(name = "payee_wallet_id", nullable = false)
    private WalletEntity payeeWallet;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

}
