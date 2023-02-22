/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables.records;


import com.example.peachapi.driver.peachdb.gen.tables.Items;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ItemsRecord extends UpdatableRecordImpl<ItemsRecord> implements Record10<UUID, UUID, String, String, LocalDateTime, UUID, LocalDateTime, UUID, UUID, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.items.item_id</code>.
     */
    public void setItemId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.items.item_id</code>.
     */
    public UUID getItemId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.items.category_id</code>.
     */
    public void setCategoryId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.items.category_id</code>.
     */
    public UUID getCategoryId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.items.item_name</code>.
     */
    public void setItemName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.items.item_name</code>.
     */
    public String getItemName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.items.item_remarks</code>.
     */
    public void setItemRemarks(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.items.item_remarks</code>.
     */
    public String getItemRemarks() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.items.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.items.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>public.items.created_by</code>.
     */
    public void setCreatedBy(UUID value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.items.created_by</code>.
     */
    public UUID getCreatedBy() {
        return (UUID) get(5);
    }

    /**
     * Setter for <code>public.items.changed_at</code>.
     */
    public void setChangedAt(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.items.changed_at</code>.
     */
    public LocalDateTime getChangedAt() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>public.items.changed_by</code>.
     */
    public void setChangedBy(UUID value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.items.changed_by</code>.
     */
    public UUID getChangedBy() {
        return (UUID) get(7);
    }

    /**
     * Setter for <code>public.items.deleted_by</code>.
     */
    public void setDeletedBy(UUID value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.items.deleted_by</code>.
     */
    public UUID getDeletedBy() {
        return (UUID) get(8);
    }

    /**
     * Setter for <code>public.items.deleted_at</code>.
     */
    public void setDeletedAt(LocalDateTime value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.items.deleted_at</code>.
     */
    public LocalDateTime getDeletedAt() {
        return (LocalDateTime) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<UUID, UUID, String, String, LocalDateTime, UUID, LocalDateTime, UUID, UUID, LocalDateTime> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<UUID, UUID, String, String, LocalDateTime, UUID, LocalDateTime, UUID, UUID, LocalDateTime> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Items.ITEMS.ITEM_ID;
    }

    @Override
    public Field<UUID> field2() {
        return Items.ITEMS.CATEGORY_ID;
    }

    @Override
    public Field<String> field3() {
        return Items.ITEMS.ITEM_NAME;
    }

    @Override
    public Field<String> field4() {
        return Items.ITEMS.ITEM_REMARKS;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Items.ITEMS.CREATED_AT;
    }

    @Override
    public Field<UUID> field6() {
        return Items.ITEMS.CREATED_BY;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return Items.ITEMS.CHANGED_AT;
    }

    @Override
    public Field<UUID> field8() {
        return Items.ITEMS.CHANGED_BY;
    }

    @Override
    public Field<UUID> field9() {
        return Items.ITEMS.DELETED_BY;
    }

    @Override
    public Field<LocalDateTime> field10() {
        return Items.ITEMS.DELETED_AT;
    }

    @Override
    public UUID component1() {
        return getItemId();
    }

    @Override
    public UUID component2() {
        return getCategoryId();
    }

    @Override
    public String component3() {
        return getItemName();
    }

    @Override
    public String component4() {
        return getItemRemarks();
    }

    @Override
    public LocalDateTime component5() {
        return getCreatedAt();
    }

    @Override
    public UUID component6() {
        return getCreatedBy();
    }

    @Override
    public LocalDateTime component7() {
        return getChangedAt();
    }

    @Override
    public UUID component8() {
        return getChangedBy();
    }

    @Override
    public UUID component9() {
        return getDeletedBy();
    }

    @Override
    public LocalDateTime component10() {
        return getDeletedAt();
    }

    @Override
    public UUID value1() {
        return getItemId();
    }

    @Override
    public UUID value2() {
        return getCategoryId();
    }

    @Override
    public String value3() {
        return getItemName();
    }

    @Override
    public String value4() {
        return getItemRemarks();
    }

    @Override
    public LocalDateTime value5() {
        return getCreatedAt();
    }

    @Override
    public UUID value6() {
        return getCreatedBy();
    }

    @Override
    public LocalDateTime value7() {
        return getChangedAt();
    }

    @Override
    public UUID value8() {
        return getChangedBy();
    }

    @Override
    public UUID value9() {
        return getDeletedBy();
    }

    @Override
    public LocalDateTime value10() {
        return getDeletedAt();
    }

    @Override
    public ItemsRecord value1(UUID value) {
        setItemId(value);
        return this;
    }

    @Override
    public ItemsRecord value2(UUID value) {
        setCategoryId(value);
        return this;
    }

    @Override
    public ItemsRecord value3(String value) {
        setItemName(value);
        return this;
    }

    @Override
    public ItemsRecord value4(String value) {
        setItemRemarks(value);
        return this;
    }

    @Override
    public ItemsRecord value5(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public ItemsRecord value6(UUID value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    public ItemsRecord value7(LocalDateTime value) {
        setChangedAt(value);
        return this;
    }

    @Override
    public ItemsRecord value8(UUID value) {
        setChangedBy(value);
        return this;
    }

    @Override
    public ItemsRecord value9(UUID value) {
        setDeletedBy(value);
        return this;
    }

    @Override
    public ItemsRecord value10(LocalDateTime value) {
        setDeletedAt(value);
        return this;
    }

    @Override
    public ItemsRecord values(UUID value1, UUID value2, String value3, String value4, LocalDateTime value5, UUID value6, LocalDateTime value7, UUID value8, UUID value9, LocalDateTime value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ItemsRecord
     */
    public ItemsRecord() {
        super(Items.ITEMS);
    }

    /**
     * Create a detached, initialised ItemsRecord
     */
    public ItemsRecord(UUID itemId, UUID categoryId, String itemName, String itemRemarks, LocalDateTime createdAt, UUID createdBy, LocalDateTime changedAt, UUID changedBy, UUID deletedBy, LocalDateTime deletedAt) {
        super(Items.ITEMS);

        setItemId(itemId);
        setCategoryId(categoryId);
        setItemName(itemName);
        setItemRemarks(itemRemarks);
        setCreatedAt(createdAt);
        setCreatedBy(createdBy);
        setChangedAt(changedAt);
        setChangedBy(changedBy);
        setDeletedBy(deletedBy);
        setDeletedAt(deletedAt);
    }
}