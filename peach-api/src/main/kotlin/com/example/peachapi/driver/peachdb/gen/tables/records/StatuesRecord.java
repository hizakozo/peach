/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables.records;


import com.example.peachapi.driver.peachdb.gen.tables.Statues;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StatuesRecord extends UpdatableRecordImpl<StatuesRecord> implements Record8<UUID, UUID, String, String, LocalDateTime, String, LocalDateTime, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.statues.status_id</code>.
     */
    public void setStatusId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.statues.status_id</code>.
     */
    public UUID getStatusId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.statues.category_id</code>.
     */
    public void setCategoryId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.statues.category_id</code>.
     */
    public UUID getCategoryId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.statues.status_name</code>.
     */
    public void setStatusName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.statues.status_name</code>.
     */
    public String getStatusName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.statues.status_color</code>.
     */
    public void setStatusColor(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.statues.status_color</code>.
     */
    public String getStatusColor() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.statues.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.statues.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>public.statues.created_by</code>.
     */
    public void setCreatedBy(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.statues.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.statues.changed_at</code>.
     */
    public void setChangedAt(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.statues.changed_at</code>.
     */
    public LocalDateTime getChangedAt() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>public.statues.changed_by</code>.
     */
    public void setChangedBy(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.statues.changed_by</code>.
     */
    public String getChangedBy() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<UUID, UUID, String, String, LocalDateTime, String, LocalDateTime, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<UUID, UUID, String, String, LocalDateTime, String, LocalDateTime, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Statues.STATUES.STATUS_ID;
    }

    @Override
    public Field<UUID> field2() {
        return Statues.STATUES.CATEGORY_ID;
    }

    @Override
    public Field<String> field3() {
        return Statues.STATUES.STATUS_NAME;
    }

    @Override
    public Field<String> field4() {
        return Statues.STATUES.STATUS_COLOR;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Statues.STATUES.CREATED_AT;
    }

    @Override
    public Field<String> field6() {
        return Statues.STATUES.CREATED_BY;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return Statues.STATUES.CHANGED_AT;
    }

    @Override
    public Field<String> field8() {
        return Statues.STATUES.CHANGED_BY;
    }

    @Override
    public UUID component1() {
        return getStatusId();
    }

    @Override
    public UUID component2() {
        return getCategoryId();
    }

    @Override
    public String component3() {
        return getStatusName();
    }

    @Override
    public String component4() {
        return getStatusColor();
    }

    @Override
    public LocalDateTime component5() {
        return getCreatedAt();
    }

    @Override
    public String component6() {
        return getCreatedBy();
    }

    @Override
    public LocalDateTime component7() {
        return getChangedAt();
    }

    @Override
    public String component8() {
        return getChangedBy();
    }

    @Override
    public UUID value1() {
        return getStatusId();
    }

    @Override
    public UUID value2() {
        return getCategoryId();
    }

    @Override
    public String value3() {
        return getStatusName();
    }

    @Override
    public String value4() {
        return getStatusColor();
    }

    @Override
    public LocalDateTime value5() {
        return getCreatedAt();
    }

    @Override
    public String value6() {
        return getCreatedBy();
    }

    @Override
    public LocalDateTime value7() {
        return getChangedAt();
    }

    @Override
    public String value8() {
        return getChangedBy();
    }

    @Override
    public StatuesRecord value1(UUID value) {
        setStatusId(value);
        return this;
    }

    @Override
    public StatuesRecord value2(UUID value) {
        setCategoryId(value);
        return this;
    }

    @Override
    public StatuesRecord value3(String value) {
        setStatusName(value);
        return this;
    }

    @Override
    public StatuesRecord value4(String value) {
        setStatusColor(value);
        return this;
    }

    @Override
    public StatuesRecord value5(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public StatuesRecord value6(String value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    public StatuesRecord value7(LocalDateTime value) {
        setChangedAt(value);
        return this;
    }

    @Override
    public StatuesRecord value8(String value) {
        setChangedBy(value);
        return this;
    }

    @Override
    public StatuesRecord values(UUID value1, UUID value2, String value3, String value4, LocalDateTime value5, String value6, LocalDateTime value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StatuesRecord
     */
    public StatuesRecord() {
        super(Statues.STATUES);
    }

    /**
     * Create a detached, initialised StatuesRecord
     */
    public StatuesRecord(UUID statusId, UUID categoryId, String statusName, String statusColor, LocalDateTime createdAt, String createdBy, LocalDateTime changedAt, String changedBy) {
        super(Statues.STATUES);

        setStatusId(statusId);
        setCategoryId(categoryId);
        setStatusName(statusName);
        setStatusColor(statusColor);
        setCreatedAt(createdAt);
        setCreatedBy(createdBy);
        setChangedAt(changedAt);
        setChangedBy(changedBy);
    }
}
