package com.example.javadb.model;

import javax.persistence.*;

@Entity
@Table (name = "communitygroups")
public class CommunityGroup {

    //    not necessary but can keep this func and try breaking the program
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communityGroupID")
        private Long id;

    public String getCommunityGroup() {
        return communityGroup;
    }

    public void setCommunityGroup(String communityGroup) {
        this.communityGroup = communityGroup;
    }

    @Column(name = "communityGroup_string")
    private String communityGroup;

}
