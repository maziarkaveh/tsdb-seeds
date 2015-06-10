package no.uis.seeds.model

import groovy.transform.ToString
import groovy.util.logging.Slf4j

@ToString(includePackage = false, includeNames = true,includeSuper = true)
@Slf4j
class InsideTemperatureRecord extends TSDBRecord {
    final static String tableName = 'cipsi_seeds_uis_in_temp'
    final static String metric    = 'seeds.temp.in'

    InsideTemperatureRecord() {

    }

    InsideTemperatureRecord(Integer identity, Long timestamp, Number value) {
        super(identity, timestamp, value)
    }
    InsideTemperatureRecord(Room room, Long timestamp, Number value) {
        super(room, timestamp, value)
    }
    @Override
    Room parsIdentity(Integer identity) {
        Room.findByTemperatureIdentify(identity)
    }
}