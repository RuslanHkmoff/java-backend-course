package edu.java.model;

import java.net.URI;
import java.time.OffsetDateTime;
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
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime visitedAt;
    private Integer updateCount;
}
