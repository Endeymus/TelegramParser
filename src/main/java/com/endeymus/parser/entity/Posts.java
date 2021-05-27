package com.endeymus.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Posts.FIND_BY_ID_INTERNAL,
        query = "select p from Posts p " +
                "where p.idInternal = :id"),
        @NamedQuery(name = Posts.FIND_LAST_TOP_POSTS_BY_CHANNEL_ID,
        query = "select p from Posts p where p.idChannel.idInternal = :id_channel order by p.date desc")

})
public class Posts {
    public static final String FIND_BY_ID_INTERNAL = "Posts.findByIdInternal";
    public static final String FIND_LAST_TOP_POSTS_BY_CHANNEL_ID = "Posts.findLastTopPosts";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_channel")
    private Channel idChannel;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private PostType type;

    @Column(name = "content")
    private String content;

    @JsonIgnore
    @Transient
    private String shortContent;

    @Column(name = "media")
    private byte[] media;

    @JsonIgnore
    @Column(name = "reference")
    private String reference;

    @JsonIgnore
    @Column(name = "reference_other_channel")
    private String referenceOtherChannel;

    @JsonIgnore
    @Column(name = "id_internal")
    private Long idInternal;

    @Column(name = "count_views")
    private Integer countViews;

    @Column(name = "count_forward")
    private Integer countForward;

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", idChannel=" + (idChannel == null ? null : idChannel.getId()) +
                ", date=" + date +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", media=" + Arrays.toString(media) +
                ", reference='" + reference + '\'' +
                ", referenceOtherChannel='" + referenceOtherChannel + '\'' +
                ", idInternal=" + idInternal +
                ", countViews=" + countViews +
                ", countForward=" + countForward +
                '}';
    }
}
