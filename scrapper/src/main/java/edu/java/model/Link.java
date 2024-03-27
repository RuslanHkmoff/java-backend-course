package edu.java.model;

import edu.java.model.util.UriConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", unique = true, nullable = false)
    @Convert(converter = UriConverter.class)
    private URI url;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "visited_at")
    private OffsetDateTime visitedAt;

    @Column(name = "updates_count")
    private Integer updatesCount;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id")
    @Builder.Default
    private List<Subscription> subscriptions = new ArrayList<>();

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        subscription.setLink(this);
    }
}
