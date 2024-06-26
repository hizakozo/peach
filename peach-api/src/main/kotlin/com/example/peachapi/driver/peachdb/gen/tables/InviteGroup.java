/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables;


import com.example.peachapi.driver.peachdb.gen.Keys;
import com.example.peachapi.driver.peachdb.gen.Public;
import com.example.peachapi.driver.peachdb.gen.tables.records.InviteGroupRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
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
public class InviteGroup extends TableImpl<InviteGroupRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.invite_group</code>
     */
    public static final InviteGroup INVITE_GROUP = new InviteGroup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InviteGroupRecord> getRecordType() {
        return InviteGroupRecord.class;
    }

    /**
     * The column <code>public.invite_group.group_id</code>.
     */
    public final TableField<InviteGroupRecord, UUID> GROUP_ID = createField(DSL.name("group_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.invite_group.invite_code</code>.
     */
    public final TableField<InviteGroupRecord, String> INVITE_CODE = createField(DSL.name("invite_code"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.invite_group.term_to</code>.
     */
    public final TableField<InviteGroupRecord, LocalDateTime> TERM_TO = createField(DSL.name("term_to"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.invite_group.invite_at</code>.
     */
    public final TableField<InviteGroupRecord, LocalDateTime> INVITE_AT = createField(DSL.name("invite_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.invite_group.invite_by</code>.
     */
    public final TableField<InviteGroupRecord, String> INVITE_BY = createField(DSL.name("invite_by"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    private InviteGroup(Name alias, Table<InviteGroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private InviteGroup(Name alias, Table<InviteGroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.invite_group</code> table reference
     */
    public InviteGroup(String alias) {
        this(DSL.name(alias), INVITE_GROUP);
    }

    /**
     * Create an aliased <code>public.invite_group</code> table reference
     */
    public InviteGroup(Name alias) {
        this(alias, INVITE_GROUP);
    }

    /**
     * Create a <code>public.invite_group</code> table reference
     */
    public InviteGroup() {
        this(DSL.name("invite_group"), null);
    }

    public <O extends Record> InviteGroup(Table<O> child, ForeignKey<O, InviteGroupRecord> key) {
        super(child, key, INVITE_GROUP);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<InviteGroupRecord> getPrimaryKey() {
        return Keys.INVITE_GROUP_PKEY;
    }

    @Override
    public List<UniqueKey<InviteGroupRecord>> getKeys() {
        return Arrays.<UniqueKey<InviteGroupRecord>>asList(Keys.INVITE_GROUP_PKEY, Keys.INVITE_GROUP_INVITE_CODE_KEY);
    }

    @Override
    public List<ForeignKey<InviteGroupRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<InviteGroupRecord, ?>>asList(Keys.INVITE_GROUP__INVITE_GROUP_GROUP_ID_FKEY);
    }

    public Groups groups() {
        return new Groups(this, Keys.INVITE_GROUP__INVITE_GROUP_GROUP_ID_FKEY);
    }

    @Override
    public InviteGroup as(String alias) {
        return new InviteGroup(DSL.name(alias), this);
    }

    @Override
    public InviteGroup as(Name alias) {
        return new InviteGroup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public InviteGroup rename(String name) {
        return new InviteGroup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public InviteGroup rename(Name name) {
        return new InviteGroup(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, String, LocalDateTime, LocalDateTime, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
