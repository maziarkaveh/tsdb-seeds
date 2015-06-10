package no.uis.seeds.model

import groovy.transform.ToString
import groovy.util.logging.Slf4j

@ToString(includePackage = false, includeNames = true,includeSuper = true)
@Slf4j
class OutsideHumidityRecord extends TSDBRecord {
    final static String tableName = 'cipsi_seeds_uis_out_hum'
    final static String metric    = 'seeds.hum.out'

    OutsideHumidityRecord(){

    }
    OutsideHumidityRecord(Room room, Long timestamp, Number value) {
        super(room, timestamp, value)
    }
    OutsideHumidityRecord(Integer identity, Long timestamp, Number value) {
        super(identity, timestamp, value)
    }
    @Override
    Room parsIdentity(Integer identity) {
        Room.findByHumidityIdentify(identity)
    }
}