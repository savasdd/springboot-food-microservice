package com.food.dialect;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Index;
import org.hibernate.spatial.dialect.postgis.PostgisPG94Dialect;
import org.hibernate.tool.schema.internal.StandardIndexExporter;
import org.hibernate.tool.schema.spi.Exporter;

import java.io.Serial;
import java.sql.Types;

@Slf4j
public class PostgresSQL94JsonDialect extends PostgisPG94Dialect {

    @Serial
    private static final long serialVersionUID = 5361969522758902453L;

    static final String UNDERSCORE = "_";

    public PostgresSQL94JsonDialect() {
        registerColumnType(Types.JAVA_OBJECT - 1, "jsonb");
    }

    @Override
    public Exporter<Index> getIndexExporter() {
        return new IndexExporter(this);
    }

    public static class IndexExporter extends StandardIndexExporter {
        public IndexExporter(Dialect dialect) {
            super(dialect);
        }

        @Override
        public String[] getSqlCreateStrings(Index index, Metadata metadata, SqlStringGenerationContext context) {
            String indexName = "idx_" + index.getTable().getQualifiedTableName() + UNDERSCORE + index.getName();
            index.setName(indexName);
//            log.info(indexName);
            return super.getSqlCreateStrings(index, metadata, context);
        }
    }

}
