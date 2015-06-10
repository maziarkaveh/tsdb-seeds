package no.uis.seeds.model

import groovy.transform.ToString
import groovy.util.logging.Slf4j

@ToString(includePackage = false, includeNames = true, includeSuper = true)
@Slf4j
class InsideHumidityRecord extends TSDBRecord {
    final static String tableName = 'cipsi_seeds_uis_in_hum'
    final static String metric    = 'seeds.hum.in'

    InsideHumidityRecord() {

    }

    InsideHumidityRecord(Integer identity, Long timestamp, Number value) {
        super(identity, timestamp, value)
    }
    InsideHumidityRecord(Room room, Long timestamp, Number value) {
        super(room, timestamp, value)
    }

    @Override
    Room parsIdentity(Integer identity) {
        Room.findByHumidityIdentify(identity)
    }
}