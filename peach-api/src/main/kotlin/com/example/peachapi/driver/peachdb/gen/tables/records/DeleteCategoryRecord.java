/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables.records;


import com.example.peachapi.driver.peachdb.gen.tables.DeleteCategory;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DeleteCategoryRecord extends UpdatableRecordImpl<DeleteCategoryRecord> implements Record3<UUID, LocalDateTime, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.delete_category.category_id</code>.
     */
    public void setCategoryId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.delete_category.category_id</code>.
     */
    public UUID getCategoryId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.delete_category.deleted_at</code>.
     */
    public void setDeletedAt(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.delete_category.deleted_at</code>.
     */
    public LocalDateTime getDeletedAt() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>public.delete_category.deleted_by</code>.
     */
    public void setDeletedBy(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.delete_category.deleted_by</code>.
     */
    public String getDeletedBy() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, LocalDateTime, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, LocalDateTime, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return DeleteCategory.DELETE_CATEGORY.CATEGORY_ID;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return DeleteCategory.DELETE_CATEGORY.DELETED_AT;
    }

    @Override
    public Field<String> field3() {
        return DeleteCategory.DELETE_CATEGORY.DELETED_BY;
    }

    @Override
    public UUID component1() {
        return getCategoryId();
    }

    @Override
    public LocalDateTime component2() {
        return getDeletedAt();
    }

    @Override
    public String component3() {
        return getDeletedBy();
    }

    @Override
    public UUID value1() {
        return getCategoryId();
    }

    @Override
    public LocalDateTime value2() {
        return getDeletedAt();
    }

    @Override
    public String value3() {
        return getDeletedBy();
    }

    @Override
    public DeleteCategoryRecord value1(UUID value) {
        setCategoryId(value);
        return this;
    }

    @Override
    public DeleteCategoryRecord value2(LocalDateTime value) {
        setDeletedAt(value);
        return this;
    }

    @Override
    public DeleteCategoryRecord value3(String value) {
        setDeletedBy(value);
        return this;
    }

    @Override
    public DeleteCategoryRecord values(UUID value1, LocalDateTime value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DeleteCategoryRecord
     */
    public DeleteCategoryRecord() {
        super(DeleteCategory.DELETE_CATEGORY);
    }

    /**
     * Create a detached, initialised DeleteCategoryRecord
     */
    public DeleteCategoryRecord(UUID categoryId, LocalDateTime deletedAt, String deletedBy) {
        super(DeleteCategory.DELETE_CATEGORY);

        setCategoryId(categoryId);
        setDeletedAt(deletedAt);
        setDeletedBy(deletedBy);
    }
}