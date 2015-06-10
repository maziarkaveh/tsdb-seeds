package no.uis.seeds.model

import groovy.json.JsonSlurper
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.logging.Slf4j

import java.text.SimpleDateFormat

@ToString(includePackage = false, includeNames = true)
@Slf4j
@EqualsAndHashCode(excludes = 'value')
abstract class TSDBRecord implements Serializable {
    Room   room
    Long   timestamp
    Number value

    TSDBRecord() {
    }

    TSDBRecord(Integer identity, Long timestamp, Number value) {
        this.room = parsIdentity(identity)
        this.timestamp = timestamp
        this.value = value
    }

    TSDBRecord(Room room, Long timestamp, Number value) {
        this.room = room
        this.timestamp = timestamp
        this.value = value
    }

    abstract Room parsIdentity(Integer identity)

    abstract String getMetric()

    abstract String getTableName()

    String getFormattedDate() {
        new SimpleDateFormat().format(new Date(timestamp))
    }

    String getTagsAsJson() {
        """
            {
                "room": "${room.room}",
                "floor": ${room.floor},
                "locationType": "${room.locationType.name().toLowerCase()}"
            }
        """
    }

    boolean isValid() {
        this.room && timestamp && value
    }

    String toRow() {
        "$metric $timestamp $value room=${room.room} floor=${room.floor} locationType=${room.locationType.name().toLowerCase()}"
    }

    String toJson() {
        assert valid: "Not valid values for $this"
        """
        {
            "metric": "$metric",
            "timestamp": $timestamp,
            "value": $value,
            "tags":  $tagsAsJson
        }
        """
    }

    static TSDBRecord fromJson(String json) {
        def row = new JsonSlurper().parseText(json)
        def type = [OutsideHumidityRecord, OutsideTemperatureRecord, InsideHumidityRecord, InsideTemperatureRecord].find {
            it.metric == row.metric
        }
        if (!type) {
            return null
        }
        type.getConstructor(Room, Long, Number).newInstance(Room.fromMap(row.tags), row.timestamp, row.value)
    }

    static String batchToRows(Collection<TSDBRecord> records) {
        log.info "all =${records.size()}"
        def valid = records.findAll { it.valid }
        log.info "valid =${valid.size()}"
        valid.sort { it.timestamp }.collect { it.toRow() }.join('\n')
    }

    static String batchToJson(List<TSDBRecord> records) {
        def join = records.findAll { it.valid }.collect { it.toJson() }.join(',')
        "[$join\n]"
    }

}
