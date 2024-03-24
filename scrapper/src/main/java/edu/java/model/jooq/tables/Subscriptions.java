/*
 * This file is generated by jOOQ.
 */

package edu.java.model.jooq.tables;

import edu.java.model.jooq.Keys;
import edu.java.model.jooq.Public;
import edu.java.model.jooq.tables.records.SubscriptionsRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

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
public class Subscriptions extends TableImpl<SubscriptionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.subscriptions</code>
     */
    public static final Subscriptions SUBSCRIPTIONS = new Subscriptions();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<SubscriptionsRecord> getRecordType() {
        return SubscriptionsRecord.class;
    }

    /**
     * The column <code>public.subscriptions.id</code>.
     */
    public final TableField<SubscriptionsRecord, Long> ID =
        createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.subscriptions.link_id</code>.
     */
    public final TableField<SubscriptionsRecord, Long> LINK_ID =
        createField(DSL.name("link_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.subscriptions.chat_id</code>.
     */
    public final TableField<SubscriptionsRecord, Long> CHAT_ID =
        createField(DSL.name("chat_id"), SQLDataType.BIGINT.nullable(false), this, "");

    private Subscriptions(Name alias, Table<SubscriptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Subscriptions(Name alias, Table<SubscriptionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.subscriptions</code> table reference
     */
    public Subscriptions(String alias) {
        this(DSL.name(alias), SUBSCRIPTIONS);
    }

    /**
     * Create an aliased <code>public.subscriptions</code> table reference
     */
    public Subscriptions(Name alias) {
        this(alias, SUBSCRIPTIONS);
    }

    /**
     * Create a <code>public.subscriptions</code> table reference
     */
    public Subscriptions() {
        this(DSL.name("subscriptions"), null);
    }

    public <O extends Record> Subscriptions(Table<O> child, ForeignKey<O, SubscriptionsRecord> key) {
        super(child, key, SUBSCRIPTIONS);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    @NotNull
    public Identity<SubscriptionsRecord, Long> getIdentity() {
        return (Identity<SubscriptionsRecord, Long>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<SubscriptionsRecord> getPrimaryKey() {
        return Keys.SUBSCRIPTIONS_PKEY;
    }

    @Override
    @NotNull
    public List<ForeignKey<SubscriptionsRecord, ?>> getReferences() {
        return Arrays.asList(
            Keys.SUBSCRIPTIONS__SUBSCRIPTIONS_LINK_ID_FKEY,
            Keys.SUBSCRIPTIONS__SUBSCRIPTIONS_CHAT_ID_FKEY
        );
    }

    private transient Link _link;
    private transient Chat _chat;

    /**
     * Get the implicit join path to the <code>public.link</code> table.
     */
    public Link link() {
        if (_link == null) {
            _link = new Link(this, Keys.SUBSCRIPTIONS__SUBSCRIPTIONS_LINK_ID_FKEY);
        }

        return _link;
    }

    /**
     * Get the implicit join path to the <code>public.chat</code> table.
     */
    public Chat chat() {
        if (_chat == null) {
            _chat = new Chat(this, Keys.SUBSCRIPTIONS__SUBSCRIPTIONS_CHAT_ID_FKEY);
        }

        return _chat;
    }

    @Override
    @NotNull
    public Subscriptions as(String alias) {
        return new Subscriptions(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Subscriptions as(Name alias) {
        return new Subscriptions(alias, this);
    }

    @Override
    @NotNull
    public Subscriptions as(Table<?> alias) {
        return new Subscriptions(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Subscriptions rename(String name) {
        return new Subscriptions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Subscriptions rename(Name name) {
        return new Subscriptions(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Subscriptions rename(Table<?> name) {
        return new Subscriptions(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super Long, ? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
        Class<U> toType,
        Function3<? super Long, ? super Long, ? super Long, ? extends U> from
    ) {
        return convertFrom(toType, Records.mapping(from));
    }
}