package no.uis.seeds.repository

import groovy.sql.Sql
import no.uis.seeds.model.TSDBRecord

class MysqlRecordRepository implements RecordRepository {
    static final DEFAULT_SQL = new DBConfig().buildSqlInstance()
    Sql          sql         = DEFAULT_SQL

    @Override
    def <T extends TSDBRecord> List<T> fetchAll(Class<T> type) {
        def list = []
        eachRow(type) {
            list << it
        }
        list
    }

    @Override
    void forEach(Class<? extends TSDBRecord> type, Closure closure) {
        eachRow(type, closure)

    }


    private void eachRow(Class<? extends TSDBRecord> type, Closure closure) {
        // group by time,identity
        def tableName = getTableName(type)
        def query = "select * from $tableName WHERE id > ?  ORDER BY id limit 100000 "
        Integer maxId = 0
        def executeQuery = {
            def oldMaxId = maxId
            sql.eachRow(query, [maxId]) { row ->
                closure(makeInstance(type, row))
                maxId = row.id
            }
            maxId != oldMaxId
        }


        while (executeQuery());

    }

    private makeInstance(Class<? extends TSDBRecord> type, row) {
        type.getConstructor(Integer,Long,Number).newInstance(row.identity, row.time?.time, row.value)
    }

    private getTableName(Class<? extends TSDBRecord> type) {
        type.newInstance().tableName
    }

    static class DBConfig {
        String dbName     = 'seeds'
        String dbServer   = '127.0.0.1'
        String dbPort     = '3306'
        String dbUserName = 'root'
        String dbPassword = ''
        String dbDriver   = 'com.mysql.jdbc.Driver'


        Sql buildSqlInstance() {
            Sql.newInstance("jdbc:mysql://$dbServer:$dbPort/$dbName?createDatabaseIfNotExist=true&amp;", dbUserName,
                    dbPassword, dbDriver)
        }

    }

}
