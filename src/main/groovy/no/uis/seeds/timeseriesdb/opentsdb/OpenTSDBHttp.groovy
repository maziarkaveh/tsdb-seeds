package no.uis.seeds.timeseriesdb.opentsdb

import groovy.transform.Memoized
import groovy.util.logging.Slf4j
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import no.uis.seeds.model.InsideTemperatureRecord
import no.uis.seeds.model.TSDBRecord
import no.uis.seeds.timeseriesdb.Query
import no.uis.seeds.timeseriesdb.TimeSeriesDBHttpApi

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST

@Slf4j
class OpenTSDBHttp implements TimeSeriesDBHttpApi {
    final String url

    private OpenTSDBHttp(String url) {
        this.url = url
    }

    @Memoized
    static OpenTSDBHttp getInstance(String url = 'http://localhost:8073') {
        new OpenTSDBHttp(url)
    }

    void put(String jsonBody) {
        new HTTPBuilder("$url/api/put").request(POST, JSON) { request ->
            body = jsonBody
            response.success = { HttpResponseDecorator response ->

            }
        }
    }

    void put(TSDBRecord record) {
        new HTTPBuilder("$url/api/put").request(POST, JSON) { request ->
            body = record.toJson()
            response.success = { HttpResponseDecorator response ->
                log.info record.formattedDate
            }

        }
    }

    @Override
    def query(Query query) {
        new HTTPBuilder(url).get(path: '/api/query', query: query.toOpenTSDBFormat()).find()?.dps
    }
}
