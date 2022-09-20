package com.ll.exam.RecipiaProject.allergy;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allergyId;

    private String allergyContent;

    private String allergyCategory;

    @ManyToOne
    private SiteUser siteUser;

    @Builder
    public Allergy(int allergyId, String allergyContent, String allergyCategory, SiteUser siteUser){
        this.allergyId = allergyId;
        this.allergyContent = allergyContent;
        this.allergyCategory = allergyCategory;
        this.siteUser = siteUser;
    }

}
