package no.uis.seeds.timeseriesdb;

import org.junit.Test;

class QueryTest {

    @Test
    void testToOpenTSDBFormat() {
        def start = 61342354800000l
        def end = 61347625200000l
        assert [start: 61342354800000, m: 'avg:seeds.temp.in{}'] == new Query(start: start).toOpenTSDBFormat()
        assert [start: 61342354800000, end: 61347625200000l, m: 'avg:seeds.temp.in{}'] == new Query(start: start, end: end).toOpenTSDBFormat()
        assert [start: 61342354800000, end: 61347625200000l, m: 'avg:seeds.temp.in{ss=tt,pp=kk}'] == new Query(start: start, end: end, tags: [ss: 'tt', pp: 'kk']).toOpenTSDBFormat()
    }

    @Test
    void testToKairosDBFormat() {
        def start = 61342354800000l
        def end = 61347625200000l
        println new Query(start: start).toKairosDBFormat()
        println new Query(start: start, end: end).toKairosDBFormat()
        println new Query(start: start, end: end, tags: [ss: 'tt', pp: 'kk']).toKairosDBFormat()
    }
}