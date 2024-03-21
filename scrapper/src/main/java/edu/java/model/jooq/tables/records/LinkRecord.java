/*
 * This file is generated by jOOQ.
 */

package edu.java.model.jooq.tables.records;

import edu.java.model.jooq.tables.Link;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class LinkRecord extends UpdatableRecordImpl<LinkRecord>
    implements Record5<Long, String, OffsetDateTime, OffsetDateTime, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.link.id</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.link.id</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.link.url</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.link.url</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 2048)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.link.visited_at</code>.
     */
    public void setVisitedAt(@Nullable OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.link.visited_at</code>.
     */
    @Nullable
    public OffsetDateTime getVisitedAt() {
        return (OffsetDateTime) get(2);
    }

    /**
     * Setter for <code>public.link.updated_at</code>.
     */
    public void setUpdatedAt(@Nullable OffsetDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.link.updated_at</code>.
     */
    @Nullable
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(3);
    }

    /**
     * Setter for <code>public.link.updates_count</code>.
     */
    public void setUpdatesCount(@Nullable Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.link.updates_count</code>.
     */
    @Nullable
    public Integer getUpdatesCount() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row5<Long, String, OffsetDateTime, OffsetDateTime, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row5<Long, String, OffsetDateTime, OffsetDateTime, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Link.LINK.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Link.LINK.URL;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return Link.LINK.VISITED_AT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field4() {
        return Link.LINK.UPDATED_AT;
    }

    @Override
    @NotNull
    public Field<Integer> field5() {
        return Link.LINK.UPDATES_COUNT;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @Nullable
    public OffsetDateTime component3() {
        return getVisitedAt();
    }

    @Override
    @Nullable
    public OffsetDateTime component4() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public Integer component5() {
        return getUpdatesCount();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @Nullable
    public OffsetDateTime value3() {
        return getVisitedAt();
    }

    @Override
    @Nullable
    public OffsetDateTime value4() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public Integer value5() {
        return getUpdatesCount();
    }

    @Override
    @NotNull
    public LinkRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value3(@Nullable OffsetDateTime value) {
        setVisitedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value4(@Nullable OffsetDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value5(@Nullable Integer value) {
        setUpdatesCount(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord values(
        @Nullable Long value1,
        @NotNull String value2,
        @Nullable OffsetDateTime value3,
        @Nullable OffsetDateTime value4,
        @Nullable Integer value5
    ) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({"id", "url", "visitedAt", "updatedAt", "updatesCount"})
    public LinkRecord(
        @Nullable Long id,
        @NotNull String url,
        @Nullable OffsetDateTime visitedAt,
        @Nullable OffsetDateTime updatedAt,
        @Nullable Integer updatesCount
    ) {
        super(Link.LINK);

        setId(id);
        setUrl(url);
        setVisitedAt(visitedAt);
        setUpdatedAt(updatedAt);
        setUpdatesCount(updatesCount);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    public LinkRecord(edu.java.model.jooq.tables.pojos.Link value) {
        super(Link.LINK);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setVisitedAt(value.getVisitedAt());
            setUpdatedAt(value.getUpdatedAt());
            setUpdatesCount(value.getUpdatesCount());
            resetChangedOnNotNull();
        }
    }
}
