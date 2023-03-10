/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables;


import com.example.peachapi.driver.peachdb.gen.Keys;
import com.example.peachapi.driver.peachdb.gen.Public;
import com.example.peachapi.driver.peachdb.gen.tables.records.DeleteStatusRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DeleteStatus extends TableImpl<DeleteStatusRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.delete_status</code>
     */
    public static final DeleteStatus DELETE_STATUS = new DeleteStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DeleteStatusRecord> getRecordType() {
        return DeleteStatusRecord.class;
    }

    /**
     * The column <code>public.delete_status.status_id</code>.
     */
    public final TableField<DeleteStatusRecord, UUID> STATUS_ID = createField(DSL.name("status_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.delete_status.deleted_at</code>.
     */
    public final TableField<DeleteStatusRecord, LocalDateTime> DELETED_AT = createField(DSL.name("deleted_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.delete_status.deleted_by</code>.
     */
    public final TableField<DeleteStatusRecord, String> DELETED_BY = createField(DSL.name("deleted_by"), SQLDataType.VARCHAR(100).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.VARCHAR)), this, "");

    private DeleteStatus(Name alias, Table<DeleteStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private DeleteStatus(Name alias, Table<DeleteStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.delete_status</code> table reference
     */
    public DeleteStatus(String alias) {
        this(DSL.name(alias), DELETE_STATUS);
    }

    /**
     * Create an aliased <code>public.delete_status</code> table reference
     */
    public DeleteStatus(Name alias) {
        this(alias, DELETE_STATUS);
    }

    /**
     * Create a <code>public.delete_status</code> table reference
     */
    public DeleteStatus() {
        this(DSL.name("delete_status"), null);
    }

    public <O extends Record> DeleteStatus(Table<O> child, ForeignKey<O, DeleteStatusRecord> key) {
        super(child, key, DELETE_STATUS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<DeleteStatusRecord> getPrimaryKey() {
        return Keys.DELETE_STATUS_PKEY;
    }

    @Override
    public List<UniqueKey<DeleteStatusRecord>> getKeys() {
        return Arrays.<UniqueKey<DeleteStatusRecord>>asList(Keys.DELETE_STATUS_PKEY);
    }

    @Override
    public List<ForeignKey<DeleteStatusRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DeleteStatusRecord, ?>>asList(Keys.DELETE_STATUS__DELETE_STATUS_STATUS_ID_FKEY);
    }

    public Statues statues() {
        return new Statues(this, Keys.DELETE_STATUS__DELETE_STATUS_STATUS_ID_FKEY);
    }

    @Override
    public DeleteStatus as(String alias) {
        return new DeleteStatus(DSL.name(alias), this);
    }

    @Override
    public DeleteStatus as(Name alias) {
        return new DeleteStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DeleteStatus rename(String name) {
        return new DeleteStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DeleteStatus rename(Name name) {
        return new DeleteStatus(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, LocalDateTime, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
