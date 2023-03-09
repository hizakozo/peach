/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables;


import com.example.peachapi.driver.peachdb.gen.Keys;
import com.example.peachapi.driver.peachdb.gen.Public;
import com.example.peachapi.driver.peachdb.gen.tables.records.AssignedStatusRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
public class AssignedStatus extends TableImpl<AssignedStatusRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.assigned_status</code>
     */
    public static final AssignedStatus ASSIGNED_STATUS = new AssignedStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AssignedStatusRecord> getRecordType() {
        return AssignedStatusRecord.class;
    }

    /**
     * The column <code>public.assigned_status.item_id</code>.
     */
    public final TableField<AssignedStatusRecord, UUID> ITEM_ID = createField(DSL.name("item_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.assigned_status.status_id</code>.
     */
    public final TableField<AssignedStatusRecord, UUID> STATUS_ID = createField(DSL.name("status_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.assigned_status.assigned_by</code>.
     */
    public final TableField<AssignedStatusRecord, String> ASSIGNED_BY = createField(DSL.name("assigned_by"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.assigned_status.assigned_at</code>.
     */
    public final TableField<AssignedStatusRecord, LocalDateTime> ASSIGNED_AT = createField(DSL.name("assigned_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private AssignedStatus(Name alias, Table<AssignedStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private AssignedStatus(Name alias, Table<AssignedStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.assigned_status</code> table reference
     */
    public AssignedStatus(String alias) {
        this(DSL.name(alias), ASSIGNED_STATUS);
    }

    /**
     * Create an aliased <code>public.assigned_status</code> table reference
     */
    public AssignedStatus(Name alias) {
        this(alias, ASSIGNED_STATUS);
    }

    /**
     * Create a <code>public.assigned_status</code> table reference
     */
    public AssignedStatus() {
        this(DSL.name("assigned_status"), null);
    }

    public <O extends Record> AssignedStatus(Table<O> child, ForeignKey<O, AssignedStatusRecord> key) {
        super(child, key, ASSIGNED_STATUS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<AssignedStatusRecord> getPrimaryKey() {
        return Keys.ASSIGNED_STATUS_PKEY;
    }

    @Override
    public List<UniqueKey<AssignedStatusRecord>> getKeys() {
        return Arrays.<UniqueKey<AssignedStatusRecord>>asList(Keys.ASSIGNED_STATUS_PKEY);
    }

    @Override
    public List<ForeignKey<AssignedStatusRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AssignedStatusRecord, ?>>asList(Keys.ASSIGNED_STATUS__ASSIGNED_STATUS_ITEM_ID_FKEY, Keys.ASSIGNED_STATUS__ASSIGNED_STATUS_STATUS_ID_FKEY);
    }

    public Items items() {
        return new Items(this, Keys.ASSIGNED_STATUS__ASSIGNED_STATUS_ITEM_ID_FKEY);
    }

    public Statues statues() {
        return new Statues(this, Keys.ASSIGNED_STATUS__ASSIGNED_STATUS_STATUS_ID_FKEY);
    }

    @Override
    public AssignedStatus as(String alias) {
        return new AssignedStatus(DSL.name(alias), this);
    }

    @Override
    public AssignedStatus as(Name alias) {
        return new AssignedStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AssignedStatus rename(String name) {
        return new AssignedStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AssignedStatus rename(Name name) {
        return new AssignedStatus(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, UUID, String, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
