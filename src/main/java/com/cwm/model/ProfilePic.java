package com.cwm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile_pics")
@Getter
@Setter
@ToString(exclude = {"imageData", "member"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "profile_pic_id")
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "member_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_profile_pics_member_id")
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
