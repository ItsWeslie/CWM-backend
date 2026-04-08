package com.cwm.model;

import com.cwm.enums.Active;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "members", indexes = {
        @Index(name = "idx_member_phone", columnList = "phone")
})
@Getter
@Setter
@ToString(exclude = {"proof", "profilePic"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false,unique = true)
    private String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<IDProof> proof;

    @OneToOne(mappedBy = "member",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private ProfilePic profilePic;

    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Active status;
}
