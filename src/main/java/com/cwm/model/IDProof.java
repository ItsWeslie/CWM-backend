package com.cwm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "id_proofs", indexes = {
        @Index(name = "idx_idproof_member_id",
                columnList = "member_id")
})
@Getter
@Setter
@ToString(exclude = {"imageData", "member"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IDProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "id_proof_id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_Id_proof_member_id")
    )
    private Member member;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imageType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] imageData;
}
