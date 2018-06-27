package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "WebActorAssign")
@Data
public class WebActorAssign {
    @Id
    @Column(name = "ActorAssignID")
    private Integer actor_assign_id;

    private String A0188;

    @Column(name = "ActorId")
    private Integer actorid;

}