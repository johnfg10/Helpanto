package io.github.johnfg10.groovy

import groovy.lang.Binding
import groovy.util.GroovyScriptEngine
import kotlinx.coroutines.experimental.launch
import java.io.File
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchService

//ajudant
class GroovyScriptManager(val scriptPath: String) : AutoCloseable {

    val binding = Binding()

    val engine = GroovyScriptEngine(scriptPath)

    init {

    }

    fun eval(string: String){

    }

    fun fileWatcher(){
        val patha = Paths.get(URI(scriptPath))
        val watcherService = patha.fileSystem.newWatchService()
        patha.register(watcherService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE)

        launch {
            val key = watcherService.take()

            key.pollEvents().forEach {
                val path = it.context() as String
                when(it.kind().type()){
                    StandardWatchEventKinds.ENTRY_CREATE -> {

                    }
                }
            }

            key.reset()
        }
    }

    //acts as a shutdown hook
    override fun close() {
    }
}

