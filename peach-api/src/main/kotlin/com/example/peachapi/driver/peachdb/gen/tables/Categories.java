/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables;


import com.example.peachapi.driver.peachdb.gen.Keys;
import com.example.peachapi.driver.peachdb.gen.Public;
import com.example.peachapi.driver.peachdb.gen.tables.records.CategoriesRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
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
public class Categories extends TableImpl<CategoriesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.categories</code>
     */
    public static final Categories CATEGORIES = new Categories();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CategoriesRecord> getRecordType() {
        return CategoriesRecord.class;
    }

    /**
     * The column <code>public.categories.category_id</code>.
     */
    public final TableField<CategoriesRecord, UUID> CATEGORY_ID = createField(DSL.name("category_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.categories.group_id</code>.
     */
    public final TableField<CategoriesRecord, UUID> GROUP_ID = createField(DSL.name("group_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.categories.category_name</code>.
     */
    public final TableField<CategoriesRecord, String> CATEGORY_NAME = createField(DSL.name("category_name"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.categories.category_remarks</code>.
     */
    public final TableField<CategoriesRecord, String> CATEGORY_REMARKS = createField(DSL.name("category_remarks"), SQLDataType.VARCHAR(225).nullable(false).defaultValue(DSL.field("NULL::character varying", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>public.categories.created_at</code>.
     */
    public final TableField<CategoriesRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.categories.created_by</code>.
     */
    public final TableField<CategoriesRecord, String> CREATED_BY = createField(DSL.name("created_by"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.categories.changed_at</code>.
     */
    public final TableField<CategoriesRecord, LocalDateTime> CHANGED_AT = createField(DSL.name("changed_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.categories.changed_by</code>.
     */
    public final TableField<CategoriesRecord, String> CHANGED_BY = createField(DSL.name("changed_by"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    private Categories(Name alias, Table<CategoriesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Categories(Name alias, Table<CategoriesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.categories</code> table reference
     */
    public Categories(String alias) {
        this(DSL.name(alias), CATEGORIES);
    }

    /**
     * Create an aliased <code>public.categories</code> table reference
     */
    public Categories(Name alias) {
        this(alias, CATEGORIES);
    }

    /**
     * Create a <code>public.categories</code> table reference
     */
    public Categories() {
        this(DSL.name("categories"), null);
    }

    public <O extends Record> Categories(Table<O> child, ForeignKey<O, CategoriesRecord> key) {
        super(child, key, CATEGORIES);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<CategoriesRecord> getPrimaryKey() {
        return Keys.CATEGORIES_PKEY;
    }

    @Override
    public List<UniqueKey<CategoriesRecord>> getKeys() {
        return Arrays.<UniqueKey<CategoriesRecord>>asList(Keys.CATEGORIES_PKEY);
    }

    @Override
    public List<ForeignKey<CategoriesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CategoriesRecord, ?>>asList(Keys.CATEGORIES__CATEGORIES_GROUP_ID_FKEY);
    }

    public Groups groups() {
        return new Groups(this, Keys.CATEGORIES__CATEGORIES_GROUP_ID_FKEY);
    }

    @Override
    public Categories as(String alias) {
        return new Categories(DSL.name(alias), this);
    }

    @Override
    public Categories as(Name alias) {
        return new Categories(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Categories rename(String name) {
        return new Categories(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Categories rename(Name name) {
        return new Categories(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<UUID, UUID, String, String, LocalDateTime, String, LocalDateTime, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
