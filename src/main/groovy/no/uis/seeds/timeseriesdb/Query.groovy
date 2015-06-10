package no.uis.seeds.timeseriesdb

class Query {
    Long                start
    Long                end
    String              aggregate = 'avg'
    String              metric
    Map<String, String> tags      = [:]


    Map toOpenTSDBFormat() {
        assert start
        assert aggregate
        assert metric
        def tags = tags.collect { "$it.key=$it.value" }.join(',')
        def subQuery = "$aggregate:10m-$aggregate:$metric{$tags}"
        [start: start, end: end, m: subQuery].findAll { it.value }
    }

}
