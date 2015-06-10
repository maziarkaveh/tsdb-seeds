package no.uis.seeds.timeseriesdb

import groovy.util.logging.Slf4j
import groovyx.gpars.GParsPool
import no.uis.seeds.model.InsideHumidityRecord
import no.uis.seeds.model.InsideTemperatureRecord
import no.uis.seeds.model.OutsideHumidityRecord
import no.uis.seeds.model.OutsideTemperatureRecord
import no.uis.seeds.repository.MysqlRecordRepository
import no.uis.seeds.timeseriesdb.opentsdb.OpenTSDBHttp

@Slf4j
class PushTest {
    static void main(String[] args) {
        GParsPool.withPool {
            def collect = [OutsideHumidityRecord, OutsideTemperatureRecord, InsideHumidityRecord, InsideTemperatureRecord].collectParallel { record ->
                new MysqlRecordRepository(sql: MysqlRecordRepository.DEFAULT_SQL).fetchAll(record).findAll {
                    it.valid
                }
            }.flatten().groupByParallel { it.timestamp }

            collect.each {
                it.value.eachParallel {
                    try {
                        OpenTSDBHttp.instance.put(it)
                        sleep 100
                    } catch (e) {
                        log.info(it.toJson())
                    }
                }
            }

        }
    }
}
