package com.ll.exam.RecipiaProject.allergy;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allergyId;

    private String allergyContent;

    private String allergyCategory;

    @ManyToOne
    private SiteUser siteUser;

}
