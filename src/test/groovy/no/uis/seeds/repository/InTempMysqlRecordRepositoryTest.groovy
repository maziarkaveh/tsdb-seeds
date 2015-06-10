package no.uis.seeds.repository

import no.uis.seeds.model.InsideHumidityRecord
import no.uis.seeds.model.InsideTemperatureRecord
import no.uis.seeds.model.OutsideHumidityRecord
import no.uis.seeds.model.OutsideTemperatureRecord

class InTempMysqlRecordRepositoryTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {
    }

    void testLoad() {
        def load = new MysqlRecordRepository().fetchAll(OutsideTemperatureRecord)
        println InsideTemperatureRecord.batchToJson(load)
        println InsideTemperatureRecord.batchToRows(load)

    }

    void testForEachInsideTemperatureRecord() {
        def repository = new MysqlRecordRepository()
        repository.forEach(InsideTemperatureRecord) { InsideTemperatureRecord InTempRecord ->
            println InTempRecord
        }

    }

    void testForEachOutsideTemperatureRecord() {
        def repository = new MysqlRecordRepository()
        repository.forEach(OutsideTemperatureRecord) { OutsideTemperatureRecord record ->
            println record
        }

    }

    void testForEachOutsideHumidityRecord() {
        def repository = new MysqlRecordRepository()
        repository.forEach(OutsideHumidityRecord) { OutsideHumidityRecord record ->
            println record
        }

    }

    void testForEachInsideHumidityRecord() {
        def repository = new MysqlRecordRepository()
        repository.forEach(InsideHumidityRecord) { InsideHumidityRecord record ->
            println record
        }
    }

}
