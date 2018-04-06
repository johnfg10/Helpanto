package io.github.johnfg10.helpers

import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchService

/**
 * creates a file watcher from the path
 */
public fun Path.watch() : WatchService {
    //Create a watch service
    val watchService = this.fileSystem.newWatchService()

    //Register the service, specifying which events to watch
    register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW,
            StandardWatchEventKinds.ENTRY_DELETE)

    //Return the watch service
    return watchService
}