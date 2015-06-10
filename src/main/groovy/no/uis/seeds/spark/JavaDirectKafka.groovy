/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the 'License') you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.uis.seeds.spark

import kafka.serializer.StringDecoder
import no.uis.seeds.model.*
import org.apache.spark.SparkConf
import org.apache.spark.api.java.function.Function
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.api.java.JavaDStream
import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.apache.spark.streaming.kafka.KafkaUtils

class JavaDirectKafka {

    static <T> T toType(Class<T> aClass = Function, Closure closure) {
        [call: closure].asType(aClass)
    }

    static void main(String[] args) {

        String brokers = 'localhost:9092'

        def sparkConf = new SparkConf().setMaster('local').setAppName('JavaDirectKafka')
        def jssc = new JavaStreamingContext(sparkConf, Durations.seconds(10))

        def topicsSet = [OutsideHumidityRecord, OutsideTemperatureRecord, InsideHumidityRecord, InsideTemperatureRecord].collect {
            it.metric
        } as Set
        def kafkaParams = ['metadata.broker.list': brokers]

        def messages = KafkaUtils.createDirectStream(
                jssc, String, String, StringDecoder, StringDecoder, kafkaParams, topicsSet
        )
        JavaDStream<? extends TSDBRecord> records = messages.map(toType { tuple2 ->
            TSDBRecord.fromJson(tuple2._2())
        })

        def inTemp = records.filter(toType { TSDBRecord record -> record.metric == InsideTemperatureRecord.metric })
        def ouTemp = records.filter(toType { TSDBRecord record -> record.metric == OutsideTemperatureRecord.metric })
        def inHum = records.filter(toType { TSDBRecord record -> record.metric == InsideHumidityRecord.metric })
        def ouHum = records.filter(toType { TSDBRecord record -> record.metric == OutsideHumidityRecord.metric })

        inTemp.print()
        ouTemp.print()
        inHum.print()
        ouHum.print()

        jssc.start()
        jssc.awaitTermination()
    }
}