/*
 * This file is generated by jOOQ.
 */

package edu.java.model.jooq.tables.pojos;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class Subscriptions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long linkId;
    private Long chatId;

    public Subscriptions() {
    }

    public Subscriptions(Subscriptions value) {
        this.id = value.id;
        this.linkId = value.linkId;
        this.chatId = value.chatId;
    }

    @ConstructorProperties({"id", "linkId", "chatId"})
    public Subscriptions(
        @Nullable Long id,
        @NotNull Long linkId,
        @NotNull Long chatId
    ) {
        this.id = id;
        this.linkId = linkId;
        this.chatId = chatId;
    }

    /**
     * Getter for <code>public.subscriptions.id</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.subscriptions.id</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>public.subscriptions.link_id</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>public.subscriptions.link_id</code>.
     */
    public void setLinkId(@NotNull Long linkId) {
        this.linkId = linkId;
    }

    /**
     * Getter for <code>public.subscriptions.chat_id</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>public.subscriptions.chat_id</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subscriptions other = (Subscriptions) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.linkId == null) {
            if (other.linkId != null) {
                return false;
            }
        } else if (!this.linkId.equals(other.linkId)) {
            return false;
        }
        if (this.chatId == null) {
            if (other.chatId != null) {
                return false;
            }
        } else if (!this.chatId.equals(other.chatId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Subscriptions (");

        sb.append(id);
        sb.append(", ").append(linkId);
        sb.append(", ").append(chatId);

        sb.append(")");
        return sb.toString();
    }
}