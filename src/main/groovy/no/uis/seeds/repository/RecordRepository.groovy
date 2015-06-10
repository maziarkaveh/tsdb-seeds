package no.uis.seeds.repository

import no.uis.seeds.model.TSDBRecord

interface RecordRepository {
    public <T extends TSDBRecord> List<T> fetchAll(Class<T> type)
    void forEach(Class<? extends TSDBRecord> type, Closure closure)
}
