/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables;


import com.example.peachapi.driver.peachdb.gen.Keys;
import com.example.peachapi.driver.peachdb.gen.Public;
import com.example.peachapi.driver.peachdb.gen.tables.records.DeleteItemRecord;

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
public class DeleteItem extends TableImpl<DeleteItemRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.delete_item</code>
     */
    public static final DeleteItem DELETE_ITEM = new DeleteItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DeleteItemRecord> getRecordType() {
        return DeleteItemRecord.class;
    }

    /**
     * The column <code>public.delete_item.item_id</code>.
     */
    public final TableField<DeleteItemRecord, UUID> ITEM_ID = createField(DSL.name("item_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.delete_item.deleted_at</code>.
     */
    public final TableField<DeleteItemRecord, LocalDateTime> DELETED_AT = createField(DSL.name("deleted_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.delete_item.deleted_by</code>.
     */
    public final TableField<DeleteItemRecord, String> DELETED_BY = createField(DSL.name("deleted_by"), SQLDataType.VARCHAR(100).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.VARCHAR)), this, "");

    private DeleteItem(Name alias, Table<DeleteItemRecord> aliased) {
        this(alias, aliased, null);
    }

    private DeleteItem(Name alias, Table<DeleteItemRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.delete_item</code> table reference
     */
    public DeleteItem(String alias) {
        this(DSL.name(alias), DELETE_ITEM);
    }

    /**
     * Create an aliased <code>public.delete_item</code> table reference
     */
    public DeleteItem(Name alias) {
        this(alias, DELETE_ITEM);
    }

    /**
     * Create a <code>public.delete_item</code> table reference
     */
    public DeleteItem() {
        this(DSL.name("delete_item"), null);
    }

    public <O extends Record> DeleteItem(Table<O> child, ForeignKey<O, DeleteItemRecord> key) {
        super(child, key, DELETE_ITEM);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<DeleteItemRecord> getPrimaryKey() {
        return Keys.DELETE_ITEM_PKEY;
    }

    @Override
    public List<UniqueKey<DeleteItemRecord>> getKeys() {
        return Arrays.<UniqueKey<DeleteItemRecord>>asList(Keys.DELETE_ITEM_PKEY);
    }

    @Override
    public List<ForeignKey<DeleteItemRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DeleteItemRecord, ?>>asList(Keys.DELETE_ITEM__DELETE_ITEM_ITEM_ID_FKEY);
    }

    public Items items() {
        return new Items(this, Keys.DELETE_ITEM__DELETE_ITEM_ITEM_ID_FKEY);
    }

    @Override
    public DeleteItem as(String alias) {
        return new DeleteItem(DSL.name(alias), this);
    }

    @Override
    public DeleteItem as(Name alias) {
        return new DeleteItem(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DeleteItem rename(String name) {
        return new DeleteItem(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DeleteItem rename(Name name) {
        return new DeleteItem(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, LocalDateTime, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
