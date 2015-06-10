package no.uis.seeds.model

import groovy.transform.Memoized

enum Room {
    E_101(tempId: 27, humId: 28, room: 'KE-E101', floor: 1, locationType: LocationType.AUDITORIUM),
    E_102(tempId: 33, humId: 34, room: 'KE-E102', floor: 1, locationType: LocationType.AUDITORIUM),
    E_162(tempId: 39, humId: 40, room: 'KE-E162', floor: 1, locationType: LocationType.CLASSROOM),
    E_164(tempId: 45, humId: 46, room: 'KE-E164', floor: 1, locationType: LocationType.AUDITORIUM),
    E_166(tempId: 51, humId: 52, room: 'KE-E166', floor: 1, locationType: LocationType.AUDITORIUM),
    E_260(tempId: 74, humId: 75, room: 'KE-E260', floor: 2, locationType: LocationType.CORRIDOR),
    E_260B(tempId: 69, humId: 70, room: 'KE-E260B', floor: 2, locationType: LocationType.CORRIDOR),
    E_262(tempId: 57, humId: 58, room: 'KE-E262', floor: 2, locationType: LocationType.CLASSROOM),
    E_264(tempId: 63, humId: 64, room: 'KE-E264', floor: 2, locationType: LocationType.CLASSROOM),
    E_270(tempId: 109, humId: 110, room: 'KE-E270', floor: 2, locationType: LocationType.CLASSROOM),
    E_272A(tempId: 119, humId: 120, room: 'KE-E272A', floor: 2, locationType: LocationType.CLASSROOM),
    E_362(tempId: 129, humId: 130, room: 'KE-E362', floor: 3, locationType: LocationType.OFFICE),
    E_364(tempId: 139, humId: 140, room: 'KE-E364', floor: 3, locationType: LocationType.OFFICE),
    E_374(tempId: 179, humId: 180, room: 'KE-E374', floor: 3, locationType: LocationType.CONFERENCE_ROOM),
    E_423A(tempId: 259, humId: 260, room: 'KE-E423A', floor: 4, locationType: LocationType.OFFICE),
    E_423B(tempId: 264, humId: 265, room: 'KE-E423B', floor: 4, locationType: LocationType.OFFICE),
    E_425A(tempId: 289, humId: 290, room: 'KE-E425A', floor: 4, locationType: LocationType.OFFICE),
    E_425B(tempId: 294, humId: 295, room: 'KE-E425B', floor: 4, locationType: LocationType.OFFICE),
    E_427(tempId: 304, humId: 305, room: 'KE-E427', floor: 4, locationType: LocationType.OFFICE),
    E_429(tempId: 314, humId: 315, room: 'KE-E429', floor: 4, locationType: LocationType.CONFERENCE_ROOM),
    E_460(tempId: 369, humId: 370, room: 'KE-E460', floor: 4, locationType: LocationType.CORRIDOR),
    E_460A(tempId: 399, humId: 400, room: 'KE-E460A', floor: 4, locationType: LocationType.CORRIDOR),
    E_462(tempId: 374, humId: 375, room: 'KE-E462', floor: 4, locationType: LocationType.CLASSROOM),
    E_462B(tempId: 379, humId: 380, room: 'KE-E462B', floor: 4, locationType: LocationType.CLASSROOM),
    E_464(tempId: 384, humId: 385, room: 'KE-E464', floor: 4, locationType: LocationType.CLASSROOM),
    E_466(tempId: 389, humId: 390, room: 'KE-E466', floor: 4, locationType: LocationType.CLASSROOM),
    E_468(tempId: 404, humId: 405, room: 'KE-E468', floor: 4, locationType: LocationType.CLASSROOM),
    E_470(tempId: 409, humId: 410, room: 'KE-E470', floor: 4, locationType: LocationType.CLASSROOM),
    E_472(tempId: 424, humId: 425, room: 'KE-E472', floor: 4, locationType: LocationType.CLASSROOM),
    OUTDOOR(tempId: 460, humId: 461, room: 'ROOF', floor: 6, locationType: LocationType.ROOF)
    Integer      tempId
    Integer      humId
    String       room
    Integer      floor
    LocationType locationType

    @Memoized
    static findByTemperatureIdentify(Integer identity) {
        values().find { it.tempId == identity }
    }

    @Memoized
    static findByHumidityIdentify(Integer identity) {
        values().find { it.humId == identity }
    }

    Map toMap() {
        [room: room, floor: floor, locationType: LocationType.locationType.name()]
    }

    @Memoized
    static Room fromMap(Map map) {
        values().find {
            it.room == map.room && it.floor == map.floor as int && it.locationType.name().toLowerCase() == map.locationType
        }
    }
}