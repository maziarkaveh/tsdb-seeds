package no.uis.seeds.timeseriesdb

import no.uis.seeds.model.InsideTemperatureRecord
import no.uis.seeds.model.TSDBRecord

interface TimeSeriesDBHttpApi {
    void put(TSDBRecord tsdbRecord)
    def query(Query query)
}