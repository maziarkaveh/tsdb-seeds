package no.uis.seeds.spark

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaDoubleRDD
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.Function
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import scala.Tuple2

class LinearRegression {
    static <T> T toType(Class<T> aClass = Function, Closure closure) {
        [call: closure].asType(aClass)
    }

    static void main(String[] args) {
        def conf = new SparkConf().setMaster("local").setAppName("Linear Regression Example")
        def sc = new JavaSparkContext(conf)
        def path = "data/lr.csv"
        def data = sc.textFile(path)
        def parsedData = data.map(toType { String line ->
            def parts = line.split(',').collect { it as double }
            new LabeledPoint(parts.find(), Vectors.dense(parts[1..3] as double[]))
        })
        parsedData.cache()
        int numIterations = 9
        def model = LinearRegressionWithSGD.train(JavaRDD.toRDD(parsedData), numIterations)
        def valuesAndPreds = parsedData.map(
                toType { LabeledPoint point ->
                    double prediction = model.predict(point.features())
                    new Tuple2(prediction, point.label())
                })
        def rdd = valuesAndPreds.map(
                toType { Tuple2<Double, Double> pair ->
                    Math.pow(pair._1() - pair._2(), 2.0)
                }).rdd()
        def MSE = new JavaDoubleRDD(rdd)
        println("Training Mean Squared Error =  ${MSE.mean()}")

    }
}

