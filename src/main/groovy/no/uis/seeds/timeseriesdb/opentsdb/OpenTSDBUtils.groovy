package no.uis.seeds.timeseriesdb.opentsdb

import no.uis.seeds.model.TSDBRecord

class OpenTSDBUtils {
    static writeToFileForImport(String path, Collection<TSDBRecord> data) {
        new File(path).text = TSDBRecord.batchToRows(data)
    }

}
