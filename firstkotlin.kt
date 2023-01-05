import kotlin.math.ln
import kotlin.math.max

fun main(){
    var firstReady = 0.0
    val queue = genQueue()
    var beginExe = mutableListOf<Double>()
    var endExe = mutableListOf<Double>()
    var firstReadyList = mutableListOf<Double>()
    var wait = mutableListOf<Double>()

    for(proc in queue){
        firstReady += proc.x1
        firstReadyList.add(firstReady)
    }

    beginExe.add(queue[0].x1)
    endExe.add(queue[0].x1 + queue[0].x0)

    for(i in 1..11){
        beginExe.add(max(firstReadyList[i], endExe[i-1]))
        endExe.add(beginExe[i] + queue[i].x0)
    }
    
    for(i in 0..11){
        wait.add(beginExe[i] - firstReadyList[i])
        println("Process $i is first ready at: ")
        println(firstReadyList[i])
        println("Execution begins at: ")
        println(beginExe[i])
        println("Execution ends at: ")
        println(endExe[i])
    }
    
    println("Minimum wait: ")
    println(wait.minOrNull())
    println("Maximum wait: ")
    println(wait.maxOrNull())
    println("Average wait: ")
    println(wait.average())

}

data class Process(val x0: Double, val x1: Double)

fun genQueue(): List<Process>{
    val dataList = genExpDistList()
    val elementInit: (Int) -> Process = { _ : Int -> Process(dataList.random(), dataList.random())}
    val queue = List<Process>(12, elementInit)
    return queue
}

fun genExpDistList(): List<Double>{
    val specMean = 3.0
    val rng = kotlin.random.Random(System.nanoTime())
    val makeExpRNG =
        { mean:Double -> { _ :Int -> -mean * ln(rng.nextDouble()) }}
    val expRNG: (Int) -> Double = makeExpRNG( specMean )
    val dataList = List<Double>(1024, expRNG )
    return dataList
}



